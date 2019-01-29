package com.example.findNumber.processingData.service;

import com.example.findNumber.processingData.model.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

@Service
public class FindFilesService {

    private Integer number;
    private final String PATH_WITH_FILES = "./files";

    private final Logger log = LoggerFactory.getLogger((getClass()));

    public Result findNumber(Integer number) {

        this.number = number;
        return run();
    }

    private Result run() {
        log.info("start search {} in files", number);

        File path = new File(PATH_WITH_FILES);

        Result result = new Result();

        // read files in path
        for (File file : Objects.requireNonNull(path.listFiles())) {

            String fileName = path + "/" +  file.getName();

            try {
                if (isFileContainsNumber(fileName)) {
                    result.setFileNames(fileName);
                    result.setCode(Result.Code.OK);
                } else {
                    result.setCode(Result.Code.NotFound);
                }
            } catch (IOException e) {
                result.setCode(Result.Code.Error);
                result.setError(e.getMessage());
            }
            result.setNumber(number);
        }

        return result;
    }

    private boolean isFileContainsNumber(String fileName) throws IOException {
        log.info("start processing file {}", fileName);

        BufferedReader br;
        String line;

        // read file
        br = new BufferedReader(new FileReader(fileName));

        while ((line = br.readLine()) != null) {
            String[] numbers = line.split(",");

            for (String item : numbers) {
                // found number
                if (number.equals(Integer.valueOf(item))) return true;
            }
        }

        // not found
        return false;
    }
}
