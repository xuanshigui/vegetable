package com.aquatic.service.impl;

import com.aquatic.dao.AtmosphereDao;
import com.aquatic.dao.WaterDao;
import com.aquatic.service.AtmosphereQueryService;
import com.aquatic.service.WaterQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * created by zbs on 2018/3/17
 */
@Service
public class WaterServiceImpl implements WaterQueryService {

    private final WaterDao waterDao;

    @Autowired
    public WaterServiceImpl(WaterDao waterDao) {
        this.waterDao = waterDao;
    }

    @Override
    public boolean add(Map<String, String> data) {
        return waterDao.add(data);
    }

    @Override
    public boolean delete(String id) {
        return waterDao.delete(id);
    }

    @Override
    public boolean update(Map<String, String> data) {
        return waterDao.update(data);
    }

    @Override
    public List<Map<String, Object>> query(Map<String, String> condition) {
        return waterDao.query(condition);
    }

    @Override
    public int queryTotal(Map<String, String> condition) {
        return waterDao.queryTotal(condition);
    }
}
