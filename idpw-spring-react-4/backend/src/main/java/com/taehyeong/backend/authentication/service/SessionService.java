package com.taehyeong.backend.authentication.service;

import com.taehyeong.backend.authentication.domain.SessionInfo;
import com.taehyeong.backend.authentication.domain.SessionStatus;
import com.taehyeong.backend.authentication.repository.SessionRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final SessionRegistry sessionRegistry;
    private final SessionRepository sessionRepository;

    public void invalidateSession(String sessionId) {

        ConcurrentHashMap<String, SessionInfo> activeSessionList = sessionRepository.getActiveSessions();
        ConcurrentHashMap<String, SessionInfo> inactiveSessionList = sessionRepository.getInactiveSessions();
        boolean flag = false;
        for (Object principal : sessionRegistry.getAllPrincipals()) {
            for (SessionInformation sessionInformation : sessionRegistry.getAllSessions(principal, false)) {
                if (sessionInformation.getSessionId().equals(sessionId)) {
                    sessionInformation.expireNow();
                    sessionRegistry.removeSessionInformation(sessionId);

                    SessionInfo sessionInfo = activeSessionList.get(sessionInformation.getSessionId());
                    if (sessionInfo != null) {
                        sessionInfo.setExpired(true);
                        sessionInfo.setStatus(SessionStatus.KICKED);
                        sessionInfo.setEndTime(LocalDateTime.now());
                        activeSessionList.remove(sessionInformation.getSessionId());
                        inactiveSessionList.put(sessionId, sessionInfo);
                    }
                    flag = true;
                    break;
                }
            }
            if (flag) {
                break;
            }
        }

    }

    public void onDuplicateEvent(SessionInformation sessionInformation) {


    }

//    public List<SessionInfo> getInactiveSessions() {
//        return sessionRepository.getInActiveSessions();
//    }

}
