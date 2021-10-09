package com.vege.service.impl;

import com.vege.dao.CultivateModeRepository;
import com.vege.dao.VarietyRepository;
import com.vege.model.CultivateMode;
import com.vege.model.Variety;
import com.vege.service.CultivateModeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CultivateModeServiceImpl extends BaseService implements CultivateModeService {

    @Autowired
    CultivateModeRepository cultivateModeRepository;

    @Autowired
    VarietyRepository varietyRepository;

    @Override
    public CultivateMode add(CultivateMode cultivateMode) {
        return cultivateModeRepository.save(cultivateMode);
    }

    @Transactional
    @Override
    public boolean delete(String cmId) {
        try {
            CultivateMode cultivateMode = cultivateModeRepository.findByCmId(Integer.parseInt(cmId));
            List<Variety> varietyList = cultivateMode.getVarieties();
            for(Variety variety: varietyList){
                variety.getCultivateModes().remove(cultivateMode);
                varietyRepository.save(variety);
            }
            cultivateModeRepository.delete(cultivateMode);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public CultivateMode update(CultivateMode cultivateMode) {
        return cultivateModeRepository.save(cultivateMode);
    }

    @Override
    public Page<CultivateMode> query(Map<String, String> condition) {
        String cmNameStr = condition.get("cultivateModeName");
        if(cmNameStr != null && !cmNameStr.equals("")){
            cmNameStr = "%" + cmNameStr + "%";
            return cultivateModeRepository.findAllByCultivateModeNameLike(cmNameStr,getPageable(condition));
        }
        return cultivateModeRepository.findAll(getPageable(condition));
    }

    @Override
    public long queryTotal(Map<String, String> condition) {
        return cultivateModeRepository.count();
    }

    @Override
    public CultivateMode queryById(String epId) {
        return cultivateModeRepository.findByCmId(Integer.parseInt(epId));
    }

    @Override
    public Map<String, String> getCultivateModeName() {
        List<CultivateMode> cultivateModeList = cultivateModeRepository.findAll();
        Map<String,String> cultivateModeNameMap = new HashMap<>();
        for(CultivateMode cultivateMode:cultivateModeList){
            cultivateModeNameMap.put(cultivateMode.getCmId().toString(),cultivateMode.getCultivateModeName());
        }
        return cultivateModeNameMap;
    }

    @Override
    public List<CultivateMode> findAll() {
        return cultivateModeRepository.findAll();
    }
}
