package com.vege.service.impl;

import com.vege.dao.VarietyDao;
import com.vege.model.Variety;
import com.vege.service.VarietyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class VarietyServiceImpl implements VarietyService {

    private final VarietyDao varietyDao;

    @Autowired
    public VarietyServiceImpl(VarietyDao varietyDao) {
        this.varietyDao = varietyDao;
    }

    @Override
    public boolean add(Variety variety) {
        return varietyDao.add(variety);
    }

    @Override
    public boolean delete(String varietyId) {
        return varietyDao.delete(varietyId);
    }

    @Override
    public boolean update(Variety variety) {
        return varietyDao.update(variety);
    }

    @Override
    public List<Variety> query(Map<String, String> condition) {
        return varietyDao.query(condition);
    }

    @Override
    public int queryTotal(Map<String, String> condition) {
        return varietyDao.queryTotal(condition);
    }

    public Variety queryById(String variety){
        return varietyDao.queryById(variety);
    }
}
