package com.vege.dao;

import com.vege.model.Drug;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DrugRepository extends JpaRepository<Drug, Integer> {


    Drug findByDrugId(Integer drugId);

    Page<Drug> findAll(Pageable pageable);

    Page<Drug> findAllByDrugNameLike(String drugName, Pageable pageable);

}
