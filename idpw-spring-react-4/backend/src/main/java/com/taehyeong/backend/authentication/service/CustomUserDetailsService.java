package com.taehyeong.backend.authentication.service;

import com.taehyeong.backend.authentication.domain.CustomUserDetails;
import com.taehyeong.backend.authentication.entity.Authority;
import com.taehyeong.backend.authentication.entity.Member;
import com.taehyeong.backend.authentication.repository.AuthorityRepository;
import com.taehyeong.backend.authentication.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final AuthorityRepository authorityRepository;

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Member member = memberRepository.findByUsername(username)
                .orElseThrow(
                        () -> new UsernameNotFoundException("사용자를 찾을 수 없습니다.")
                );
        Set<Authority> authorities = authorityRepository.findByRoleId(member.getRole().getId());
        Set<GrantedAuthority> list = authorities.stream()
                .map(a -> new SimpleGrantedAuthority(
                        a.getName().toString()))
                .collect(Collectors.toSet());
        return new CustomUserDetails(member.getId(), member.getUsername(), member.getPassword(), member.getExpireTime(), member.getRole(), list);

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
