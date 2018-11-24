package com.xieh.config;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by xiech on 2018/11/23.
 */
@Component("permitAuthenticationFilter")
public class PermitAuthenticationFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(PermitAuthenticationFilter.class);
    @Autowired
    private TokenStore tokenStore;
    @Autowired
    private AuthorizationServerTokenServices authorizationServerTokenServices;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        if("/login/token".startsWith(request.getRequestURI())){
//            filterChain.doFilter(request, response);
//            return;
//        }
//        String str  = request.getHeader("Authorization");
//        if(StringUtils.isNoneBlank(str)){
//            str = str.split(" ")[1];
//        }
//
//        String str1 = request.getHeader("refreshToken");
//
//        OAuth2Authentication oAuth2Authentication = tokenStore.readAuthentication(str);
//        //密码授权 模式, 组建 authentication
////密码授权 模式, 组建 authentication
//        TokenRequest tokenRequest = new TokenRequest(MapUtils.EMPTY_MAP,oAuth2Authentication.getOAuth2Request().getClientId(),oAuth2Authentication.getOAuth2Request().getScope(),"password");
//
//        OAuth2AccessToken oAuth2AccessToken = authorizationServerTokenServices.refreshAccessToken(str1,tokenRequest);
//        response.setHeader("tt",oAuth2AccessToken.getValue().toString());
        logger.info("当前访问的地址:{}", request.getRequestURI());
        filterChain.doFilter(request, response);

    }
}