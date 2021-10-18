package com.vege.controller;

import com.alibaba.fastjson.JSONObject;
import com.vege.model.Drug;
import com.vege.service.CureService;
import com.vege.service.DiseaseService;
import com.vege.service.DrugService;
import com.vege.service.VegeInfoService;
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
public class DrugController extends BaseController {

    private final DrugService drugService;
    private final DiseaseService diseaseService;
    private final VegeInfoService vegeInfoService;
    private final CureService cureService;

    @Autowired
    public DrugController(DrugService drugService, DiseaseService diseaseService, VegeInfoService vegeInfoService, CureService cureService) {
        this.drugService = drugService;
        this.diseaseService = diseaseService;
        this.vegeInfoService = vegeInfoService;
        this.cureService = cureService;
    }

    @RequestMapping(value = "/add_drug.json", method = RequestMethod.POST)
    public Map add(HttpServletRequest request, HttpServletResponse response) {

        List<String> fields = Arrays.asList( "drugName", "methods","dosage", "savePeriod", "attention", "cureId");
        Map<String, String> data = buildData(request,fields);
        Drug drug = new Drug();
        drug.setDrugName(data.get("drugName"));
        drug.setMethods(data.get("methods"));
        drug.setDosage(data.get("dosage"));
        drug.setSavePeriod(data.get("savePeriod"));
        drug.setAttention(data.get("attention"));
        drug.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        boolean flag = drugService.add(drug);
        return buildResponse(flag);
    }

    @RequestMapping(value = "/delete_drug.json", method = RequestMethod.GET)
    public Map delete(HttpServletRequest request, HttpServletResponse response) {
        String drugId = request.getParameter("drugId");
        boolean flag = drugService.delete(drugId);
        return buildResponse(flag);
    }

    @RequestMapping(value = "/update_drug.json", method = RequestMethod.POST)
    public Map update(HttpServletRequest request, HttpServletResponse response) {
        List<String> fields = Arrays.asList("drugId", "drugName", "methods","dosage","savePeriod", "attention", "cureId");
        Map<String, String> data = buildData(request,fields);
        Drug drug = drugService.queryById(data.get("drugId"));
        drug.setDrugName(data.get("drugName"));
        drug.setMethods(data.get("methods"));
        drug.setDosage(data.get("dosage"));
        drug.setSavePeriod(data.get("savePeriod"));
        drug.setAttention(data.get("attention"));
        drug.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        boolean flag = drugService.update(drug);
        return buildResponse(flag);
    }

    @RequestMapping(value = "/query_drug.json", method = {RequestMethod.POST, RequestMethod.GET})
    public Map query(HttpServletRequest request, HttpServletResponse response) {
        List<String> fields = Arrays.asList("diseaseName", "page", "size");
        Map<String, String> condition = buildData(request, fields);
        Page<Drug> result = drugService.query(condition);
        long total = drugService.queryTotal(condition);
        JSONObject data = new JSONObject();
        data.put("total", result.getTotalElements());
        data.put("rows", result.getContent());
        return buildResponse(data);
    }

    @RequestMapping(value = "/query_drugbyid.json", method = {RequestMethod.POST, RequestMethod.GET})
    public Map queryById(HttpServletRequest request, HttpServletResponse response) {
        String drugid = request.getParameter("drugId");

        Drug drug = drugService.queryById(drugid);
        JSONObject data = new JSONObject();
        data.put("drugId", drug.getDrugId());
        data.put("drugName", drug.getDrugName());
        data.put("methods", drug.getMethods());
        data.put("dosage", drug.getDosage());
        data.put("savePeriod", drug.getSavePeriod());
        data.put("attention", drug.getAttention());
        data.put("updateTime", drug.getUpdateTime().toString());
        return buildResponse(data);
    }

    @RequestMapping(value = "/load_curename.json", method = {RequestMethod.GET})
    public Map getCureNames(HttpServletRequest request, HttpServletResponse response) {
        String diseaseId = request.getParameter("diseaseId");
        Map<Integer,String> cureNameMap = cureService.getCureMapByDiseaseId(Integer.parseInt(diseaseId));
        JSONObject data = new JSONObject();
        data.put("cureNameMap", cureNameMap);
        return buildResponse(data);
    }


}
