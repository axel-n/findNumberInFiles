package com.example.findNumber.processingData.service;

import com.example.findNumber.processingData.model.Result;
import org.springframework.stereotype.Service;

@Service
public class FindFilesService {

    public Result findNumber(Integer number) {

        Result result = new Result();
        //result.setNumber(number);
        //result.setCode(Result.Code.NotFound);

        return result;
    }
}
