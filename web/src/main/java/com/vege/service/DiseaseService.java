package com.vege.service;

import com.vege.model.Disease;
import org.springframework.data.domain.Page;

import java.util.Map;

public interface DiseaseService {

    boolean add(Disease data);

    boolean delete(String diseaseId);

    boolean update(Disease data);

    Page<Disease> query(Map<String, String> condition);

    long queryTotal(Map<String, String> condition);

    Disease queryById(String diseaseId);

    Map<Integer,String> getDiseaseMapByVegeId(Integer vegeId);

}
