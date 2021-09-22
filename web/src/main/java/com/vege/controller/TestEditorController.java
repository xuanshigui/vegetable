package com.vege.controller;

import com.vege.service.FileService;
import com.vege.service.impl.TestServiceImpl;
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
public class TestEditorController extends BaseController {

    private final TestServiceImpl testService;

    private final FileService fileService;

    @Autowired
    public TestEditorController(TestServiceImpl testService, FileService fileService) {
        this.testService = testService;
        this.fileService = fileService;
    }

    @RequestMapping(value = "/add_content.json", method = RequestMethod.POST)
    public Map add(HttpServletRequest request, HttpServletResponse response) {

        List<String> fields = Arrays.asList( "content");
        Map<String, String> data = buildData(request,fields);
        System.out.println(data);
        boolean flag = false;
        if(!data.isEmpty()){
            flag = true;
        }
        return buildResponse(flag);
    }


}
