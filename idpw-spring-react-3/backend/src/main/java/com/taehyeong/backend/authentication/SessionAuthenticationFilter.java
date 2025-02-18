package com.taehyeong.backend.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taehyeong.backend.authentication.entity.Member;
import com.taehyeong.backend.authentication.repository.MemberRepository;
import com.taehyeong.backend.response.StatusCode;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
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

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException {

        try {
            HttpSession session = request.getSession(false);
            Long userId = (Long) request.getSession().getAttribute("ID");
            if (userId == null) {
                throw new SessionAuthenticationException("인증되지 않은 세션입니다.");
            }
            Member member = memberRepository.findById(userId).orElseThrow(
                    () -> new UsernameNotFoundException(StatusCode.MEMBER_NOT_FOUND.getMessage() + ": " + userId)
            );

            if (session == null) {
                throw new SessionAuthenticationException("세션 인증 실패");
            }
            filterChain.doFilter(request, response);
        } catch (AuthenticationException e) {
            SecurityResponse.fail(response, StatusCode.UNAUTHORIZED);
        } catch (Exception e) {
            SecurityResponse.fail(response, e);
        }
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