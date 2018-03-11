package com.aquatic.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * created by zbs on 2018/3/11
 */
@RestController
@RequestMapping(path = "/")
public class ProcessController extends BaseController {
    @RequestMapping(value = "/upload_atmosphere.json", method = RequestMethod.POST)
    public Map<String, Object> uploadAtmosphere(HttpServletRequest request, HttpServletResponse response) {
        List<String> fields = Arrays.asList("name", "lprice", "mprice", "hprice", "classify", "unit", "date");
        Map<String, String> data = buildData(request, fields);
        String type = request.getParameter("type");

        return buildResponse(null);
    }

    @RequestMapping(value = "/upload_water.json", method = RequestMethod.POST)
    public Map<String, Object> uploadWater(HttpServletRequest request, HttpServletResponse response) {
        List<String> fields = Arrays.asList("name", "lprice", "mprice", "hprice", "classify", "unit", "date");
        Map<String, String> data = buildData(request, fields);
        String type = request.getParameter("type");

        return buildResponse(null);
    }

    @RequestMapping(value = "/process.json", method = RequestMethod.GET)
    public Map<String, Object> add(HttpServletRequest request, HttpServletResponse response) {
        List<String> fields = Arrays.asList("name", "lprice", "mprice", "hprice", "classify", "unit", "date");
        Map<String, String> data = buildData(request, fields);
        String type = request.getParameter("type");

        return buildResponse(null);
    }
}
