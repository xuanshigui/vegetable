package com.vege.service;

import com.vege.model.Variety;
import org.springframework.data.domain.Page;

import java.util.Map;

public interface VarietyService {

    boolean add(Variety data);

    boolean delete(String varietyId);

    boolean update(Variety data);

    Page<Variety> query(Map<String, String> condition);

    long queryTotal(Map<String, String> condition);

    Variety queryById(String varietyId);
}
