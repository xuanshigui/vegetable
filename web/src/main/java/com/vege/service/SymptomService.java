package com.vege.service;

import com.vege.model.Symptom;
import org.springframework.data.domain.Page;

import java.util.Map;

public interface SymptomService {

    boolean add(Symptom data);

    boolean delete(String symptomId);

    boolean update(Symptom data);

    Page<Symptom> query(Map<String, String> condition);

    long queryTotal(Map<String, String> condition);

    Symptom queryById(String symptomId);

}
