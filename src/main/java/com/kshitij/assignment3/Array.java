package com.kshitij.assignment3;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kshitij.assignment3.exception.IncompatibleType;
import com.kshitij.assignment3.exception.KeyNotFoundException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Array {
    private List<Object> value = new ArrayList<>();

    public Array() {

    }

    public Array(List list) {
        value = list;
    }

    public boolean put(Object object) {
        value.add(object);
        return true;
    }

    public Object get(int index) {
        if (index>= this.value.size()) {
            throw new KeyNotFoundException("Index Out Of Bounds");
        }
        return this.value.get(index);
    }

    public Integer getInt(int index) {
        if (index>= this.value.size()) {
            throw new KeyNotFoundException("Index Out Of Bounds");
        }

        if (!(this.value.get(index) instanceof Integer) ) {
            throw new IncompatibleType("The value is associated with this index is not an Integer");
        }
        return (Integer)this.value.get(index);
    }

    public Array getArray(int index) {
        if (index>= this.value.size()) {
            throw new KeyNotFoundException("Index Out Of Bounds");
        }

        if (!(this.value.get(index) instanceof ArrayList) ) {
            throw new IncompatibleType("The value is associated with this index is not an Integer");
        }
        return new Array((ArrayList)this.value.get(index));

    }

    public Object getObject(int index) {
        if (index>= this.value.size()) {
            throw new KeyNotFoundException("Index Out Of Bounds");
        }

        if (!(this.value.get(index) instanceof HashMap) ) {
            throw new IncompatibleType("The value is associated with this index is not an Integer");
        }

        return this.value.get(index);
    }

    public int length() {
        return value.size();
    }

    public Object remove(int index) {
        if(index< this.value.size()-1) {
            return this.value.remove(index);
        }
        return null;
    }
    public Array fromString(String value) {
        Gson gson = new Gson();
        String s = "['1',2]";
        Type userListType = new TypeToken<ArrayList<Object>>(){}.getType();
        CustomObject object = gson.fromJson(s, CustomObject.class);
        System.out.println(object.toString());
//        this.value = object;
//        for (Object content : object) {
//            if (content instanceof Array) {
//                put(new Array((ArrayList)content));
//                continue;
//            }
//            put(content);
//        }
        return this;
    }

    public static void main(){

    }
}
