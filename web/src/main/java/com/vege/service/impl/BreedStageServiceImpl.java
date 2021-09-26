package com.vege.service.impl;

import com.vege.dao.BreedStageRepository;
import com.vege.model.BreedStage;
import com.vege.service.BreedStageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class BreedStageServiceImpl implements BreedStageService {


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
        int page = 0;
        int size = 7;
        String pageStr = condition.get("page");
        if (pageStr != null && !pageStr.equals("")) {
            page = Integer.valueOf(pageStr)-1;
        }

        String sizeStr = condition.get("size");
        if (sizeStr != null && !sizeStr.equals("")) {
            size = Integer.valueOf(sizeStr);
        }

        Pageable pageable = PageRequest.of(page, size);
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
}
