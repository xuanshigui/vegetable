package com.vege.service.impl;

import com.vege.dao.KnowledgeCategoryRepository;
import com.vege.dao.VegeInfoRepository;
import com.vege.dao.VegeKnowledgeRepository;
import com.vege.model.KnowledgeCategory;
import com.vege.model.VegeInfo;
import com.vege.model.VegeKnowledge;
import com.vege.service.VegeKnowledgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Map;

@Service
public class VegeKnowledgeServiceImpl extends BaseService implements VegeKnowledgeService {


    @Autowired
    VegeKnowledgeRepository vegeKnowledgeRepository;

    @Autowired
    KnowledgeCategoryRepository knowledgeCategoryRepository;

    @Autowired
    VegeInfoRepository vegeInfoRepository;

    @Override
    @Transactional
    public boolean add(VegeKnowledge vegeKnowledge) {
        try {
            vegeKnowledgeRepository.saveAndFlush(vegeKnowledge);
            KnowledgeCategory knowledgeCategory = vegeKnowledge.getKnowledgeCategory();
            knowledgeCategory.getVegeknowledges().add(vegeKnowledge);
            knowledgeCategoryRepository.save(knowledgeCategory);
            if(vegeKnowledge.getVegeInfo()!=null){
                VegeInfo vegeInfo = vegeKnowledge.getVegeInfo();
                vegeInfo.getVegeKnowledges().add(vegeKnowledge);
                vegeInfoRepository.save(vegeInfo);
            }
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Transactional
    @Override
    public boolean delete(String vegeKnowledgeId) {
        try {
            VegeKnowledge vegeKnowledge = vegeKnowledgeRepository.findByVkId(Integer.parseInt(vegeKnowledgeId));
            KnowledgeCategory knowledgeCategory = vegeKnowledge.getKnowledgeCategory();
            knowledgeCategory.getVegeknowledges().remove(vegeKnowledge);
            knowledgeCategoryRepository.save(knowledgeCategory);
            if(vegeKnowledge.getVegeInfo()!=null){
                VegeInfo vegeInfo = vegeKnowledge.getVegeInfo();
                vegeInfo.getVegeKnowledges().remove(vegeKnowledge);
                vegeInfoRepository.save(vegeInfo);
            }
            vegeKnowledgeRepository.delete(vegeKnowledge);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean update(VegeKnowledge vegeKnowledge) {
        try {
            vegeKnowledgeRepository.saveAndFlush(vegeKnowledge);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public Page<VegeKnowledge> query(Map<String, String> condition) {

        String headline = condition.get("headline");
        Integer kcId = Integer.parseInt(condition.get("kcId"));
        Pageable pageable = getPageable(condition);
        if(headline != null && !headline.equals("")){
            headline = "%" + headline + "%";
            if(kcId!=0){
                return vegeKnowledgeRepository.findAllByHeadlineLikeAndKnowledgeCategory_KcId(headline, kcId, pageable);
            }
            return vegeKnowledgeRepository.findAllByHeadlineLike(headline,getPageable(condition));
        }
        if(kcId!=0){
            return vegeKnowledgeRepository.findAllByKnowledgeCategory_KcId(kcId, pageable);
        }
        return vegeKnowledgeRepository.findAll(getPageable(condition));
    }

    @Override
    public long queryTotal(Map<String, String> condition) {
        return vegeKnowledgeRepository.count();
    }

    @Override
    public VegeKnowledge queryById(String vegeKnowledgeId) {
        return vegeKnowledgeRepository.findByVkId(Integer.parseInt(vegeKnowledgeId));
    }


}
