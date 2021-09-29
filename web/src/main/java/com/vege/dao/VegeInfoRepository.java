package com.vege.dao;

import com.vege.model.VegeInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface VegeInfoRepository extends JpaRepository<VegeInfo, Integer> {


    VegeInfo findByVegeId(Integer vegeId);

    Page<VegeInfo> findAll(Pageable pageable);

    Page<VegeInfo> findAllByVegeNameLike(String vegeName, Pageable pageable);

}
