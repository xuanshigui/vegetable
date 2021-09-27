package com.vege.controller;

import com.alibaba.fastjson.JSONObject;
import com.vege.model.BreedStage;
import com.vege.model.EnvParam;
import com.vege.service.BreedStageService;
import com.vege.service.EnvParamService;
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
public class EnvParamController extends BaseController {

    private final EnvParamService envparamService;

    private final BreedStageService breedStageService;

    @Autowired
    public EnvParamController(EnvParamService envparamService, BreedStageService breedStageService) {
        this.envparamService = envparamService;
        this.breedStageService = breedStageService;
    }

    @RequestMapping(value = "/add_envparam.json", method = RequestMethod.POST)
    public Map add(HttpServletRequest request, HttpServletResponse response) {

        List<String> fields = Arrays.asList("bsId","paramName", "type", "boundH", "boundL", "paramUnit", "note");
        Map<String, String> data = buildData(request,fields);
        EnvParam envParam = new EnvParam();
        envParam.setParaName(data.get("paramName"));
        BreedStage bs = breedStageService.queryById(data.get("bsId"));
        envParam.setBreedStage(bs);
        if("0".equals(data.get("type"))){
            envParam.setType(false);
        }else {
            envParam.setType(true);
        }
        envParam.setBoundH(Double.parseDouble(data.get("boundH")));
        envParam.setBoundL(Double.parseDouble(data.get("boundL")));
        envParam.setParamUnit(data.get("paramUnit"));
        envParam.setNote(data.get("note"));
        envParam.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        EnvParam envParamNew = envparamService.add(envParam);
        boolean flag = false;
        if(envParamNew.getEpId()!=null){
            flag = true;
        }
        return buildResponse(flag);
    }

    @RequestMapping(value = "/delete_envparam.json", method = RequestMethod.GET)
    public Map delete(HttpServletRequest request, HttpServletResponse response) {
        String envparamid = request.getParameter("epId");
        boolean flag = envparamService.delete(envparamid);
        return buildResponse(flag);
    }

    @RequestMapping(value = "/update_envparam.json", method = RequestMethod.POST)
    public Map update(HttpServletRequest request, HttpServletResponse response) {
        List<String> fields = Arrays.asList("epId","bsId","paramName", "type", "boundH", "boundL", "paramUnit", "note");
        Map<String, String> data = buildData(request,fields);
        EnvParam envParam = new EnvParam();
        envParam.setEpId(Integer.parseInt(data.get("epId")));
        envParam.setParaName(data.get("paramName"));
        BreedStage bs = breedStageService.queryById(data.get("bsId"));
        envParam.setBreedStage(bs);
        if(Integer.parseInt(data.get("type"))==0){
            envParam.setType(false);
        }else {
            envParam.setType(true);
        }
        envParam.setBoundH(Double.parseDouble(data.get("boundH")));
        envParam.setBoundL(Double.parseDouble(data.get("boundL")));
        envParam.setParamUnit(data.get("paramUnit"));
        envParam.setNote(data.get("note"));
        envParam.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        EnvParam envParamNew = envparamService.update(envParam);
        boolean flag = false;
        if(envParamNew.getEpId()!=null){
            flag = true;
        }
        return buildResponse(flag);
    }


    @RequestMapping(value = "/query_envparam.json", method = {RequestMethod.POST, RequestMethod.GET})
    public Map query(HttpServletRequest request, HttpServletResponse response) {
        List<String> fields = Arrays.asList("stageName","paramName", "page", "size");
        Map<String, String> condition = buildData(request, fields);
        Page<EnvParam> result = envparamService.query(condition);
        long total = result.getSize();
        JSONObject data = new JSONObject();
        data.put("total", total);
        data.put("rows", result.getContent());
        return buildResponse(data);
    }

    @RequestMapping(value = "/query_envparambyid.json", method = {RequestMethod.POST, RequestMethod.GET})
    public Map queryById(HttpServletRequest request, HttpServletResponse response) {
        String epId = request.getParameter("epId");

        EnvParam envParam = envparamService.queryById(epId);
        JSONObject data = new JSONObject();
        data.put("epId",envParam.getEpId());
        data.put("paramName",envParam.getParaName());
        //传回所有BreedStage
        Map breedStages = breedStageService.getBsIdAndName();
        data.put("breedStageMap",breedStages);
        data.put("stageName", envParam.getBreedStage().getStageName());
        if(envParam.isType()){
            data.put("type","1");
        }else{
            data.put("type","0");
        }
        data.put("boundH",envParam.getBoundH());
        data.put("boundL",envParam.getBoundL());
        data.put("paramUnit",envParam.getParamUnit());
        data.put("note",envParam.getNote());
        data.put("updateTime",envParam.getUpdateTime());
        return buildResponse(data);
    }

    //loadBreedStage
    @RequestMapping(value = "/load_breedstage.json", method = {RequestMethod.GET})
    public Map getBreedStageNames(HttpServletRequest request, HttpServletResponse response) {
        Map<String,String> breedStage = breedStageService.getBsIdAndName();
        JSONObject data = new JSONObject();
        data.put("breedStageMap",breedStage);
        return buildResponse(data);
    }
}