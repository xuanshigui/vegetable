package com.vege.controller;

import com.alibaba.fastjson.JSONObject;
import com.vege.constants.Constants;
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
import java.sql.Timestamp;
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

        List<String> fields = Arrays.asList( "varietyName", "varietyImg", "introduction", "vegeId", "area", "source");
        Map<String, String> data = buildData(request,fields);
        Variety variety = new Variety();
        variety.setVarietyName(data.get("varietyName"));
        variety.setVegeId(Integer.parseInt(data.get("vegeId")));
        //存储图片
        String imgUuid = imageService.add(data.get("varietyImg"),Variety.class.getSimpleName());
        variety.setImgUuid(imgUuid);
        variety.setDescription(data.get("introduction"));
        variety.setArea(data.get("area"));
        variety.setSource(data.get("source"));
        variety.setTimestamp(new Timestamp(System.currentTimeMillis()));
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
        List<String> fields = Arrays.asList("varietyId", "varietyName", "password", "realName", "phone", "email", "note");
        Map<String, String> data = buildData(request,fields);
        Variety variety = new Variety();
        variety.setVegeId(Integer.parseInt(data.get("varietyName")));
        variety.setVarietyName(data.get("varietyName"));
        variety.setDescription(data.get("description"));
        variety.setArea(data.get("area"));
        variety.setImgUuid(data.get("imgUuid"));
        variety.setSource(data.get("source"));
        boolean flag = varietyService.update(variety);
        return buildResponse(flag);
    }

    @RequestMapping(value = "/query_variety.json", method = {RequestMethod.POST, RequestMethod.GET})
    public Map query(HttpServletRequest request, HttpServletResponse response) {
        List<String> fields = Arrays.asList("varietyName","vegeName", "page", "size");
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
        String varietyid = request.getParameter("varietyId");

        Variety variety = varietyService.queryById(varietyid);
        JSONObject data = new JSONObject();
        data.put("varietyId", variety.getVarietyId());
        data.put("vegeName", variety.getVegeInfo().getVegeName());
        data.put("varietyName", variety.getVarietyName());
        data.put("description", variety.getDescription());
        data.put("area", variety.getArea());
        String imgPath = "";
        imgPath = imageService.queryPathByUuid(variety.getImgUuid());
        data.put("imgPath", "http://127.0.0.1:8080/show_img?imgPath="+imgPath);
        data.put("imgUuId", variety.getImgUuid());
        data.put("source", Constants.VARIETY_SOURCE_MAP.get(variety.getSource()));
        return buildResponse(data);
    }

}
