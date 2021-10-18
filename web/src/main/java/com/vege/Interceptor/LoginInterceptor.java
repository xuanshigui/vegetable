package com.vege.Interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.vege.model.User;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        try {
            HttpSession session = request.getSession();
            User user = (User) request.getSession().getAttribute("user");
            if (user != null) {
                return true;
            }
            response.sendRedirect(request.getContextPath() + "login.html");

        }catch (Exception e){
            e.printStackTrace();
        }

        return false;
    }
}
