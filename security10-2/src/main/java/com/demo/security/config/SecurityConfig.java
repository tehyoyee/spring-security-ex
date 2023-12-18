package com.demo.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

// csrf 토큰

@Configuration
public class SecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(cs -> cs
				.ignoringRequestMatchers("/ciao"));
		http.authorizeHttpRequests(auth->auth
				.anyRequest().authenticated());
		http.formLogin(login -> login
				.defaultSuccessUrl("/main", true));
		return http.build();
	}

}
