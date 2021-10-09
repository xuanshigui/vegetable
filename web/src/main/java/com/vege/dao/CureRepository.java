package com.vege.dao;

import com.vege.model.Cure;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CureRepository extends JpaRepository<Cure, Integer> {


    Cure findByCureId(Integer cureId);

    Page<Cure> findAll(Pageable pageable);

    Page<Cure> findAllByDisease_DiseaseNameLike(String diseaseName, Pageable pageable);

}
