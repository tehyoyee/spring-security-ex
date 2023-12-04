package com.demo.securitytest.config;

//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.password.NoOpPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//public class ProjectConfig {
//
//	@Bean
//	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//		http.httpBasic();
//		http.authorizeRequests()
//				.anyRequest().authenticated(); //.permitAll() 인증없이 가능
//		return http.build();
//	}
//
//	@Bean
//	public UserDetailsService userDetailsService() {
//		var userDetailsService = new InMemoryUserDetailsManager();
//
//		var user = User.withUsername("john")
//				.password("12345")
//				.authorities("read")
//				.build();
//		userDetailsService.createUser(user);
//		return userDetailsService;
//	}
//
//	@Bean
//	public PasswordEncoder passwordEncoder() {
//		return NoOpPasswordEncoder.getInstance();
//	}
//}

import com.demo.securitytest.entity.User;
//import com.demo.securitytest.service.InMemoryUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;
import java.util.List;

@Configuration
public class ProjectConfig {
//
//	@Bean
//	public UserDetailsService userDetailsService(DataSource dataSource) {
//
//		String usersByUsernameQuery =
//				"select username, password, enabled from users where username = ?";
//		String authsByUserQuery =
//				"select username, authority from spring.authorities where username = ?";
//
//		var userDetailsManager = new JdbcUserDetailsManager(dataSource);
//		userDetailsManager.setUsersByUsernameQuery(usersByUsernameQuery);
//		userDetailsManager.setAuthoritiesByUsernameQuery(authsByUserQuery);
//
//		return userDetailsManager;
//	}

	@Autowired
	private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

	@Autowired
	private CustomAuthenticaionFailureHandler customAuthenticaionFailureHandler;

	@Autowired
	private AuthenticationProvider authenticationProvider;

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return new ProviderManager(authenticationProvider);
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
//					.httpBasic(c->{
//						c.realmName("OTHER");
//					})
				.formLogin()
				.successHandler(customAuthenticationSuccessHandler)
				.failureHandler(customAuthenticaionFailureHandler);
//					.requestMatchers("/login").per
		http
				.authorizeHttpRequests()
				.anyRequest().authenticated();


		return http.build();
	}

	}
