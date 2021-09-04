package com.vege.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

/**
 * created by zbs on 2018/3/3
 */
@Controller
class NavigateController extends BaseController{
    @GetMapping(path = "/")
    ModelAndView index() {
        return page("index");
    }

    @GetMapping(path = "/{pageName}.html")
    ModelAndView page(@PathVariable("pageName") String pageName) {
        ModelAndView modelAndView = new ModelAndView("common/aquatic");
        modelAndView.addObject("page", pageName);
        return modelAndView;
    }
}
