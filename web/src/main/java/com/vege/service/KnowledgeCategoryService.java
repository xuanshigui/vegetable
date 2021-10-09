package com.vege.service;

import com.vege.model.KnowledgeCategory;
import org.springframework.data.domain.Page;

import java.util.Map;

public interface KnowledgeCategoryService {

    public KnowledgeCategory add(KnowledgeCategory knowledgeCategory);

    public boolean delete(String kcId);

    public KnowledgeCategory update(KnowledgeCategory knowledgeCategory);

    public Page<KnowledgeCategory> query(Map<String, String> condition);

    public long queryTotal(Map<String, String> condition);

    public KnowledgeCategory queryById(String knowledgeCategoryId);

    Map<String,String> getKcIdAndName();
}
