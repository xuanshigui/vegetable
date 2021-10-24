package com.vege.service;

import com.vege.model.EnvParam;
import org.springframework.data.domain.Page;

import java.util.Map;

public interface EnvParamService {

    public boolean add(EnvParam envParam);

    public boolean delete(String envParamId);

    public boolean update(EnvParam envParam);

    public Page<EnvParam> query(Map<String, String> condition);

    //List<EnvParam> queryByConditions(Map<String, String> condition);

    public long queryTotal(Map<String, String> condition);

    public EnvParam queryById(String envParamId);
}
