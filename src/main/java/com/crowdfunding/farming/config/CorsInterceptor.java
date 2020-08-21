package com.crowdfunding.farming.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: kevin
 * @create: 11/20/19 02:22
 * @description:
 **/

@Configuration
public class CorsInterceptor implements HandlerInterceptor {

  @Override
  public boolean preHandle(
      HttpServletRequest request,
      HttpServletResponse response,
      Object handler) {
    // 此处配置的是允许任意域名跨域请求，可根据需求指定
    response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
    response.setHeader("Access-Control-Allow-Credentials", "true");
    response.setHeader("Access-Control-Allow-Methods",
        "GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS");
    response.setHeader("Access-Control-Max-Age", "3600");
    //这个是根据项目来定的
    response.setHeader("Access-Control-Allow-Headers",
        "Content-Type, Range, X-E4M-With, X-Request-With");
    // 如果是OPTIONS则结束请求
    if (HttpMethod.OPTIONS.toString().equals(request.getMethod())) {
      response.setStatus(HttpStatus.NO_CONTENT.value());
      return false;
    }
    return true;
  }
}
