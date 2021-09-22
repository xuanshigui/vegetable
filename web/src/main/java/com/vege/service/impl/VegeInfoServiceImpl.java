package com.vege.service.impl;

import com.vege.dao.VegeInfoDao;
import com.vege.model.VegeInfo;
import com.vege.service.VegeInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class VegeInfoServiceImpl implements VegeInfoService {

    private final VegeInfoDao vegeInfoDao;

    @Autowired
    public VegeInfoServiceImpl(VegeInfoDao vegeInfoDao) {
        this.vegeInfoDao = vegeInfoDao;
    }

    @Override
    public boolean add(VegeInfo vegeInfo) {
        if("".equals(vegeInfo.getNote())||vegeInfo.getNote()==null){
            vegeInfo.setNote("无");
        }
        if("".equals(vegeInfo.getAlias())||vegeInfo.getAlias()==null){
            vegeInfo.setAlias("无");
        }
        return vegeInfoDao.add(vegeInfo);
    }

    @Override
    public boolean delete(String vegeId) {
        return vegeInfoDao.delete(vegeId);
    }

    @Override
    public boolean update(VegeInfo vegeInfo) {
        return vegeInfoDao.update(vegeInfo);
    }

    @Override
    public List<VegeInfo> query(Map<String, String> condition) {
        return vegeInfoDao.query(condition);
    }

    @Override
    public int queryTotal(Map<String, String> condition) {
        return vegeInfoDao.queryTotal(condition);
    }

    public VegeInfo queryById(String vegeId){
        return vegeInfoDao.queryById(vegeId);
    }
}
