package com.example.security.config;

import com.example.security.csrf.CustomCsrfTokenRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.XorCsrfTokenRequestAttributeHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public CsrfTokenRepository customTokenRepository() {
		return new CustomCsrfTokenRepository();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		XorCsrfTokenRequestAttributeHandler requestHandler = new XorCsrfTokenRequestAttributeHandler();
		// set the name of the attribute the CsrfToken will be populated on
		requestHandler.setCsrfRequestAttributeName("_csrf");
//		requestHandler.setCsrfRequestAttributeName(null);
		http
				// ...
				.csrf((csrf) -> csrf
				.csrfTokenRequestHandler(requestHandler)
				.csrfTokenRepository(customTokenRepository()));

//		http.csrf(cs -> cs
//				.csrfTokenRepository(customTokenRepository())
//				.ignoringRequestMatchers("ciao"));

//		http.csrf(c -> {
//			c.csrfTokenRepository(customTokenRepository());
//			c.ignoringRequestMatchers("/ciao");

//            HandlerMappingIntrospector i = new HandlerMappingIntrospector();
//            MvcRequestMatcher r = new MvcRequestMatcher(i, "/ciao");
//            c.ignoringRequestMatchers(r);

//            String pattern = ".*[0-9].*";
//            String httpMethod = HttpMethod.POST.name();
//            RegexRequestMatcher r = new RegexRequestMatcher(pattern, httpMethod);
//            c.ignoringRequestMatchers(r);

		http.authorizeHttpRequests(auth -> auth
				.anyRequest().permitAll());

		return http.build();
	}
}