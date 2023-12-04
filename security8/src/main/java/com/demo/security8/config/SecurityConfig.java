package com.demo.security8.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
				.httpBasic(withDefaults());
		http.authorizeHttpRequests((authz)-> authz
				.antMatchers("/hello").hasRole("ADMIN")
				.requestMatchers("/ciao").hasRole("MANAGER")
				.anyRequest().permitAll()
		);

		return http.build();

	}
}
