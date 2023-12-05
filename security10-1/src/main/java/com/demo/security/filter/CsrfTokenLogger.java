package com.demo.security.filter;

import jakarta.servlet.*;
import org.springframework.security.web.csrf.CsrfToken;

import java.io.IOException;
import java.util.logging.Logger;

public class CsrfTokenLogger implements Filter {

	private Logger logger =
			Logger.getLogger(CsrfTokenLogger.class.getName());

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		Object o = request.getAttribute("_csrf");
		CsrfToken token = (CsrfToken) o;
		logger.info("CSRF token " + token.getToken());

		filterChain.doFilter(request, response);
	}
}