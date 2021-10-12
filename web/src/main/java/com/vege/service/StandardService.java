package com.vege.service;

import com.vege.model.Standard;
import org.springframework.data.domain.Page;

import java.util.Map;

public interface StandardService {

    boolean add(Standard data);

    boolean delete(String standardId);

    boolean update(Standard data);

    Page<Standard> query(Map<String, String> condition);

    long queryTotal(Map<String, String> condition);

    Standard queryById(String standardId);

}
