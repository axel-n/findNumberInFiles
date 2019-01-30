package com.example.findNumber.processingData.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private Code code;
    private int number;

    @ElementCollection
    @CollectionTable(name="fileNames")
    private List<String> fileNames = new ArrayList<>();

    private String error;

    public enum Code {
        OK, NotFound, Error
    }

    @Override
    public String toString() {
        return String.format("{id %s, code %s, fileNames %s, error %s}", id, code, fileNames, error);
    }
}
