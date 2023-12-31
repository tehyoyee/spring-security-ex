package com.demo.security7.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class CustomUserDetailsService {

	@Bean
	public UserDetailsService userDetailsService() {
		var manager = new InMemoryUserDetailsManager();

//		var user1 = User.withUserDetails("john")     구버전
//				.password("12345")
//				.authorities("READ")
//				.build();

		var user1 = User.builder()
				.username("john")
				.password("12345")
				.authorities("ROLE_ADMIN", "READ")
				.build();

		var user2 = User.builder()
				.username("jane")
				.password("12345")
				.authorities("WRITE", "ROLE_MANAGER")
				.build();

		manager.createUser(user1);
		manager.createUser(user2);

		return manager;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
}
