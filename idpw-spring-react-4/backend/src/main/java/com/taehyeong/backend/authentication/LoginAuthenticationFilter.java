package com.taehyeong.backend.authentication;

import com.taehyeong.backend.authentication.domain.CustomUserDetails;
import com.taehyeong.backend.authentication.domain.SessionInfo;
import com.taehyeong.backend.authentication.domain.SessionStatus;
import com.taehyeong.backend.authentication.repository.MemberRepository;
import com.taehyeong.backend.authentication.repository.SessionRepository;
import com.taehyeong.backend.authentication.service.CustomUserDetailsService;
import com.taehyeong.backend.request.MemberLoginDTO;
import com.taehyeong.backend.response.StatusCode;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class LoginAuthenticationFilter extends OncePerRequestFilter {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationProviderImpl authenticationProvider;
    private final SessionRepository sessionRepository;
    private final SessionRegistry sessionRegistry;
    private final HttpSession httpSession;
    private final HttpSession session;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        HttpSession session = request.getSession(true);

        for (Object principal : sessionRegistry.getAllPrincipals()) {
            List<SessionInformation> sessions = sessionRegistry.getAllSessions(principal, true);
            for (SessionInformation sessionInfo : sessions) {
                if (sessionInfo.getSessionId().equals(session.getId())) {
                    SecurityResponse.fail(response, StatusCode.SESSION_ALREADY_LOGGEDIN);
                    return;
                }
            }
        }
        ConcurrentHashMap<String, SessionInfo> activeSessionList = sessionRepository.getActiveSessions();
        ConcurrentHashMap<String, SessionInfo> inactiveSessionList = sessionRepository.getInactiveSessions();
        // 비활성화된 세션이라면 세션 재생성
        if (inactiveSessionList.containsKey(session.getId())) {
            request.getSession().invalidate();
            session = request.getSession(true);
        }

        // Authentication 인증
        try {
            String body = new String(request.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
            JSONObject json = new JSONObject(body);
            String username = json.getString("username");
            String password = json.getString("password");
            MemberLoginDTO memberLoginDTO = new MemberLoginDTO(username, password);
            Authentication authentication = authenticationProvider.authenticate(
                    new UsernamePasswordAuthenticationToken(memberLoginDTO.username(), memberLoginDTO.password())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            session.setMaxInactiveInterval((int)userDetails.getExpireTime().getSeconds());
            session.setAttribute("ID", userDetails.getId());
            session.setAttribute("USERNAME", userDetails.getUsername());

            for (SessionInfo sessionInfo : activeSessionList.values()) {
                if (sessionInfo.getUserId().equals(userDetails.getId())) {
                    sessionInfo.setStatus(SessionStatus.DUPLICATE);
                    sessionInfo.setEndTime(LocalDateTime.now());
                    sessionInfo.setExpired(true);
                    inactiveSessionList.put(sessionInfo.getSessionId(), sessionInfo);
                    activeSessionList.remove(sessionInfo.getSessionId());
                }
            }
            filterChain.doFilter(request, response);
        } catch (UsernameNotFoundException e) {
            e.printStackTrace();
            SecurityResponse.fail(response, StatusCode.LOGIN_FAILED);
        } catch (Exception e) {
            e.printStackTrace();
            SecurityResponse.fail(response, StatusCode.LOGIN_FAILED_UNCATCHED);
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return

                !request.getServletPath().equals("/members/login")
//                && !request.getServletPath().equals("/members/logout")
        ;
    }
}
