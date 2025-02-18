package com.taehyeong.backend.authentication;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final HandlerExceptionResolver resolver;

    public CustomAuthenticationEntryPoint(@Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
        this.resolver = resolver;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        System.out.println("CustomAuthenticationEntryPoint commence");
        resolver.resolveException(request, response, null, (Exception) request.getAttribute("exception"));
    }


//    @Override
//    public void commence(HttpServletRequest request,
//                         HttpServletResponse response,
//                         AuthenticationException authException) throws IOException, ServletException {
//
//        System.out.println("CustomAuthenticationEntryPoint");
//        System.out.println("authException = " + authException);
//        System.out.println("authException = " + authException.getMessage());
//        System.out.println("authException = " + authException.fillInStackTrace());
//        System.out.println("request = " + request.getCookies());
//        System.out.println("request = " + request.getSession().getId());
//        // HTTP 상태 코드를 401 Unauthorized로 설정
//        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//        response.setContentType("application/json;charset=UTF-8");
//
//        // 에러 메시지를 JSON 형식으로 작성하여 응답
//        PrintWriter writer = response.getWriter();
//        writer.write("{\"message\": \"인증에 실패하였습니다. " + authException.getMessage() + "\"}");
//        writer.flush();
//        writer.close();
//
//    }

}
