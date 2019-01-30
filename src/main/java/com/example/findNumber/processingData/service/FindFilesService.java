package com.example.findNumber.processingData.service;

import com.example.findNumber.processingData.model.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.*;

@Service
public class FindFilesService {

    private final Logger log = LoggerFactory.getLogger(FindFilesService.class);
    private Result result;

    private final static ExecutorService EXECUTOR_SERVICE = Executors.newCachedThreadPool();

    public Result findNumber(Integer number) {
        return run(number);
    }

    private Result run(Integer number) {
        File path = new File("./files");
        result = new Result();
        HashMap<Future, String> futures = new HashMap<>();
        String fileName;

        for (File file : Objects.requireNonNull(path.listFiles())) {

            fileName = path + "/" + file.getName();

            Future future = runTask(fileName, number);
            futures.put(future, fileName);
        }
        waitThreads();

        // save result fileNames
        for (Map.Entry<Future, String> entry : futures.entrySet()) {
            try {
                // find 'true'
                if ((Boolean) entry.getKey().get()) {
                    result.setFileNames(entry.getValue());
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        if (result.getError() == null) {
            if (result.getFileNames().size() > 0) {
                result.setCode(Result.Code.OK);
            } else {
                result.setCode(Result.Code.NotFound);
            }
        }
        result.setNumber(number);

        return result;

    }

    private void waitThreads() {
        try {
            EXECUTOR_SERVICE.awaitTermination(10L, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private Future<?> runTask(String fileName, Integer number) {
        return EXECUTOR_SERVICE.submit(() ->  isFileContainsNumber(fileName, number));
    }

    private boolean isFileContainsNumber(String fileName, Integer number) {
        log.info("start processing file {}", fileName);

        BufferedReader br;
        String line;
        boolean complete = false;

        // read file
        try {
            br = new BufferedReader(new FileReader(fileName));
            while ((line = br.readLine()) != null && !complete) {
                String[] numbers = line.split(",");

                for (String item : numbers) {
                    if (number.equals(Integer.valueOf(item))) {
                        log.info("нашли число {}, в файле {}. запоминаем", number, fileName);
                        complete = true;
                    }
                }
            }

        } catch (IOException e) {
            log.error("получили ошибку {}. запоминаем", e.getMessage());
            result.setError(e.getMessage());
            result.setCode(Result.Code.Error);
        }
        return complete;
    }

}
