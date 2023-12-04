package com.demo.security6.config;

import com.demo.security6.service.AuthenticationProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

	@Autowired
	private AuthenticationProviderService authenticationProvider;


	@Bean
	protected SecurityFilterChain filterChain(AuthenticationManagerBuilder auth, HttpSecurity http) throws Exception {
		http
				.formLogin()
				.defaultSuccessUrl("/main", true);
		http
				.authorizeHttpRequests()
				.anyRequest().authenticated();
		return http.build();
	}

}
