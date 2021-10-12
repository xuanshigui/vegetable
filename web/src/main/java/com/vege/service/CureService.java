package com.vege.service;

import com.vege.model.Cure;
import org.springframework.data.domain.Page;

import java.util.Map;

public interface CureService {

    boolean add(Cure data);

    boolean delete(String cureId);

    boolean update(Cure data);

    Page<Cure> query(Map<String, String> condition);

    long queryTotal(Map<String, String> condition);

    Cure queryById(String cureId);

    Map<Integer,String> getCureMapByDiseaseId(Integer diseaseId);

}
