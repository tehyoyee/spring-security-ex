package com.taehyeong.backend.authentication;

import com.taehyeong.backend.authentication.domain.CustomUserDetails;
import com.taehyeong.backend.authentication.repository.MemberRepository;
import com.taehyeong.backend.request.MemberLoginDTO;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class LoginAuthenticationFilter extends OncePerRequestFilter {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationProviderImpl authenticationProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (request.getRequestURI().equals("/members/logout")) {
            HttpSession session = request.getSession();
            session.invalidate();
            SecurityResponse.success(response, "로그아웃되었습니다.");
            return;
        }
        System.out.println("============ Login Filter ==============");
        String body = new String(request.getInputStream().readAllBytes(), StandardCharsets.UTF_8);

        HttpSession session = request.getSession(true);

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
        filterChain.doFilter(request, response);

    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return

                !request.getServletPath().equals("/members/login") &&
                        !request.getServletPath().equals("/members/logout")
                ;
    }
}
