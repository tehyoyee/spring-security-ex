package com.taehyeong.backend.controller;

import com.taehyeong.backend.dto.member.request.MemberCreateDTO;
import com.taehyeong.backend.dto.member.request.MemberLoginDTO;
import com.taehyeong.backend.response.ApiResponse;
import com.taehyeong.backend.response.StatusCode;
import com.taehyeong.backend.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping
    public String get() {
        return "asdf";
    }

    @PostMapping
    public ApiResponse create(HttpServletRequest request,
                              @RequestBody MemberCreateDTO memberCreateDTO) {

        List<MemberCreateDTO> lst = new ArrayList<>();
        memberService.create(memberCreateDTO);
        return ApiResponse.success(lst, StatusCode.MEMBER_CREATED);
    }

//    @PostMapping
//    public ApiResponse signUp(HttpServletRequest,
//                              @RequestBody MemberLoginDTO) {
//
//
//
//    }

}
