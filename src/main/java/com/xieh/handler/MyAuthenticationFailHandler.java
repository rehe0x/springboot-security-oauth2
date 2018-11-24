package com.xieh.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by xiech on 2018/11/22.
 */
@Component
public class MyAuthenticationFailHandler extends SimpleUrlAuthenticationFailureHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ObjectMapper objectMapper;


    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        logger.info("登录失败");
//        //设置状态码
//        response.setStatus(500);
//        response.setContentType("application/json;charset=UTF-8");
//        //将 登录失败 信息打包成json格式返回
//        response.getWriter().write(JSON.toJSONString("error"));

        String message = "";
        response.setContentType("application/json;charset=UTF-8");
        if(exception instanceof BadCredentialsException){
            message = "用户名或密码错误！";
            response.getWriter().write(objectMapper.writeValueAsString(message));
        }
    }
}
