package com.vege.controller;

import com.alibaba.fastjson.JSONObject;
import com.vege.model.Image;
import com.vege.service.ImageService;
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
public class ImageController extends BaseController {

    private final ImageService imageService;

    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @RequestMapping(value = "/delete_image.json", method = RequestMethod.GET)
    public Map delete(HttpServletRequest request, HttpServletResponse response) {
        String imageId = request.getParameter("imgId");
        boolean flag = imageService.delete(imageId);
        return buildResponse(flag);
    }

    @RequestMapping(value = "/update_image.json", method = RequestMethod.POST)
    public Map update(HttpServletRequest request, HttpServletResponse response) {
        //只允许改名称，描述，图片
        List<String> fields = Arrays.asList("imgId", "imgName", "imgPath", "note");
        Map<String, String> data = buildData(request,fields);
        Image image = imageService.queryById(data.get("imgId"));
        image.setImgName(data.get("imgName"));
        image.setNote(data.get("note"));
        image.setImgPath(data.get("imgPath"));
        //更新时间
        image.setTimestamp(new Timestamp(System.currentTimeMillis()));
        boolean flag = imageService.update(image);
        return buildResponse(flag);
    }

    @RequestMapping(value = "/query_image.json", method = {RequestMethod.POST, RequestMethod.GET})
    public Map query(HttpServletRequest request, HttpServletResponse response) {
        List<String> fields = Arrays.asList("imgName", "page", "size");
        Map<String, String> condition = buildData(request, fields);
        List<Image> result = imageService.query(condition);
        int total = imageService.queryTotal(condition);
        JSONObject data = new JSONObject();
        data.put("total", total);
        data.put("rows", result);
        return buildResponse(data);
    }

    @RequestMapping(value = "/query_imagebyid.json", method = {RequestMethod.POST, RequestMethod.GET})
    public Map queryById(HttpServletRequest request, HttpServletResponse response) {
        String userid = request.getParameter("imgId");

        Image image = imageService.queryById(userid);
        JSONObject data = new JSONObject();
        data.put("imgId", image.getImgId());
        data.put("imgName", image.getImgName());
        data.put("imgPath", "http://127.0.0.1:8080/show_img?imgPath="+ image.getImgPath());
        data.put("oldPath",image.getImgPath());
        data.put("timestamp", image.getTimestamp());
        data.put("tableName", image.getTableName());
        data.put("note", image.getNote());
        return buildResponse(data);
    }

}
