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
    private final HttpSession session;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        for (Object principal : sessionRegistry.getAllPrincipals()) {
            // 각 Principal에 연결된 세션 정보
            List<SessionInformation> sessions = sessionRegistry.getAllSessions(principal, true);

            for (SessionInformation sessionInfo : sessions) {
                if (sessionInfo.getSessionId().equals(session.getId())) {
                    SecurityResponse.fail(response, StatusCode.SESSION_ALREADY_LOGGEDIN);
                    return;
                }
            }
        }





        System.out.println("============ Login Filter ==============");
//        System.out.println("SecurityContextHolder.getContext().getAuthentication().getPrincipal() = " + SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        ConcurrentHashMap<String, SessionInfo> activeSessionList = sessionRepository.getActiveSessions();
        ConcurrentHashMap<String, SessionInfo> inactiveSessionList = sessionRepository.getInactiveSessions();
        if (activeSessionList.isEmpty()) {}
        String body = new String(request.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        System.out.println("============ Login Filter ==============2");

        System.out.println("session.getId() = " + session.getId());

        if (session.getAttribute("ID") != null) {
            if (activeSessionList.get(String.valueOf(session.getAttribute("ID"))) != null) {
                System.out.println("이미 로그인 상태인 세션은 요청 무시");
                SecurityResponse.success(response, "이미 로그인 되어있습니다.");
                return;
            }
        }
        System.out.println("============ Login Filter ==============3");
        JSONObject json = new JSONObject(body);
        String username = json.getString("username");
        String password = json.getString("password");
        MemberLoginDTO memberLoginDTO = new MemberLoginDTO(username, password);
        Authentication authentication = authenticationProvider.authenticate(
            new UsernamePasswordAuthenticationToken(memberLoginDTO.username(), memberLoginDTO.password())
        );
        System.out.println("============ Login Filter ==============4");
        SecurityContextHolder.getContext().setAuthentication(authentication);
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        session.setMaxInactiveInterval((int)userDetails.getExpireTime().getSeconds());
        session.setAttribute("ID", userDetails.getId());
        session.setAttribute("USERNAME", userDetails.getUsername());
//        session.setAttribute("EXPIRED", false);
        System.out.println("session.getAttribute(\"ID\") = " + session.getAttribute("ID"));
        System.out.println("session.getAttribute(\"USERNAME\") = " + session.getAttribute("USERNAME"));
//        String stompChannel = sessionRepository.addStompChannel(session.getId());
//        System.out.println("stompChannel = " + stompChannel);
        System.out.println("sessionId = " + session.getId());


        System.out.println("============ Login Filter ==============5");


//        List<String> duplicatedSessionList = new ArrayList<>();
        // 기존 로그인된 애 DUPLICATE 상태로 바꿈.
        for (SessionInfo sessionInfo : activeSessionList.values()) {
            System.out.println(sessionInfo.getSessionId());
            if (sessionInfo.getUserId().equals(userDetails.getId())) {
                sessionInfo.setStatus(SessionStatus.DUPLICATE);
                sessionInfo.setEndTime(LocalDateTime.now());
                sessionInfo.setExpired(true);
                inactiveSessionList.put(sessionInfo.getSessionId(), sessionInfo);
                activeSessionList.remove(sessionInfo.getSessionId());
//                duplicatedSessionList.add(sessionInfo.getSessionId());
            }
        }
        filterChain.doFilter(request, response);

    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return

                !request.getServletPath().equals("/members/login")
//                && !request.getServletPath().equals("/members/logout")
        ;
    }
}
