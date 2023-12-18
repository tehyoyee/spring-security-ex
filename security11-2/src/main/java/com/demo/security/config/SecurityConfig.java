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
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.util.ArrayList;
import java.util.List;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private InitialAuthenticationFilter initialAuthenticationFilter;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private OtpAuthenticationProvider otpAuthenticationProvider;

    @Autowired
    private UsernamePasswordAuthenticationProvider usernamePasswordAuthenticationProvider;

//    @Autowired
//    private AuthenticationConfiguration authenticationConfiguration;

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) {
//        auth.authenticationProvider(otpAuthenticationProvider)
//                .authenticationProvider(usernamePasswordAuthenticationProvider);
//    }

//    @Bean
//    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
//        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
//        authenticationManagerBuilder.authenticationProvider(otpAuthenticationProvider).authenticationProvider(usernamePasswordAuthenticationProvider);
//        // Get AuthenticationManager
//        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();
//        return authenticationManager;
//    }
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//        return authenticationConfiguration.getAuthenticationManager();
//    }



    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable());
        http
                .authorizeHttpRequests(auth -> auth
                .anyRequest().authenticated());
//        http.apply(customDsl());
        http.addFilterAt(
                        initialAuthenticationFilter,
                        BasicAuthenticationFilter.class)
                .addFilterAfter(
                        jwtAuthenticationFilter,
                        BasicAuthenticationFilter.class
                );

        return http.build();
    }

//    @Bean
//    public AuthenticationManager manager(AuthenticationManagerBuilder managerBuilder) throws Exception {
//        return managerBuilder.authenticationProvider(otpAuthenticationProvider)
//                .authenticationProvider(usernamePasswordAuthenticationProvider).build();
//    }
//    @Bean
//    public AuthenticationManager authenticationManager() {
//        return new ProviderManager(new CustomAuthenticationManager());
//    }

//    @Bean
//    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager
//    = authenticationConfiguration.authenticationManager
//    @Override
//    @Bean
//    protected AuthenticationManager authenticationManager() throws Exception {
//        return super.authenticationManager();
//    }
}