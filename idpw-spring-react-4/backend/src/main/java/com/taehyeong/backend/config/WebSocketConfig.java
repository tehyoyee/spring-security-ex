package com.taehyeong.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 클라이언트가 WebSocket 연결을 맺을 엔드포인트 설정
        registry.addEndpoint("/ws")
                .setAllowedOrigins("http://localhost:3000") // React 앱의 도메인
                .addInterceptors(new HttpSessionHandshakeInterceptor()) // HTTP 세션 정보(쿠키 포함)를 복사
                .withSockJS(); // SockJS를 사용한 폴백 지원
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 메시지 브로커 설정
        registry.enableSimpleBroker("/topic", "/queue"); // 메시지 브로커가 사용할 프리픽스
        registry.setApplicationDestinationPrefixes("/app"); // 애플리케이션 목적지 프리픽스
    }

}