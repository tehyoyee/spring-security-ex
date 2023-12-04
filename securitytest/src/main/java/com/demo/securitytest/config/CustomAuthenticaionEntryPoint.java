package com.demo.securitytest.config;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticaionEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse,
			AuthenticationException e) throws IOException, ServletException {

		httpServletResponse
				.addHeader("message", "Luke, I am your father!");
		httpServletResponse
				.sendError(HttpStatus.UNAUTHORIZED.value());
	}

}
