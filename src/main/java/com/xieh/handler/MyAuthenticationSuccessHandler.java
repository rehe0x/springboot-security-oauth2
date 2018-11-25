package com.xieh.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xieh.common.ResultUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登陆成功处理器
 * Created by xiech on 2018/11/22.
 */
@Component
public class MyAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ClientDetailsService clientDetailsService;

    @Autowired
    private AuthorizationServerTokenServices authorizationServerTokenServices;
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 密码验证成功 > CustomOauthException异常本应该返回自定义格式 目前无效
     * @param request
     * @param response
     * @param authentication
     * @throws IOException
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        logger.info("【AppLoginInSuccessHandler】 onAuthenticationSuccess authentication={}", authentication);

        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Basic")) {
            throw new CustomOauthException("请求投中无client信息");
        }
        String[] tokens = this.extractAndDecodeHeader(header, request);

        assert tokens.length == 2;

        //获取clientId 和 clientSecret
        String clientId = tokens[0];
        String clientSecret = tokens[1];

        //获取 ClientDetails
        ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);

        if (clientDetails == null){
            throw new CustomOauthException("clientId 不存在"+clientId);
            //判断  方言  是否一致
        }else if (!StringUtils.equals(clientDetails.getClientSecret(),clientSecret)){
            throw new CustomOauthException("clientSecret 不匹配"+clientId);
        }
        //密码授权 模式, 组建 authentication
        TokenRequest tokenRequest = new TokenRequest(MapUtils.EMPTY_MAP,clientId,clientDetails.getScope(),"password");

        OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);
        OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request,authentication);

        OAuth2AccessToken token = authorizationServerTokenServices.createAccessToken(oAuth2Authentication);
        //super.onAuthenticationSuccess(request,response,authentication);
        //将 authention 信息打包成json格式返回
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(ResultUtil.success(token)));
    }


    /**
     * 解码请求头
     */
    private String[] extractAndDecodeHeader(String header, HttpServletRequest request) throws IOException {
        byte[] base64Token = header.substring(6).getBytes("UTF-8");

        byte[] decoded;
        try {
            decoded = Base64.decodeBase64(base64Token);
        } catch (IllegalArgumentException var7) {
            throw new BadCredentialsException("Failed to decode basic authentication token");
        }

        String token = new String(decoded, "UTF-8");
        int delim = token.indexOf(":");
        if (delim == -1) {
            throw new BadCredentialsException("Invalid basic authentication token");
        } else {
            return new String[]{token.substring(0, delim), token.substring(delim + 1)};
        }
    }
}
