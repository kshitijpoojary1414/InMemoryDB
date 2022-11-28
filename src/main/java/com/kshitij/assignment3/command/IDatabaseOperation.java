package com.kshitij.assignment3.command;

import com.kshitij.assignment3.database.Database;
import com.kshitij.assignment3.database.array.Array;
import com.kshitij.assignment3.database.dbobject.CustomObject;

public interface IDatabaseOperation {

    Object execute (Object object);

    Object undo();

    default String operationToString(String operation, Array array, Object value) {
        return convertParemetersToString("ARRAY", operation, value.toString(), array.getParent()+".*index*" + array.length());
    }

    default String operationToString(String operation, CustomObject customObject, Object value) {
        return convertParemetersToString("CustomObject", operation, value.toString(), customObject.getParent());
    }

    default String operationToString(String operation, Database db, Object value, String key) {
        return convertParemetersToString("DB", operation, value.toString(), key);
    }

    default String convertParemetersToString(String operatesOn, String operation, Object value, String parent) {
        return ( operatesOn + "#"+operation+"#"+value + "#" + parent);
    }

}
