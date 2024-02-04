package com.actionvoca.taehyeong.authentication.filters;

import com.actionvoca.taehyeong.authentication.UsernamePasswordAuthentication;
import com.actionvoca.taehyeong.authentication.provider.UsernamePasswordAuthenticationProvider;
import com.actionvoca.taehyeong.entities.User;
import com.actionvoca.taehyeong.entities.enums.Role;
import com.actionvoca.taehyeong.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;

@Component
public class InitialAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private UsernamePasswordAuthenticationProvider usernamePasswordAuthenticationProvider;

    @Autowired
    private UserRepository userRepository;

    @Value("${jwt.signing.key}")
    private String signingKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String username = request.getHeader("username");
        String password = request.getHeader("password");

        System.out.println("initial filter");
        if (username.isEmpty()) throw new BadRequestException("아이디를 입력해주세요.");
        if (password.isEmpty()) throw new BadRequestException("비밀번호를 입력해주세요.");
        Authentication a = new UsernamePasswordAuthentication(username, password);
        usernamePasswordAuthenticationProvider.authenticate(a);
        SecretKey key = Keys.hmacShaKeyFor(signingKey.getBytes(StandardCharsets.UTF_8));
        Optional<User> user = userRepository.findByUsername(username);
        Role role;
        if (user.isPresent())
            role = user.get().getRole();
        else throw new UsernameNotFoundException("잘못된 아이디 혹은 패스워드입니다.");

        sendAuthorizationByCookie(username, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return
                request.getServletPath().equals("/")
                || !request.getServletPath().equals("/login")
                || request.getRequestURI().equals("/user/create")
                || request.getRequestURI().equals("/user/check/duplicate")
                || request.getRequestURI().equals("/otp/check")
                || request.getRequestURI().equals("/otp/create")
                || request.getRequestURI().equals("/api/**");
    }
    public void sendAuthorizationByCookie(String username, HttpServletResponse response) {
        SecretKey key = Keys.hmacShaKeyFor(signingKey.getBytes(StandardCharsets.UTF_8));

        String jwt = Jwts.builder()
                .setClaims(Map.of("username", username, "role", "ROLE_USER"))
                .signWith(key)
                .compact();
        Cookie cookie = new Cookie("Authorization", jwt);
        cookie.setDomain("https://www.actionvoca.com");
        cookie.setPath("/");
        cookie.setMaxAge(30*60);
        cookie.setSecure(true);
        response.setHeader("Authorization", jwt);
        response.addCookie(cookie);
    }
}