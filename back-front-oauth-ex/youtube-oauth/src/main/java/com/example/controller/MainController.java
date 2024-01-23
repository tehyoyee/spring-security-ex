package com.example.controller;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class MainController {

    @GetMapping("/attri")
    public Map<String, Object> getUser(@AuthenticationPrincipal OAuth2User oAuth2User) {
        return oAuth2User.getAttributes();
    }

    @GetMapping("/attri-1")
    public void getUser1(@AuthenticationPrincipal OAuth2User oAuth2User) {
        String add = oAuth2User.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));
        System.out.println(add);
    }
}