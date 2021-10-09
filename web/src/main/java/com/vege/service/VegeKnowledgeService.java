package com.vege.service;

import com.vege.model.VegeKnowledge;
import org.springframework.data.domain.Page;

import java.util.Map;

public interface VegeKnowledgeService {

    boolean add(VegeKnowledge data);

    boolean delete(String vegeId);

    boolean update(VegeKnowledge data);

    Page<VegeKnowledge> query(Map<String, String> condition);

    long queryTotal(Map<String, String> condition);

    VegeKnowledge queryById(String vegeId);

}
