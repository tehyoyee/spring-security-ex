package com.demo.security.config;

import com.demo.security.authentication.filters.InitialAuthenticationFilter;
import com.demo.security.authentication.filters.JwtAuthenticationFilter;
import com.demo.security.authentication.provider.OtpAuthenticationProvider;
import com.demo.security.authentication.provider.UsernamePasswordAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Autowired
    private InitialAuthenticationFilter initialAuthenticationFilter;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

//    @Autowired
//    private OtpAuthenticationProvider otpAuthenticationProvider;
//
//    @Autowired
//    private UsernamePasswordAuthenticationProvider usernamePasswordAuthenticationProvider;
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) {
//        auth.authenticationProvider(otpAuthenticationProvider)
//                .authenticationProvider(usernamePasswordAuthenticationProvider);
//    }

//    @Bean
//    AuthenticationManager authenticationManager(AuthenticationManagerBuilder managerBuilder) throws Exception {
//        return managerBuilder.authenticationProvider(otpAuthenticationProvider)
//                .authenticationProvider(usernamePasswordAuthenticationProvider).build();
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf
                .disable());

        http.addFilterAt(
                        initialAuthenticationFilter,
                        BasicAuthenticationFilter.class)
                .addFilterAfter(
                        jwtAuthenticationFilter,
                        BasicAuthenticationFilter.class
                );

        http.authorizeHttpRequests(auth -> auth
                .anyRequest().authenticated());
        return http.build();
    }

//    @Bean
//    protected AuthenticationManager authenticationManager() throws Exception {
//        return super.authenticationManager();
//    }
}