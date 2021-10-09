package com.vege.service.impl;

import com.vege.dao.DiseaseRepository;
import com.vege.model.Disease;
import com.vege.service.DiseaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DiseaseServiceImpl extends BaseService implements DiseaseService {


    @Autowired
    DiseaseRepository diseaseRepository;

    @Override
    public boolean add(Disease disease) {
        try {
            diseaseRepository.save(disease);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean delete(String diseaseId) {
        try {
            Disease disease = diseaseRepository.findByDiseaseId(Integer.parseInt(diseaseId));
            diseaseRepository.delete(disease);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean update(Disease disease) {
        try {
            diseaseRepository.save(disease);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public Page<Disease> query(Map<String, String> condition) {

        String diseaseName = condition.get("diseaseName");
        String vegeName = condition.get("vegeName");;

        Pageable pageable = getPageable(condition);
        if(diseaseName != null && !diseaseName.equals("")){
            diseaseName = "%" + diseaseName + "%";
            if(vegeName!=null){
                vegeName = "%" + vegeName + "%";
                return diseaseRepository.findAllByDiseaseNameLikeAndVegeInfo_VegeNameLike(diseaseName, vegeName, pageable);
            }
            return diseaseRepository.findAllByDiseaseNameLike(diseaseName,getPageable(condition));
        }
        if(vegeName!="" && vegeName!=null){
            vegeName = "%" + vegeName + "%";
            return diseaseRepository.findAllByVegeInfo_VegeNameLike(vegeName, pageable);
        }
        return diseaseRepository.findAll(getPageable(condition));
    }

    @Override
    public long queryTotal(Map<String, String> condition) {
        return diseaseRepository.count();
    }

    @Override
    public Disease queryById(String diseaseId) {
        return diseaseRepository.findByDiseaseId(Integer.parseInt(diseaseId));
    }

    @Override
    public Map<Integer, String> getDiseaseMaoByVegeId(Integer vegeId) {
        List<Disease> diseaseList = diseaseRepository.findAllByVegeInfo_VegeId(vegeId);
        Map<Integer,String> diseaseNameMap = new HashMap<>();
        for(Disease disease:diseaseList){
            diseaseNameMap.put(disease.getDiseaseId(),disease.getDiseaseName());
        }
        return diseaseNameMap;
    }
}