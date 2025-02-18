package com.taehyeong.backend.authentication.repository;

import com.taehyeong.backend.authentication.domain.SessionInfo;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class SessionRepository {

    private final ConcurrentHashMap<String, SessionInfo> activeSessions = new ConcurrentHashMap<>();

    private final ConcurrentHashMap<String, SessionInfo> inactiveSessions = new ConcurrentHashMap<>();

    public void saveInactiveSession(SessionInfo sessionInfo) {
        inactiveSessions.put(sessionInfo.getSessionId(), sessionInfo);
    }

    public List<SessionInfo> getInActiveSessions() {
        return new ArrayList<>(inactiveSessions.values());
    }

}
