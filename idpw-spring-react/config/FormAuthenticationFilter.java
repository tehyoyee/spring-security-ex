package com.taehyeong.backend.config;

import com.taehyeong.backend.response.StatusCode;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FormAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private HttpSession httpSession;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Long userId = (Long) request.getSession().getAttribute("ID");
        HttpSession session = request.getSession(false);
        System.out.println("session = " + session);
        if (session == null || userId == null) {
            // 세션이 없거나 ID 속성이 없는 경우, 세션이 만료된 것으로 간주
            response.setStatus(StatusCode.UNAUTHORIZED.getCode()); // 401 Unauthorized
            response.setContentType("application/json");
            response.getWriter().write("{\"message\": \"Session has expired.\"}"); // JSON 응답
            return; // 더 이상 필터 체인 진행하지 않음
        }System.out.println("USER" + userId);
        if (session.getAttribute("EXPIRED").equals(true)) {
            response.setStatus(StatusCode.LOGIN_TIMEOUT.getCode()); // 401 Unauthorized
            response.setContentType("application/json");
            response.getWriter().write("{\"message\": \"Session has expired.\"}"); // JSON 응답
        }
        System.out.println("ROLE" + httpSession.getAttribute("ROLE"));
        GrantedAuthority a = new SimpleGrantedAuthority((String)request.getSession().getAttribute("ROLE"));
        var auth = new UsernamePasswordAuthenticationToken(userId, null, List.of(a));
        SecurityContextHolder.getContext().setAuthentication(auth);
        System.out.println("SecurityContextHolder.getContext().getAuthentication() = " + SecurityContextHolder.getContext().getAuthentication());
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
//        RequestMatcher requestMatcher = new RequestMatcher() {
//            @Override
//            public boolean matches(HttpServletRequest request) {
//                return false;
//            }
//        };
        return
                request.getServletPath().equals("/login") ||
                        request.getServletPath().equals("/register")
                ;
    }
}