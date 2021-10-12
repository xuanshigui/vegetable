package com.vege.service.impl;

import com.vege.dao.StandardRepository;
import com.vege.model.Standard;
import com.vege.service.StandardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Service
public class StandardServiceImpl extends BaseService implements StandardService {


    @Autowired
    StandardRepository standardRepository;

    @Override
    public boolean add(Standard standard) {
        try {
            standardRepository.save(standard);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean delete(String standardId) {
        try {
            Standard standard = standardRepository.findByStandardId(Integer.parseInt(standardId));
            standardRepository.delete(standard);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean update(Standard standard) {
        try {
            standardRepository.save(standard);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public Page<Standard> query(Map<String, String> condition) {

        String headline = condition.get("headline");
        String startTimeStr = condition.get("startTime");
        String endTimeStr = condition.get("endTime");
        SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd");
        Date startTime = null;
        Date endTime = null;
        if(startTimeStr != null && !startTimeStr.equals("")){
            try {
                startTime = formatter.parse(startTimeStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if(endTimeStr != null && !endTimeStr.equals("")){
            try {
                endTime = formatter.parse(endTimeStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        Pageable pageable = getPageable(condition);
        if(headline != null && !headline.equals("")){
            headline = "%" + headline + "%";
            if(startTime != null && !startTime.equals("")){
                if(endTime != null && !endTime.equals("")){
                    return standardRepository.findAllByPublicTimeBetweenAndHeadlineLike(startTime,endTime,headline,pageable);
                }
                return standardRepository.findAllByPublicTimeAfterAndHeadlineLike(startTime,headline,pageable);
            }
            if(endTime != null && !endTime.equals("")){
                return standardRepository.findAllByPublicTimeBeforeAndHeadlineLike(endTime,headline,pageable);
            }
            return standardRepository.findAllByHeadlineLike(headline,getPageable(condition));
        }
        if(startTime != null && !startTime.equals("")){
            if(endTime != null && !endTime.equals("")){
                return standardRepository.findAllByPublicTimeBetween(startTime,endTime,pageable);
            }
            return standardRepository.findAllByPublicTimeAfter(startTime,pageable);
        }
        if(endTime != null && !endTime.equals("")){
            return standardRepository.findAllByPublicTimeBefore(endTime,pageable);
        }
        return standardRepository.findAll(getPageable(condition));
    }

    @Override
    public long queryTotal(Map<String, String> condition) {
        return standardRepository.count();
    }

    @Override
    public Standard queryById(String standardId) {
        return standardRepository.findByStandardId(Integer.parseInt(standardId));
    }

}
