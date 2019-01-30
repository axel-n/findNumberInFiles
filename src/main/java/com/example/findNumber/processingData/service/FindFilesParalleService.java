package com.example.findNumber.processingData.service;

import com.example.findNumber.processingData.model.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.*;


@Service
public class FindFilesParalleService {

    private static final Logger log = LoggerFactory.getLogger(FindFilesParalleService.class);

    private final static ExecutorService EXECUTOR_SERVICE = Executors.newCachedThreadPool();

    public Result findNumber(Integer number) {
        return run(number);
    }

    private Result run(Integer number) {
        File path = new File("./files");
        Result result = new Result();
        HashMap<Future, String> futures = new HashMap<>();

        for (File file : Objects.requireNonNull(path.listFiles())) {

            String fileName = path + "/" + file.getName();

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


        if (result.getFileNames().size() > 0) {
            result.setCode(Result.Code.OK);
        } else {
            result.setCode(Result.Code.NotFound);
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

    private Future<Boolean> runTask(String fileName, Integer number) {
        Future<Boolean> future = EXECUTOR_SERVICE.submit(() -> isFileContainsNumber(fileName, number));

        return future;
    }

    private boolean isFileContainsNumber(String fileName, Integer number) throws IOException {
        log.info("start processing file {}", fileName);

        BufferedReader br;
        String line;
        boolean complete = false;

        // read file
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
        return complete;
    }

}
