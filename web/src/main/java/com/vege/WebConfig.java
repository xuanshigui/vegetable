package com.vege;

import com.vege.Interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ConditionalOnClass(WebMvcConfigurer.class)
public class WebConfig {

    @Configuration
    @ConditionalOnWebApplication
    public static class CustomWebMvcConfigure implements WebMvcConfigurer{

        @Autowired
        LoginInterceptor loginInterceptor;

        @Override
        public void addInterceptors(InterceptorRegistry registry) {
            registry.addInterceptor(loginInterceptor).addPathPatterns("/**_manage.html","/add_**.html","/delete_**.html","/update_**.html","/main.html")
                    .excludePathPatterns("/", "/login.html", "**/css/**","**/fonts/**","**/img/**","**/js/**");
        }
    }

}
