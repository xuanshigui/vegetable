package com.vege.controller;

import com.alibaba.fastjson.JSONObject;
import com.vege.model.Variety;
import com.vege.service.ImageService;
import com.vege.service.VarietyService;
import com.vege.service.VegeInfoService;
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
    private final VegeInfoService vegeInfoService;
    private final ImageService imageService;

    @Autowired
    public VarietyController(VarietyService varietyService, VegeInfoService vegeInfoService,ImageService imageService) {
        this.varietyService = varietyService;
        this.vegeInfoService = vegeInfoService;
        this.imageService = imageService;
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
        String varietyId = request.getParameter("varietyId");
        boolean flag = varietyService.delete(varietyId);
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
        data.put("vegeName", vegeInfoService.getVegeIdAndName());
        data.put("varietyName", variety.getVarietyName());
        data.put("description", variety.getDescription());
        data.put("area", variety.getArea());
        String imgPath = "";
        imgPath = imageService.queryPathByUuid(variety.getImgUuid());
        data.put("imgPath", "http://127.0.0.1:8080/show_img?imgPath="+imgPath);
        data.put("imgUuId", variety.getImgUuid());
        data.put("source", variety.getSource());
        return buildResponse(data);
    }
    @RequestMapping(value = "/load_vegename.json", method = {RequestMethod.GET})
    public Map getVegeNames(HttpServletRequest request, HttpServletResponse response) {
        Map<String,String> vegeNames = vegeInfoService.getVegeIdAndName();
        JSONObject data = new JSONObject();
        data.put("vegeNameMap",vegeNames);

        return buildResponse(data);
    }
}
