package com.taehyeong.backend.authentication;

import com.taehyeong.backend.response.StatusCode;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class SecurityResponse {

    public static void fail(HttpServletResponse response, StatusCode statusCode) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(statusCode.getCode());
        PrintWriter writer = response.getWriter();
        writer.write("{\"success\": \"false\", \"message\": \" 시큐리티 리스판스 페일 + "  + statusCode.getMessage() + "\"}");
        writer.flush();
        writer.close();
    }

    public static void fail(HttpServletResponse response, Exception e) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        PrintWriter writer = response.getWriter();
        writer.write("{\"success\": \"false\", \"message\": \"" + e.getMessage() + "\"}");
        writer.flush();
        writer.close();
    }

    public static void success(HttpServletResponse response, String message) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);
        PrintWriter writer = response.getWriter();
        writer.write("{\"success\": \"true\", \"message\": \"" + message + "\"}");
        writer.flush();
        writer.close();
    }

}
