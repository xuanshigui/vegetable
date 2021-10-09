package com.vege.dao;

import com.vege.model.VegeKnowledge;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface VegeKnowledgeRepository extends JpaRepository<VegeKnowledge, Integer> {


    VegeKnowledge findByVkId(Integer vkId);

    Page<VegeKnowledge> findAll(Pageable pageable);

    Page<VegeKnowledge> findAllByHeadlineLike(String headline, Pageable pageable);

    Page<VegeKnowledge> findAllByKnowledgeCategory_KcId(Integer kcId, Pageable pageable);

    Page<VegeKnowledge> findAllByHeadlineLikeAndKnowledgeCategory_KcId(String headline,Integer kcId, Pageable pageable);

}
