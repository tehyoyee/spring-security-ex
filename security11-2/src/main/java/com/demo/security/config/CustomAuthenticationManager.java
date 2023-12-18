//package com.demo.security.config;
//
//import com.demo.security.authentication.provider.OtpAuthenticationProvider;
//import com.demo.security.authentication.provider.UsernamePasswordAuthenticationProvider;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.ProviderManager;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.stereotype.Component;
//
//@Component
//public class CustomAuthenticationManager {
//
//    @Autowired
//    private OtpAuthenticationProvider otpAuthenticationProvider;
//
//    @Autowired
//    private UsernamePasswordAuthenticationProvider usernamePasswordAuthenticationProvider;
//
////    @Bean
////    public AuthenticationManager manager(AuthenticationManagerBuilder managerBuilder) throws Exception {
////        return managerBuilder.authenticationProvider(otpAuthenticationProvider)
////                .authenticationProvider(usernamePasswordAuthenticationProvider).build();
////    }
//    @Bean
//    public AuthenticationManager manager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//        ProviderManager authenticationManager = (ProviderManager)authenticationConfiguration.getAuthenticationManager();
//        authenticationManager.getProviders().add(otpAuthenticationProvider);
////        authenticationManager.getProviders().add(usernamePasswordAuthenticationProvider);
//        return authenticationManager;
//}
//
//
////    public AuthenticationManager manager(AuthenticationManagerBuilder managerBuilder) throws Exception {
////        return managerBuilder.authenticationProvider(otpAuthenticationProvider)
////                .authenticationProvider(usernamePasswordAuthenticationProvider).build();
////    }
//}
