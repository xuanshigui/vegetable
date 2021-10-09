package com.vege.service.impl;

import com.vege.dao.VegeKnowledgeRepository;
import com.vege.model.VegeKnowledge;
import com.vege.service.VegeKnowledgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class VegeKnowledgeServiceImpl extends BaseService implements VegeKnowledgeService {


    @Autowired
    VegeKnowledgeRepository vegeKnowledgeRepository;

    @Override
    public boolean add(VegeKnowledge vegeKnowledge) {
        try {
            vegeKnowledgeRepository.save(vegeKnowledge);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean delete(String vegeKnowledgeId) {
        try {
            VegeKnowledge vegeKnowledge = vegeKnowledgeRepository.findByVkId(Integer.parseInt(vegeKnowledgeId));
            vegeKnowledgeRepository.delete(vegeKnowledge);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean update(VegeKnowledge vegeKnowledge) {
        try {
            vegeKnowledgeRepository.save(vegeKnowledge);
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