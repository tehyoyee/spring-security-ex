//package com.taehyeong.backend.authentication;
//
//import com.taehyeong.backend.authentication.domain.CustomUserDetails;
//import com.taehyeong.backend.authentication.domain.SessionInfo;
//import com.taehyeong.backend.authentication.domain.SessionStatus;
//import com.taehyeong.backend.authentication.entity.Member;
//import com.taehyeong.backend.authentication.repository.MemberRepository;
//import com.taehyeong.backend.authentication.repository.SessionRepository;
//import com.taehyeong.backend.authentication.service.SessionService;
//import com.taehyeong.backend.request.MemberLoginDTO;
//import com.taehyeong.backend.response.StatusCode;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//import lombok.RequiredArgsConstructor;
//import org.json.JSONObject;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.session.SessionInformation;
//import org.springframework.security.core.session.SessionRegistry;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.authentication.session.SessionAuthenticationException;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//import java.nio.charset.StandardCharsets;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.ConcurrentHashMap;
//
//@Component
//@RequiredArgsConstructor
//public class LogoutAuthenticationFilter extends OncePerRequestFilter {
//
////    private final SessionRegistry sessionRegistry;
//    private final HttpSession session;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//
//        System.out.println("============ LOGOUT Filter ==============2");
//        System.out.println("SecurityContextHolder.getContext().getAuthentication().getPrincipal() = " + SecurityContextHolder.getContext().getAuthentication().getPrincipal());
//        System.out.println("============ LOGOUT Filter ==============2");
//
//        if (request.getServletPath().equals("/members/logout")) {
//            System.out.println("============ LOGOUT Filter ==============2");
//            System.out.println("session.getId() = " + session.getId());
//            session.invalidate();
////            sessionRegistry.removeSessionInformation(session.getId());
//            filterChain.doFilter(request, response);
//            return;
//        }
//        else {
//            return;
//        }
////        HttpSession session = request.getSession(false);
////        try {
////            Long userId = (Long) request.getSession().getAttribute("ID");
////            if (userId == null) {
////                throw new SessionAuthenticationException("인증되지 않은 세션입니다.");
////            }
////            Member member = memberRepository.findById(userId).orElseThrow(
////                    () -> new UsernameNotFoundException(StatusCode.MEMBER_NOT_FOUND.getMessage() + ": " + userId)
////            );
////
////            if (session == null) {
////                throw new SessionAuthenticationException("세션 인증 실패");
////            }
////            filterChain.doFilter(request, response);
////        } catch (AuthenticationException e) {
////            System.out.println("세션 필터 authentication exception");
////            SecurityResponse.fail(response, StatusCode.UNAUTHORIZED);
////        } catch (Exception e) {
////            System.out.println("세션 필터 익셉션");
////            SecurityResponse.fail(response, e);
////        }
////        sessionService.invalidateSession(session.getId());
////        SecurityResponse.success(response, "로그아웃되었습니다.");
////        filterChain.doFilter(request, response);
//
//    }
//
//    @Override
//    protected boolean shouldNotFilter(HttpServletRequest request) {
//        return
//                !request.getServletPath().equals("/members/logout");
//    }
//}
