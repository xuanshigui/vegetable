package com.aquatic.service.impl;

import com.aquatic.dao.AtmosphereDao;
import com.aquatic.service.AtmosphereQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * created by zbs on 2018/3/17
 */
@Service
public class AtmosphereServiceImpl implements AtmosphereQueryService {

    private final AtmosphereDao atmosphereDao;

    @Autowired
    public AtmosphereServiceImpl(AtmosphereDao atmosphereDao) {
        this.atmosphereDao = atmosphereDao;
    }

    @Override
    public boolean add(Map<String, String> data) {
        return atmosphereDao.add(data);
    }

    @Override
    public boolean delete(String id) {
        return atmosphereDao.delete(id);
    }

    @Override
    public boolean update(Map<String, String> data) {
        return atmosphereDao.update(data);
    }

    @Override
    public List<Map<String, Object>> query(Map<String, String> condition) {
        return atmosphereDao.query(condition);
    }

    @Override
    public int queryTotal(Map<String, String> condition) {
        return atmosphereDao.queryTotal(condition);
    }
}
