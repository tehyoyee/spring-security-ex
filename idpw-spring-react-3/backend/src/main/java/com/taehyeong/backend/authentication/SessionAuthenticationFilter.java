package com.taehyeong.backend.authentication;

import com.taehyeong.backend.authentication.entity.Member;
import com.taehyeong.backend.authentication.repository.MemberRepository;
import com.taehyeong.backend.response.StatusCode;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class FormAuthenticationFilter extends OncePerRequestFilter {

    private final MemberRepository memberRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Long userId = (Long) request.getSession().getAttribute("ID");
        if (userId == null) {
            response.setStatus(StatusCode.UNAUTHORIZED.getCode()); // 401 Unauthorized
            response.setContentType("application/json");
            response.getWriter().write("{\"message\": \"로그인 해주세요.\"}"); // JSON 응답
            return;
        }
        Member member = memberRepository.findById(userId).orElseThrow(
                () -> new UsernameNotFoundException(StatusCode.MEMBER_NOT_FOUND.getMessage() + ": " + userId)
        );

        if (session == null) {
            // 세션이 없거나 ID 속성이 없는 경우, 세션이 만료된 것으로 간주
            response.setStatus(StatusCode.UNAUTHORIZED.getCode()); // 401 Unauthorized
            response.setContentType("application/json");
            response.getWriter().write("{\"message\": \"로그인 해주세요.\"}"); // JSON 응답
            return; // 더 이상 필터 체인 진행하지 않음
        }
        filterChain.doFilter(request, response);

    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return
                request.getServletPath().equals("/members") ||
                        request.getServletPath().equals("/members/login")
                ;
    }
}