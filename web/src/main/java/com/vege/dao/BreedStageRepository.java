package com.vege.dao;

import com.vege.model.BreedStage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface BreedStageRepository extends JpaRepository<BreedStage, Integer> {


    BreedStage findByBsId(Integer bsIdS);

    Page<BreedStage> findAll(Pageable pageable);

    List<BreedStage> findAllByVegeInfo_VegeId(Integer vegeId);

}
