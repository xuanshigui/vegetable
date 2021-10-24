package com.vege.controller;

import com.alibaba.fastjson.JSONObject;
import com.vege.constants.Constants;
import com.vege.model.Cure;
import com.vege.model.Disease;
import com.vege.model.Symptom;
import com.vege.model.VegeInfo;
import com.vege.service.DiseaseService;
import com.vege.service.ImageService;
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
public class DiseaseController extends BaseController {

    private final DiseaseService diseaseService;
    private final VegeInfoService vegeInfoService;
    private final ImageService imageService;

    @Autowired
    public DiseaseController(DiseaseService diseaseService, VegeInfoService vegeInfoService, ImageService imageService) {
        this.diseaseService = diseaseService;
        this.vegeInfoService = vegeInfoService;
        this.imageService = imageService;
    }

    @RequestMapping(value = "/add_disease.json", method = RequestMethod.POST)
    public Map add(HttpServletRequest request, HttpServletResponse response) {

        List<String> fields = Arrays.asList( "diseaseName", "diseaseImg0","diseaseImg1","diseaseImg2","diseaseImg3", "etiology", "vegeId", "regularity", "diseaseType");
        Map<String, String> data = buildData(request,fields);
        Disease disease = new Disease();
        disease.setDiseaseName(data.get("diseaseName"));
        VegeInfo vegeInfo = vegeInfoService.queryById(data.get("vegeId"));
        disease.setVegeInfo(vegeInfo);
        //存储图片
        if (data.get("diseaseImg0")!=null&&!data.get("diseaseImg0").equals("")){
            //建立图片
            String imgUuid = imageService.add(data.get("diseaseImg0"),disease.getClass().getSimpleName());
            disease.setImgUuid0(imgUuid);
        }
        if (data.get("diseaseImg1")!=null&&!data.get("diseaseImg1").equals("")){
            //建立图片
            String imgUuid = imageService.add(data.get("diseaseImg1"),disease.getClass().getSimpleName());
            disease.setImgUuid1(imgUuid);
        }
        if (data.get("diseaseImg2")!=null&&!data.get("diseaseImg2").equals("")){
            //建立图片
            String imgUuid = imageService.add(data.get("diseaseImg2"),disease.getClass().getSimpleName());
            disease.setImgUuid2(imgUuid);
        }
        if (data.get("diseaseImg3")!=null&&!data.get("diseaseImg3").equals("")){
            //建立图片
            String imgUuid = imageService.add(data.get("diseaseImg3"),disease.getClass().getSimpleName());
            disease.setImgUuid3(imgUuid);
        }

        disease.setEtiology(data.get("etiology"));
        disease.setRegularity(data.get("regularity"));
        disease.setDiseaseType(Integer.parseInt(data.get("diseaseType")));
        disease.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        boolean flag = diseaseService.add(disease);
        return buildResponse(flag);
    }

    @RequestMapping(value = "/delete_disease.json", method = RequestMethod.GET)
    public Map delete(HttpServletRequest request, HttpServletResponse response) {
        String diseaseId = request.getParameter("diseaseId");
        boolean flag = diseaseService.delete(diseaseId);
        return buildResponse(flag);
    }

    @RequestMapping(value = "/update_disease.json", method = RequestMethod.POST)
    public Map update(HttpServletRequest request, HttpServletResponse response) {
        List<String> fields = Arrays.asList("diseaseId", "diseaseName", "diseaseImg0","diseaseImg1","diseaseImg2","diseaseImg3", "imguuid0", "imguuid1", "imguuid2", "imguuid3", "etiology", "vegeId", "regularity", "diseaseType");
        Map<String, String> data = buildData(request,fields);
        Disease disease = diseaseService.queryById(data.get("diseaseId"));
        if(Integer.parseInt(data.get("vegeId"))!=0 && data.get("vegeId")!=null){
            VegeInfo vegeInfo = disease.getVegeInfo();
            vegeInfo.getDiseases().remove(disease);
            VegeInfo newVegeInfo = vegeInfoService.queryById(data.get("vegeId"));
            newVegeInfo.getDiseases().add(disease);
            vegeInfoService.update(newVegeInfo);
            disease.setVegeInfo(newVegeInfo);
        }
        disease.setDiseaseName(data.get("diseaseName"));
        if (data.get("imguuid0")==null||data.get("imguuid0").equals("")){
            //建立图片
            String imgUuid = imageService.add(data.get("diseaseImg0"),disease.getClass().getSimpleName());
            disease.setImgUuid0(imgUuid);
        } else {
            disease.setImgUuid0(data.get("imguuid0"));
        }
        if (data.get("imguuid1")==null||data.get("imguuid1").equals("")){
            //建立图片
            String imgUuid = imageService.add(data.get("diseaseImg1"),disease.getClass().getSimpleName());
            disease.setImgUuid1(imgUuid);
        } else {
            disease.setImgUuid1(data.get("imguuid1"));
        }
        if (data.get("imguuid2")==null||data.get("imguuid2").equals("")){
            //建立图片
            String imgUuid = imageService.add(data.get("diseaseImg2"),disease.getClass().getSimpleName());
            disease.setImgUuid2(imgUuid);
        } else {
            disease.setImgUuid2(data.get("imguuid2"));
        }
        if (data.get("imguuid3")==null||data.get("imguuid3").equals("")){
            //建立图片
            String imgUuid = imageService.add(data.get("diseaseImg3"),disease.getClass().getSimpleName());
            disease.setImgUuid3(imgUuid);
        } else {
            disease.setImgUuid3(data.get("imguuid0"));
        }
        disease.setEtiology(data.get("etiology"));
        disease.setRegularity(data.get("regularity"));
        disease.setDiseaseType(Integer.parseInt(data.get("diseaseType")));
        disease.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        boolean flag = diseaseService.update(disease);
        return buildResponse(flag);
    }

