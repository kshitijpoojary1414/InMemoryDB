package com.kshitij.assignment3.database;
import com.kshitij.assignment3.database.array.Array;
import com.kshitij.assignment3.database.dbobject.CustomObject;
import com.kshitij.assignment3.decorator.ArrayExecuter;
import com.kshitij.assignment3.decorator.DatabaseExecutor;
import com.kshitij.assignment3.fileio.FileOperations;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

public class DatabaseTest {

    @Test
    void testSFromString() {

        Array firstArr = new Array();
        ArrayExecuter array = new ArrayExecuter(firstArr);

        CustomObject secondObj = new CustomObject();
        CustomObject thirdObj = new CustomObject();

        secondObj.put("Kp",1);
        firstArr.put(secondObj);
        DatabaseExecutor db = new DatabaseExecutor(new Database());
        db.put("KP",firstArr);
        db.getArray("KP").getObject(0).put("Abhi",thirdObj);
        db.getArray("KP").put("Anuj");
//        System.out.println();
//        System.out.println(db.getArray("KP").get(1));
        System.out.println(db.get("KP"));
        FileOperations fileOperation = new FileOperations();
////        Object commandHistory = fileOperation.readNext();
        try {
            List<String> res = fileOperation.readData();
//            Gson gson = new Gson();
//            Type expectedType = new TypeToken<ArrayList<Object>>(){}.getType();
//            ArrayList object = gson.fromJson(res.get(0), expectedType);
            Array abc = new Array();
//            String[] r = res.get(1).split(":");
//            Array ac = abc.fromString(r[r.length-1]);
//            System.out.println(ac.get(0));
//            System.out.println(ac.get(1));
//            System.out.println(bc.getParent());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertEquals(true,true);
    }
}
