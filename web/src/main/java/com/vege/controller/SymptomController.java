package com.vege.controller;

import com.alibaba.fastjson.JSONObject;
import com.vege.constants.Constants;
import com.vege.model.Disease;
import com.vege.model.Symptom;
import com.vege.service.DiseaseService;
import com.vege.service.SymptomService;
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
public class SymptomController extends BaseController {

    private final SymptomService symptomService;
    private final DiseaseService diseaseService;
    private final VegeInfoService vegeInfoService;

    @Autowired
    public SymptomController(SymptomService symptomService, DiseaseService diseaseService, VegeInfoService vegeInfoService) {
        this.symptomService = symptomService;
        this.diseaseService = diseaseService;
        this.vegeInfoService = vegeInfoService;
    }

    @RequestMapping(value = "/add_symptom.json", method = RequestMethod.POST)
    public Map add(HttpServletRequest request, HttpServletResponse response) {

        List<String> fields = Arrays.asList( "symptomName", "description", "diseaseId", "location");
        Map<String, String> data = buildData(request,fields);
        Symptom symptom = new Symptom();
        symptom.setSymptomName(data.get("symptomName"));
        Disease disease = diseaseService.queryById(data.get("diseaseId"));
        symptom.setDisease(disease);
        symptom.setDescription(data.get("description"));
        symptom.setLocation(data.get("location"));
        symptom.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        boolean flag = symptomService.add(symptom);
        return buildResponse(flag);
    }

    @RequestMapping(value = "/delete_symptom.json", method = RequestMethod.GET)
    public Map delete(HttpServletRequest request, HttpServletResponse response) {
        String symptomId = request.getParameter("symptomId");
        boolean flag = symptomService.delete(symptomId);
        return buildResponse(flag);
    }

    @RequestMapping(value = "/update_symptom.json", method = RequestMethod.POST)
    public Map update(HttpServletRequest request, HttpServletResponse response) {
        List<String> fields = Arrays.asList("symptomId", "symptomName", "description", "diseaseId", "location");
        Map<String, String> data = buildData(request,fields);
        Symptom symptom = symptomService.queryById(data.get("symptomId"));
        symptom.setSymptomName(data.get("symptomName"));
        Disease disease = diseaseService.queryById(data.get("diseaseId"));
        symptom.setDisease(disease);
        symptom.setDescription(data.get("description"));
        symptom.setLocation(data.get("location"));
        symptom.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        boolean flag = symptomService.update(symptom);
        return buildResponse(flag);
    }

    @RequestMapping(value = "/query_symptom.json", method = {RequestMethod.POST, RequestMethod.GET})
    public Map query(HttpServletRequest request, HttpServletResponse response) {
        List<String> fields = Arrays.asList("symptomName","diseaseName", "page", "size");
        Map<String, String> condition = buildData(request, fields);
        Page<Symptom> result = symptomService.query(condition);
        long total = symptomService.queryTotal(condition);
        JSONObject data = new JSONObject();
        data.put("total", total);
        data.put("rows", result.getContent());
        return buildResponse(data);
    }

    @RequestMapping(value = "/query_symptombyid.json", method = {RequestMethod.POST, RequestMethod.GET})
    public Map queryById(HttpServletRequest request, HttpServletResponse response) {
        String symptomid = request.getParameter("symptomId");

        Symptom symptom = symptomService.queryById(symptomid);
        JSONObject data = new JSONObject();
        data.put("symptomId", symptom.getSymptomId());
        data.put("diseaseName", symptom.getDisease().getDiseaseName());
        data.put("vegeName", symptom.getDisease().getVegeInfo().getVegeName());
        data.put("symptomName", symptom.getSymptomName());
        data.put("disease", symptom.getDisease());
        data.put("description", symptom.getDescription());
        data.put("location", symptom.getLocation());
        data.put("updateTime", symptom.getUpdateTime());
        Integer vegeId = symptom.getDisease().getVegeInfo().getVegeId();
        Map<Integer,String> diseaseNameMap = diseaseService.getDiseaseMaoByVegeId(vegeId);
        data.put("diseaseNameMap",diseaseNameMap);


        //载入列表选项
        Map<String,String> vegeNameMap = vegeInfoService.getVegeIdAndName();
        data.put("vegeNameMap",vegeNameMap);
        Map<String,String> symptomLoctationMap = Constants.SYMPTOM_LOCATION_MAP;
        data.put("symptomLoctationMap",symptomLoctationMap);
        return buildResponse(data);
    }

    @RequestMapping(value = "/load_diseasename.json", method = {RequestMethod.GET})
    public Map getDiseaseNames(HttpServletRequest request, HttpServletResponse response) {
        String vegeId = request.getParameter("vegeId");
        Map<Integer,String> diseaseNameMap = diseaseService.getDiseaseMaoByVegeId(Integer.parseInt(vegeId));
        JSONObject data = new JSONObject();
        data.put("diseaseNameMap",diseaseNameMap);
        return buildResponse(data);
    }

    @RequestMapping(value = "/load_locations.json", method = {RequestMethod.GET})
    public Map loadVegeNameAndLocations(HttpServletRequest request, HttpServletResponse response) {
        JSONObject data = new JSONObject();
        Map<String,String> vegeNameMap = vegeInfoService.getVegeIdAndName();
        data.put("vegeNameMap",vegeNameMap);
        Map<String,String> symptomLoctationMap = Constants.SYMPTOM_LOCATION_MAP;
        data.put("symptomLoctationMap",symptomLoctationMap);
        return buildResponse(data);
    }
}
