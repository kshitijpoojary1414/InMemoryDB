package com.kshitij.assignment3.transaction;

import com.kshitij.assignment3.command.IDatabaseOperation;
import com.kshitij.assignment3.database.array.Array;
import com.kshitij.assignment3.database.dbobject.CustomObject;
import com.kshitij.assignment3.database.IDatabase;
import com.kshitij.assignment3.database.Database;
import com.kshitij.assignment3.decorator.DatabaseExecutor;

import java.util.Stack;

public class Transaction implements IDatabase {

    private Database db;
    private DatabaseExecutor databaseExecutor;

    private Boolean isActive = true;
    Stack<IDatabaseOperation> operations = new Stack<>();
    public Transaction(Database db){
        this.db = db;
        System.out.println("Current db");
        System.out.println(this.db.toString());
        databaseExecutor = new DatabaseExecutor(this.db,operations);
    }
    public boolean put(String key, Object value) {
        return databaseExecutor.put(key,value);
    }

    public int getInt(String key) {
        return this.db.getInt(key);
    }

    public String getString(String key) {
        return this.db.getString(key);
    }

    public Array getArray(String key) {
        return this.db.getArray(key);
    }

    public CustomObject getObject(String key) {
        return this.db.getObject(key);
    }


    public Object get(String key) {
        return this.db.get(key);
    }

    public Object remove(String key) {
        return this.databaseExecutor.remove(key);
    }

    public boolean abort() {
        this.operations = this.databaseExecutor.getCommands();
        System.out.println("Command lit");
        System.out.println(this.operations);
        while(!this.operations.isEmpty()) {
            IDatabaseOperation operation = this.operations.pop();
            System.out.println(operation);
            operation.undo();
        }
        this.isActive = false;
        System.out.println("Herre");

        System.out.println(databaseExecutor.toString());
        databaseExecutor.snapshot();
        return true;
    }

    public boolean commit() {
        databaseExecutor.commitCommands();
        this.isActive = false;
        return true;
    }


}
