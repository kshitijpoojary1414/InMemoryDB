package com.kshitij.assignment3.database;

import com.kshitij.assignment3.database.array.Array;
import com.kshitij.assignment3.database.dbobject.CustomObject;
import com.kshitij.assignment3.exception.IncompatibleType;
import com.kshitij.assignment3.exception.KeyNotFoundException;

import java.io.Serializable;
import java.util.HashMap;

public class Database implements IDatabase, Serializable {
    private HashMap<String, Object> db;

    public Database() {
        db = new HashMap<>();
    }

    public boolean put(String key, Object value) {
        if(db.containsKey(key)) {
            return false;
        }
        setParentForNestedValue(key,value);
        db.put(key,value);
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
        if(db.containsKey(key)) {
            return null;
        }

        return db.remove(key);
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
        } else {
            ((CustomObject)value).setParentForNestedValue(parent);
        }
    }

}
