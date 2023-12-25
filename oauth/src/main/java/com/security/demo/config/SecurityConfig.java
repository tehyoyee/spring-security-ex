package com.security.demo.config;

import com.security.demo.service.CustomOauth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Autowired
    CustomOauth2UserService customOauth2UserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/").permitAll()
                .requestMatchers("/login").permitAll()
                .requestMatchers("/user").hasRole("USER")
                .anyRequest().authenticated());
        http
                .exceptionHandling(eh -> eh
                        .accessDeniedPage("accessDenied"));
        http
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/").permitAll())
                .oauth2Login(oauth -> oauth
                        .loginPage("/login")
                        .userInfoEndpoint(u -> u
                                .userService(customOauth2UserService)
                        ));
//    @Bean
//    public ClientRegistrationRepository clientRepository() {
//        var c = clientRegistration();
//        return new InMemoryClientRegistrationRepository(c);
//    }
        return http.build();
    }
}