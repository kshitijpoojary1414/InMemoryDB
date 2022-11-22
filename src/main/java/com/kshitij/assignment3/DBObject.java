package com.kshitij.assignment3;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kshitij.assignment3.exception.IncompatibleType;
import com.kshitij.assignment3.exception.KeyNotFoundException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class DBObject {
    private HashMap<String,Object> map = new HashMap<>();

    public DBObject() {

    }

    public DBObject(HashMap<String,Object> value) {
        this.map = value;
    }

    public boolean put(String key, Object value) {
        if (this.map.containsKey(key)) {
            throw new KeyNotFoundException("Key exixts");
        }

        this.map.put(key,value);
        return true;
    }

    public Object get(String key) {
        if (!this.map.containsKey(key)) {
            throw new KeyNotFoundException("Key not found");
        }

        return this.map.get(key);
    }

    public Integer getInt(String key) {
        if (!this.map.containsKey(key)) {
            throw new KeyNotFoundException("Key not found");
        }

        if (!(this.map.get(key) instanceof Integer) ) {
            throw new IncompatibleType("The value is associated with this index is not an Integer");
        }

        return (Integer)this.map.get(key);
    }

    public Array getArray(int index) {
        if (index>= this.map.size()) {
            throw new KeyNotFoundException("Index Out Of Bounds");
        }

        if (!(this.map.get(index) instanceof ArrayList) ) {
            throw new IncompatibleType("The value is associated with this index is not an Array");
        }
        return new Array((ArrayList)this.map.get(index));

    }

    public DBObject getDBObject(int index) {
        if (index>= this.map.size()) {
            throw new KeyNotFoundException("Index Out Of Bounds");
        }

        if (!(this.map.get(index) instanceof HashMap) ) {
            throw new IncompatibleType("The value is associated with this index is not an Array");
        }
        return new DBObject((HashMap)this.map.get(index));

    }

    public DBObject fromString(String value) {
        Gson gson = new Gson();
        Type userListType = new TypeToken<HashMap<String,Object>>(){}.getType();
        HashMap object = (HashMap)gson.fromJson(value, userListType);

        this.map = object;
//        for (Object content : object) {
//            if (content instanceof Array) {
//                put(new Array((ArrayList)content));
//                continue;
//            }
//            put(content);
//        }
        return this;
    }

}
