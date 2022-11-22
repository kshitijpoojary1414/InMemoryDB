package com.kshitij.assignment3;

import com.kshitij.assignment3.exception.IncompatibleType;
import com.kshitij.assignment3.exception.KeyNotFoundException;

import java.security.KeyException;
import java.util.HashMap;

public class Database {
    private HashMap<String, Object> db;

    public Database() {

    }

    public boolean put(String key, Object value) {
        if(db.containsKey(key)) {
            return false;
        }

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

    public Object getArray(String key) {
        if(!db.containsKey(key)) {
            throw new KeyNotFoundException("Key does not exist in the database");
        }

        Object value = db.get(key);
        if (!(value instanceof Array)) {
            throw new IncompatibleType("The key does not contain an Int value");
        }

        return db.get(key);
    }

    public Object getObject(String key) {
        if(!db.containsKey(key)) {
            throw new KeyNotFoundException("Key does not exist in the database");
        }

//        DBObject value = db.get(key);
//        if (!(value instanceof DBObject)) {
//            throw new IncompatibleType("The key does not contain an Int value");
//        }

        return db.get(key);
    }


    public Object get(String key) {
        if(!db.containsKey(key)) {
            throw new KeyNotFoundException("Key does not exist in the database");
        }

        return db.get(key);
    }

    public Object remove(String key, Object value) {
        if(db.containsKey(key)) {
            return null;
        }

        return db.remove(key,value);
    }

}
