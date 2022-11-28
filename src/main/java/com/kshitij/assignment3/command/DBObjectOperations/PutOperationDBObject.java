package com.kshitij.assignment3.command.DBObjectOperations;

import com.kshitij.assignment3.command.IDatabaseOperation;
import com.kshitij.assignment3.database.dbobject.CustomObject;

import java.io.Serializable;

public class PutOperationDBObject implements IDatabaseOperation, Serializable {
    private Object value;
    private CustomObject customObject;
    private String key;

    public PutOperationDBObject(String key,Object value) {
        this.key = key;
        this.value = value;
    }

    public Object execute(Object object) {
        this.customObject = (CustomObject) object;
        return this.customObject.put(this.key, this.value);
    }
    public Object undo() {
        return this.customObject.remove(this.key);
    }

    public String toString() {
        return this.operationToString("PUT", this.customObject, this.value);
    }
}
