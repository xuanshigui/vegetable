package com.vege.controller;

import com.alibaba.fastjson.JSONObject;
import com.vege.constants.Constants;
import com.vege.model.Standard;
import com.vege.service.StandardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/")
public class StandardController extends BaseController {

    private final StandardService standardService;

    @Autowired
    public StandardController(StandardService standardService) {
        this.standardService = standardService;
    }

    @RequestMapping(value = "/add_standard.json", method = RequestMethod.POST)
    public Map add(HttpServletRequest request, HttpServletResponse response) {

        List<String> fields = Arrays.asList( "headline", "content", "type", "state", "code", "fileLink", "publicTime");
        Map<String, String> data = buildData(request,fields);
        Standard standard = new Standard();
        standard.setHeadline(data.get("headline"));
        standard.setCode(data.get("code"));
        standard.setContent(data.get("content"));
        standard.setType(Integer.parseInt(data.get("type")));
        standard.setState(Boolean.parseBoolean(data.get("state")));
        standard.setFilelink(data.get("fileLink"));
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        String publicTime = data.get("publicTime");
        Date date = null;
        try {
            date = fmt.parse(publicTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        standard.setPublicTime(date);
        boolean flag = standardService.add(standard);
        return buildResponse(flag);
    }

    @RequestMapping(value = "/delete_standard.json", method = RequestMethod.GET)
    public Map delete(HttpServletRequest request, HttpServletResponse response) {
        String standardId = request.getParameter("standardId");
        boolean flag = standardService.delete(standardId);
        return buildResponse(flag);
    }

    @RequestMapping(value = "/update_standard.json", method = RequestMethod.POST)
    public Map update(HttpServletRequest request, HttpServletResponse response) {
        List<String> fields = Arrays.asList("standardId", "headline", "content", "type", "state", "code", "fileLink", "publicTime");
        Map<String, String> data = buildData(request,fields);
        Standard standard = standardService.queryById(data.get("standardId"));
        standard.setHeadline(data.get("headline"));
        standard.setContent(data.get("content"));
        standard.setCode(data.get("code"));
        standard.setType(Integer.parseInt(data.get("type")));
        standard.setState(Boolean.parseBoolean(data.get("state")));
        standard.setFilelink(data.get("fileLink"));
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        String publicTime = data.get("publicTime");
        Date date = null;
        try {
            date = fmt.parse(publicTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        standard.setPublicTime(date);
        boolean flag = standardService.update(standard);
        return buildResponse(flag);
    }

    @RequestMapping(value = "/query_standard.json", method = {RequestMethod.POST, RequestMethod.GET})
    public Map query(HttpServletRequest request, HttpServletResponse response) {
        List<String> fields = Arrays.asList("headline", "page", "size", "startTime", "endTime");
        Map<String, String> condition = buildData(request, fields);
        Page<Standard> result = standardService.query(condition);
        long total = standardService.queryTotal(condition);
        JSONObject data = new JSONObject();
        data.put("total", total);
        data.put("rows", result.getContent());
        return buildResponse(data);
    }

    @RequestMapping(value = "/query_standardbyid.json", method = {RequestMethod.GET, RequestMethod.GET})
    public Map queryById(HttpServletRequest request, HttpServletResponse response) {
        String vkId = request.getParameter("standardId");

        Standard standard = standardService.queryById(vkId);
        JSONObject data = new JSONObject();
        data.put("standardId", standard.getStandardId());
        data.put("headline", standard.getHeadline());
        data.put("content", standard.getContent());
        data.put("type",standard.getType());
        data.put("code", standard.getCode());
        data.put("state",standard.isState());
        data.put("fileLink", standard.getFilelink());
        data.put("publicTime",standard.getPublicTime());
        data.put("typeMap",Constants.STANDARD_TYPE_MAP);
        return buildResponse(data);
    }

    @RequestMapping(value = "/load_standardtype.json", method = RequestMethod.GET)
    public Map loadKnowledgeCategory(HttpServletRequest request, HttpServletResponse response) {
        JSONObject data = new JSONObject();
        data.put("typeMap",Constants.STANDARD_TYPE_MAP);
        return buildResponse(data);
    }
}