package com.mycompany.webapp.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

public class Ch17LogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler{
	private static final Logger logger = LoggerFactory.getLogger(Ch17LogoutSuccessHandler.class);
	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		logger.info("onLogoutSuccess 실행");
		super.onLogoutSuccess(request, response, authentication);
	}
}
