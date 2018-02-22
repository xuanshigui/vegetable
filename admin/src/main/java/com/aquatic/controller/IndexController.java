package com.aquatic.controller;

import com.aquatic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class IndexController extends BaseController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/")
    public String index() {
        userService.delRecord();
        return "hello world";
    }

    @GetMapping(path = "/{item}.html")
    public ModelAndView index(@PathVariable("item") String item) {
        return new ModelAndView("index");
    }
}
