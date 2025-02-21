package com.taehyeong.backend.controller;

import com.taehyeong.backend.authentication.SecurityResponse;
import com.taehyeong.backend.authentication.domain.CustomUserDetails;
import com.taehyeong.backend.authentication.domain.LoginInfo;
import com.taehyeong.backend.authentication.domain.SessionInfo;
import com.taehyeong.backend.authentication.repository.SessionRepository;
import com.taehyeong.backend.authentication.service.SessionService;
import com.taehyeong.backend.response.ApiResponse;
import com.taehyeong.backend.response.StatusCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sessions")
public class SessionController {

    private final SessionRegistry sessionRegistry;
    private final SessionService sessionService;
    private final SessionRepository sessionRepository;

    @GetMapping("/left")
    public ApiResponse getSessionRemainingTime(HttpServletRequest request,
                                               HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        Map<String, Object> result = new HashMap<>();

        if (session == null) {
            result.put("message", "No active session");
            result.put("remainingSec", -1);
            return ApiResponse.fail(response, "인증되지 않은 세션입니다.", StatusCode.UNAUTHORIZED);
        }

        int maxInactiveSec = session.getMaxInactiveInterval();
        long lastAccessedMillis = session.getLastAccessedTime();
        long nowMillis = System.currentTimeMillis();

        long elapsedSec = (nowMillis - lastAccessedMillis) / 1000;
        long remainingSec = maxInactiveSec - elapsedSec;

        if (remainingSec < 0) remainingSec = 0; // 이미 만료됐을 수도 있음

        result.put("message", "Active session");
        result.put("maxInactiveSec", maxInactiveSec);
        result.put("remainingSec", remainingSec);
        return ApiResponse.success(result, StatusCode.OK);
    }

    @GetMapping
    public ApiResponse getMembers(HttpServletRequest request) {
        System.out.println("sessionRegistry.getAllPrincipals().size() = " + sessionRegistry.getAllPrincipals().size());
//        sessionRegistry.getAllPrincipals().forEach(
//
//        );
        List<String> sessionInfos = new ArrayList<>();
        List<LoginInfo> loginInfos = new ArrayList<>();
        for (Object principal : sessionRegistry.getAllPrincipals()) {
            // 각 Principal에 연결된 세션 정보
            List<SessionInformation> sessions = sessionRegistry.getAllSessions(principal, true);

            for (SessionInformation sessionInfo : sessions) {
                CustomUserDetails userDetails = (CustomUserDetails) principal;
                long maxInterval = userDetails.getExpireTime().getSeconds();
                long lastRequestMillis = sessionInfo.getLastRequest().getTime();
                long currentMillis = System.currentTimeMillis();
                long elapsedSeconds = (currentMillis - lastRequestMillis) / 1000;
                long remainingSeconds = userDetails.getExpireTime().getSeconds() - elapsedSeconds;
//                System.out.println("remainingSeconds = " + remainingSeconds);
//                System.out.println("elapsedSeconds = " + elapsedSeconds);
//                System.out.println("currentMillis = " + currentMillis);
//                System.out.println("lastRequestMillis = " + lastRequestMillis);
//                System.out.println("maxInterval = " + maxInterval);

                remainingSeconds = remainingSeconds > 0 ? remainingSeconds : 0;
                loginInfos.add(LoginInfo.builder()
                        .id(userDetails.getId())
                        .username(userDetails.getUsername())
                        .sessionId(sessionInfo.getSessionId())
                        .expireTime(userDetails.getExpireTime().getSeconds())
                        .remainingTime(remainingSeconds)
                        .roleName(userDetails.getRole().getName())
                        .authorities(userDetails.getAuthorities().toString())
                        .isSessionExpired(sessionInfo.isExpired())
                        .build());

            }
        }

        return ApiResponse.success(loginInfos, StatusCode.OK);
    }

    @GetMapping("/inactive")
    public ApiResponse getInactiveSessions(HttpServletRequest request) {

        List<SessionInfo> res = sessionRepository.getInactiveSessions().values().stream().toList();
        return ApiResponse.success(res, StatusCode.OK);

    }

    @GetMapping("/active")
    public ApiResponse getActiveSessions(HttpServletRequest request) {

        List<SessionInfo> res = sessionRepository.getActiveSessions().values().stream().toList();
        return ApiResponse.success(res, StatusCode.OK);

    }

    @DeleteMapping("{sessionId}")
    public ApiResponse deleteSession(@PathVariable("sessionId") String sessionId,
                                     HttpServletRequest request,
                                     HttpServletResponse response) {

        HttpSession session = request.getSession(false);
        for (Object principal : sessionRegistry.getAllPrincipals()) {
            // 각 Principal에 연결된 세션 정보
            List<SessionInformation> sessions = sessionRegistry.getAllSessions(principal, true);

            for (SessionInformation sessionInfo : sessions) {
                if (sessionInfo.getSessionId().equals(session.getId())) {
                    return ApiResponse.fail(response, StatusCode.SESSION_DISABLE_SELF);
                }
            }
        }
        sessionService.invalidateSession(sessionId);
        return ApiResponse.success(sessionId, StatusCode.SESSION_INVALIDATED);

    }
}
