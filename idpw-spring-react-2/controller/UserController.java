package com.taehyeong.backend.controller;

import com.taehyeong.backend.dto.request.UserRegisterReqDTO;
import com.taehyeong.backend.entity.User;
import com.taehyeong.backend.repository.UserRepository;
import com.taehyeong.backend.service.AuthenticationProviderService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationProviderService authenticationProviderService;
    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegisterReqDTO userRegisterReqDTO) {
        System.out.println("POST register");
        if (userRepository.findByUsername(userRegisterReqDTO.username()).isPresent()) {
            return ResponseEntity.badRequest().body("Username already exists.");
        }
        userRepository.save(
                new User(
                        userRegisterReqDTO.username(),
                        passwordEncoder.encode(userRegisterReqDTO.password())
                )
        );
        return ResponseEntity.ok("User registered successfully.");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserRegisterReqDTO userDto, HttpServletRequest request, HttpSession session) {
        System.out.println("POST /login");
        Map<String, String> res = new HashMap<>();
        try {
//            Authentication authentication =
//                    authenticationProviderService.authenticate(
//                            new UsernamePasswordAuthenticationToken(user.username(), user.password())
//            );
//            UserDetails user = userDetailsService.loadUserByUsername(userDto.username());
            Authentication authentication = authenticationProviderService.authenticate(new UsernamePasswordAuthenticationToken(userDto.username(), userDto.password()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
//            Long id = userRepository.findIdByUsername(userDto.username());
//            Long id = userRepository.findByUsername(userDto.username()).orElseThrow(
//                    () -> new UsernameNotFoundException()
//            ).getId();
            Long id = userRepository.findByUsername(userDto.username())
                    .map(User::getId)  // User 객체에서 id를 추출
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + userDto.username()));

            Cookie[] cookies = request.getCookies();
            for (Cookie x : cookies) {
                System.out.println("파싱된 쿠키 이름 = " + x.getName() + "값 + " + x.getValue());
            }
            session = request.getSession(true);
            session.setAttribute("ROLE", "ROLE_USER");
            session.setAttribute("ID", id);
            System.out.println("session.role = " + session.getAttribute("ROLE").toString());
            System.out.println("session.id = " + session.getAttribute("ID").toString());
            return ResponseEntity.status(200).body(res);
        } catch (Exception e) {
            System.out.println("e.getMessage() = " + e.getMessage());;
            return ResponseEntity.status(401).body("Invalid credentials.");
        }
    }
//    @PostMapping("/login")
//    public String login(@RequestBody UserRegisterReqDTO userRegisterReqDTO) {
//        System.out.println("/login");
//        return "login";
//    }
    @PostMapping("/loginss")
    public ResponseEntity<Map<String, String>> loginOk() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        String authorities = authentication.getAuthorities().toString();

        System.out.println("로그인한 유저 이메일:" + email);
        System.out.println("유저 권한:" + authentication.getAuthorities());

        Map<String, String> userInfo = new HashMap<>();
        userInfo.put("email", email);
        userInfo.put("authorities", authorities);

        return ResponseEntity.ok(userInfo);
    }
    @GetMapping("/logoutOk")
    public ResponseEntity<Void> logoutOk() {
        System.out.println("로그아웃 성공");
        return ResponseEntity.ok().build();
    }
//    @GetMapping("/user")
//    public ResponseEntity<User> getUserPage() {
//        System.out.println("일반 인증 성공");
//
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String email = authentication.getName();
//
//        // 유저 정보
//        User user = userService.getUserInfo(email);
//
//        return ResponseEntity.ok(user);
//    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return ResponseEntity.ok("Logout successful.");
    }

}