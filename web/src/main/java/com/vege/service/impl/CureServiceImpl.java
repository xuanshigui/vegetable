package com.vege.service.impl;

import com.vege.dao.CureRepository;
import com.vege.model.Cure;
import com.vege.service.CureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CureServiceImpl extends BaseService implements CureService {


    @Autowired
    CureRepository cureRepository;

    @Override
    public boolean add(Cure cure) {
        try {
            cureRepository.save(cure);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean delete(String cureId) {
        try {
            Cure cure = cureRepository.findByCureId(Integer.parseInt(cureId));
            cureRepository.delete(cure);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean update(Cure cure) {
        try {
            cureRepository.save(cure);
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


}
