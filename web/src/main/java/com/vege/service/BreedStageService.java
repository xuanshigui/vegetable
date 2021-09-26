package com.vege.service;

import com.vege.model.BreedStage;
import org.springframework.data.domain.Page;

import java.util.Map;

public interface BreedStageService {

    public BreedStage add(BreedStage breedStage);

    public boolean delete(String breedStageId);

    public BreedStage update(BreedStage breedStage);

    public Page<BreedStage> query(Map<String, String> condition);

    public long queryTotal(Map<String, String> condition);

    public BreedStage queryById(String breedStageId);
}
