package com.example.findNumber.generateData;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Run {

    private final static Logger log = LoggerFactory.getLogger("Run");

    public static void main(String[] args) {
        GenerateData.generateFiles();

        log.info("files generated!");
    }
}
