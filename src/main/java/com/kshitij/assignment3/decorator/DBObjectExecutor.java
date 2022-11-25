package com.kshitij.assignment3.decorator;

import com.kshitij.assignment3.command.DBObjectOperations.PutOperationDBObject;
import com.kshitij.assignment3.command.DBObjectOperations.RemoveOperationDBObject;
import com.kshitij.assignment3.command.IDatabaseOperation;
import com.kshitij.assignment3.database.array.Array;
import com.kshitij.assignment3.database.array.IArray;
import com.kshitij.assignment3.database.dbobject.CustomObject;
import com.kshitij.assignment3.database.dbobject.ICustomObject;
import com.kshitij.assignment3.fileio.FileOperations;

import java.io.IOException;

public class DBObjectExecutor extends Executor implements ICustomObject {
    private CustomObject db;
    private FileOperations fileOperation;

    public DBObjectExecutor(ICustomObject db) {
        super(false);
        this.db = (CustomObject) db;
        this.fileOperation = new FileOperations();

    }

    public boolean put(String key, Object value) {
        IDatabaseOperation put = new PutOperationDBObject(key,value);

        Boolean res = (boolean)put.execute(this.db);

        writeToFile(put.toString());
        return res;
    }

    public Object get(String key) {
        return this.db.get(key);
    }

    public int getInt(String key) {
        return this.db.getInt(key);
    }

    public String toString(){
        return this.db.toString();
    }

    public IArray getArray(String key) {
        return new ArrayExecuter(this.db.getArray(key));
    }

    public ICustomObject getObject(String key){
        return new DBObjectExecutor(this.db.getObject(key));
    }

    public Object remove(String key) {
        IDatabaseOperation remove = new RemoveOperationDBObject(key);

        Object value = remove.execute(this.db);

        writeToFile(remove.toString());
        return value;
    }

}
