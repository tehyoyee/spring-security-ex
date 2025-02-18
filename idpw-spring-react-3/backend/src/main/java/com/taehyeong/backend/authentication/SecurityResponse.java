package com.taehyeong.backend.authentication;

import com.taehyeong.backend.response.StatusCode;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class SecurityErrorFormatter {

    public static SecurityErrorFormatter INSTANCE = new SecurityErrorFormatter();

    public static void makeSecurityResponse(HttpServletResponse response, StatusCode statusCode) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        PrintWriter writer = response.getWriter();
        writer.write("{\"success\": \"false\", \"message\": \"" + statusCode.getMessage() + "\"}");
        writer.flush();
        writer.close();
    }

}
