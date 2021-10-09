package com.vege.controller;

import com.alibaba.fastjson.JSONObject;
import com.vege.model.KnowledgeCategory;
import com.vege.model.VegeInfo;
import com.vege.model.VegeKnowledge;
import com.vege.service.KnowledgeCategoryService;
import com.vege.service.VegeInfoService;
import com.vege.service.VegeKnowledgeService;
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
public class VegeKnowledgeController extends BaseController {

    private final VegeKnowledgeService vegeKnowledgeService;

    private final VegeInfoService vegeInfoService;

    private final KnowledgeCategoryService knowledgeCategoryService;

    @Autowired
    public VegeKnowledgeController(VegeKnowledgeService vegeKnowledgeService,VegeInfoService vegeInfoService,KnowledgeCategoryService knowledgeCategoryService) {
        this.vegeKnowledgeService = vegeKnowledgeService;
        this.vegeInfoService = vegeInfoService;
        this.knowledgeCategoryService = knowledgeCategoryService;
    }

    @RequestMapping(value = "/add_vegeknowledge.json", method = RequestMethod.POST)
    public Map add(HttpServletRequest request, HttpServletResponse response) {

        List<String> fields = Arrays.asList( "headline", "content", "kcId", "vegeId");
        Map<String, String> data = buildData(request,fields);
        VegeKnowledge vegeKnowledge = new VegeKnowledge();
        vegeKnowledge.setHeadline(data.get("headline"));
        vegeKnowledge.setContent(data.get("content"));

        VegeInfo vegeInfo = vegeInfoService.queryById(data.get("vegeId"));
        vegeKnowledge.setVegeInfo(vegeInfo);

        if(!data.get("kcId").isEmpty()||Integer.parseInt(data.get("kcId"))!=0){
            KnowledgeCategory knowledgeCategory = knowledgeCategoryService.queryById(data.get("kcId"));
            vegeKnowledge.setKnowledgeCategory(knowledgeCategory);
        }

        vegeKnowledge.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        boolean flag = vegeKnowledgeService.add(vegeKnowledge);
        return buildResponse(flag);
    }

    @RequestMapping(value = "/delete_vegeknowledge.json", method = RequestMethod.GET)
    public Map delete(HttpServletRequest request, HttpServletResponse response) {
        String vkId = request.getParameter("vkId");
        boolean flag = vegeKnowledgeService.delete(vkId);
        return buildResponse(flag);
    }

    @RequestMapping(value = "/update_vegeknowledge.json", method = RequestMethod.POST)
    public Map update(HttpServletRequest request, HttpServletResponse response) {
        List<String> fields = Arrays.asList("headline", "content", "kcId", "vegeId","vkId");
        Map<String, String> data = buildData(request,fields);

        VegeKnowledge vegeKnowledge = new VegeKnowledge();
        vegeKnowledge.setVkId(Integer.parseInt(data.get("vkId")));

        vegeKnowledge.setHeadline(data.get("headline"));
        vegeKnowledge.setContent(data.get("content"));

        VegeInfo vegeInfo = vegeInfoService.queryById(data.get("vegeId"));
        vegeKnowledge.setVegeInfo(vegeInfo);

        KnowledgeCategory knowledgeCategory = knowledgeCategoryService.queryById(data.get("kcId"));
        vegeKnowledge.setKnowledgeCategory(knowledgeCategory);

        vegeKnowledge.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        boolean flag = vegeKnowledgeService.update(vegeKnowledge);
        return buildResponse(flag);
    }

    @RequestMapping(value = "/query_vegeknowledge.json", method = {RequestMethod.POST, RequestMethod.GET})
    public Map query(HttpServletRequest request, HttpServletResponse response) {
        List<String> fields = Arrays.asList("kcId", "headline", "page", "size");
        Map<String, String> condition = buildData(request, fields);
        Page<VegeKnowledge> result = vegeKnowledgeService.query(condition);
        long total = vegeKnowledgeService.queryTotal(condition);
        JSONObject data = new JSONObject();
        data.put("total", total);
        data.put("rows", result.getContent());
        return buildResponse(data);
    }

    @RequestMapping(value = "/query_vegeknowledgebyid.json", method = {RequestMethod.GET, RequestMethod.GET})
    public Map queryById(HttpServletRequest request, HttpServletResponse response) {
        String vkId = request.getParameter("vkId");

        VegeKnowledge vegeKnowledge = vegeKnowledgeService.queryById(vkId);
        JSONObject data = new JSONObject();
        data.put("vkId", vegeKnowledge.getVkId());
        data.put("headline", vegeKnowledge.getHeadline());
        data.put("content", vegeKnowledge.getContent());
        data.put("vegeInfo",vegeKnowledge.getVegeInfo());
        data.put("updateTime",vegeKnowledge.getUpdateTime());
        data.put("knowledgeCategory",vegeKnowledge.getKnowledgeCategory());
        data.put("vegeNameMap",vegeInfoService.getVegeIdAndName());
        data.put("knowledgeCategoryMap", knowledgeCategoryService.getKcIdAndName());
        return buildResponse(data);
    }

    @RequestMapping(value = "/load_knowledgecategory.json", method = RequestMethod.GET)
    public Map loadKnowledgeCategory(HttpServletRequest request, HttpServletResponse response) {

        Map<String,String> knowledgeCategoryName = knowledgeCategoryService.getKcIdAndName();
        JSONObject data = new JSONObject();
        data.put("knowledgeCategoryName",knowledgeCategoryName);
        data.put("vegeNameMap",vegeInfoService.getVegeIdAndName());
        return buildResponse(data);
    }
}