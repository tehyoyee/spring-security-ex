package com.tete.back.entity;

import com.tete.back.dto.enums.Authority;
import com.tete.back.dto.enums.Role;
import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Getter
public class UserDetailsImpl implements UserDetails {

    @Setter
    private Long id;
    @Setter
    private String username;
    @Setter
    private String password;

    private Role role;

    private final List<Authority> authorities = new ArrayList<>();

    public UserDetailsImpl(Long id, String username, String password, List<Authority> authorities, Role role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.authorities.addAll(authorities);
        this.role = role;
    }

    @Builder
    public UserDetailsImpl(Long id, String username, List<Authority> authorities, Role role) {
        this.id = id;
        this.username = username;
        this.authorities.addAll(authorities);
        this.role = role;
    }

    // Spring Security가 UserDetails 인터페이스의 getAuthorities 메서드 사용
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities.stream()
                .map(authority -> new SimpleGrantedAuthority(authority.toString()))
                .toList();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // 비활성화가 필요하면 false로 설정
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // 비활성화가 필요하면 false로 설정
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 비활성화가 필요하면 false로 설정
    }

    @Override
    public boolean isEnabled() {
        return true; // 비활성화가 필요하면 false로 설정
    }

}
