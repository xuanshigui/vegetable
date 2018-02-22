package com.aquatic.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * 将 xx_yy.html 解析成模板
 */
@RestController
public class NavigateController {
    @GetMapping(path = "/{item}.html")
    public ModelAndView index(@PathVariable("item") String item) {
        return new ModelAndView(item);
    }
}
