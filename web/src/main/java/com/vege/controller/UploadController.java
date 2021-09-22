package com.vege.controller;

import com.alibaba.fastjson.JSONObject;
import com.vege.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "/")
public class UploadController extends BaseController{

    private FileService fileService;

    @Autowired
    public UploadController(FileService fileService) {
        this.fileService = fileService;
    }

    @RequestMapping("/uploadimg")
    public Map upload(@RequestParam("file") MultipartFile file) throws Exception {

        if (file.isEmpty()) {
            throw new Exception("上传文件不能为空");
        }
        String fileNames = file.getOriginalFilename();
        //上传文件
        String filePath = fileService.uploadFile(file, fileNames);
        JSONObject data = new JSONObject();
        data.put("path", filePath);
        return buildResponse(data);
    }

    @RequestMapping("/upload_img")
    public Map upload_textarea(@RequestParam("file") MultipartFile file) throws Exception {

        if (file.isEmpty()) {
            throw new Exception("上传文件不能为空");
        }
        String fileNames = file.getOriginalFilename();
        //上传文件
        String filePath = fileService.uploadFile(file, fileNames);
        Map<String, String> map = new HashMap<>();
        String newFileName = filePath.substring(filePath.lastIndexOf("/"),filePath.length());
        String newFilePath = "http://127.0.0.1:8080/show_img?imgPath=/Users/liuyiran/Downloads/resource/image" + newFileName;
        map.put("location", newFilePath);
        //"location", downloadUrl + dir + "/" + name

        return map;
    }

    @RequestMapping("/show_img")
    public byte[] showImg(@RequestParam("imgPath") String imgPath) throws Exception {

        File file = new File(imgPath);
        FileInputStream inputStream = new FileInputStream(file);
        byte[] bytes = new byte[inputStream.available()];
        inputStream.read(bytes, 0, inputStream.available());

        return bytes;
    }
}
