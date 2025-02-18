package com.taehyeong.backend.service;

import com.taehyeong.backend.authentication.repository.RoleRepository;
import com.taehyeong.backend.dto.member.request.MemberCreateDTO;
import com.taehyeong.backend.authentication.entity.Member;
import com.taehyeong.backend.authentication.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final PasswordEncoder bCryptPasswordEncoder;
    private final MemberRepository memberRepository;
    private final RoleRepository roleRepository;

    @Transactional
    public void create(MemberCreateDTO memberCreateDTO) {

        Member member = memberRepository.save(
                Member.builder()
                        .username(memberCreateDTO.getUsername())
                        .password(bCryptPasswordEncoder.encode(memberCreateDTO.getPassword()))
                        .build()
        );
        member.setRole(roleRepository.findById(1).get());

    }

}
