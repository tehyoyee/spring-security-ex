package com.demo.security9.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.logging.Logger;

// 단순 로깅 필터
//public class AuthenticationLoggingFilter implements Filter {
//
//	private final Logger logger =
//			Logger.getLogger(AuthenticationLoggingFilter.class.getName());
//
//	@Override
//	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
//		var httpRequest = (HttpServletRequest) request;
//		String requestId = httpRequest.getHeader("Request-Id");
//		logger.info("Successfully authenticated request with id " +  requestId);
//		filterChain.doFilter(request, response);
//	}
//}

// 한번만 실행되게 보장 extends OncePerRequestFilter
public class AuthenticationLoggingFilter extends OncePerRequestFilter {

	private final Logger logger =
			Logger.getLogger(AuthenticationLoggingFilter.class.getName());


	@Override
	protected void doFilterInternal(HttpServletRequest request,
	                                HttpServletResponse response,
	                                FilterChain filterChain) throws ServletException, IOException {

		String requestId = request.getHeader("Request-Id");

		logger.info("Successfully authenticated request with id " +  requestId);

		filterChain.doFilter(request, response);
	}
}