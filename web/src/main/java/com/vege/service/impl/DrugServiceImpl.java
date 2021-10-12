package com.vege.service.impl;

import com.vege.dao.DrugRepository;
import com.vege.model.Drug;
import com.vege.service.DrugService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class DrugServiceImpl extends BaseService implements DrugService {


    @Autowired
    DrugRepository drugRepository;

    @Override
    public boolean add(Drug drug) {
        try {
            drugRepository.save(drug);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean delete(String drugId) {
        try {
            Drug drug = drugRepository.findByDrugId(Integer.parseInt(drugId));
            drugRepository.delete(drug);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean update(Drug drug) {
        try {
            drugRepository.save(drug);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public Page<Drug> query(Map<String, String> condition) {

        String drugName = condition.get("drugName");

        Pageable pageable = getPageable(condition);
        if(drugName != null && !drugName.equals("")){
            drugName = "%" + drugName + "%";
            return drugRepository.findAllByDrugNameLike(drugName,getPageable(condition));
        }
        return drugRepository.findAll(getPageable(condition));
    }

    @Override
    public long queryTotal(Map<String, String> condition) {
        return drugRepository.count();
    }

    @Override
    public Drug queryById(String drugId) {
        return drugRepository.findByDrugId(Integer.parseInt(drugId));
    }


}
