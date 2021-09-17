package com.vege.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

/**
 * created by lyr on 2021/9/16
 */
@Controller
class NavigateController extends BaseController{
    @GetMapping(path = "/")
    ModelAndView index() {
        return page("index");
    }

    @GetMapping(path = "/{pageName}.html")
    ModelAndView page(@PathVariable("pageName") String pageName) {
        ModelAndView modelAndView = new ModelAndView("common/mainmenu");
        modelAndView.addObject("page", pageName);
        return modelAndView;
    }
}
