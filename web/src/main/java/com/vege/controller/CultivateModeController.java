package com.vege.controller;

import com.alibaba.fastjson.JSONObject;
import com.vege.model.CultivateMode;
import com.vege.service.CultivateModeService;
import com.vege.service.VarietyService;
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
public class CultivateModeController extends BaseController {

    private final CultivateModeService cultivatemodeService;

    private final VarietyService varietyService;


    @Autowired
    public CultivateModeController(CultivateModeService cultivatemodeService, VarietyService varietyService) {
        this.cultivatemodeService = cultivatemodeService;
        this.varietyService = varietyService;
    }

    @RequestMapping(value = "/add_cultivatemode.json", method = RequestMethod.POST)
    public Map add(HttpServletRequest request, HttpServletResponse response) {

        List<String> fields = Arrays.asList("varietyId","cultivateModeName", "note", "updateTime");
        Map<String, String> data = buildData(request,fields);
        CultivateMode cultivateMode = new CultivateMode();
        cultivateMode.setCultivateModeName(data.get("cultivateModeName"));
        cultivateMode.setNote(data.get("note"));
        cultivateMode.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        CultivateMode cultivateModeNew = cultivatemodeService.add(cultivateMode);
        boolean flag = false;
        if(cultivateModeNew.getCmId()!=null){
            flag = true;
        }
        return buildResponse(flag);
    }

    @RequestMapping(value = "/delete_cultivatemode.json", method = RequestMethod.GET)
    public Map delete(HttpServletRequest request, HttpServletResponse response) {
        String cmId = request.getParameter("cmId");
        boolean flag = cultivatemodeService.delete(cmId);
        return buildResponse(flag);
    }

    @RequestMapping(value = "/update_cultivatemode.json", method = RequestMethod.POST)
    public Map update(HttpServletRequest request, HttpServletResponse response) {
        List<String> fields = Arrays.asList("cmId", "cultivateModeName", "note", "updateTime");
        Map<String, String> data = buildData(request,fields);
        CultivateMode cultivateMode = cultivatemodeService.queryById(data.get("cmId"));
        cultivateMode.setCultivateModeName(data.get("cultivateModeName"));
        cultivateMode.setNote(data.get("note"));
        cultivateMode.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        CultivateMode cultivateModeNew = cultivatemodeService.update(cultivateMode);
        boolean flag = false;
        if(cultivateModeNew.getCmId()!=null){
            flag = true;
        }
        return buildResponse(flag);
    }


    @RequestMapping(value = "/query_cultivatemode.json", method = {RequestMethod.POST, RequestMethod.GET})
    public Map query(HttpServletRequest request, HttpServletResponse response) {
        List<String> fields = Arrays.asList("cultivateModeName", "page", "size");
        Map<String, String> condition = buildData(request, fields);
        Page<CultivateMode> result = cultivatemodeService.query(condition);
        long total = result.getSize();
        JSONObject data = new JSONObject();
        data.put("total", result.getTotalElements());
        data.put("rows", result.getContent());
        return buildResponse(data);
    }

    @RequestMapping(value = "/query_cultivatemodebyid.json", method = {RequestMethod.POST, RequestMethod.GET})
    public Map queryById(HttpServletRequest request, HttpServletResponse response) {
        String cmId = request.getParameter("cmId");

        CultivateMode cultivateMode = cultivatemodeService.queryById(cmId);
        JSONObject data = new JSONObject();
        data.put("cmId",cultivateMode.getCmId());
        data.put("cultivateModeName",cultivateMode.getCultivateModeName());
        data.put("varietyList",cultivateMode.getVarieties());
        data.put("note",cultivateMode.getNote());
        data.put("updateTime",cultivateMode.getUpdateTime());
        return buildResponse(data);
    }

}
