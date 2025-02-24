package com.taehyeong.backend.controller;

import com.taehyeong.backend.authentication.domain.CustomUserDetails;
import com.taehyeong.backend.authentication.domain.SessionInfo;
import com.taehyeong.backend.authentication.domain.SessionStatus;
import com.taehyeong.backend.authentication.dto.LoginSuccessResDTO;
import com.taehyeong.backend.authentication.repository.SessionRepository;
import com.taehyeong.backend.authentication.service.SessionService;
import com.taehyeong.backend.dto.member.request.MemberCreateDTO;
import com.taehyeong.backend.response.ApiResponse;
import com.taehyeong.backend.response.StatusCode;
import com.taehyeong.backend.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final SessionRepository sessionRepository;
    private final MemberService memberService;
    private final SessionRegistry sessionRegistry;
    private final SessionService sessionService;

    @GetMapping("/check")
    public ApiResponse check(HttpServletRequest request) {
        return ApiResponse.success(StatusCode.OK);
    }

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

    @PostMapping("/logouts")
    public ApiResponse logout(HttpServletRequest request) {

        HttpSession session = request.getSession();
        String sessionId = session.getId();
//        System.out.println("SecurityContextHolder.getContext().getAuthentication().getPrincipal() = " + SecurityContextHolder.getContext().getAuthentication().getPrincipal());
////        sessionRegistry.getSessionInformation(sessionId);
        ConcurrentHashMap<String, SessionInfo> activeSessionList = sessionRepository.getActiveSessions();
        ConcurrentHashMap<String, SessionInfo> inactiveSessionList = sessionRepository.getInactiveSessions();
        SessionInfo sessionInfo = activeSessionList.get(sessionId);
        sessionInfo.setEndTime(LocalDateTime.now());
        sessionInfo.setStatus(SessionStatus.LOGOUT);
        inactiveSessionList.put(sessionId, sessionInfo);
        activeSessionList.remove(sessionId);
        request.getSession().invalidate();
        System.out.println("logout");
        return ApiResponse.success(StatusCode.OK);

//        sessionRegistry.getSessionInformation(session.getId());
//        ConcurrentHashMap<String, SessionInfo> activeSessionList = sessionRepository.getActiveSessions();
//        ConcurrentHashMap<String, SessionInfo> inactiveSessionList = sessionRepository.getInactiveSessions();
//        SessionInfo sessionInfo = activeSessionList.get(session.getId());
//        sessionInfo.setEndTime(LocalDateTime.now());
//        sessionInfo.setStatus(SessionStatus.LOGOUT);
//        inactiveSessionList.put(session.getId(), sessionInfo);
//        activeSessionList.remove(session.getId());
//        request.getSession().invalidate();
//        System.out.println("logout");
//        return ApiResponse.success(StatusCode.OK);

    }

    @PostMapping("/login")
    public ApiResponse login(HttpServletRequest request, HttpServletResponse response) {

        String newStompChannel = sessionRepository.generateStompChannel();
        HttpSession session = request.getSession();
//        if (session.)
        System.out.println("session.getAttribute(\"ID\") = " + session.getAttribute("ID"));
        System.out.println("session.getAttribute(\"USERNAME\") = " + session.getAttribute("USERNAME"));

        sessionRepository.getActiveSessions().put(
                session.getId(), SessionInfo.builder()
                        .username((String) session.getAttribute("USERNAME"))
                        .sessionId(session.getId())
                        .isExpired(false)
                        .userId((Long) session.getAttribute("ID"))
                        .status(SessionStatus.ALIVE)
                        .startTime(LocalDateTime.now())
                        .stompChannel(newStompChannel)
                        .build()
        );
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        return ApiResponse.success(new LoginSuccessResDTO(customUserDetails.getId(), newStompChannel), StatusCode.OK);

    }



}
