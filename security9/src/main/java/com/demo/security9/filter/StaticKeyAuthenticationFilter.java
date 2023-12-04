//package com.demo.security9.filter;
//
//import jakarta.servlet.*;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//
//// 정적 키를 이용한 필터 구현
//@Component
//public class StaticKeyAuthenticationFilter implements Filter {
//
//	@Value("${authorization.key}")
//	private String authorizationKey;
//
//	@Override
//	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
//		var httpRequest = (HttpServletRequest) request;
//		var httpResponse = (HttpServletResponse) response;
//
//		String authentication = httpRequest.getHeader("Authorization");
//
//		if (authorizationKey.equals(authentication)) {
//			filterChain.doFilter(request, response);
//		} else {
//			httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//		}
//	}
//}