package com.vege.dao;

import com.vege.model.Disease;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface DiseaseRepository extends JpaRepository<Disease, Integer> {


    Disease findByDiseaseId(Integer deseaseId);

    Page<Disease> findAll(Pageable pageable);

    Page<Disease> findAllByDiseaseNameLike(String diseaseName, Pageable pageable);

    Page<Disease> findAllByDiseaseNameLikeAndVegeInfo_VegeNameLike(String diseaseName, String vegeName, Pageable pageable);

    Page<Disease> findAllByVegeInfo_VegeNameLike(String vegeName, Pageable pageable);

    List<Disease> findAllByVegeInfo_VegeId(Integer vegeId);
}
