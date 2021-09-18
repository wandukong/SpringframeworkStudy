package com.mycompany.webapp.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

public class Ch17AuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler{
	private static final Logger logger = LoggerFactory.getLogger(Ch17AuthenticationFailureHandler.class);
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {

		logger.info("onAuthenticationFailure 실행");
		super.onAuthenticationFailure(request, response, exception);
	}
}
