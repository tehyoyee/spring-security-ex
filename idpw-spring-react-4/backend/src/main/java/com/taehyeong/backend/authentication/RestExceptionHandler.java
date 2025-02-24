//package com.taehyeong.backend.authentication;
//
//import com.taehyeong.backend.response.ApiResponse;
//import com.taehyeong.backend.response.StatusCode;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//@RestControllerAdvice
//public class RestExceptionHandler {
//
//    // 아래 내용 모두 추가
//    @ExceptionHandler({AuthenticationException.class})
//    public ApiResponse handleSignatureException(HttpServletResponse response, HttpServletRequest request) {
//
//        System.out.println("handleSignatureException");
//        Exception e = (Exception) request.getAttribute("exception");
//        if (e == null) {
//            return ApiResponse.fail(response, "인증에 실패하였습니다. exception handler", StatusCode.UNAUTHORIZED);
//        }
//            return ApiResponse.fail(response, e.getMessage(), StatusCode.UNAUTHORIZED);
//
//    }
//
//}
