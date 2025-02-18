package com.taehyeong.backend.controller;

import com.taehyeong.backend.authentication.domain.CustomUserDetails;
import com.taehyeong.backend.authentication.domain.SessionStatus;
import com.taehyeong.backend.response.ApiResponse;
import com.taehyeong.backend.response.StatusCode;
import jakarta.annotation.Nullable;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class MainController {

    private final SimpMessagingTemplate messagingTemplate;
//    private final Map<Long, String> SessionList = new HashMap<>();
    private final SessionRegistry sessionRegistry;

    @GetMapping
    public ResponseEntity<Map<String, String>> main() {

        Map<String, String> res = new HashMap<>();
        res.put("asdf", "zxcv");

        return ResponseEntity.ok(res);

    }

    @GetMapping("/test")
    public void asdf() {
        System.out.println("asdf");
    }

    @MessageMapping("/session-checker")
    @SendToUser("/queue/reply")
    public String checkSession(String message, Principal principal, StompHeaderAccessor accessor) {
        System.out.println("checkSession");
        if (principal == null) {
            System.out.println("Error: Principal is null");
            return SessionStatus.EXPIRED.toString();
        }
        String sessionId = (String) accessor.getSessionAttributes().get("HTTP.SESSION.ID");
        if (sessionId == null) {
            System.out.println("sessionId " + sessionId + "died");
            return SessionStatus.EXPIRED.toString();
        }
        SessionInformation sessionInformation = sessionRegistry.getSessionInformation(sessionId);
        if (sessionInformation == null) {
            messagingTemplate.convertAndSendToUser(sessionId, "/queue/reply",
                    "Direct reply to " + sessionId + ": " + message);
            return SessionStatus.EXPIRED.toString();
        }
        System.out.println("meesssage sendedededr");
        return SessionStatus.ALIVE.toString();

    }

    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public String processMessage(String message, StompHeaderAccessor stompHeaderAccessor) throws Exception {

        System.out.println(stompHeaderAccessor.getSessionAttributes().get("HTTP.SESSION.ID"));

//        for (Map.Entry<String, Object> x : stompHeaderAccessor.getSessionAttributes().entrySet()) {
//            System.out.println(x);
//        }
//        System.out.println("stompHeaderAccessor = " + stompHeaderAccessor.getSessionAttributes());
//        System.out.println(stompHeaderAccessor.getSessionAttributes().toString());
//        System.out.println(stompHeaderAccessor.getSessionId());
//        System.out.println(stompHeaderAccessor.getMessage());
//        System.out.println(stompHeaderAccessor.getDestination());
//        System.out.println(stompHeaderAccessor.getSessionId());
//        System.out.println(stompHeaderAccessor.getSessionAttributes());
//        System.out.println("message = " + message);
//        System.out.println("stompHeaderAccessor = " + stompHeaderAccessor.getFirstNativeHeader("JSESSIONID"));

//        HTTP.SESSION.ID=753D5B470B26B94419F03B1CC0FFC36C
//                USERNAME=asdf
//        ID=1
//        SPRING_SECURITY_CONTEXT=SecurityContextImpl [Authentication=UsernamePasswordAuthenticationToken [Principal=com.taehyeong.backend.authentication.domain.CustomUserDetails@1, Credentials=[PROTECTED], Authenticated=true, Details=null, Granted Authorities=[MENU1_WRITE, MENU1_READ]]]
//        stompHeaderAccessor = {HTTP.SESSION.ID=753D5B470B26B94419F03B1CC0FFC36C, USERNAME=asdf, ID=1, SPRING_SECURITY_CONTEXT=SecurityContextImpl [Authentication=UsernamePasswordAuthenticationToken [Principal=com.taehyeong.backend.authentication.domain.CustomUserDetails@1, Credentials=[PROTECTED], Authenticated=true, Details=null, Granted Authorities=[MENU1_WRITE, MENU1_READ]]]}
//        {HTTP.SESSION.ID=753D5B470B26B94419F03B1CC0FFC36C, USERNAME=asdf, ID=1, SPRING_SECURITY_CONTEXT=SecurityContextImpl [Authentication=UsernamePasswordAuthenticationToken [Principal=com.taehyeong.backend.authentication.domain.CustomUserDetails@1, Credentials=[PROTECTED], Authenticated=true, Details=null, Granted Authorities=[MENU1_WRITE, MENU1_READ]]]}
//        jzhrhbe5
//        null
//                /app/chat
//        jzhrhbe5
//        {HTTP.SESSION.ID=753D5B470B26B94419F03B1CC0FFC36C, USERNAME=asdf, ID=1, SPRING_SECURITY_CONTEXT=SecurityContextImpl [Authentication=UsernamePasswordAuthenticationToken [Principal=com.taehyeong.backend.authentication.domain.CustomUserDetails@1, Credentials=[PROTECTED], Authenticated=true, Details=null, Granted Authorities=[MENU1_WRITE, MENU1_READ]]]}

//        HTTP.SESSION.ID=01751A78C6F83BBD16AD7DE4FD8D82E1
//                USERNAME=asdf
//        ID=1
//        SPRING_SECURITY_CONTEXT=SecurityContextImpl [Authentication=UsernamePasswordAuthenticationToken [Principal=com.taehyeong.backend.authentication.domain.CustomUserDetails@1, Credentials=[PROTECTED], Authenticated=true, Details=null, Granted Authorities=[MENU1_WRITE, MENU1_READ]]]
//        stompHeaderAccessor = {HTTP.SESSION.ID=01751A78C6F83BBD16AD7DE4FD8D82E1, USERNAME=asdf, ID=1, SPRING_SECURITY_CONTEXT=SecurityContextImpl [Authentication=UsernamePasswordAuthenticationToken [Principal=com.taehyeong.backend.authentication.domain.CustomUserDetails@1, Credentials=[PROTECTED], Authenticated=true, Details=null, Granted Authorities=[MENU1_WRITE, MENU1_READ]]]}
//        {HTTP.SESSION.ID=01751A78C6F83BBD16AD7DE4FD8D82E1, USERNAME=asdf, ID=1, SPRING_SECURITY_CONTEXT=SecurityContextImpl [Authentication=UsernamePasswordAuthenticationToken [Principal=com.taehyeong.backend.authentication.domain.CustomUserDetails@1, Credentials=[PROTECTED], Authenticated=true, Details=null, Granted Authorities=[MENU1_WRITE, MENU1_READ]]]}
//        2hyoi0yk
//        null
//                /app/chat
//        2hyoi0yk
//        {HTTP.SESSION.ID=01751A78C6F83BBD16AD7DE4FD8D82E1, USERNAME=asdf, ID=1, SPRING_SECURITY_CONTEXT=SecurityContextImpl [Authentication=UsernamePasswordAuthenticationToken [Principal=com.taehyeong.backend.authentication.domain.CustomUserDetails@1, Credentials=[PROTECTED], Authenticated=true, Details=null, Granted Authorities=[MENU1_WRITE, MENU1_READ]]]}
//        message = SESSION_CHECKER
//        stompHeaderAccessor = null
//        message = SESSION_CHECKER


        // 간단하게 서버가 받은 메시지 앞에 접두사를 붙여서 반환
        System.out.println("message = " + message);
        return "Server Received: " + message;
    }

//    @MessageMapping("/chat.sendMessage")
//    @SendTo("/topic/public") // "/topic/public"을 구독 중인 모든 클라이언트에게 메시지를 보냄
//    public ApiResponse sendMessage() {
//        System.out.println("sendMessage");
////        HttpSession session = request.getSession(false);
//        Map<String, Object> result = new HashMap<>();
//
//        if (session == null) {
//            result.put("message", "No active session");
//            result.put("remainingSec", -1);
//            return ApiResponse.failure("인증되지 않은 세션입니다.", StatusCode.UNAUTHORIZED);
//        }
//
//        int maxInactiveSec = session.getMaxInactiveInterval();
//        long lastAccessedMillis = session.getLastAccessedTime();
//        long nowMillis = System.currentTimeMillis();
//
//        long elapsedSec = (nowMillis - lastAccessedMillis) / 1000;
//        long remainingSec = maxInactiveSec - elapsedSec;
//
//        if (remainingSec < 0) remainingSec = 0; // 이미 만료됐을 수도 있음
//
//        result.put("message", "Active session");
//        result.put("maxInactiveSec", maxInactiveSec);
//        result.put("remainingSec", remainingSec);
//        return ApiResponse.success(result, StatusCode.OK);
//    }

}
