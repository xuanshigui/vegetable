package com.vege.dao;

import com.vege.model.Image;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;


public interface ImageRepository extends JpaRepository<Image, Integer> {


    Image findByImgId(Integer companyId);

    Page<Image> findAll(Pageable pageable);

    Image findByUuid(String uuid);

    Page<Image> findByTimestampBetweenAndImgNameLike(Date startTime,Date endTime,String imgName,Pageable pageable);

    Page<Image> findByTimestampAfterAndImgNameLike(Date startTime,String imgName,Pageable pageable);

    Page<Image> findByTimestampBeforeAndImgNameLike(Date endTime,String imgName,Pageable pageable);

    Page<Image> findByImgNameLike(String imgName,Pageable pageable);

    Page<Image> findByTimestampBefore(Date endTime,Pageable pageable);

    Page<Image> findByTimestampAfter(Date startTime,Pageable pageable);

    Page<Image> findByTimestampBetween(Date startTime,Date endTime,Pageable pageable);
}
