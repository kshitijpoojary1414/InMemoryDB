package com.kshitij.assignment3.database.array;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kshitij.assignment3.cursor.Cursor;
import com.kshitij.assignment3.cursor.CursorMapper;
import com.kshitij.assignment3.database.dbobject.CustomObject;
import com.kshitij.assignment3.database.dbobject.ICustomObject;
import com.kshitij.assignment3.exception.IncompatibleType;
import com.kshitij.assignment3.exception.KeyNotFoundException;

import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

public class Array implements IArray,Cloneable {
    private List<Object> value = new ArrayList<>();
    private Gson gson = new Gson();

    private CursorMapper mapper = CursorMapper.CursorMapper();
    private String parent = "";

    public Array() {

    }

    public Object clone() throws CloneNotSupportedException
    {
        return fromString(toString());
    }

    public Array(List list) {
        value = list;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public void setParentForNestedValue(String parent) {
        int index = 0;
        setParent(parent);
        this.value.forEach((v)->{
            if(v instanceof Array) {
                ((Array)v).setParentForNestedValue(parent+"."+"*index*" + index);
            } else if (v instanceof CustomObject) {
                ((CustomObject)v).setParentForNestedValue(parent+"."+"*index*"+ index
                );
            }
        });
    }

    public String getParent() {
        return this.parent;
    }

    public boolean put(Object object) {
        if (object instanceof Array) {
            ((Array)object).setParentForNestedValue(getParent()+".*index*"+((Array) object).length());
        } else if (object instanceof CustomObject) {
            ((CustomObject)object).setParentForNestedValue(getParent()+".*index*"+((CustomObject)object).length());
        }
        this.value.add(object);

        if(getParent().length()>0) {
            if (!(getParent().contains("\\."))) {
                this.mapper.notifyCursor(getParent());
            } else {
            String[] keys = getParent().split(".");
            System.out.println(getParent());
            this.mapper.notifyCursor(keys[0]);}
        }
        return true;
    }



    public Object get(int index) {
        if (index>= this.value.size()) {
            throw new KeyNotFoundException("Index Out Of Bounds");
        }
        return this.value.get(index);
    }

    public String getString(int index) {
        if (index>= this.value.size()) {
            throw new KeyNotFoundException("Index Out Of Bounds");
        }

        if (!(this.value.get(index) instanceof String) ) {
            throw new IncompatibleType("The value is associated with this index is not an Integer");
        }
        return (String)this.value.get(index);
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

    public IArray getArray(int index) {
        if (index>= this.value.size()) {
            throw new KeyNotFoundException("Index Out Of Bounds");
        }

            if (!(this.value.get(index) instanceof Array) ) {
            throw new IncompatibleType("The value is associated with this index is not an Array");
        }
        return (Array) this.value.get(index);

    }

    public ICustomObject getObject(int index) {
        if (index>= this.value.size()) {
            throw new KeyNotFoundException("Index Out Of Bounds");
        }

        if (!(this.value.get(index) instanceof CustomObject) ) {
            throw new IncompatibleType("The value is associated with this index is not an Integer");
        }

        return (CustomObject) this.value.get(index);
    }

    public int length() {
        return value.size();
    }

    public Object remove(int index) {
        if(index< this.value.size()-1) {
            return this.value.remove(index);
        }

        if(getParent().length()>0) {
            if (!(getParent().contains("\\."))) {
                this.mapper.notifyCursor(getParent());
            } else {
                String[] keys = getParent().split(".");
                System.out.println(getParent());
                this.mapper.notifyCursor(keys[0]);}
        }

        return null;
    }


    public ArrayList<Object> convertToArrayList(Array array) {
        ArrayList newArrayList = new ArrayList<>();

        array.value.forEach(v -> {
            if (v instanceof Array) {
                ArrayList a = this.convertToArrayList((Array)v);
                newArrayList.add(a);
            }else if (v instanceof CustomObject) {
                HashMap a = new CustomObject().convertToHashMap((CustomObject) v);
                newArrayList.add(a);
            } else {
                newArrayList.add(v);
            }
        });
        return newArrayList;
    }

    public String toString() {
        ArrayList arr = convertToArrayList(this);
        return gson.toJson(arr);
    }


    public Array fromString(String value) {
        Array newArray = new Array();
        Type expectedType = new TypeToken<ArrayList<Object>>(){}.getType();
        ArrayList object = gson.fromJson(value, expectedType);
        object.forEach((v)
                -> {
            if (v instanceof ArrayList) {
                Array a = this.fromString(v.toString());
                newArray.put(a);
            }else if (v instanceof Map) {
                CustomObject db = new CustomObject();
                CustomObject a = db.fromString(v.toString());
                newArray.put(a);
            } else {
                newArray.put(v);
            }
        });
        return newArray;
    }

}