    @RequestMapping(value = "/query_disease.json", method = {RequestMethod.POST, RequestMethod.GET})
    public Map query(HttpServletRequest request, HttpServletResponse response) {
        List<String> fields = Arrays.asList("diseaseName","vegeName", "page", "size");
        Map<String, String> condition = buildData(request, fields);
        Page<Disease> result = diseaseService.query(condition);
        long total = diseaseService.queryTotal(condition);
        JSONObject data = new JSONObject();
        data.put("total", result.getTotalElements());
        data.put("rows", result.getContent());
        return buildResponse(data);
    }

    @RequestMapping(value = "/query_diseasebyid.json", method = {RequestMethod.POST, RequestMethod.GET})
    public Map queryById(HttpServletRequest request, HttpServletResponse response) {
        String diseaseid = request.getParameter("diseaseId");

        Disease disease = diseaseService.queryById(diseaseid);
        JSONObject data = new JSONObject();
        data.put("diseaseId", disease.getDiseaseId());
        data.put("vegeName", disease.getVegeInfo().getVegeName());
        data.put("diseaseName", disease.getDiseaseName());

        //载入图片
        String imgPath;
        if(disease.getImgUuid0()!=null&&!"".equals(disease.getImgUuid0())){
            imgPath = imageService.queryPathByUuid(disease.getImgUuid0());
            data.put("imgPath0", "http://127.0.0.1:8080/show_img?imgPath="+imgPath);
            data.put("imgUuid0", disease.getImgUuid0());
        }
        if(disease.getImgUuid1()!=null&&!"".equals(disease.getImgUuid1())){
            imgPath = imageService.queryPathByUuid(disease.getImgUuid1());
            data.put("imgPath1", "http://127.0.0.1:8080/show_img?imgPath="+imgPath);
            data.put("imgUuid1", disease.getImgUuid1());
        }
        if(disease.getImgUuid2()!=null&&!"".equals(disease.getImgUuid2())){
            imgPath = imageService.queryPathByUuid(disease.getImgUuid2());
            data.put("imgPath2", "http://127.0.0.1:8080/show_img?imgPath="+imgPath);
            data.put("imgUuid2", disease.getImgUuid2());
        }
        if(disease.getImgUuid3()!=null&&!"".equals(disease.getImgUuid3())){
            imgPath = imageService.queryPathByUuid(disease.getImgUuid3());
            data.put("imgPath3", "http://127.0.0.1:8080/show_img?imgPath="+imgPath);
            data.put("imgUuid3", disease.getImgUuid3());
        }

        List<Symptom> symptomList = disease.getSymptoms();
        if(symptomList!=null){
            data.put("symptomList", symptomList);
        }
        List<Cure> cureList = disease.getCures();
        if(cureList!=null){
            data.put("cureList", cureList);
        }

        data.put("regularity", disease.getRegularity());
        data.put("etiology", disease.getEtiology());
        data.put("diseaseType",disease.getDiseaseType());
        data.put("updateTime",disease.getUpdateTime());

        //载入列表选项
        Map<String,String> vegeNameMap = vegeInfoService.getVegeIdAndName();
        data.put("vegeNameMap",vegeNameMap);
        data.put("diseaseTypeMap",Constants.DISEASE_TYPE_MAP);
        return buildResponse(data);
    }

    @RequestMapping(value = "/load_diseasetype.json", method = {RequestMethod.GET})
    public Map getVegeNames(HttpServletRequest request, HttpServletResponse response) {
        Map<String,String> vegeNames = vegeInfoService.getVegeIdAndName();
        JSONObject data = new JSONObject();
        data.put("vegeNameMap",vegeNames);
        data.put("diseaseTypeMap",Constants.DISEASE_TYPE_MAP);
        return buildResponse(data);
    }
}
