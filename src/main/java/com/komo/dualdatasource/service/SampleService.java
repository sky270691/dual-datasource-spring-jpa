package com.komo.dualdatasource.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SampleService {

    @Transactional(transactionManager = "")
    public void insertData1() {

    }

}
