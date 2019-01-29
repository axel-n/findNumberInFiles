package com.example.findNumber.processingData.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;

@Entity
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private Code code;
    private int number;
    private ArrayList<String> fileNames = new ArrayList<>();
    private String error;

    public enum Code {
        OK, NotFound, Error
    }

    public int getId() {
        return id;
    }

    public Code getCode() {
        return code;
    }

    public void setCode(Code code) {
        this.code = code;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public ArrayList<String> getFileNames() {
        return fileNames;
    }

    public void setFileNames(String fileNames) {
        this.fileNames.add(fileNames);
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return String.format("{id %s, code %s, fileNames %s, error %s}", id, code, fileNames, error);
    }
}
