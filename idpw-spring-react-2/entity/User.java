package com.taehyeong.backend.entity;


import com.taehyeong.backend.dto.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

//    @ElementCollection(fetch = FetchType.EAGER)
//    private List<String> authorities = new ArrayList<>(); // 권한 목록 (WRITE, READ 등)
    private Collection<SimpleGrantedAuthority> authorities;

//    @Enumerated(EnumType.STRING)
    private Role role; // 역할 목록 (ROLE_USER, ROLE_ADMIN)

    // Getters and setters

    public Long getId() {
        return this.id;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.role = Role.ROLE_USER;
    }

    public User() {
    }


    // Getter for authorities
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        // authorities + roles를 GrantedAuthority 형태로 변환하여 반환
//        return authorities.stream()
//                .map(auth -> (GrantedAuthority) () -> auth)
//                .collect(Collectors.toList());
//    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

//    public List<String> getAuthorities() {
//        return authorities;
//    }

//    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
//        this.authorities = authorities;
//    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return authorities;
//        return Collections.emptyList();
//    }
//
    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}