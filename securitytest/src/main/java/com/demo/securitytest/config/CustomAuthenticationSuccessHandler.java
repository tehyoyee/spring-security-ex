package com.demo.securitytest.config;

import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse,
			Authentication authentication)
				throws IOException {
		var authorities = authentication.getAuthorities();

		var auth = authorities.stream()
				.filter(a -> a.getAuthority().equals("read"))
				.findFirst();

		if (auth.isPresent()) {
			httpServletResponse
					.sendRedirect("/home");
		} else {
			httpServletResponse
					.sendRedirect("/error");
		}
	}

}