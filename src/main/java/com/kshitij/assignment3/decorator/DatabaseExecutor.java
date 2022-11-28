package com.kshitij.assignment3.decorator;

import com.kshitij.assignment3.command.DBOperations.PutOperationDatabase;
import com.kshitij.assignment3.command.DBOperations.RemoveOperationDatabase;
import com.kshitij.assignment3.command.IDatabaseOperation;
import com.kshitij.assignment3.cursor.Cursor;
import com.kshitij.assignment3.database.*;
import com.kshitij.assignment3.database.array.Array;
import com.kshitij.assignment3.database.array.IArray;
import com.kshitij.assignment3.database.dbobject.CustomObject;
import com.kshitij.assignment3.fileio.FileOperations;
import com.kshitij.assignment3.transaction.Transaction;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class DatabaseExecutor extends Executor implements IDatabase {

    private Database db;
    private Stack<IDatabaseOperation> stack ;

    private File commandFile;
    public DatabaseExecutor(Database db) {
        super(true);
        this.db = db;
        this.isSavedOperation = false;
    }

    public DatabaseExecutor(Database db, File commandFile) {
        super(true);
        this.db = db;
        this.isSavedOperation = false;
        this.commandFile = commandFile;
    }

    public DatabaseExecutor(Database db, Stack<IDatabaseOperation> operations) {
        super(true);
        this.db = db;
        this.stack = operations;
        this.isSavedOperation = false;
    }



    public void executeSavedOperations(File commandFile) {
        List<List<String>> operations;

        if (commandFile == null) {
            operations = retrieveOperations(new File("commands.txt"));
        } else {
            operations = retrieveOperations(commandFile);
        }
        System.out.println("Retrieved operations");
        System.out.println(operations);
        for (List<String> operation : operations) {
            executeOperation(operation);
        }
    }

    public void executeOperation(List<String> operations) {
        String operation = operations.get(1) + " " +operations.get(0);
        String value = operations.get(2);
        String key = operations.get(3);
        List<String> listOfKeys = getKeys(key);

        switch(operation) {
            case "PUT DB" :
                put(key,parseValue(value));
                break;
            case "REMOVE DB" :
                remove(key);
                break;
            case "PUT ARRAY":
                insertValueIntoNestedKeys(listOfKeys,value);
                break;
            case "REMOVE ARRAY":
                removeValueFromNestedKeys(listOfKeys);
                break;
            case "PUT CustomObject":
                insertValueIntoNestedKeys(listOfKeys,value);
                break;
            case "REMOVE CustomObject":
                removeValueFromNestedKeys(listOfKeys);
                break;
        }
    }

    public List<String> getKeys(String key) {
        String[] keysss = key.split("\\.");
        return Arrays.stream(keysss).collect(Collectors.toList());
    }

    public void removeValueFromNestedKeys(List<String> listOfKeys) {
        Object object = get(listOfKeys.get(0));
        listOfKeys.remove(0);
        int listSize = listOfKeys.size()-1;
        int currentIndex = 0;

        for (String key : listOfKeys) {
            if( key.contains("I")) {
                int index = Integer.parseInt(key.substring(1,key.length()));
                if (currentIndex == listSize) {
                    object = (new ArrayExecuter((Array) object)).remove(index);
                } else {
                    object = ((Array)(object)).get(index);
                }
            } else {
                if (currentIndex == listSize) {
                    object = (new DBObjectExecutor((CustomObject) object)).remove(key);
                } else {
                    object = ((CustomObject)object).get(key);

                }
            }
            currentIndex++;
        }

    }

    public void insertValueIntoNestedKeys(List<String> listOfKeys, String value) {
        Object object = get(listOfKeys.get(0));
        listOfKeys.remove(0);
        int listSize = listOfKeys.size()-1;
        int currentIndex = 0;

        for (String key : listOfKeys) {
            if( key.contains("*index*")) {
                int index = Integer.parseInt(key.substring(7,key.length()));
                if (currentIndex == listSize) {
                    object = (new ArrayExecuter((Array) object)).put(parseValue(value));
                } else {
                    object = ((Array)(object)).get(index);
                }
            } else {
                if (currentIndex == listSize) {
                    object = (new DBObjectExecutor((CustomObject) object)).put(key,parseValue(value));
                } else {
                    object = ((CustomObject)object).get(key);

                }
            }
            currentIndex++;
        }

    }

    public Object parseValue(String value) {

        if (value.charAt(0) == '[') {
            return new Array().fromString(value);
        } else if (value.charAt(0) == '{') {
            return new CustomObject().fromString(value);
        } else if(value.charAt(0) == '('){
            String intValue = value.substring(1,value.length()-1);
            return Integer.parseInt(intValue);
        } else {
            return value;
        }

    }

    public String toString() {
        return this.db.toString();
    }
    public boolean put(String key, Object value) {
        IDatabaseOperation put = new PutOperationDatabase(key,value);

        put.execute(this.db);
        System.out.println("Stack");
        System.out.println(stack);
        if (stack == null) {
            writeToFile(put.toString());
        } else {
            this.stack.add(put);

        }

        return true;
    }

    public Object get(String key) {
        return this.db.get(key);
    }

    public int getInt(String key) {
        return this.db.getInt(key);
    }

    public String getString(String key){
        return this.db.getString(key);
    }

    public IArray getArray(String key) {
        return new ArrayExecuter(this.db.getArray(key));
    }

    public CustomObject getObject(String key){
        return this.db.getObject(key);
    }

    public Object remove(String key) {
        IDatabaseOperation remove = new RemoveOperationDatabase(key);

        Object value = remove.execute(this.db);

        if (stack == null) {
            writeToFile(remove.toString());
        } else {
            this.stack.add(remove);

        }
        return value;
    }

    public Cursor getCursor(String key) {
        return this.db.getCursor(key);
    }

    public Stack<IDatabaseOperation> getCommands() {
        return stack;
    }
    public boolean clearCommands() {
        this.stack = new Stack<>();
        return true;
    }

    public Transaction transaction() {
        return this.db.transaction();
    }

    public void commitCommands() {
        System.out.println(stack);
        this.stack.forEach((operation) -> {
            writeToFile(operation.toString());
        });
        snapshot();
    }

    public void snapshot() {
        db.snapshot();
    }

}
