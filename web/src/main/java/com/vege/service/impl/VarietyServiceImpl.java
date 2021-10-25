package com.vege.service.impl;

import com.vege.dao.ImageRepository;
import com.vege.dao.VarietyRepository;
import com.vege.dao.VegeInfoRepository;
import com.vege.model.Image;
import com.vege.model.Variety;
import com.vege.model.VegeInfo;
import com.vege.service.VarietyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Map;

@Service
public class VarietyServiceImpl extends BaseService implements VarietyService {


    @Autowired
    private VarietyRepository varietyRepository;
    @Autowired
    private VegeInfoRepository vegeInfoRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Transactional
    @Override
    public boolean add(Variety variety) {

        try {
            varietyRepository.saveAndFlush(variety);
            VegeInfo vegeInfo = variety.getVegeInfo();
            vegeInfo.getVarieties().add(variety);
            vegeInfoRepository.save(vegeInfo);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Transactional
    @Override
    public boolean delete(String varietyId) {
        try {
            Variety variety = varietyRepository.findByVarietyId(Integer.parseInt(varietyId));
            VegeInfo vegeInfo = variety.getVegeInfo();
            vegeInfo.getVarieties().remove(variety);
            vegeInfoRepository.save(vegeInfo);
            varietyRepository.delete(variety);
            if(variety.getImgUuid()!=null){
                Image image = imageRepository.findByUuid(variety.getImgUuid());
                imageRepository.delete(image);
            }
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Transactional
    @Override
    public boolean update(Variety variety) {

        try {
            varietyRepository.saveAndFlush(variety);
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
