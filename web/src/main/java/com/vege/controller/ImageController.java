package com.vege.controller;

import com.alibaba.fastjson.JSONObject;
import com.vege.model.Image;
import com.vege.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.ui.Model;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
        String imageId = request.getParameter("imageid");
        boolean flag = imageService.delete(imageId);
        return buildResponse(flag);
    }

    @RequestMapping(value = "/update_image.json", method = RequestMethod.POST)
    public Map update(HttpServletRequest request, HttpServletResponse response) {
        List<String> fields = Arrays.asList("imageId", "imageName", "image", "imagePath", "note");
        Map<String, String> data = buildData(request,fields);
        Image image = new Image();
        image.setImgName(data.get("imageName"));
        image.setNote(data.get("note"));
        image.setImgId(Integer.parseInt(data.get("imageId")));
        boolean flag = imageService.update(image);
        return buildResponse(flag);
    }

    @RequestMapping(value = "/query_image.json", method = {RequestMethod.POST, RequestMethod.GET})
    public Map query(HttpServletRequest request, HttpServletResponse response) {
        List<String> fields = Arrays.asList("userName", "page", "size");
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
        String userid = request.getParameter("userid");

        Image image = imageService.queryById(userid);
        JSONObject data = new JSONObject();
        data.put("imageId", image.getImgId());
        data.put("imageName", image.getImgName());
        data.put("imgPath", image.getImgPath());
        data.put("timestamp", image.getTimestamp());
        data.put("note", image.getNote());
        return buildResponse(data);
    }

    @PostMapping(value = "/imgUpload")
    public String fileUpload(@RequestParam(value = "file") MultipartFile file, Model model, HttpServletRequest request) {
        if (file.isEmpty()) {
            System.out.println("文件为空!");
        }
        String fileName = file.getOriginalFilename();  // 文件名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名
        String filePath = "D://temp-rainy//"; // 上传后的路径
        fileName = UUID.randomUUID() + suffixName; // 新文件名
        File dest = new File(filePath + fileName);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String filename = "/temp-rainy/" + fileName;
        model.addAttribute("filename", filename);
        return "file";
    }

}
