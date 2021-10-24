package com.vege.service.impl;

import com.vege.dao.BreedStageRepository;
import com.vege.dao.VegeInfoRepository;
import com.vege.model.BreedStage;
import com.vege.model.VegeInfo;
import com.vege.service.BreedStageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BreedStageServiceImpl extends BaseService implements BreedStageService {


    @Autowired
    BreedStageRepository breedStageRepository;

    @Autowired
    VegeInfoRepository vegeInfoRepository;

    @Transactional
    @Override
    public boolean add(BreedStage breedStage) {
        try {
            breedStageRepository.saveAndFlush(breedStage);
            VegeInfo vegeInfo = breedStage.getVegeInfo();
            vegeInfo.getBreedStages().add(breedStage);
            vegeInfoRepository.save(vegeInfo);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    @Transactional
    public boolean delete(String breedStageId) {
        BreedStage breedStage = breedStageRepository.findByBsId(Integer.parseInt(breedStageId));
        try {
            if (breedStage.getEnvParams().size()!=0){
                return false;
            }
            VegeInfo vegeInfo = breedStage.getVegeInfo();
            vegeInfo.getBreedStages().remove(breedStage);
            vegeInfoRepository.saveAndFlush(vegeInfo);
            breedStageRepository.delete(breedStage);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean update(BreedStage breedStage) {
        try {
            breedStageRepository.save(breedStage);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public Page<BreedStage> query(Map<String, String> condition) {

        Pageable pageable = getPageable(condition);
        return breedStageRepository.findAll(pageable);
    }

    @Override
    public long queryTotal(Map<String, String> condition) {
        return breedStageRepository.count();
    }

    @Override
    public BreedStage queryById(String breedStageId) {
        return breedStageRepository.findByBsId(Integer.parseInt(breedStageId));
    }

    @Override
    public Map<String, String> getBsIdAndName() {
        Map<String,String> idNameMap = new HashMap<>();
        List<BreedStage> bsList = breedStageRepository.findAll();
        for(BreedStage breedStage:bsList){
            idNameMap.put(breedStage.getBsId().toString(),breedStage.getStageName());
        }
        return idNameMap;
    }

    @Override
    public Map<String, String> getBsIdAndNameByvegeId(String vegeId) {
        Map<String,String> idNameMap = new HashMap<>();
        List<BreedStage> bsList = breedStageRepository.findAllByVegeInfo_VegeId(Integer.parseInt(vegeId));
        for(BreedStage breedStage:bsList){
            idNameMap.put(breedStage.getBsId().toString(),breedStage.getStageName());
        }
        return idNameMap;
    }
}
