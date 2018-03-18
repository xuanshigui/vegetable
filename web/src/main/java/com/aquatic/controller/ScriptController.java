package com.aquatic.controller;

import com.aquatic.service.script.ScriptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletRequest;
import java.util.Map;

/**
 * created by zbs on 2018/3/18
 */
@RestController
@RequestMapping(path = "/")
public class ScriptController extends BaseController {
    private final ScriptService scriptService;

    @Autowired
    public ScriptController(ScriptService scriptService) {
        this.scriptService = scriptService;
    }

    @RequestMapping(value = "/call_python.json", method = RequestMethod.GET)
    public Map<String, Object> uploadAtmosphere(ServletRequest request) {
        String env = request.getParameter("env");
        String profit = request.getParameter("profit");

        String response = scriptService.evaluateWaterQuality(env, profit);
        return buildResponse(response);
    }

}
