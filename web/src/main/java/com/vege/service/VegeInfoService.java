package com.vege.service;

import com.vege.model.VegeInfo;

import java.util.List;
import java.util.Map;

public interface VegeInfoService {

    boolean add(VegeInfo data);

    boolean delete(String vegeId);

    boolean update(VegeInfo data);

    List<VegeInfo> query(Map<String, String> condition);

    int queryTotal(Map<String, String> condition);

    VegeInfo queryById(String vegeId);
}
