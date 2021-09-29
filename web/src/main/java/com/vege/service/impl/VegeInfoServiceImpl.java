package com.vege.service.impl;

import com.vege.dao.VegeInfoRepository;
import com.vege.model.VegeInfo;
import com.vege.service.VegeInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VegeInfoServiceImpl extends BaseService implements VegeInfoService {


    @Autowired
    private VegeInfoRepository vegeInfoRepository;

    @Override
    public boolean add(VegeInfo vegeInfo) {
        if("".equals(vegeInfo.getNote())||vegeInfo.getNote()==null){
            vegeInfo.setNote("无");
        }
        if("".equals(vegeInfo.getAlias())||vegeInfo.getAlias()==null){
            vegeInfo.setAlias("无");
        }
        try {
            vegeInfoRepository.save(vegeInfo);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean delete(String vegeId) {

        VegeInfo vegeInfo = vegeInfoRepository.findByVegeId(Integer.parseInt(vegeId));
        try {
            vegeInfoRepository.delete(vegeInfo);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean update(VegeInfo vegeInfo) {
        try {
            vegeInfoRepository.save(vegeInfo);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public Page<VegeInfo> query(Map<String, String> condition) {

        String vegeName = condition.get("vegeName");
        Pageable pageable = getPageable(condition);
        if (vegeName != null && !vegeName.equals("")) {
            vegeName = "%" + vegeName + "%";
            return vegeInfoRepository.findAllByVegeNameLike(vegeName,pageable);
        }
        return vegeInfoRepository.findAll(pageable);
    }

    @Override
    public long queryTotal(Map<String, String> condition) {
        return vegeInfoRepository.count();
    }

    @Override
    public VegeInfo queryById(String vegeId){
        return vegeInfoRepository.findByVegeId(Integer.parseInt(vegeId));
    }

    @Override
    public Map<String,String> getVegeIdAndName(){
        List<VegeInfo> vegeInfoList = vegeInfoRepository.findAll();
        Map<String,String> vegeIdAndNameMap = new HashMap<>();
        for(VegeInfo vegeInfo:vegeInfoList){
            vegeIdAndNameMap.put(String.valueOf(vegeInfo.getVegeId()),vegeInfo.getVegeName());
        }
        return vegeIdAndNameMap;
    }

    @Override
    public String getVegeNameById(String vegeId){
        VegeInfo vegeInfo = vegeInfoRepository.findByVegeId(Integer.parseInt(vegeId));
        return vegeInfo.getVegeName();
    }
}
