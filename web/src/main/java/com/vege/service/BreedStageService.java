package com.vege.service;

import com.vege.model.BreedStage;
import org.springframework.data.domain.Page;

import java.util.Map;

public interface BreedStageService {

    boolean add(BreedStage breedStage);

    boolean delete(String breedStageId);

    boolean update(BreedStage breedStage);

    Page<BreedStage> query(Map<String, String> condition);

    long queryTotal(Map<String, String> condition);

    BreedStage queryById(String breedStageId);

    Map<String,String> getBsIdAndName();

    Map<String,String> getBsIdAndNameByvegeId(String vegeId);
}
