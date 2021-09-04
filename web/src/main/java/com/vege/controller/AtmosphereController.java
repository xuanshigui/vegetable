package com.vege.controller;

import com.alibaba.fastjson.JSONObject;
import com.vege.service.AtmosphereQueryService;
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
public class AtmosphereController extends BaseController {

    private final AtmosphereQueryService atmosphereService;

    @Autowired
    public AtmosphereController(AtmosphereQueryService queryService) {
        this.atmosphereService = queryService;
    }

    @RequestMapping(value = "/add_atmosphere.json", method = RequestMethod.POST)
    public Map<String, Object> add(HttpServletRequest request, HttpServletResponse response) {

        List<String> fields = Arrays.asList("relative_himidity", "air_temp", "atmos_pressure", "wind_speed", "wind_direction", "soil_moisture", "soil_temp", "sun_radiation", "rain_fall", "timestamp");
        Map<String, String> data = buildData(request, fields);

        return buildResponse(atmosphereService.add(data));
    }

    @RequestMapping(value = "/delete_atmosphere.json", method = RequestMethod.GET)
    public Map<String, Object> delete(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");

        return buildResponse(atmosphereService.delete(id));
    }

    @RequestMapping(value = "/update_atmosphere.json", method = RequestMethod.POST)
    public Map<String, Object> update(HttpServletRequest request, HttpServletResponse response) {
        List<String> fields = Arrays.asList("relative_himidity", "air_temp", "atmos_pressure", "wind_speed", "wind_direction", "soil_moisture", "soil_temp", "sun_radiation", "rain_fall", "timestamp", "id");
        Map<String, String> data = buildData(request, fields);

        return buildResponse(atmosphereService.update(data));
    }

    @RequestMapping(value = "/query_atmosphere.json", method = {RequestMethod.POST, RequestMethod.GET})
    public Map query(HttpServletRequest request, HttpServletResponse response) {

        List<String> fields = Arrays.asList("start_date", "end_date", "page", "size");
        Map<String, String> condition = buildData(request, fields);

        List<Map<String, Object>> result = atmosphereService.query(condition);

        int total = atmosphereService.queryTotal(condition);
        JSONObject data = new JSONObject();
        data.put("total", total);
        data.put("rows", result);

        return buildResponse(data);
    }

}
