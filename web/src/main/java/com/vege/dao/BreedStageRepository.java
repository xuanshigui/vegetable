package com.vege.dao;
import com.vege.model.BreedStage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BreedStageRepository extends JpaRepository<BreedStage, Integer> {


    BreedStage findByBsId(Integer bsIdS);

    Page<BreedStage> findAll(Pageable pageable);

}
