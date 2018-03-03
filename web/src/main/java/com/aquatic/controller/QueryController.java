package com.aquatic.controller;

import com.aquatic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
public class QueryController extends BaseController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "query.json", method = RequestMethod.POST)
    public Map<String, Object> query(HttpServletRequest request, HttpServletResponse response) {

        return new HashMap<>();
    }

}
