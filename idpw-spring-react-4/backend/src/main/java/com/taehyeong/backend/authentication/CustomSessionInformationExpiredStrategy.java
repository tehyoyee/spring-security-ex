package com.taehyeong.backend.authentication;

import com.taehyeong.backend.authentication.domain.CustomUserDetails;
import com.taehyeong.backend.authentication.domain.SessionInfo;
import com.taehyeong.backend.authentication.domain.SessionStatus;
import com.taehyeong.backend.authentication.repository.SessionRepository;
import com.taehyeong.backend.authentication.service.SessionService;
import com.taehyeong.backend.service.StompService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
@Component
public class CustomSessionInformationExpiredStrategy implements SessionInformationExpiredStrategy {

    private final SessionRepository sessionRepository;
    private final StompService stompService;
//    private final SessionService sessionService;

    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException {
        HttpServletResponse response = event.getResponse();
        System.out.println("onExpiredSessionDetected START");
        CustomUserDetails customUserDetails = (CustomUserDetails) event.getSessionInformation().getPrincipal();
        System.out.println("event.getSessionInformation().getSessionId() = " + event.getSessionInformation().getSessionId());
        String sessionId = event.getSessionInformation().getSessionId();
        ConcurrentHashMap inactiveSessionList = sessionRepository.getInactiveSessions();
        ConcurrentHashMap activeSessionList = sessionRepository.getActiveSessions();
        if (inactiveSessionList.containsKey(sessionId)) {
            SessionInfo sessionInfo = (SessionInfo) inactiveSessionList.get(sessionId);
            sessionInfo.setStatus(SessionStatus.DUPLICATE);
            System.out.println("onExpiredSessionDetected 이미 갖고 있다.");
        } else {
            if (activeSessionList.containsKey(sessionId)) {
                // 비활성화 세션리스트 존재 X && 세션 활성화 리스트에 존재 O
                System.out.println("onExpiredSessionDetected : 비활성화 리스트에 삽입 11111111111");
                SessionInfo sessionInfo = sessionRepository.getActiveSessions().get(sessionId);
                sessionInfo.setEndTime(LocalDateTime.now());
                sessionInfo.setExpired(true);
                sessionInfo.setStatus(SessionStatus.DUPLICATE);
                inactiveSessionList.put(sessionId, sessionInfo);
                activeSessionList.remove(sessionId);
            } else {
                // 비활성화 세션리스트 존재 X && 세션 활성화 리스트에 존재 X
                System.out.println("onExpiredSessionDetected : 비활성화 리스트에 삽입  222222222");
                inactiveSessionList.put(sessionId,
                        SessionInfo.builder().sessionId(sessionId)
                                .status(SessionStatus.DUPLICATE)
                                .endTime(LocalDateTime.now())
//                                .userId((Long)sessionId.getAttribute("ID"))
                                .isExpired(true)
//                                .username((String)sessionId.getAttribute("USERNAME"))
                                .build());
                activeSessionList.remove(sessionId);
            }
        }
//        stompService.alertDuplicate(customUserDetails.getUsername());
//        sessionRepository.saveInactiveSession(SessionInfo.builder()
//                        .username(customUserDetails.getUsername())
//                        .userId(customUserDetails.getId())
//                .sessionId(event.getSessionInformation().getSessionId())
//                .isExpired(true)
//                .sessionDuration(0)
//                .expireTime(0)
//                .status(SessionStatus.DUPLICATE)
//                .userId(0L)
//                .endTime(LocalDateTime.now())
//                .build()
//        );

        System.out.println("onExpiredSessionDetected END");
//        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//        response.setContentType("application/json;charset=UTF-8");
//        SecurityResponse.fail(response, StatusCode.DUPLICATE_LOGIN);
//        response.getWriter().write("{\"error\": \"SESSION_EXPIRED\", \"message\": \"중복 로그인으로 인해 로그아웃되었습니다.\"}");
//        response.getWriter().flush();
    }

}

