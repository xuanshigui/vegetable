package com.vege.controller;

import com.alibaba.fastjson.JSONObject;
import com.vege.model.BreedStage;
import com.vege.model.VegeInfo;
import com.vege.service.BreedStageService;
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
public class BreedStageController extends BaseController {

    private final BreedStageService breedstageService;

    private final VegeInfoService vegeInfoService;

    @Autowired
    public BreedStageController(BreedStageService breedstageService, VegeInfoService vegeInfoService) {
        this.breedstageService = breedstageService;
        this.vegeInfoService = vegeInfoService;
    }

    @RequestMapping(value = "/add_breedstage.json", method = RequestMethod.POST)
    public Map add(HttpServletRequest request, HttpServletResponse response) {

        List<String> fields = Arrays.asList( "stageName", "definition", "duration", "vegeId");
        Map<String, String> data = buildData(request,fields);
        BreedStage breedStage = new BreedStage();
        breedStage.setStageName(data.get("stageName"));
        VegeInfo vegeInfo = vegeInfoService.queryById(data.get("vegeId"));
        breedStage.setVegeInfo(vegeInfo);
        breedStage.setDefinition(data.get("definition"));
        breedStage.setDuration(data.get("duration"));
        breedStage.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        boolean flag = breedstageService.add(breedStage);
        return buildResponse(flag);
    }

    @RequestMapping(value = "/delete_breedstage.json", method = RequestMethod.GET)
    public Map delete(HttpServletRequest request, HttpServletResponse response) {
        String breedstageid = request.getParameter("bsId");
        boolean flag = breedstageService.delete(breedstageid);
        return buildResponse(flag);
    }

    @RequestMapping(value = "/update_breedstage.json", method = RequestMethod.POST)
    public Map update(HttpServletRequest request, HttpServletResponse response) {
        List<String> fields = Arrays.asList("bsId", "stageName", "vegeId", "definition", "duration");
        Map<String, String> data = buildData(request,fields);
        BreedStage breedStage = breedstageService.queryById(data.get("bsId"));
        //如果不相等，修改两者关系
        if(breedStage.getVegeInfo().getVegeId()!=Integer.parseInt(data.get("vegeId"))){
            //旧的VegeInfo去掉BreedStage
            VegeInfo vegeInfo = breedStage.getVegeInfo();
            vegeInfo.getBreedStages().remove(breedStage);
            //新的VegeInfo加上
            VegeInfo newVege = vegeInfoService.queryById(data.get("vegeId"));
            newVege.getBreedStages().add(breedStage);
            vegeInfoService.update(newVege);
            breedStage.setVegeInfo(newVege);
        }
        breedStage.setStageName(data.get("stageName"));
        breedStage.setBsId(Integer.parseInt(data.get("bsId")));
        breedStage.setDefinition(data.get("definition"));
        breedStage.setDuration(data.get("duration"));
        breedStage.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        boolean flag = breedstageService.update(breedStage);
        return buildResponse(flag);
    }


    @RequestMapping(value = "/query_breedstage.json", method = {RequestMethod.POST, RequestMethod.GET})
    public Map query(HttpServletRequest request, HttpServletResponse response) {
        List<String> fields = Arrays.asList("stageName", "page", "size");
        Map<String, String> condition = buildData(request, fields);
        Page<BreedStage> result = breedstageService.query(condition);
        long total = breedstageService.queryTotal(condition);
        JSONObject data = new JSONObject();
        data.put("total", total);
        data.put("rows", result.getContent());
        return buildResponse(data);
    }

    @RequestMapping(value = "/query_breedstagebyid.json", method = {RequestMethod.POST, RequestMethod.GET})
    public Map queryById(HttpServletRequest request, HttpServletResponse response) {
        String breedstageid = request.getParameter("bsId");

        BreedStage breedStage = breedstageService.queryById(breedstageid);
        JSONObject data = new JSONObject();
        data.put("stageName",breedStage.getStageName());
        data.put("vegeNameMap", vegeInfoService.getVegeIdAndName());
        data.put("vegeName", breedStage.getVegeInfo().getVegeName());
        data.put("bsId",breedStage.getBsId());
        data.put("vegeInfo",breedStage.getVegeInfo());
        data.put("definition",breedStage.getDefinition());
        data.put("duration",breedStage.getDuration());
        data.put("updateTime",breedStage.getUpdateTime());
        return buildResponse(data);
    }
}
