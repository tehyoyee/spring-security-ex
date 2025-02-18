package com.tete.back.config;

import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import org.springframework.context.annotation.Bean;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class CustomSessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        System.out.println("세션이 생성되었습니다: " + se.getSession().getId());
        System.out.println("se.getSession().getLastAccessedTime() = " + se.getSession().getLastAccessedTime());
        se.getSession().setAttribute("EXPIRED", false); // 플래그 설정 (선택)
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        System.out.println("세션이 만료되었습니다: " + se.getSession().getId());
        System.out.println("se.getSession().getLastAccessedTime() = " + se.getSession().getLastAccessedTime());
        se.getSession().setAttribute("EXPIRED", true); // 플래그 설정 (선택)
    }

//    @Bean
//    public HttpSessionEventPublisher httpSessionEventPublisher() {
//        return new HttpSessionEventPublisher();
//    }
}

