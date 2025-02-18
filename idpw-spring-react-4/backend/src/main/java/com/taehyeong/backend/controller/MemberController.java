package com.taehyeong.backend.controller;

import com.taehyeong.backend.authentication.domain.CustomUserDetails;
import com.taehyeong.backend.authentication.dto.LoginSuccessResDTO;
import com.taehyeong.backend.dto.member.request.MemberCreateDTO;
import com.taehyeong.backend.response.ApiResponse;
import com.taehyeong.backend.response.StatusCode;
import com.taehyeong.backend.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;
    private final SessionRegistry sessionRegistry;

    @GetMapping
    public String get() {
        return "asdf";
    }

    @PostMapping
    public ApiResponse create(HttpServletRequest request,
                              @RequestBody MemberCreateDTO memberCreateDTO) {

        memberService.create(memberCreateDTO);

        return ApiResponse.success(StatusCode.MEMBER_CREATED);
    }

    @PostMapping("/login")
    public ApiResponse login(HttpServletRequest request) {

        System.out.println("login");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        return ApiResponse.success(new LoginSuccessResDTO(customUserDetails.getId()), StatusCode.OK);

    }



}
