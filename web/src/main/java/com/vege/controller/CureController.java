package com.vege.controller;

import com.alibaba.fastjson.JSONObject;
import com.vege.model.Cure;
import com.vege.model.Disease;
import com.vege.service.CureService;
import com.vege.service.DiseaseService;
import com.vege.service.VegeInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/")
public class CureController extends BaseController {

    private final CureService cureService;
    private final DiseaseService diseaseService;
    private final VegeInfoService vegeInfoService;

    @Autowired
    public CureController(CureService cureService, DiseaseService diseaseService,VegeInfoService vegeInfoService) {
        this.cureService = cureService;
        this.diseaseService = diseaseService;
        this.vegeInfoService = vegeInfoService;
    }

    @RequestMapping(value = "/add_cure.json", method = RequestMethod.POST)
    public Map add(HttpServletRequest request, HttpServletResponse response) {

        List<String> fields = Arrays.asList("cureName", "agricontrol", "biocontrol","chemcontrol", "diseaseId");
        Map<String, String> data = buildData(request,fields);
        Cure cure = new Cure();
        cure.setCureName(data.get("cureName"));
        cure.setAgriControl(data.get("agricontrol"));
        cure.setBioControl(data.get("biocontrol"));
        cure.setChemControl(data.get("chemcontrol"));
        Disease disease = diseaseService.queryById(data.get("diseaseId"));
        cure.setDisease(disease);
        boolean flag = cureService.add(cure);
        return buildResponse(flag);
    }

    @RequestMapping(value = "/delete_cure.json", method = RequestMethod.GET)
    public Map delete(HttpServletRequest request, HttpServletResponse response) {
        String cureId = request.getParameter("cureId");
        boolean flag = cureService.delete(cureId);
        return buildResponse(flag);
    }

    @RequestMapping(value = "/update_cure.json", method = RequestMethod.POST)
    public Map update(HttpServletRequest request, HttpServletResponse response) {
        List<String> fields = Arrays.asList("cureId", "cureName", "agricontrol", "biocontrol","chemcontrol", "diseaseId");
        Map<String, String> data = buildData(request,fields);
        Cure cure = cureService.queryById(data.get("cureId"));
        cure.setCureName(data.get("cureName"));
        cure.setAgriControl(data.get("agricontrol"));
        cure.setBioControl(data.get("biocontrol"));
        cure.setChemControl(data.get("chemcontrol"));
        Disease disease = diseaseService.queryById(data.get("diseaseId"));
        cure.setDisease(disease);
        boolean flag = cureService.update(cure);
        return buildResponse(flag);
    }

    @RequestMapping(value = "/query_cure.json", method = {RequestMethod.POST, RequestMethod.GET})
    public Map query(HttpServletRequest request, HttpServletResponse response) {
        List<String> fields = Arrays.asList("diseaseName", "page", "size");
        Map<String, String> condition = buildData(request, fields);
        Page<Cure> result = cureService.query(condition);
        long total = cureService.queryTotal(condition);
        JSONObject data = new JSONObject();
        data.put("total", result.getTotalElements());
        data.put("rows", result.getContent());
        return buildResponse(data);
    }

    @RequestMapping(value = "/query_curebyid.json", method = {RequestMethod.POST, RequestMethod.GET})
    public Map queryById(HttpServletRequest request, HttpServletResponse response) {
        String cureid = request.getParameter("cureId");

        Cure cure = cureService.queryById(cureid);
        JSONObject data = new JSONObject();
        data.put("cureId", cure.getCureId());
        data.put("cureName", cure.getCureName());
        data.put("agricontrol", cure.getAgriControl());
        data.put("biocontrol", cure.getBioControl());
        data.put("chemcontrol", cure.getChemControl());
        data.put("diseaseName", cure.getDisease().getDiseaseName());
        data.put("vegeName", cure.getDisease().getVegeInfo().getVegeName());
        //载入列表选项
        Map<String,String> vegeNameMap = vegeInfoService.getVegeIdAndName();
        data.put("vegeNameMap",vegeNameMap);
        Integer vegeId = cure.getDisease().getVegeInfo().getVegeId();
        Map<Integer,String> diseaseNameMap = diseaseService.getDiseaseMapByVegeId(vegeId);
        data.put("diseaseNameMap",diseaseNameMap);
        return buildResponse(data);
    }

}
