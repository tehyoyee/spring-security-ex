//package com.demo.security.authentication.filters;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
//
//public class MyCustomDsl extends AbstractHttpConfigurer<MyCustomDsl, HttpSecurity> {
//
//	@Autowired
//	private InitialAuthenticationFilter initialAuthenticationFilter;
//
//	@Autowired
//	private JwtAuthenticationFilter jwtAuthenticationFilter;
//
//	@Bean
//	public void configure(HttpSecurity http) throws Exception {
//		AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
//
//		http.addFilterAt(
//						initialAuthenticationFilter,
//						BasicAuthenticationFilter.class)
//				.addFilterAfter(
//						jwtAuthenticationFilter,
//						BasicAuthenticationFilter.class
//				);
//	}
//
//	public static MyCustomDsl customDsl() {
//		return new MyCustomDsl();
//	}
//}