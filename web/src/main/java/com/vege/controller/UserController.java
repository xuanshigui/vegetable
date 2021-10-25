package com.vege.controller;

import com.alibaba.fastjson.JSONObject;
import com.vege.model.User;
import com.vege.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
public class UserController extends BaseController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/add_user.json", method = RequestMethod.POST)
    public Map add(HttpServletRequest request, HttpServletResponse response) {

        List<String> fields = Arrays.asList( "userName", "password", "realName", "phone", "email", "note");
        Map<String, String> data = buildData(request,fields);
        User user = new User();
        user.setUserName(data.get("userName"));
        user.setPassword(data.get("password"));
        user.setRealName(data.get("realName"));
        user.setPhone(data.get("phone"));
        user.setEmail(data.get("email"));
        user.setNote(data.get("note"));
        boolean flag = userService.add(user);
        return buildResponse(flag);
    }

    @RequestMapping(value = "/delete_user.json", method = RequestMethod.GET)
    public Map delete(HttpServletRequest request, HttpServletResponse response) {
        String userid = request.getParameter("userid");
        boolean flag = userService.delete(userid);
        return buildResponse(flag);
    }

    @RequestMapping(value = "/update_user.json", method = RequestMethod.POST)
    public Map update(HttpServletRequest request, HttpServletResponse response) {
        List<String> fields = Arrays.asList("userId", "userName", "password", "realName", "phone", "email", "note");
        Map<String, String> data = buildData(request,fields);
        User user = userService.queryById(data.get("userId"));
        user.setUserName(data.get("userName"));
        user.setPassword(data.get("password"));
        user.setRealName(data.get("realName"));
        user.setPhone(data.get("phone"));
        user.setEmail(data.get("email"));
        user.setNote(data.get("note"));
        user.setUserId(Integer.parseInt(data.get("userId")));
        boolean flag = userService.update(user);
        return buildResponse(flag);
    }

    @RequestMapping(value = "/query_user.json", method = {RequestMethod.POST, RequestMethod.GET})
    public Map query(HttpServletRequest request, HttpServletResponse response) {
        List<String> fields = Arrays.asList("userName", "page", "size");
        Map<String, String> condition = buildData(request, fields);
        Page<User> result = userService.query(condition);
        long total = userService.queryTotal(condition);
        JSONObject data = new JSONObject();
        data.put("total", result.getTotalElements());
        data.put("rows", result.getContent());
        return buildResponse(data);
    }

    @RequestMapping(value = "/query_userbyid.json", method = {RequestMethod.POST, RequestMethod.GET})
    public Map queryById(HttpServletRequest request, HttpServletResponse response) {
        String userid = request.getParameter("userid");

        User user = userService.queryById(userid);
        JSONObject data = new JSONObject();
        data.put("userId", user.getUserId());
        data.put("userName", user.getUserName());
        data.put("password", user.getPassword());
        data.put("realName", user.getRealName());
        data.put("phone", user.getPhone());
        data.put("email", user.getEmail());
        data.put("note", user.getNote());
        return buildResponse(data);
    }

    @RequestMapping(value = "/check_user.json", method = RequestMethod.GET)
    public Map check(HttpServletRequest request, HttpServletResponse response) {
        String userName = request.getParameter("userName");
        boolean flag = userService.isUnique(userName);
        return buildResponse(flag);
    }
}
