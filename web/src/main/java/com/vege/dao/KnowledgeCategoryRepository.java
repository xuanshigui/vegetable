package com.vege.dao;

import com.vege.model.KnowledgeCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface KnowledgeCategoryRepository extends JpaRepository<KnowledgeCategory, Integer> {


    KnowledgeCategory findByKcId(Integer kcId);

    Page<KnowledgeCategory> findAll(Pageable pageable);

    Page<KnowledgeCategory> findAllByKnowledgeCategoryNameLike(String knowledgeCategory, Pageable pageable);

}
