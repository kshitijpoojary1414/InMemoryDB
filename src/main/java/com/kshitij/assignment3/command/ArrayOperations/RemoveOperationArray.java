package com.kshitij.assignment3.command.ArrayOperations;

import com.kshitij.assignment3.command.IDatabaseOperation;
import com.kshitij.assignment3.database.array.Array;

import java.io.Serializable;

public class RemoveOperationArray implements IDatabaseOperation, Serializable {
    private int index;
    private Array array;
    private Object removedValue;

    public RemoveOperationArray(int index) {
        this.index = index;
    }

    public Object execute(Object array) {
        this.array = (Array) array;
        this.removedValue =  this.array.remove(this.index);
        return this.removedValue;
    }

    public Object undo() {
        return this.array.put(this.removedValue);
    }

    public String toString() {
        return this.operationToString("REMOVE", this.array, this.removedValue);
    }
}
