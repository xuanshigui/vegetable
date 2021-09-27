package com.vege.service.impl;

import com.vege.dao.EnvParamRepository;
import com.vege.model.EnvParam;
import com.vege.service.EnvParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Map;
@Service
public class EnvParamServiceImpl extends BaseService implements EnvParamService {

    @Autowired
    EnvParamRepository envParamRepository;

    @Override
    public EnvParam add(EnvParam envParam) {
        return envParamRepository.save(envParam);
    }

    @Override
    public boolean delete(String epId) {
        try {
            envParamRepository.deleteById(Integer.parseInt(epId));
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public EnvParam update(EnvParam envParam) {
        return envParamRepository.save(envParam);
    }

    @Override
    public Page<EnvParam> query(Map<String, String> condition) {
        String paramNameStr = condition.get("paramName");
        String stageNameStr = condition.get("stageName");
        Pageable pageable = getPageable(condition);
        if (paramNameStr != null && !paramNameStr.equals("")) {
            paramNameStr = "%"+paramNameStr+"%";
            if (stageNameStr != null && !stageNameStr.equals("")) {
                stageNameStr = "%" + stageNameStr + "%";
                return envParamRepository.findAllByParaNameLikeAndBreedStage_StageNameLike(paramNameStr,stageNameStr,pageable);
            }
            return envParamRepository.findAllByParaNameLike(paramNameStr,pageable);
        }
        if(stageNameStr != null && !stageNameStr.equals("")){
            stageNameStr = "%" + stageNameStr + "%";
            return envParamRepository.findAllByBreedStage_StageNameLike(stageNameStr,pageable);
        }
        return envParamRepository.findAll(pageable);
    }

    @Override
    public long queryTotal(Map<String, String> condition) {
        return envParamRepository.count();
    }

    @Override
    public EnvParam queryById(String epId) {
        return envParamRepository.findByEpId(Integer.parseInt(epId));
    }
}
