package com.vege.service.impl;

import com.vege.dao.CureRepository;
import com.vege.dao.DiseaseRepository;
import com.vege.model.Cure;
import com.vege.model.Disease;
import com.vege.service.CureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CureServiceImpl extends BaseService implements CureService {


    @Autowired
    CureRepository cureRepository;

    @Autowired
    DiseaseRepository diseaseRepository;

    @Override
    @Transactional
    public boolean add(Cure cure) {
        try {
            cureRepository.saveAndFlush(cure);
            Disease disease = cure.getDisease();
            disease.getCures().add(cure);
            diseaseRepository.save(disease);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    @Transactional
    public boolean delete(String cureId) {
        try {
            Cure cure = cureRepository.findByCureId(Integer.parseInt(cureId));
            Disease disease = cure.getDisease();
            disease.getCures().remove(cure);
            diseaseRepository.save(disease);
            cureRepository.delete(cure);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean update(Cure cure) {
        try {
            cureRepository.saveAndFlush(cure);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public Page<Cure> query(Map<String, String> condition) {

        String diseaseName = condition.get("diseaseName");
        if(diseaseName != null && !diseaseName.equals("")){
            diseaseName = "%" + diseaseName + "%";
            return cureRepository.findAllByDisease_DiseaseNameLike(diseaseName,getPageable(condition));
        }
        return cureRepository.findAll(getPageable(condition));
    }

    @Override
    public long queryTotal(Map<String, String> condition) {
        return cureRepository.count();
    }

    @Override
    public Cure queryById(String cureId) {
        return cureRepository.findByCureId(Integer.parseInt(cureId));
    }

    @Override
    public Map<Integer, String> getCureMapByDiseaseId(Integer diseaseId) {
        Map<Integer, String> cureNameMap = new HashMap<>();
        List<Cure> cureList = cureRepository.findAllByDisease_DiseaseId(diseaseId);
        for(Cure cure:cureList){
            cureNameMap.put(cure.getCureId(),cure.getCureName());
        }
        return cureNameMap;
    }
}
