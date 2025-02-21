package com.taehyeong.backend.config;

import com.taehyeong.backend.authentication.domain.CustomUserDetails;
import com.taehyeong.backend.authentication.domain.SessionInfo;
import com.taehyeong.backend.authentication.domain.SessionStatus;
import com.taehyeong.backend.authentication.repository.SessionRepository;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
@WebListener
public class CustomSessionListener implements HttpSessionListener {


    private final SessionRepository sessionRepository;
//    SessionRegistry sessionRegistry;
    // 현재 활성(Active) 세션 수를 관리할 static 변수
    private static int activeSessions = 0;
    private final SessionRegistry sessionRegistry;

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        activeSessions++;
        System.out.println("sessionRegistry.getAllPrincipals().size() = " + sessionRegistry.getAllPrincipals().size());
        System.out.print("[session created] " + session.getId() + " ");
        System.out.println(se.getSession());
        System.out.println("session.getAttribute(\"ID\") = " + session.getAttribute("ID"));
        if (session.getAttribute("ID") != null) {
            System.out.println("비로그인 세션");
            return;
        }
        System.out.println("session = " + session.getAttribute("USERNAME"));
        System.out.println("Active Sessions: " + activeSessions);
//        System.out.println("Active Sessions: " + sessionRegistry.getAllPrincipals() .size());
        System.out.println("===========================");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        HttpSession session = se.getSession();
//        System.out.println("se.getSession(). = " + se.getSession().);
        activeSessions--;
        System.out.println("SessionDestroyed: " + session.getId() + " ");
        System.out.println("session.getAttribute(\"USERNAME\") = " + session.getAttribute("USERNAME"));
        System.out.println("session.getAttribute(\"ID\") = " + session.getAttribute("ID"));
        System.out.println("se.getSession().getId() = " + se.getSession().getId());
        ConcurrentHashMap<String, SessionInfo> inactiveSessionList = sessionRepository.getInactiveSessions();
        ConcurrentHashMap<String, SessionInfo> activeSessionList = sessionRepository.getActiveSessions();
        if (!inactiveSessionList.containsKey(session.getId())) {
            if (sessionRepository.getActiveSessions().containsKey(session.getId())) {
                // 비활성화 세션리스트 존재 X && 세션 활성화 리스트에 존재 O
                SessionInfo sessionInfo = sessionRepository.getActiveSessions().get(session.getId());
                sessionInfo.setEndTime(LocalDateTime.now());
                sessionInfo.setExpired(true);
                System.out.println("sessionInfo.getStatus() = " + sessionInfo.getStatus());
                if (sessionInfo.getStatus().equals(SessionStatus.ALIVE)) {
                    sessionInfo.setStatus(SessionStatus.EXPIRED);
                }
                inactiveSessionList.put(session.getId(), sessionInfo);
                activeSessionList.remove(session.getId());
                System.out.println("세션 아이디 비활성화 리스트로 삽입1");
            } else {
                // 비활성화 세션리스트 존재 X && 세션 활성화 리스트에 존재 X
                inactiveSessionList.put(session.getId(),
                        SessionInfo.builder().sessionId(session.getId())
                                .status(SessionStatus.EXPIRED)
                                .endTime(LocalDateTime.now())
                                .userId((Long)session.getAttribute("ID"))
                                .isExpired(true)
                                .username((String)session.getAttribute("USERNAME")).build());
                activeSessionList.remove(session.getId());
                System.out.println("세션 아이디 비활성화 리스트로 삽입2");
            }
        } else {
            System.out.println("세션 아이디 비활성화 리스트로 안삽입");
        }
//        sessionRepository.removeSession(SessionInfo.builder()
//                .userId().build());
//        sessionRepository.saveInactiveSession(SessionInfo.builder()
//                .endTime(LocalDateTime.now())
//                .sessionId(se.getSession().getId())
//                .status(SessionStatus.EXPIRED).build());

//
//        CustomUserDetails customUserDetails = (CustomUserDetails) event.getSessionInformation().getPrincipal();
//
//        sessionRepository.saveInactiveSession(SessionInfo.builder()
//                .username(customUserDetails.getUsername())
//                .userId(customUserDetails.getId())
//                .sessionId(event.getSessionInformation().getSessionId())
//                .isExpired(true)
//                .sessionDuration(0)
//                .expireTime(0)
//                .status(SessionStatus.DUPLICATE)
//                .userId(0L)
//                .endTime(LocalDateTime.now())
//                .build()
//        );
        System.out.println("sessionRegistry.getAllPrincipals().size() = " + sessionRegistry.getAllPrincipals().size());
        System.out.println("[session destroyed] " + session.getId() + " ");
        System.out.println("session.getAttribute(\"ID\") = " + session.getAttribute("ID"));
        System.out.println("session = " + session);
        if (session.getAttribute("ID") != null) {
            System.out.println("비로그인 세션");
            return;
        }
        System.out.println("session = " + session.getAttribute("USERNAME"));
        System.out.println("Active Sessions: " + activeSessions);
        System.out.println("===========================");
    }
}
