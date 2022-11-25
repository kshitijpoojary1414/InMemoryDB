package com.kshitij.assignment3.command.DBOperations;

import com.kshitij.assignment3.command.IDatabaseOperation;
import com.kshitij.assignment3.database.Database;

import java.io.Serializable;

public class PutOperationDatabase implements IDatabaseOperation, Serializable {

    private final Object object;
    private Database db;
    private String key;

    public PutOperationDatabase(String key, Object object) {
        this.key = key;
        this.object = object;
    }

    @Override
    public Object execute(Object db) {
        this.db = (Database) db;
        return this.db.put(this.key, this.object);
    }

    public Object undoOperation() {
        return this.db.remove(this.key);
    }

    public String toString() {
        return this.operationToString("PUT", this.db, this.object, this.key);
    }

}
