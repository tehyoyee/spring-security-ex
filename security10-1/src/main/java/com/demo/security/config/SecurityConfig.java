package com.demo.security.config;

import com.demo.security.filter.CsrfTokenLogger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CsrfFilter;

// csrf 토큰

@Configuration
public class SecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
				.addFilterAfter(
						new CsrfTokenLogger(),
						CsrfFilter.class)
				.authorizeHttpRequests((auth->auth
						.anyRequest()
						.permitAll()));

		return http.build();
	}
}
