package com.vege.service.impl;

import com.vege.dao.DiseaseRepository;
import com.vege.dao.SymptomRepository;
import com.vege.model.Disease;
import com.vege.model.Symptom;
import com.vege.service.SymptomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Map;

@Service
public class SymptomServiceImpl extends BaseService implements SymptomService {


    @Autowired
    SymptomRepository symptomRepository;

    @Autowired
    DiseaseRepository diseaseRepository;

    @Override
    @Transactional
    public boolean add(Symptom symptom) {
        try {
            symptomRepository.saveAndFlush(symptom);
            Disease disease = symptom.getDisease();
            disease.getSymptoms().add(symptom);
            diseaseRepository.save(disease);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    @Transactional
    public boolean delete(String symptomId) {
        try {
            Symptom symptom = symptomRepository.findBySymptomId(Integer.parseInt(symptomId));
            Disease disease = symptom.getDisease();
            disease.getSymptoms().remove(symptom);
            diseaseRepository.save(disease);
            symptomRepository.delete(symptom);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean update(Symptom symptom) {
        try {
            symptomRepository.save(symptom);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public Page<Symptom> query(Map<String, String> condition) {

        String symptomName = condition.get("symptomName");
        String diseaseName = condition.get("diseaseName");
        Pageable pageable = getPageable(condition);
        if (symptomName != null && !symptomName.equals("")) {
            symptomName = "%"+symptomName+"%";
            if (diseaseName != null && !diseaseName.equals("")) {
                diseaseName = "%" + diseaseName + "%";
                return symptomRepository.findAllBySymptomNameLikeAndDisease_DiseaseNameLike(symptomName,diseaseName,pageable);
            }
            return symptomRepository.findAllBySymptomNameLike(symptomName,pageable);
        }
        if (diseaseName != null && !diseaseName.equals("")) {
            diseaseName = "%" + diseaseName + "%";
            return symptomRepository.findAllByDisease_DiseaseNameLike(diseaseName,pageable);
        }
        return symptomRepository.findAll(pageable);
    }

    @Override
    public long queryTotal(Map<String, String> condition) {
        return symptomRepository.count();
    }

    @Override
    public Symptom queryById(String symptomId) {
        return symptomRepository.findBySymptomId(Integer.parseInt(symptomId));
    }


}
