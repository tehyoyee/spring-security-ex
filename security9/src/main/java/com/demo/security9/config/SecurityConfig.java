package com.demo.security9.config;

import com.demo.security9.filter.AuthenticationLoggingFilter;
import com.demo.security9.filter.RequestValidationFilter;
//import com.demo.security9.filter.StaticKeyAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

// 기본 필터 앞뒤로 필터 구현

@Configuration
public class SecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.addFilterBefore(
						new RequestValidationFilter(),
						BasicAuthenticationFilter.class)
				.addFilterAfter(
						new AuthenticationLoggingFilter(),
						BasicAuthenticationFilter.class)
				.authorizeHttpRequests((auth->auth
						.anyRequest()
						.permitAll()));

		return http.build();
	}
}

//  헤더에 정적 키 이용 필터 구현
//@Configuration
//public class SecurityConfig {
//
//	@Autowired
//	private StaticKeyAuthenticationFilter filter;
//
//	@Bean
//	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//		http.addFilterAt(filter,
//						BasicAuthenticationFilter.class)
//				.authorizeHttpRequests(auth->auth
//						.anyRequest()
//						.permitAll()
//				);
//
//		return http.build();
//	}
//
//}