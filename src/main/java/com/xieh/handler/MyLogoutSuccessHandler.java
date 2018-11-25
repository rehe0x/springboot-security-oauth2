package com.xieh.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xieh.common.ResultUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @program security-oauth2
 * @description: 退出处理器
 * @author: Horng
 * @create: 2018/11/25 11:59
 */
@Component
public class MyLogoutSuccessHandler implements LogoutSuccessHandler {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ConsumerTokenServices consumerTokenServices;

    /**
     * 目前退出登陆 不传token也会成功 。。。
     * @param request
     * @param response
     * @param authentication
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String str  = request.getHeader("Authorization");
        if(StringUtils.isNoneBlank(str)){
            str = str.split(" ")[1];
            consumerTokenServices.revokeToken(str);
        }
        response.setContentType("application/json;charset=UTF-8");
        String message = "退出成功！";
        response.getWriter().write(objectMapper.writeValueAsString(ResultUtil.success(message)));

    }
}
