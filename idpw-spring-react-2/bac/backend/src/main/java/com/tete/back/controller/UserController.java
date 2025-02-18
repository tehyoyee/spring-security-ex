package com.tete.back.controller;

import com.tete.back.dto.enums.Authority;
import com.tete.back.dto.request.UserRegisterReqDTO;
import com.tete.back.entity.User;
import com.tete.back.entity.UserDetailsImpl;
import com.tete.back.repository.UserRepository;
import com.tete.back.service.AuthenticationProviderService;
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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationProviderService authenticationProviderService;
    private final UserDetailsService userDetailsService;

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

//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody UserRegisterReqDTO userDto, HttpServletRequest request, HttpSession session) {
//        System.out.println("POST /login ========== START");
//        Map<String, String> res = new HashMap<>();
//        try {
//            Authentication authentication = authenticationProviderService.authenticate(new UsernamePasswordAuthenticationToken(userDto.username(), userDto.password()));
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//            User user = userRepository.findByUsername(userDto.username()) // User 객체에서 id를 추출
//                    .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + userDto.username()));
//
//            Cookie[] cookies = request.getCookies();
//            for (Cookie x : cookies) {
//                System.out.println("파싱된 쿠키 이름 = " + x.getName() + "값 + " + x.getValue());
//            }
//            session = request.getSession(true);
//            System.out.println("authentication = " + authentication);
//            session.setAttribute("ROLE", user.getRole());
//            session.setAttribute("ID", user.getId());
//            session.setAttribute("AUTHORITIES", authentication.getAuthorities());
//            UserDetailsImpl userDetails = (UserDetailsImpl) (authentication.getPrincipal());
//            print("=============== 로그인 정보 =============  ", session, authentication);
//            Set<Authority> authorities = authentication.getAuthorities().stream()
//                    .map(GrantedAuthority::getAuthority)
//                    .map(Authority::valueOf) // Authority enum으로 변환
//                    .collect(Collectors.toSet());
//
//// 결과 출력
//            for (Authority authority : authorities) {
//                System.out.println("Authority: " + authority);
//            }
//
//        System.out.println("POST /login ========== END");
//            return ResponseEntity.status(200).body(res);
//        } catch (Exception e) {
//            System.out.println("e.getMessage() = " + e.getMessage());;
//            return ResponseEntity.status(401).body("Invalid credentials.");
//        }
//
//    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return ResponseEntity.ok("Logout successful.");
    }

    private void print(String info, HttpSession session, Authentication authentication) {

        System.out.println("=======================================================");
        System.out.println("info = " + info);
        System.out.println("=======================================================");
        System.out.println("session.role = " + session.getAttribute("ROLE").toString());
        System.out.println("session.id = " + session.getAttribute("ID").toString());
        System.out.println("session.Authorities = " + session.getAttribute("AUTHORITIES").toString());
        System.out.println("authentication.getCredentials() = " + authentication.getCredentials());
        System.out.println("authentication.getAuthorities() = " + authentication.getAuthorities());
        System.out.println("authentication.getPrincipal() = " + authentication.getPrincipal());
    }

}