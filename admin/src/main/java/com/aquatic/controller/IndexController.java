package com.aquatic.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController extends BaseController {

    @RequestMapping(value = "/")
    public String index() {
        return "hello world";
    }
}
