package com.aquatic.controller;

import com.aquatic.service.analysis.PriceShuichanService;
import com.aquatic.service.script.ScriptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * created by zbs on 2018/3/18
 */
@RestController
@RequestMapping(path = "/")
public class ScriptController extends BaseController {
    private final ScriptService scriptService;

    private final PriceShuichanService priceShuichanService;

    @Autowired
    public ScriptController(ScriptService scriptService, PriceShuichanService priceShuichanService) {
        this.scriptService = scriptService;
        this.priceShuichanService = priceShuichanService;
    }

    @RequestMapping(value = "/call_python.json", method = RequestMethod.GET)
    public Map<String, Object> uploadAtmosphere(ServletRequest request) {
        String env = request.getParameter("env");
        String profit = request.getParameter("profit");

        String response = scriptService.evaluateWaterQuality(env, profit);
        return buildResponse(response);
    }

    @RequestMapping(value = "/call_matlab.json", method = RequestMethod.GET)
    public Map<String, Object> callMatlat(ServletRequest request) {
        String name = request.getParameter("name");
        String year = request.getParameter("year");
        name = "草鱼";
        year = "2017";
        double[][] result = priceShuichanService.predict(name, year);
        List<String> realValue = priceShuichanService.doubleToString(result[3]);
        List<String> predictValue = priceShuichanService.doubleToString(result[0]);
        List<String> jdwc = priceShuichanService.doubleToString(result[1]);
        List<String> xdwc = priceShuichanService.doubleToString(result[2]);
        String rmse = Double.toString(result[4][0]);
        Map<String, Object> res = new HashMap<>();
        res.put("realValue", realValue);
        res.put("predictValue", predictValue);
        res.put("jdwc", jdwc);
        res.put("xdwc", xdwc);
        res.put("rmse", rmse);

        return buildResponse(res);
    }

}
