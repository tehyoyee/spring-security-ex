package com.demo.security7.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.httpBasic(withDefaults());
//		http.authorizeHttpRequests((authz)-> authz.anyRequest().permitAll()); // 모든 접근허용
//		http.authorizeHttpRequests((authz)-> authz.anyRequest().hasAuthority("WRITE")); // WRITE권한 사용자 허용
//		http.authorizeHttpRequests((authz)-> authz.anyRequest().hasAnyAuthority("WRITE", "READ"));
		http.authorizeHttpRequests((authz)-> authz
//				.dispatcherTypeMatchers(FORWARD, ERROR).permitAll()
//				.anyRequest().access(new WebExpressionAuthorizationManager("hasAuthority('WRITE') or hasAuthority('READ')"))
				.anyRequest().access(new WebExpressionAuthorizationManager(
						"T(java.time.LocalTime).now().isAfter(T(java.time.LocalTime).of(22, 0))"))
				// access() 이용하면 SpEL로 시간 등으로 엑세스 제한을 걸 수 있다.
//				.anyRequest().hasRole("MANAGER")
				// prefix는 생략한다 알아서 처리해준다.
//				.anyRequest().access(new WebExpressionAuthorizationManager("hasAuthority('WRITE') and !hasAuthority('READ')"))
				// 위와 같이 복잡한 로직일 수록 access로 처리할 수 있다.
		);

		return http.build();

	}
}
