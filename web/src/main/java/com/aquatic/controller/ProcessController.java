package com.aquatic.controller;

import com.aquatic.service.FileService;
import com.aquatic.service.preprocessing.process.Preprocessing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Map;

/**
 * created by zbs on 2018/3/11
 */
@RestController
@RequestMapping(path = "/")
public class ProcessController extends BaseController {
    private final FileService fileService;

    @Autowired
    public ProcessController(FileService fileService) {
        this.fileService = fileService;
    }

    @RequestMapping(value = "/upload_atmosphere.json", method = RequestMethod.POST)
    public Map<String, Object> uploadAtmosphere(@RequestParam("file") MultipartFile file) {
        boolean res = fileService.uploadFile(file, "atmosphere.csv");
        return buildResponse(res);
    }

    @RequestMapping(value = "/upload_water.json", method = RequestMethod.POST)
    public Map<String, Object> uploadWater(@RequestParam("file") MultipartFile file) {
        boolean res = fileService.uploadFile(file, "fiveall.csv");
        return buildResponse(res);
    }

    @RequestMapping(value = "/process.json", method = RequestMethod.GET)
    public Map<String, Object> add(HttpServletRequest request, HttpServletResponse response) {
        boolean res = false;
        try {
            Preprocessing.preprocessing();
            res = true;
        } catch (Exception e) {
        }
        res = true;

        return buildResponse(res);
    }
}
