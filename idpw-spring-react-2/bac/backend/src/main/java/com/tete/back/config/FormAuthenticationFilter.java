package com.tete.back.config;

import com.tete.back.dto.enums.Authority;
import com.tete.back.entity.User;
import com.tete.back.entity.UserDetailsImpl;
import com.tete.back.repository.UserRepository;
import com.tete.back.response.StatusCode;
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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class FormAuthenticationFilter extends OncePerRequestFilter {

    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        System.out.println("====START======doFilterInternal==============");
        Long userId = (Long) request.getSession().getAttribute("ID");
        System.out.println("userId = " + userId);
        if (userId == null) {
            response.setStatus(StatusCode.UNAUTHORIZED.getCode()); // 401 Unauthorized
            response.setContentType("application/json");
            response.getWriter().write("{\"message\": \"로그인 해주세요.\"}"); // JSON 응답
            return;
        }
        HttpSession session = request.getSession(false);

        User user = userRepository.findById(userId).orElseThrow(
                () -> new UsernameNotFoundException(StatusCode.USER_NOT_FOUND.getMessage() + ": " + userId)
        );

        if (session == null) {
            // 세션이 없거나 ID 속성이 없는 경우, 세션이 만료된 것으로 간주
            response.setStatus(StatusCode.UNAUTHORIZED.getCode()); // 401 Unauthorized
            response.setContentType("application/json");
            response.getWriter().write("{\"message\": \"로그인 해주세요.\"}"); // JSON 응답
            return; // 더 이상 필터 체인 진행하지 않음
        }
        System.out.println("USER" + userId);
        if (session.getAttribute("EXPIRED").equals(true)) {
            response.setStatus(StatusCode.LOGIN_TIMEOUT.getCode()); // 440 Unauthorized
            response.setContentType("application/json");
            response.getWriter().write("{\"message\": \"Session has expired.\"}"); // JSON 응답
            return;
        }

        UserDetailsImpl userDetails = UserDetailsImpl.builder()
                .id(user.getId())
                .username(user.getUsername())
                .authorities(user.getRole().getAuthorities())
                .role(user.getRole()
                ).build();
        var auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
        System.out.println("==== END ======doFilterInternal==============");
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return
                request
                        .getServletPath().equals("/login") ||
                        request.getServletPath().equals("/register") ||
                        request.getServletPath().equals("/actuator/mappings")
                ;
    }
}