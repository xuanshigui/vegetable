package com.vege.controller;

import com.alibaba.fastjson.JSONObject;
import com.vege.model.BreedStage;
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

        List<String> fields = Arrays.asList( "stageName", "definition", "duration", "vegeid");
        Map<String, String> data = buildData(request,fields);
        BreedStage breedStage = new BreedStage();
        breedStage.setStageName(data.get("stageName"));
        breedStage.setDefinition(data.get("definition"));
        breedStage.setDuration(data.get("duration"));
        breedStage.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        BreedStage breedStageNew = breedstageService.add(breedStage);
        boolean flag = false;
        if(!(breedStageNew.getBsId()==0)){
            flag = true;
        }
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
        BreedStage breedStage = new BreedStage();
        breedStage.setStageName(data.get("stageName"));
        breedStage.setBsId(Integer.parseInt(data.get("bsId")));
        breedStage.setDefinition(data.get("definition"));
        breedStage.setDuration(data.get("duration"));
        breedStage.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        BreedStage breedStageNew = breedstageService.update(breedStage);
        boolean flag = false;
        if(!(breedStageNew.getBsId()==0)){
            flag = true;
        }
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
        data.put("vegeName", vegeInfoService.getVegeIdAndName());
        data.put("bsId",breedStage.getBsId());
        data.put("definition",breedStage.getDefinition());
        data.put("duration",breedStage.getDuration());
        data.put("updateTime",breedStage.getUpdateTime());
        return buildResponse(data);
    }
}
