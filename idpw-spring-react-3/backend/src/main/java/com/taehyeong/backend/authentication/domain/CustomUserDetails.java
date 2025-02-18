package com.taehyeong.backend.authentication.domain;

import com.taehyeong.backend.authentication.entity.Role;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Duration;
import java.util.Collection;
import java.util.Set;

@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

//    private final Member member;
    @Getter
    private Long id;

    private String username;

    private String password;

    @Getter
    private Role role;

    @Getter
    private Duration expireTime;

    private Set<GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    public CustomUserDetails(Long id, String username, String password, Duration expireTime, Role role, Set<GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.expireTime = expireTime;
        this.role = role;
        this.authorities = authorities;

    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof CustomUserDetails){
            return getId().equals(((CustomUserDetails) obj).getId());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public String getPassword() {
        return this.password;
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
