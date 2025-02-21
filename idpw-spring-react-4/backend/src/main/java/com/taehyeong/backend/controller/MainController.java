package com.taehyeong.backend.controller;

import com.taehyeong.backend.authentication.domain.CustomUserDetails;
import com.taehyeong.backend.authentication.domain.SessionInfo;
import com.taehyeong.backend.authentication.domain.SessionStatus;
import com.taehyeong.backend.authentication.repository.SessionRepository;
import com.taehyeong.backend.response.ApiResponse;
import com.taehyeong.backend.response.StatusCode;
import jakarta.annotation.Nullable;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
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
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequiredArgsConstructor
public class MainController {

    private final SimpMessagingTemplate messagingTemplate;
//    private final Map<Long, String> SessionList = new HashMap<>();
    private final SessionRegistry sessionRegistry;
    private final SessionRepository sessionRepository;

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
        System.out.println("SessionChecker : checkSession");
        System.out.println("SessionChecker : message = " + message);
        if (message == null || message.equals(false)) {
            return SessionStatus.LOGOUT.toString();
        }
        for (Map.Entry<String, SessionInfo> stringSessionInfoEntry : sessionRepository.getInactiveSessions().entrySet()) {
            System.out.println("SessionChecker : stringSessionInfoEntry = " + stringSessionInfoEntry);
            if (stringSessionInfoEntry.getValue().getStompChannel().equals(message)) {
                System.out.println("/topic/" + message + "메시지 보냄");
                messagingTemplate.convertAndSend("/topic/" + message, stringSessionInfoEntry.getValue().getStatus().toString());
            }
        }
        if (principal == null) {
            System.out.println("SessionChecker : Error: Principal is null");
            System.out.println("SessionChecker : asdfasdf");
            return SessionStatus.EXPIRED.toString();
        }
        String sessionId = (String) accessor.getSessionAttributes().get("HTTP.SESSION.ID");
        if (sessionId == null) {
            System.out.println("SessionChecker : sessionId is null");
            return SessionStatus.EXPIRED.toString();
        }
        System.out.println("SessionChecker : sessionId = " + sessionId);
        SessionInformation sessionInformation = sessionRegistry.getSessionInformation(sessionId);
        if (sessionInformation == null) {
            messagingTemplate.convertAndSendToUser(sessionId, "/queue/reply",
                    "Direct reply to " + sessionId + ": " + message);
            System.out.println("세션");
            return SessionStatus.EXPIRED.toString();
        }
        if (sessionInformation.isExpired()) {
            System.out.println("SessionChecker : sessionInformation is expired");
            return SessionStatus.EXPIRED.toString();
        }
        System.out.println("SessionChecker : checkSession ended");
        return SessionStatus.ALIVE.toString();

    }

    @MessageMapping("/chat")
//    @SendTo("/topic/messages")
    public void processMessage(String message, StompHeaderAccessor stompHeaderAccessor) throws Exception {
        if (message == null || message.length() != 32) {
            messagingTemplate.convertAndSend("/topic/" + message, SessionStatus.LOGOUT.toString());
            return ;
        }
        Map<String, SessionInfo> inactiveSessionList = sessionRepository.getInactiveSessions();
        for (SessionInfo sessionInfo : inactiveSessionList.values()) {
            if (sessionInfo.getStompChannel() == null) {
                continue;
            }
            if (sessionInfo.getStompChannel().equals(message)) {
                SessionStatus sessionStatus = sessionInfo.getStatus();
                messagingTemplate.convertAndSend("/topic/" + message, sessionStatus.toString());
                return;
            }
        }
        messagingTemplate.convertAndSend("/topic/" + message, SessionStatus.ALIVE.toString());

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
