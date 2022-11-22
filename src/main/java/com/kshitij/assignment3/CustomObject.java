package com.kshitij.assignment3;

import javax.swing.text.html.Option;
import java.util.Optional;

public class CustomObject {
    public Optional<Integer> intvalue;
    public Optional<String> stringValue;
//    Optional<Array> arrayValue;


    @Override
    public String toString() {
        return "CustomObject{" +
                "intvalue=" + intvalue +
                ", stringValue=" + stringValue +
                '}';
    }
}
