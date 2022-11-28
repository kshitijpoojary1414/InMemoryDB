package com.kshitij.assignment3.cursor;

import com.kshitij.assignment3.database.Database;
import com.kshitij.assignment3.database.array.Array;
import com.kshitij.assignment3.database.dbobject.CustomObject;
import com.kshitij.assignment3.exception.IncompatibleType;
import com.kshitij.assignment3.exception.KeyNotFoundException;
import com.kshitij.assignment3.observer.IObserver;
import com.kshitij.assignment3.observer.Observer;

import java.util.ArrayList;
import java.util.List;

public class Cursor {
    private String key;
    private Database db;
    private Object currentValue;

    public List<IObserver> observers = new ArrayList<>();

    private CursorMapper mapper = CursorMapper.CursorMapper();
    public Cursor(String key, Database db) throws CloneNotSupportedException{

        this.db = db;
        this.key = key;
        this.currentValue = this.db.get(key);

        if (this.currentValue instanceof Array) {
            this.currentValue = ((Array)this.db.get(key)).clone();
        } else if(this.currentValue instanceof CustomObject) {
            this.currentValue = ((CustomObject)this.db.get(key)).clone();
        }

        mapper.put(key,this);
    }
    
    public boolean updateObserver() {
        String message = "";
        try {
            Object newValue = this.db.get(key);
            message = "The " + key + " in the database has been updated. Old Value :- " + this.currentValue.toString() + " New Value :- " + newValue.toString();

        } catch(KeyNotFoundException e) {
            message = "The " + key + " has been removed from the database";
        }


        String finalMessage = message;
        observers.forEach((o)->o.update(finalMessage));
        return true;
    }

    public boolean addObserver(IObserver observer) {
        observers.add(observer);

        return true;
    }

    public boolean removeObserver(Observer observer) {
        observers.remove(observer);
        return true;
    }

    public Object get() {
        return this.currentValue;
    }

    public Integer getInt() {
        if(!(this.currentValue instanceof Integer)) {
            throw new IncompatibleType("The current value is not an integer");
        }
        return (Integer) this.currentValue;
    }

    public String getString() {
        if(!(this.currentValue instanceof String)) {
            throw new IncompatibleType("The current value is not a string");
        }
        return (String) this.currentValue;
    }

    public Array getArray() {
        if(!(this.currentValue instanceof Array)) {
            throw new IncompatibleType("The current value is not an Array");
        }
        return (Array) this.currentValue;
    }

    public CustomObject getObject() {
        if(!(this.currentValue instanceof CustomObject)) {
            throw new IncompatibleType("The current value is not a custom object");
        }
        return (CustomObject) this.currentValue;
    }
}
