package com.kshitij.assignment3.database;

import com.kshitij.assignment3.cursor.Cursor;
import com.kshitij.assignment3.cursor.CursorMapper;
import com.kshitij.assignment3.database.array.Array;
import com.kshitij.assignment3.database.dbobject.CustomObject;
import com.kshitij.assignment3.decorator.DatabaseExecutor;
import com.kshitij.assignment3.exception.IncompatibleType;
import com.kshitij.assignment3.exception.KeyNotFoundException;
import com.kshitij.assignment3.transaction.Transaction;

import javax.xml.crypto.Data;
import java.io.File;
import java.io.Serializable;
import java.util.HashMap;

public class Database implements IDatabase, Serializable {
    private Storage db;

    private CursorMapper mapper = CursorMapper.CursorMapper();

    private long TIME_SECONDS_TO_SNAPSHOT = 10;
    private final int COMMAND_LIMIT = 10;
    private int CURRENT_COMMAND_COUNT = 10;

    long END_TIME = System.currentTimeMillis() + TIME_SECONDS_TO_SNAPSHOT * 1000;
    public Database() {
        db = new Storage( new DatabaseExecutor(this));
        db.recover();
//        CURRENT_COMMAND_COUNT = db.size();
    }

    public Database(File mementoFile, File commandFile) {
        db = new Storage( new DatabaseExecutor(this,commandFile));
        db.recover(mementoFile,commandFile);
//        CURRENT_COMMAND_COUNT = db.size();

    }

    public String toString() {
        return db.toString();
    }


    public boolean put(String key, Object value) {
        if(db.containsKey(key)) {
            return false;
        }
        setParentForNestedValue(key,value);
        db.put(key,value);
        checkBackup();
        this.mapper.notifyCursor(key);
        return true;
    }

    public int getInt(String key) {
        if(!db.containsKey(key)) {
            throw new KeyNotFoundException("Key does not exist in the database");
        }

        Object value = db.get(key);
        if (!(value instanceof Integer)) {
            throw new IncompatibleType("The key does not contain an Int value");
        }

        return (int)db.get(key);
    }

    public String getString(String key) {
        if(!db.containsKey(key)) {
            throw new KeyNotFoundException("Key does not exist in the database");
        }

        Object value = db.get(key);
        if (!(value instanceof String)) {
            throw new IncompatibleType("The key does not contain an Int value");
        }

        return (String)db.get(key);
    }

    public Array getArray(String key) {
        if(!db.containsKey(key)) {
            throw new KeyNotFoundException("Key does not exist in the database");
        }

        Object value = db.get(key);
        if (!(value instanceof Array)) {
            throw new IncompatibleType("The key does not contain an Int value");
        }

        return (Array) db.get(key);
    }

    public CustomObject getObject(String key) {
        if(!db.containsKey(key)) {
            throw new KeyNotFoundException("Key does not exist in the database");
        }

        Object value = db.get(key);
        if (!(value instanceof CustomObject)) {
            throw new IncompatibleType("The key does not contain an Int value");
        }

        return (CustomObject) db.get(key);
    }


    public Object get(String key) {
        if(!db.containsKey(key)) {
            throw new KeyNotFoundException("Key does not exist in the database");
        }

        return db.get(key);
    }

    public Object remove(String key) {
        if(!db.containsKey(key)) {
            return null;
        }
        Object removedObject = db.remove(key);
        System.out.println(removedObject);
        System.out.println("Object removed");
        this.mapper.notifyCursor(key);
        checkBackup();
        return removedObject;
    }

    public int length() {
        return this.db.size();
    }

    public boolean contains(String key) {
        return this.db.containsKey(key);
    }

    public void setParentForNestedValue(String parent, Object value) {
        if(value instanceof Array) {
            ((Array)value).setParentForNestedValue(parent);
        } else if(value instanceof CustomObject){
            ((CustomObject)value).setParentForNestedValue(parent);
        }
    }

    public Cursor getCursor(String key) {
        Cursor cursor = null;
        try {
            cursor = new Cursor(key, this);
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        return cursor;
    }

    public Transaction transaction() {
        return new Transaction(this);
    }

    public void checkBackup() {
//        CURRENT_COMMAND_COUNT++;
//        System.out.println(CURRENT_COMMAND_COUNT);
//        if(CURRENT_COMMAND_COUNT >= COMMAND_LIMIT) {
//            db.snapshot();
//        }

        if(System.currentTimeMillis() > END_TIME){
            db.snapshot();
            END_TIME = System.currentTimeMillis() + TIME_SECONDS_TO_SNAPSHOT * 1000;
        }
    }

    public void snapshot() {
        db.snapshot();
    }

}
