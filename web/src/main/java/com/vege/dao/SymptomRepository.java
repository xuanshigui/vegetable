package com.vege.dao;

import com.vege.model.Symptom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SymptomRepository extends JpaRepository<Symptom, Integer> {


    Symptom findBySymptomId(Integer symptomId);

    Page<Symptom> findAll(Pageable pageable);

    Page<Symptom> findAllBySymptomNameLike(String symptomName, Pageable pageable);

    Page<Symptom> findAllByDisease_DiseaseNameLike(String diseaseName, Pageable pageable);

    Page<Symptom> findAllBySymptomNameLikeAndDisease_DiseaseNameLike(String symptomName, String diseaseName, Pageable pageable);

}
