package com.vege.dao;

import com.vege.model.VegeInfo;

import java.util.List;
import java.util.Map;

public interface VegeInfoDao {

    public boolean add(VegeInfo data);

    public boolean delete(String vegeId);

    public boolean update(VegeInfo data);

    public VegeInfo queryById(String vegeid);

    public List<VegeInfo> query(Map<String, String> condition);

    public int queryTotal(Map<String, String> condition);

    public Map<String,String> getVegeIdAndName();
}
