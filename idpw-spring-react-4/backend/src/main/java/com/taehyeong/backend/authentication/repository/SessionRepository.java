package com.taehyeong.backend.authentication.repository;

import com.taehyeong.backend.authentication.domain.SessionInfo;
import com.taehyeong.backend.authentication.domain.SessionStatus;
import lombok.Getter;
import org.springframework.stereotype.Repository;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class SessionRepository {

    @Getter
    private final ConcurrentHashMap<String, SessionInfo> activeSessions = new ConcurrentHashMap<>();

    @Getter
    private final ConcurrentHashMap<String, SessionInfo> inactiveSessions = new ConcurrentHashMap<>();

    private final ConcurrentHashMap<String, String> stompChannelList = new ConcurrentHashMap<>();

    public String generateStompChannel() {
        String newStompChannel = generateSecureHex(32);
        return newStompChannel;
    }

    public void addInactiveSession(String sid) {

    }

    public SessionInfo getSession(Long userId) {
        return activeSessions.get(userId);
    }

//    public List<SessionInfo> getActiveSessions() {
//        return new ArrayList<>(activeSessions.values());
//    }
//
//    public List<SessionInfo> getInactiveSessions() {
//        return new ArrayList<>(inactiveSessions.values());
//    }

    public void addSession(SessionInfo sessionInfo) {
        activeSessions.put(sessionInfo.getSessionId(), sessionInfo);
    }

    public String initStompChannel(String sid) {
        String newStompChannel = generateSecureHex(32);
        activeSessions.get(sid).setStompChannel(newStompChannel);
        return newStompChannel;
    }

//    public void removeSession(SessionInfo sessionInfo, SessionStatus sessionStatus) {
//        sessionInfo.setExpired(true);
//        sessionInfo.setStatus(sessionStatus);
//        sessionInfo.setEndTime(LocalDateTime.now());
//        inactiveSessions.put(sessionInfo.getUserId(), sessionInfo);
//        activeSessions.remove(sessionInfo.getUserId());
//    }

//    public void removeSession(SessionInfo sessionInfo, SessionStatus sessionStatus) {
//        sessionInfo.setExpired(true);
//        sessionInfo.setStatus(sessionStatus);
//        sessionInfo.setEndTime(LocalDateTime.now());
//        inactiveSessions.put(sessionInfo.getUserId(), sessionInfo);
//        activeSessions.remove(sessionInfo.getUserId());
//    }

//    public void saveInactiveSession(SessionInfo sessionInfo) {
//        inactiveSessions.put(sessionInfo.getSessionId(), sessionInfo);
//    }
//
//    public List<SessionInfo> getInActiveSessions() {
//        return new ArrayList<>(inactiveSessions.values());
//    }

    public static String generateSecureHex(int hexLength) {
        if (hexLength % 2 != 0) {
            throw new IllegalArgumentException("hexLength는 짝수여야 합니다.");
        }

        int byteLength = hexLength / 2; // 2 hex digits per byte
        byte[] bytes = new byte[byteLength];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(bytes);

        StringBuilder hexString = new StringBuilder(hexLength);
        for (byte b : bytes) {
            // 각 바이트를 16진수 2자리 문자열로 변환
            hexString.append(String.format("%02x", b));
        }

        return hexString.toString();
    }

}
