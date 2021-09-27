package com.vege.service.impl;

import com.vege.dao.BreedStageRepository;
import com.vege.model.BreedStage;
import com.vege.service.BreedStageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BreedStageServiceImpl extends BaseService implements BreedStageService {


    @Autowired
    BreedStageRepository breedStageRepository;

    @Override
    public BreedStage add(BreedStage breedStage) {
        return breedStageRepository.save(breedStage);
    }

    @Override
    public boolean delete(String breedStageId) {
        try {
            breedStageRepository.deleteById(Integer.parseInt(breedStageId));
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public BreedStage update(BreedStage breedStage) {
        return breedStageRepository.save(breedStage);
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
}
