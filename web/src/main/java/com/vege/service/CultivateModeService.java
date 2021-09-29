package com.vege.service;

import com.vege.model.CultivateMode;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface CultivateModeService {

    public CultivateMode add(CultivateMode cmId);

    public boolean delete(String cmId);

    public CultivateMode update(CultivateMode cultivateMode);

    public Page<CultivateMode> query(Map<String, String> condition);

    List<CultivateMode> findAll();

    public long queryTotal(Map<String, String> condition);

    public CultivateMode queryById(String cmId);

    public Map<String,String> getCultivateModeName();
}
