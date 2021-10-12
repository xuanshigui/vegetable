package com.vege.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

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

        Set<String> frontPageSet = new HashSet<>((Arrays.asList("index", "vegeinfo", "variety", "disease", "knowledge", "company")));
        ModelAndView modelAndView;
        if(pageName.equals("login")){
            modelAndView = new ModelAndView("common/login");
        }else if(frontPageSet.contains(pageName)){
            modelAndView = new ModelAndView("frontpages/"+pageName);
        }else {
            modelAndView = new ModelAndView("common/mainmenu");
            modelAndView.addObject("page", pageName);
        }
        return modelAndView;
    }
}
