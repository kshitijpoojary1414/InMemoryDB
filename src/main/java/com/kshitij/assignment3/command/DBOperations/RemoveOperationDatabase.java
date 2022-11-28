package com.kshitij.assignment3.command.DBOperations;

import com.kshitij.assignment3.command.IDatabaseOperation;
import com.kshitij.assignment3.database.Database;

import java.io.Serializable;

public class RemoveOperationDatabase implements IDatabaseOperation, Serializable {
    private Database db;
    private String key;
    private Object removedValue;

    public RemoveOperationDatabase(String key) {
        this.key = key;
    }

    @Override
    public Object execute(Object db) {
        this.db = (Database) db;
        this.removedValue = this.db.remove(this.key);
        return this.removedValue;
    }

    public Object undo() {
        return this.db.put(this.key,this.removedValue);
    }

    public String toString() {
        return this.operationToString("REMOVE", this.db, this.removedValue, this.key);
    }
}
