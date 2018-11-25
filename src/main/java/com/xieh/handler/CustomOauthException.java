package com.xieh.handler;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

/**
 * 自定义异常父类
 * Created by xiech on 2018/11/22.
 */
@JsonSerialize(using = CustomOauthExceptionSerializer.class)
public class CustomOauthException extends OAuth2Exception {
    public CustomOauthException(String msg) {
        super(msg);
    }
}
