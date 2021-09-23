package com.vege.dao;

import com.vege.model.Variety;

import java.util.List;
import java.util.Map;

public interface VarietyDao {

    public boolean add(Variety data);

    public boolean delete(String varietyId);

    public boolean update(Variety data);

    public Variety queryById(String varietyId);

    public List<Variety> query(Map<String, String> condition);

    public int queryTotal(Map<String, String> condition);

}
