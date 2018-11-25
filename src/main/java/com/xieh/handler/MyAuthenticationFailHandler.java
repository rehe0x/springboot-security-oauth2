package com.xieh.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xieh.common.ResultUtil;
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
 * 登陆失败处理器
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
        String message = "";
        response.setContentType("application/json;charset=UTF-8");
        if(exception instanceof BadCredentialsException){
            message = "用户名或密码错误！";
        }else{
            message = "未知错误！";
        }
        response.getWriter().write(objectMapper.writeValueAsString(ResultUtil.error(-1,message)));
    }
}
