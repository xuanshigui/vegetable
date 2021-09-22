package com.vege.service;

import com.vege.model.Variety;

import java.util.List;
import java.util.Map;

public interface VarietyService {

    boolean add(Variety data);

    boolean delete(String varietyId);

    boolean update(Variety data);

    List<Variety> query(Map<String, String> condition);

    int queryTotal(Map<String, String> condition);

    Variety queryById(String varietyId);
}
