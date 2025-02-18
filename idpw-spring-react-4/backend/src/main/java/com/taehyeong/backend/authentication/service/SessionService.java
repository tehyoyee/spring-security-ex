package com.taehyeong.backend.authentication.service;

import com.taehyeong.backend.authentication.domain.SessionInfo;
import com.taehyeong.backend.authentication.domain.SessionStatus;
import com.taehyeong.backend.authentication.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final SessionRegistry sessionRegistry;
    private final SessionRepository sessionRepository;




        public void onDuplicateEvent(SessionInformation sessionInformation) {


    }

    public List<SessionInfo> getInactiveSessions() {
        return sessionRepository.getInActiveSessions();
    }

}
