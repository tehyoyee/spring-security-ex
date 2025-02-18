package com.taehyeong.backend.config;

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
        sessionRepository.saveInactiveSession(SessionInfo.builder()
                .endTime(LocalDateTime.now())
                .sessionId(se.getSession().getId())
                .status(SessionStatus.EXPIRED).build());
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
