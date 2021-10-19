package com.vege.controller;

import com.vege.model.User;
import com.vege.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * created by lyr on 2021/9/16
 */
@Controller
class NavigateController extends BaseController{

    @Autowired
    UserService userService;

    @GetMapping(path = "/")
    ModelAndView index() {
        return page("index");
    }

    @GetMapping(path = "/{pageName}.html")
    ModelAndView page(@PathVariable("pageName") String pageName) {

        Set<String> frontPageSet = new HashSet<>((Arrays.asList("index", "vegeinfo", "variety", "disease", "vegeknowledge", "company", "standard", "drug")));
        ModelAndView modelAndView;
        if(pageName.equals("login")){
            modelAndView = new ModelAndView("login");
        }else if(frontPageSet.contains(pageName)){
            modelAndView = new ModelAndView("frontpages/frontframe");
            modelAndView.addObject("page","frontpages/"+pageName);
        }else {
            modelAndView = new ModelAndView("common/mainmenu");
            modelAndView.addObject("page", pageName);
        }
        return modelAndView;
    }

    @RequestMapping(value = {"/login"}, method = RequestMethod.POST)
    public void login(@RequestParam(name = "username")String username, @RequestParam(name = "password")String password,
                        Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = userService.login(username,password);
        if(user!=null){
            model.addAttribute("user", user);
            request.getSession().setAttribute("user", user);
            response.sendRedirect(request.getContextPath()+ "/main.html");
        }else {
            response.sendRedirect(request.getContextPath()+ "/index.html");
        }
    }

    @RequestMapping(value = {"/logout"}, method = RequestMethod.GET)
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().removeAttribute("user");
        response.sendRedirect(request.getContextPath()+ "/index.html");
    }

}
