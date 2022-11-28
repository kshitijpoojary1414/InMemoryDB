package com.kshitij.assignment3.command.DBObjectOperations;

import com.kshitij.assignment3.command.IDatabaseOperation;
import com.kshitij.assignment3.database.dbobject.CustomObject;

import java.io.Serializable;

public class RemoveOperationDBObject implements IDatabaseOperation, Serializable {
    private CustomObject customObject;
    private String key;
    private Object removedValue;

    public RemoveOperationDBObject(String key) {
        this.key = key;
    }

    public Object execute(Object object) {
        this.customObject = (CustomObject) object;
        return this.customObject.remove(this.key);
    }

    public Object undo() {
        return this.customObject.put(this.key, this.removedValue);
    }

    public String toString() {
        return this.operationToString("REMOVE", this.customObject, this.removedValue);
    }
}
