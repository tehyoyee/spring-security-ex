package com.tete.back.entity;

import com.tete.back.dto.enums.Role;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @Setter
    private String password;

//    @ElementCollection(fetch = FetchType.EAGER)

    @Setter
    @Getter
    private Role role; // 역할 목록 (ROLE_USER, ROLE_ADMIN)

    @Builder
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.role = Role.GENERAL;
        System.out.println("making User");
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

//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return this.getRole().getAuthorities();
//    }

//    public List<String> getAuthorities() {
//        return authorities;
//    }

//    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
//        this.authorities = authorities;
//    }

    //    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return authorities;
//        return Collections.emptyList();
//    }
//
//    @Override
//    public String getPassword() {
//        return password;
//    }
//
//    @Override
//    public String getUsername() {
//        return username;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
}