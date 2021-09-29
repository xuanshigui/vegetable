package com.vege.dao;

import com.vege.model.Variety;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface VarietyRepository extends JpaRepository<Variety, Integer> {


    Variety findByVarietyId(Integer varietyId);

    Page<Variety> findAll(Pageable pageable);

    Page<Variety> findAllByVegeInfo_VegeNameLike(String vegeName,Pageable pageable);

    Page<Variety> findAllByVarietyNameLike(String varietyName, Pageable pageable);

    Page<Variety> findAllByVarietyNameLikeAndVegeInfo_VegeNameLike(String varieyName, String vegeName, Pageable pageable);

}
