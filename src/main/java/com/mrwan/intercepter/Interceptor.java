package com.mrwan.intercepter;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class Interceptor implements WebMvcConfigurer {
    //配置拦截器
    public void addInterceptors(InterceptorRegistry registry){
        //registry.addInterceptor此方法添加拦截器
        registry.addInterceptor(new MyInterceptor()).addPathPatterns("/**");
    }
}