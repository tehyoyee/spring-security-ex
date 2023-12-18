package com.demo.security.config;

import com.demo.security.authentication.provider.OtpAuthenticationProvider;
import com.demo.security.authentication.provider.UsernamePasswordAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

@Configuration
public class CustomAuthenticationManager {

    @Autowired
    OtpAuthenticationProvider otpAuthenticationProvider;

    @Autowired
    UsernamePasswordAuthenticationProvider usernamePasswordAuthenticationProvider;

    @Bean
    AuthenticationManager authenticationManager(AuthenticationManagerBuilder managerBuilder) throws Exception {
        return managerBuilder.authenticationProvider(otpAuthenticationProvider)
                .authenticationProvider(usernamePasswordAuthenticationProvider).build();
    }
}
