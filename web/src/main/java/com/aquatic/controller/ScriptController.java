package com.aquatic.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * created by zbs on 2018/3/18
 */
@RestController
@RequestMapping(path = "/")
public class AnalysisController extends BaseController {
    @RequestMapping(value = "/call_python.json", method = RequestMethod.GET)
    public Map<String, Object> uploadAtmosphere() {


        return buildResponse(true);
    }

}
