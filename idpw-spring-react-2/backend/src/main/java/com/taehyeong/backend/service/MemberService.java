package com.taehyeong.backend.service;

import com.taehyeong.backend.dto.member.request.MemberCreateDTO;
import com.taehyeong.backend.entity.Member;
import com.taehyeong.backend.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final PasswordEncoder bCryptPasswordEncoder;
    private final MemberRepository memberRepository;

    @Transactional
    public void create(MemberCreateDTO memberCreateDTO) {

        memberRepository.save(
                Member.builder()
                        .username(memberCreateDTO.getUsername())
                        .password(bCryptPasswordEncoder.encode(memberCreateDTO.getPassword()))
                        .build()
        );

    }

}
