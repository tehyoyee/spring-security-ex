//package com.taehyeong.chulcheck.config;
//
//import com.taehyeong.backend.config.CustomSessionListener;
//import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class WebConfig implements WebMvcConfigurer {
//
//    @Bean
//    public ServletListenerRegistrationBean<CustomSessionListener> sessionListener() {
//        ServletListenerRegistrationBean<CustomSessionListener> bean =
//                new ServletListenerRegistrationBean<>(new CustomSessionListener());
//        return bean;
//    }
//
//}
