package com.taehyeong.backend.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taehyeong.backend.authentication.domain.SessionStatus;
import com.taehyeong.backend.authentication.entity.Member;
import com.taehyeong.backend.authentication.repository.MemberRepository;
import com.taehyeong.backend.authentication.repository.SessionRepository;
import com.taehyeong.backend.response.StatusCode;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class SessionAuthenticationFilter extends OncePerRequestFilter {

    private final MemberRepository memberRepository;
    private final SessionRepository sessionRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        System.out.println("SessionAuthenticationFilter.doFilterInternal START");
        filterChain.doFilter(request, response);
//        HttpSession session = request.getSession(false);
//        if (session == null) {
//            String sessionId = "";
//            if (request.getCookies() != null) {
//                for (Cookie cookie: request.getCookies()) {
//                    if (cookie.getName().equals("JSESSIONID")) {
//                        sessionId = cookie.getValue();
//                        break;
//                    }
//                }
//            }
//            if (sessionId != null) {
//                SecurityResponse.fail(response, StatusCode.SESSION_NOT_FOUND);
//                return;
//            }
//        }
//        try {
//            Long userId = (Long) request.getSession().getAttribute("ID");
//            System.out.println("userId = " + userId);
//            if (userId == null) {
//                throw new SessionAuthenticationException("인증되지 않은 세션입니다.");
//            }
//            Member member = memberRepository.findById(userId).orElseThrow(
//                    () -> new UsernameNotFoundException(StatusCode.MEMBER_NOT_FOUND.getMessage() + ": " + userId)
//            );
//
//            if (session == null) {
//                throw new SessionAuthenticationException("세션 인증 실패");
//            }
//            filterChain.doFilter(request, response);
//        } catch (AuthenticationException e) {
//            System.out.println("세션 필터 authentication exception");
//            SecurityResponse.fail(response, StatusCode.UNAUTHORIZED);
//        } catch (Exception e) {
//            System.out.println("세션 필터 익셉션");
//            SecurityResponse.fail(response, e);
//        }

        System.out.println("SessionAuthenticationFilter.doFilterInternal END");
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return
                request.getServletPath().equals("/members") ||
                        request.getServletPath().equals("/members/login") ||
                        request.getServletPath().startsWith("/ws")
                ;
    }


}