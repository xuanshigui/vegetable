package com.vege.service.impl;

import com.vege.dao.VarietyRepository;
import com.vege.model.Variety;
import com.vege.service.VarietyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class VarietyServiceImpl extends BaseService implements VarietyService {


    @Autowired
    private VarietyRepository varietyRepository;

    @Override
    public boolean add(Variety variety) {

        try {

            varietyRepository.save(variety);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean delete(String varietyId) {
        try {
            Variety variety = varietyRepository.findByVarietyId(Integer.parseInt(varietyId));
            varietyRepository.delete(variety);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean update(Variety variety) {

        try {
            varietyRepository.save(variety);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public Page<Variety> query(Map<String, String> condition) {
        String varietyName = condition.get("varietyName");
        String vegeName = condition.get("vegeName");
        Pageable pageable = getPageable(condition);
        if (varietyName != null && !varietyName.equals("")) {
            varietyName = "%"+varietyName+"%";
            if (vegeName != null && !vegeName.equals("")) {
                vegeName = "%" + vegeName + "%";
                return varietyRepository.findAllByVarietyNameLikeAndVegeInfo_VegeNameLike(varietyName,vegeName,pageable);
            }
            return varietyRepository.findAllByVarietyNameLike(varietyName,pageable);
        }
        if (vegeName != null && !vegeName.equals("")) {
            vegeName = "%" + vegeName + "%";
            return varietyRepository.findAllByVegeInfo_VegeNameLike(vegeName,pageable);
        }
        return varietyRepository.findAll(pageable);
    }

    @Override
    public long queryTotal(Map<String, String> condition) {
        return varietyRepository.count();
    }

    public Variety queryById(String varietyId){

        return varietyRepository.findByVarietyId(Integer.parseInt(varietyId));
    }
}
