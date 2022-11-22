package com.kshitij.assignment3.database;
import com.google.gson.Gson;
import com.kshitij.assignment3.Array;
import com.kshitij.assignment3.DBObject;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class DatabaseTest {
    @Test
    void testSFromString() {
//        String arr = "['1','2']";
//        Array ab = new Array();
//        Array s = ab.fromString(arr);

//        HashMap<String,Object> abc = new HashMap<String,Object>();
//        List<Object> c = new ArrayList<>();
////        c.add("Kshitij");
//        abc.put("kSHITIJ",1);
//        c.add(abc);
//
//        System.out.println(c.get(0) instanceof HashMap);

//        Gson gson = new Gson();
//        HashMap<String,Object> s = new HashMap<>();
//        s.put("Kshitij",1);
//        List<Object>
////        String json = gson.toJson(s);
////        DBObject db = new DBObject();
////        DBObject S = db.fromString(json);
//        System.out.println(S.get("Kshitij"));
        Array ab = new Array();
        ab.fromString("");
        assertEquals(true,true);
    }
}
