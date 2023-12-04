package com.demo.securitytest.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class CustomAuthenticaionFailureHandler implements AuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest httpServletRequest,
	                                    HttpServletResponse httpServletResponse,
	                                    AuthenticationException exception)
			throws IOException, ServletException {
		httpServletResponse
				.setHeader("failed", LocalDateTime.now().toString());

	}

}