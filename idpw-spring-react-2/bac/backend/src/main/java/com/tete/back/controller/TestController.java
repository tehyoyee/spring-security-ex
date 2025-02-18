package com.tete.back.controller;

import com.tete.back.dto.enums.Authority;
import com.tete.back.config.SessionService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final HttpSession httpSession;
    private final SessionService sessionService;
    
    @GetMapping("/test")
    public String test() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        print("=====START============ /test =====================", httpSession, authentication);
        sessionService.printAllSessionsInfo();
        print("=====END  ============ /test =====================", httpSession, authentication);

        return "asdf";
    }

    @GetMapping("/aaa")
    public String aaa() {
        System.out.println("SecurityContextHolder.getContext().getAuthentication() = " + SecurityContextHolder.getContext().getAuthentication());;
        return "aaa";
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
