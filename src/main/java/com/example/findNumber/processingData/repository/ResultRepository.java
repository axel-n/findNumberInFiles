package com.example.findNumber.processingData.repository;

import com.example.findNumber.processingData.model.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResultRepository extends JpaRepository<Result, Integer> {

    Result findById(int id);
    Result save(Result result);

}