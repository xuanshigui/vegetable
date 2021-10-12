package com.vege.dao;

import com.vege.model.Standard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;


public interface StandardRepository extends JpaRepository<Standard, Integer> {


    Standard findByStandardId(Integer standardId);

    Page<Standard> findAll(Pageable pageable);

    Page<Standard> findAllByHeadlineLike(String headline, Pageable pageable);

    Page<Standard> findAllByPublicTimeAfter(Date startTime, Pageable pageable);

    Page<Standard> findAllByPublicTimeBefore(Date endTime, Pageable pageable);

    Page<Standard> findAllByPublicTimeBetween(Date startTime,Date endTime, Pageable pageable);

    Page<Standard> findAllByPublicTimeBetweenAndHeadlineLike(Date startTime,Date endTime,String headline, Pageable pageable);

    Page<Standard> findAllByPublicTimeAfterAndHeadlineLike(Date startTime,String headline, Pageable pageable);

    Page<Standard> findAllByPublicTimeBeforeAndHeadlineLike(Date endTime,String headline, Pageable pageable);

}
