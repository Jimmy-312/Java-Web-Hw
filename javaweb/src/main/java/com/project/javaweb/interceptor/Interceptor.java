package com.project.javaweb.interceptor;

import javax.annotation.Resource;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class Interceptor implements WebMvcConfigurer {

  @Resource
  HandlerInterceptor loginAuthInterceptor;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {

      registry.addInterceptor(loginAuthInterceptor)
              .excludePathPatterns("/login");
  }
}