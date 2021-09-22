package com.vege.controller;

import com.alibaba.fastjson.JSONObject;
import com.vege.model.Variety;
import com.vege.service.VarietyService;
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
public class VarietyController extends BaseController {

    private final VarietyService varietyService;

    @Autowired
    public VarietyController(VarietyService varietyService) {
        this.varietyService = varietyService;
    }

    @RequestMapping(value = "/add_variety.json", method = RequestMethod.POST)
    public Map add(HttpServletRequest request, HttpServletResponse response) {

        List<String> fields = Arrays.asList( "userName", "password", "realName", "phone", "email", "note");
        Map<String, String> data = buildData(request,fields);
        Variety variety = new Variety();
        variety.setVarietyName(data.get("varietyName"));

        boolean flag = varietyService.add(variety);
        return buildResponse(flag);
    }

    @RequestMapping(value = "/delete_variety.json", method = RequestMethod.GET)
    public Map delete(HttpServletRequest request, HttpServletResponse response) {
        String userid = request.getParameter("userid");
        boolean flag = varietyService.delete(userid);
        return buildResponse(flag);
    }

    @RequestMapping(value = "/update_variety.json", method = RequestMethod.POST)
    public Map update(HttpServletRequest request, HttpServletResponse response) {
        List<String> fields = Arrays.asList("userId", "userName", "password", "realName", "phone", "email", "note");
        Map<String, String> data = buildData(request,fields);
        Variety variety = new Variety();
        variety.setVegeId(Integer.parseInt(data.get("userName")));
        variety.setVarietyName(data.get("varietyName"));
        variety.setDescription(data.get("description"));
        variety.setArea(data.get("area"));
        variety.setImgUuid(data.get("imgUuid"));
        variety.setSource(data.get("source"));
        boolean flag = varietyService.update(variety);
        return buildResponse(flag);
    }

    @RequestMapping(value = "/query_variey.json", method = {RequestMethod.POST, RequestMethod.GET})
    public Map query(HttpServletRequest request, HttpServletResponse response) {
        List<String> fields = Arrays.asList("userName", "page", "size");
        Map<String, String> condition = buildData(request, fields);
        List<Variety> result = varietyService.query(condition);
        int total = varietyService.queryTotal(condition);
        JSONObject data = new JSONObject();
        data.put("total", total);
        data.put("rows", result);
        return buildResponse(data);
    }

    @RequestMapping(value = "/query_varietybyid.json", method = {RequestMethod.POST, RequestMethod.GET})
    public Map queryById(HttpServletRequest request, HttpServletResponse response) {
        String userid = request.getParameter("varietyId");

        Variety variety = varietyService.queryById(userid);
        JSONObject data = new JSONObject();
        data.put("varietyId", variety.getVarietyId());
        data.put("vegeId", variety.getVegeId());
        data.put("varietyName", variety.getVarietyName());
        data.put("description", variety.getDescription());
        data.put("area", variety.getArea());
        data.put("imgUuId", variety.getImgUuid());
        data.put("note", variety.getSource());
        return buildResponse(data);
    }
}
