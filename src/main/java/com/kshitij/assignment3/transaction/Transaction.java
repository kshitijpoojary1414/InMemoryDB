package com.kshitij.assignment3.transaction;

import com.kshitij.assignment3.database.array.Array;
import com.kshitij.assignment3.database.dbobject.CustomObject;
import com.kshitij.assignment3.database.IDatabase;
import com.kshitij.assignment3.database.Database;
import com.kshitij.assignment3.exception.IncompatibleType;
import com.kshitij.assignment3.exception.KeyNotFoundException;

public class Transaction implements IDatabase {

    private Database db;
    public Transaction(Database db) {
        this.db = db;
    }
    public boolean put(String key, Object value) {
        if(this.db.contains(key)) {
            return false;
        }

        this.db.put(key,value);
        return true;
    }

    public int getInt(String key) {
        if(!db.contains(key)) {
            throw new KeyNotFoundException("Key does not exist in the database");
        }

        Object value = db.get(key);
        if (!(value instanceof Integer)) {
            throw new IncompatibleType("The key does not contain an Int value");
        }

        return (int)db.get(key);
    }

    public String getString(String key) {
        if(!db.contains(key)) {
            throw new KeyNotFoundException("Key does not exist in the database");
        }

        Object value = db.get(key);
        if (!(value instanceof String)) {
            throw new IncompatibleType("The key does not contain an Int value");
        }

        return (String)db.get(key);
    }

    public Array getArray(String key) {
        if(!db.contains(key)) {
            throw new KeyNotFoundException("Key does not exist in the database");
        }

        Object value = db.get(key);
        if (!(value instanceof Array)) {
            throw new IncompatibleType("The key does not contain an Int value");
        }

        return (Array) db.get(key);
    }

    public CustomObject getObject(String key) {
        if(!db.contains(key)) {
            throw new KeyNotFoundException("Key does not exist in the database");
        }

        Object value = db.get(key);
        if (!(value instanceof CustomObject)) {
            throw new IncompatibleType("The key does not contain an Int value");
        }

        return (CustomObject) db.get(key);
    }


    public Object get(String key) {
        if(!db.contains(key)) {
            throw new KeyNotFoundException("Key does not exist in the database");
        }

        return db.get(key);
    }

    public Object remove(String key) {
        if(db.contains(key)) {
            return null;
        }

        return db.remove(key);
    }

    public boolean abort() {
        return true;
    }

    public boolean commit() {
        return true;
    }


}
