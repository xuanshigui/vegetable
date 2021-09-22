package com.vege.controller;

import com.alibaba.fastjson.JSONObject;
import com.vege.constants.Constants;
import com.vege.model.VegeInfo;
import com.vege.service.ImageService;
import com.vege.service.VegeInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/")
public class VegeInfoController extends BaseController {

    private final VegeInfoService vegeService;

    private final ImageService imageService;

    @Autowired
    public VegeInfoController(VegeInfoService vegeService, ImageService imageService) {
        this.vegeService = vegeService;
        this.imageService = imageService;
    }

    @RequestMapping(value = "/add_vege.json", method = RequestMethod.POST)
    public Map add(HttpServletRequest request, HttpServletResponse response) {

        List<String> fields = Arrays.asList( "vegeName", "alias", "introduction", "vegeImg", "classification", "note");
        Map<String, String> data = buildData(request,fields);
        VegeInfo vege = new VegeInfo();
        vege.setVegeName(data.get("vegeName"));
        vege.setAlias(data.get("alias"));
        vege.setIntroduction(data.get("introduction"));
        vege.setClassification(data.get("classification"));
        vege.setNote(data.get("note"));
        //建立图片
        String imgUuid = imageService.add(data.get("vegeImg"),vege.getClass().getSimpleName());
        vege.setImgUuid(imgUuid);
        vege.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        boolean flag = vegeService.add(vege);
        return buildResponse(flag);
    }

    @RequestMapping(value = "/delete_vege.json", method = RequestMethod.GET)
    public Map delete(HttpServletRequest request, HttpServletResponse response) {
        String vegeId = request.getParameter("vegeId");
        boolean flag = vegeService.delete(vegeId);
        return buildResponse(flag);
    }

    @RequestMapping(value = "/update_vege.json", method = RequestMethod.POST)
    public Map update(HttpServletRequest request, HttpServletResponse response) {
        List<String> fields = Arrays.asList( "vegeId", "vegeName", "vegeImg", "alias", "introduction", "classification", "note", "imguuid");
        Map<String, String> data = buildData(request,fields);
        VegeInfo vege = new VegeInfo();
        vege.setVegeName(data.get("vegeName"));
        vege.setAlias(data.get("alias"));
        vege.setIntroduction(data.get("introduction"));
        vege.setClassification(data.get("classification"));
        if(data.size()==7){
            //旧的图片保存
            vege.setImgUuid(data.get("imguuid"));
        }else {
            //建立图片
            String imgUuid = imageService.add(data.get("vegeImg"),vege.getClass().getSimpleName());
            vege.setImgUuid(imgUuid);
        }
        //建立时间戳
        vege.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        vege.setNote(data.get("note"));
        vege.setVegeId(Integer.parseInt(data.get("vegeId")));
        boolean flag = vegeService.update(vege);
        return buildResponse(flag);
    }

    @RequestMapping(value = "/query_vege.json", method = {RequestMethod.POST, RequestMethod.GET})
    public Map query(HttpServletRequest request, HttpServletResponse response) {
        List<String> fields = Arrays.asList("vegeName", "page", "size");
        Map<String, String> condition = buildData(request, fields);
        List<VegeInfo> result = vegeService.query(condition);
        int total = vegeService.queryTotal(condition);
        JSONObject data = new JSONObject();
        data.put("total", total);
        data.put("rows", result);
        return buildResponse(data);
    }

    @RequestMapping(value = "/query_vegebyid.json", method = {RequestMethod.GET, RequestMethod.GET})
    public Map queryById(HttpServletRequest request, HttpServletResponse response) {
        String vegeid = request.getParameter("vegeId");

        VegeInfo vege = vegeService.queryById(vegeid);
        JSONObject data = new JSONObject();
        data.put("vegeId", vege.getVegeId());
        data.put("vegeName", vege.getVegeName());
        data.put("alias", vege.getAlias());
        String imgPath = "";
        imgPath = imageService.queryPathByUuid(vege.getImgUuid());
        data.put("imgPath", "http://127.0.0.1:8080/show_img?imgPath="+imgPath);
        data.put("introduction", vege.getIntroduction());
        data.put("classification", Constants.VEGE_CLASS_MAP.get(vege.getClassification()));
        data.put("note", vege.getNote());
        DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        data.put("updateTime", sdf.format(vege.getUpdateTime()));
        data.put("imgUuid", vege.getImgUuid());
        data.put("classMap",Constants.VEGE_CLASS_MAP);
        return buildResponse(data);
    }

    @RequestMapping(value = "/load_classification.json", method = RequestMethod.GET)
    public Map loadClassification(HttpServletRequest request, HttpServletResponse response) {

        return buildResponse(Constants.VEGE_CLASS_MAP);
    }
}