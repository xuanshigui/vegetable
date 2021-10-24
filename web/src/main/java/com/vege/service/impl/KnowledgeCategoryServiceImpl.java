package com.vege.service.impl;

import com.vege.dao.KnowledgeCategoryRepository;
import com.vege.model.KnowledgeCategory;
import com.vege.model.VegeKnowledge;
import com.vege.service.KnowledgeCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class KnowledgeCategoryServiceImpl extends BaseService implements KnowledgeCategoryService {


    @Autowired
    KnowledgeCategoryRepository knowledgeCategoryRepository;

    @Override
    public KnowledgeCategory add(KnowledgeCategory knowledgeCategory) {
        return knowledgeCategoryRepository.save(knowledgeCategory);
    }

    @Override
    public boolean delete(String knowledgeCategoryId) {
        try {
            KnowledgeCategory knowledgeCategory = knowledgeCategoryRepository.findByKcId(Integer.parseInt(knowledgeCategoryId));
            List<VegeKnowledge> vegeKnowledgeList = knowledgeCategory.getVegeknowledges();
            if(vegeKnowledgeList!=null||vegeKnowledgeList.size()!=0){
                return false;
            }
            knowledgeCategoryRepository.deleteById(Integer.parseInt(knowledgeCategoryId));
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public KnowledgeCategory update(KnowledgeCategory knowledgeCategory) {
        return knowledgeCategoryRepository.save(knowledgeCategory);
    }

    @Override
    public Page<KnowledgeCategory> query(Map<String, String> condition) {

        String kcNameStr = condition.get("knowledgeCategoryName");
        if(kcNameStr != null && !kcNameStr.equals("")){
            kcNameStr = "%" + kcNameStr + "%";
            return knowledgeCategoryRepository.findAllByKnowledgeCategoryNameLike(kcNameStr,getPageable(condition));
        }
        return knowledgeCategoryRepository.findAll(getPageable(condition));
    }

    @Override
    public long queryTotal(Map<String, String> condition) {
        return knowledgeCategoryRepository.count();
    }

    @Override
    public KnowledgeCategory queryById(String knowledgeCategoryId) {
        return knowledgeCategoryRepository.findByKcId(Integer.parseInt(knowledgeCategoryId));
    }

    @Override
    public Map<String, String> getKcIdAndName() {
        Map<String,String> idNameMap = new HashMap<>();
        List<KnowledgeCategory> bsList = knowledgeCategoryRepository.findAll();
        for(KnowledgeCategory knowledgeCategory:bsList){
            idNameMap.put(knowledgeCategory.getKcId().toString(),knowledgeCategory.getKnowledgeCategoryName());
        }
        return idNameMap;
    }

}
