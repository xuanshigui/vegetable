package com.vege.service.impl;

import com.vege.dao.VarietyDao;
import com.vege.dao.VegeInfoDao;
import com.vege.model.Variety;
import com.vege.model.VegeInfo;
import com.vege.service.VarietyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class VarietyServiceImpl implements VarietyService {

    private final VarietyDao varietyDao;
    private final VegeInfoDao vegeInfoDao;

    @Autowired
    public VarietyServiceImpl(VarietyDao varietyDao,VegeInfoDao vegeInfoDao) {
        this.varietyDao = varietyDao;
        this.vegeInfoDao = vegeInfoDao;
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

        List<Variety> varietyList = varietyDao.query(condition);
        for(Variety variety:varietyList){
            VegeInfo vegeInfo = vegeInfoDao.queryById(String.valueOf(variety.getVegeId()));
            variety.setVegeInfo(vegeInfo);
        }
        return varietyList;
    }

    @Override
    public int queryTotal(Map<String, String> condition) {
        return varietyDao.queryTotal(condition);
    }

    public Variety queryById(String varietyId){
        Variety variety = varietyDao.queryById(varietyId);
        VegeInfo vegeInfo = vegeInfoDao.queryById(String.valueOf(variety.getVegeId()));
        variety.setVegeInfo(vegeInfo);
        return variety;
    }
}
