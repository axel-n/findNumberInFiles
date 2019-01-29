package com.example.findNumber.processingData.controller;

import com.example.findNumber.processingData.model.Result;
import com.example.findNumber.processingData.repository.ResultRepository;
import com.example.findNumber.processingData.service.FindFilesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FindNumberController {

    private final Logger log = LoggerFactory.getLogger((getClass()));

    @Autowired
    private ResultRepository repository;

    @Autowired
    private FindFilesService service;

    @RequestMapping(value = "/find/{number}")
    public Result findFilesByNumber(@PathVariable("number") Integer number) {

        Result result = service.findNumber(number);
        Result resultSave  = repository.save(result);

        log.info("result in files: {}", result.toString());
        log.info("result save in bd: {}", resultSave.toString());

        return result;
    }

    @RequestMapping(value = "/result/{id}")
    public Result findResultById(@PathVariable("id") int id) {

        Result result = repository.findById(id);
        log.info("result in bd: {}", result);

        return result;
    }
}
