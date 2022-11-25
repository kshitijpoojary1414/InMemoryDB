package com.kshitij.assignment3.decorator;

import com.kshitij.assignment3.command.IDatabaseOperation;
import com.kshitij.assignment3.database.Database;
import com.kshitij.assignment3.fileio.FileOperations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

public class Executor {
    private Stack<IDatabaseOperation> operationList;
    private Database db;
    private DatabaseExecutor databaseExecutor;
    private FileOperations fileOperation;
    protected Boolean isSavedOperation ;
    public Executor(Boolean isSavedOperation) {
        fileOperation = new FileOperations();
        this.isSavedOperation = isSavedOperation;
    }

    public List<List<String>> retrieveOperations() {
        List<List<String>> commands = new ArrayList<>();
        try {
            List<String> operations = fileOperation.readData();


            for (String operation : operations) {
                if(operation.length()>0) {
                    String[] res = operation.split("#");
                    List<String> resp = Arrays.stream(res).collect(Collectors.toList());
                    commands.add(resp);
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return commands;
    }
    public void writeToFile(String operation) {
        System.out.println(operation);
        System.out.println(isSavedOperation);
//        if (!isSavedOperation) {
            try {
                fileOperation.writeData(operation);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
//        }
    }
}
