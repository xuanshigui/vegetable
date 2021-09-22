package com.vege.service.impl;

import com.vege.dao.TestDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl {

    private final TestDaoImpl testDao;

    @Autowired
    public TestServiceImpl(TestDaoImpl testDao) {
        this.testDao = testDao;
    }

    public boolean add(String data) {
        return testDao.add(data);
    }

}
