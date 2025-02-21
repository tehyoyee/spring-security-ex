package com.taehyeong.backend.config;

import com.taehyeong.backend.authentication.*;
//import com.taehyeong.backend.authentication.CustomAuthenticationFailureHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    @Value("${frontend.url}")
    private String frontendUrl;
    private final AuthenticationProviderImpl authenticationProvider;
    private final CustomAuthenticationEntryPoint authenticationEntryPoint;
    private final CustomSessionInformationExpiredStrategy customSessionInformationExpiredStrategy;
//    private final LogoutAuthenticationFilter logoutAuthenticationFilter;

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    // 요게 있어야 세션 자동 로그아웃
    @Bean
    public ServletListenerRegistrationBean<HttpSessionEventPublisher> httpSessionEventPublisher() {
        ServletListenerRegistrationBean<HttpSessionEventPublisher> listenerRegBean =
                new ServletListenerRegistrationBean<>(new HttpSessionEventPublisher());
        return listenerRegBean;
    }
    @Bean
    SecurityFilterChain securityFilterChain(AuthenticationManagerBuilder auths, HttpSecurity http, SessionAuthenticationFilter sessionAuthenticationFilter, LoginAuthenticationFilter loginAuthenticationFilter,
                                            SessionRegistry sessionRegistry) throws Exception {


        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(auth -> {
                    auth
                            .requestMatchers("/ws/**","/ws", "/members", "/members/login").permitAll()
                            .anyRequest().authenticated();
                })
                .sessionManagement(session -> session
                        .sessionFixation().newSession()
                        .maximumSessions(1) // 최대 세션 수
                        .maxSessionsPreventsLogin(false)
                        .sessionRegistry(sessionRegistry)
                        .expiredSessionStrategy(customSessionInformationExpiredStrategy)
                )
                .exceptionHandling(handler ->
                        handler.authenticationEntryPoint(authenticationEntryPoint))
                .addFilterAfter(
                        loginAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                )
                .addFilterBefore(
                        sessionAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                )
//                .addFilterAfter(logoutAuthenticationFilter, SessionAuthenticationFilter.class);
                ;
                return http.build();
    }


    @Bean
    CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of(frontendUrl));
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", configuration);
        configuration.setExposedHeaders(Arrays.asList("Set-Cookie"));

        return urlBasedCorsConfigurationSource;

    }

}
