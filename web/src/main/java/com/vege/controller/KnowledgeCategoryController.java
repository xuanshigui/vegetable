package com.vege.controller;

import com.alibaba.fastjson.JSONObject;
import com.vege.model.KnowledgeCategory;
import com.vege.service.KnowledgeCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/")
public class KnowledgeCategoryController extends BaseController {

    private final KnowledgeCategoryService knowledgecategoryService;


    @Autowired
    public KnowledgeCategoryController(KnowledgeCategoryService knowledgecategoryService) {
        this.knowledgecategoryService = knowledgecategoryService;
    }

    @RequestMapping(value = "/add_knowledgecategory.json", method = RequestMethod.POST)
    public Map add(HttpServletRequest request, HttpServletResponse response) {

        List<String> fields = Arrays.asList("knowledgeCategoryName", "introduction", "updateTime");
        Map<String, String> data = buildData(request,fields);
        KnowledgeCategory knowledgeCategory = new KnowledgeCategory();
        knowledgeCategory.setKnowledgeCategoryName(data.get("knowledgeCategoryName"));
        knowledgeCategory.setIntroduction(data.get("introduction"));
        knowledgeCategory.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        KnowledgeCategory knowledgeCategoryNew = knowledgecategoryService.add(knowledgeCategory);
        boolean flag = false;
        if(knowledgeCategoryNew.getKcId()!=null){
            flag = true;
        }
        return buildResponse(flag);
    }

    @RequestMapping(value = "/delete_knowledgecategory.json", method = RequestMethod.GET)
    public Map delete(HttpServletRequest request, HttpServletResponse response) {
        String kcId = request.getParameter("kcId");
        boolean flag = knowledgecategoryService.delete(kcId);
        return buildResponse(flag);
    }

    @RequestMapping(value = "/update_knowledgecategory.json", method = RequestMethod.POST)
    public Map update(HttpServletRequest request, HttpServletResponse response) {
        List<String> fields = Arrays.asList("kcId", "knowledgeCategoryName", "introduction", "updateTime");
        Map<String, String> data = buildData(request,fields);
        KnowledgeCategory knowledgeCategory = knowledgecategoryService.queryById(data.get("kcId"));
        knowledgeCategory.setKnowledgeCategoryName(data.get("knowledgeCategoryName"));
        knowledgeCategory.setIntroduction(data.get("introduction"));
        knowledgeCategory.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        KnowledgeCategory knowledgeCategoryNew = knowledgecategoryService.update(knowledgeCategory);
        boolean flag = false;
        if(knowledgeCategoryNew.getKcId()!=null){
            flag = true;
        }
        return buildResponse(flag);
    }


    @RequestMapping(value = "/query_knowledgecategory.json", method = {RequestMethod.POST, RequestMethod.GET})
    public Map query(HttpServletRequest request, HttpServletResponse response) {
        List<String> fields = Arrays.asList("knowledgeCategoryName", "page", "size");
        Map<String, String> condition = buildData(request, fields);
        Page<KnowledgeCategory> result = knowledgecategoryService.query(condition);
        long total = result.getSize();
        JSONObject data = new JSONObject();
        data.put("total", total);
        data.put("rows", result.getContent());
        return buildResponse(data);
    }

    @RequestMapping(value = "/query_knowledgecategorybyid.json", method = {RequestMethod.POST, RequestMethod.GET})
    public Map queryById(HttpServletRequest request, HttpServletResponse response) {
        String kcId = request.getParameter("kcId");

        KnowledgeCategory knowledgeCategory = knowledgecategoryService.queryById(kcId);
        JSONObject data = new JSONObject();
        data.put("kcId",knowledgeCategory.getKcId());
        data.put("knowledgeCategoryName",knowledgeCategory.getKnowledgeCategoryName());
        data.put("introduction",knowledgeCategory.getIntroduction());
        data.put("updateTime",knowledgeCategory.getUpdateTime());
        return buildResponse(data);
    }

}
