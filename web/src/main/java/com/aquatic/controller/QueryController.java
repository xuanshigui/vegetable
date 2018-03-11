package com.aquatic.controller;

import com.alibaba.fastjson.JSONObject;
import com.aquatic.service.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@RestController
@RequestMapping(path = "/")
public class QueryController extends BaseController {

    private final QueryService queryService;

    @Autowired
    public QueryController(QueryService queryService) {
        this.queryService = queryService;
    }

    @RequestMapping(value = "/add.json", method = RequestMethod.POST)
    public Map<String, Object> add(HttpServletRequest request, HttpServletResponse response) {
        List<String> fields = Arrays.asList("name", "lprice", "mprice", "hprice", "classify", "unit", "date");
        Map<String, String> data = buildData(request, fields);
        String type = request.getParameter("type");

        return buildResponse(queryService.add(type, data));
    }

    @RequestMapping(value = "/delete.json", method = RequestMethod.GET)
    public Map<String, Object> delete(HttpServletRequest request, HttpServletResponse response) {
        String type = request.getParameter("type");
        String id = request.getParameter("id");

        return buildResponse(queryService.delete(type, id));
    }

    @RequestMapping(value = "/update.json", method = RequestMethod.POST)
    public Map<String, Object> update(HttpServletRequest request, HttpServletResponse response) {
        List<String> fields = Arrays.asList("name", "lprice", "mprice", "hprice", "classify", "unit", "date", "id");
        Map<String, String> data = buildData(request, fields);
        String type = request.getParameter("type");

        return buildResponse(queryService.update(type, data));
    }

    @RequestMapping(value = "/query.json", method = {RequestMethod.POST, RequestMethod.GET})
    public Map query(HttpServletRequest request, HttpServletResponse response) {

        List<String> fields = Arrays.asList("name", "start_date", "end_date", "page", "size");
        Map<String, String> condition = buildData(request, fields);
        String type = request.getParameter("type");

        List<Map<String, Object>> result = queryService.query(type, condition);

        int total = queryService.queryTotal(type, condition);
        JSONObject data = new JSONObject();
        data.put("total", total);
        data.put("rows", result);

        return buildResponse(data);
    }


    private static Map<String, String> buildData(HttpServletRequest request, List<String> fields) {
        Map<String, String> data = new HashMap<>();
        for (String field : fields) {
            String param = request.getParameter(field);
            if (param != null && !param.equals("")) {
                data.put(field, param);
            }
        }

        return data;
    }

    private static Map<String, Object> buildResponse(Object data) {
        Map<String, Object> response = new HashMap<>();
        response.put("data", data);
        return response;
    }
}
