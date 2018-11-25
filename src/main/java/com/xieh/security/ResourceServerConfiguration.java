package com.xieh.security;

import com.xieh.handler.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.Filter;

/**
 * @program security-oauth2
 * @description: 资源服务器
 * @author: Horng
 * @create: 2018/11/24 21:31
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

	private static final String RESOURCE_ID = "my_rest_api";
	@Autowired
	private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;
	@Autowired
	private MyAuthenticationFailHandler myAuthenticationFailHandler;
	@Autowired
	private MyLogoutSuccessHandler myLogoutSuccessHandler;
	@Autowired
	private CustomAccessDeniedHandler customAccessDeniedHandler;
	@Autowired
	private Filter permitAuthenticationFilter;

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) {
		resources.resourceId(RESOURCE_ID).stateless(false);
		resources.authenticationEntryPoint(new AuthExceptionEntryPoint())//自定义授权失败处理器
		.accessDeniedHandler(customAccessDeniedHandler);//自定义的什么处理器 好像无效
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		//http.exceptionHandling().authenticationEntryPoint(new AuthExceptionEntryPoint());
		http.formLogin()
			.loginProcessingUrl("/login/token")
			.successHandler(myAuthenticationSuccessHandler)
			.failureHandler(myAuthenticationFailHandler)
			.and().logout().logoutUrl("/login/out")
			.logoutSuccessHandler(myLogoutSuccessHandler)
			.and().anonymous().disable()
			.requestMatchers().antMatchers("/**")
			.and().authorizeRequests()
			.antMatchers("/user*/**","/login/out").permitAll()
				// configure 上面也有一个 customAccessDeniedHandler 暂时这样把
			.and().exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler())
			.authenticationEntryPoint(new AuthExceptionEntryPoint()) //给上面一样
			.and().addFilterBefore(permitAuthenticationFilter,UsernamePasswordAuthenticationFilter.class);
	}

}