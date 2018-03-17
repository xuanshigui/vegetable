package com.aquatic.controller;

import com.alibaba.fastjson.JSONObject;
import com.aquatic.service.WaterQueryService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class WaterController extends BaseController {

    private final WaterQueryService waterService;

    @Autowired
    public WaterController(WaterQueryService waterQueryService) {
        this.waterService = waterQueryService;
    }

    @RequestMapping(value = "/add_water.json", method = RequestMethod.POST)
    public Map<String, Object> add(HttpServletRequest request, HttpServletResponse response) {
        List<String> fields = Arrays.asList("imsi", "water_temp", "dissolved_oxygen", "working_voltage", "charging_voltage", "operating_temp", "timestamp");
        Map<String, String> data = buildData(request, fields);

        return buildResponse(waterService.add(data));
    }

    @RequestMapping(value = "/delete_water.json", method = RequestMethod.GET)
    public Map<String, Object> delete(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");

        return buildResponse(waterService.delete(id));
    }

    @RequestMapping(value = "/update_water.json", method = RequestMethod.POST)
    public Map<String, Object> update(HttpServletRequest request, HttpServletResponse response) {
        List<String> fields = Arrays.asList("imsi", "water_temp", "dissolved_oxygen", "working_voltage", "charging_voltage", "operating_temp", "timestamp", "id");
        Map<String, String> data = buildData(request, fields);

        return buildResponse(waterService.update(data));
    }

    @RequestMapping(value = "/query_water.json", method = {RequestMethod.POST, RequestMethod.GET})
    public Map query(HttpServletRequest request, HttpServletResponse response) {
        List<String> fields = Arrays.asList("start_date", "end_date", "page", "size");
        Map<String, String> condition = buildData(request, fields);
        List<Map<String, Object>> result = waterService.query(condition);
        int total = waterService.queryTotal(condition);
        JSONObject data = new JSONObject();
        data.put("total", total);
        data.put("rows", result);

        return buildResponse(data);
    }

}
