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

@RequiredArgsConstructor
@Component
public class CustomSessionInformationExpiredStrategy implements SessionInformationExpiredStrategy {

    private final SessionRepository sessionRepository;
    private final StompService stompService;
//    private final SessionService sessionService;

    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException {
        HttpServletResponse response = event.getResponse();
        CustomUserDetails customUserDetails = (CustomUserDetails) event.getSessionInformation().getPrincipal();
//        stompService.alertDuplicate(customUserDetails.getUsername());
        sessionRepository.saveInactiveSession(SessionInfo.builder()
                        .username(customUserDetails.getUsername())
                        .userId(customUserDetails.getId())
                .sessionId(event.getSessionInformation().getSessionId())
                .isExpired(true)
                .sessionDuration(0)
                .expireTime(0)
                .status(SessionStatus.DUPLICATE)
                .userId(0L)
                .endTime(LocalDateTime.now())
                .build()
        );

        System.out.println("중복 로그인 감지");
//        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//        response.setContentType("application/json;charset=UTF-8");
//        SecurityResponse.fail(response, StatusCode.DUPLICATE_LOGIN);
//        response.getWriter().write("{\"error\": \"SESSION_EXPIRED\", \"message\": \"중복 로그인으로 인해 로그아웃되었습니다.\"}");
//        response.getWriter().flush();
    }

}

