package com.taehyeong.backend.response;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private boolean success; // 요청의 성공 여부
    private String message;  // 요청에 대한 메시지
    private T data;          // 반환할 데이터

    // 성공 응답을 위한 정적 메서드
    public static <T> ApiResponse<T> success(T data, String message) {
        return ApiResponse.<T>builder()
                .success(true)
                .message(message)
                .data(data)
                .build();
    }

    // 실패 응답을 위한 정적 메서드
    public static <T> ApiResponse<T> failure(HttpServletResponse response, String message, StatusCode statusCode) {
        response.setStatus(statusCode.getCode());
        return ApiResponse.<T>builder()
                .success(false)
                .message(message)
                .data(null)
                .build();
    }

    public static <T> ApiResponse<T> success(T data, StatusCode statusCode) {
        return ApiResponse.<T>builder()
                .success(true)
                .message(statusCode.getMessage())
                .data(data)
                .build();
    }

    public static <T> ApiResponse<T> success(StatusCode statusCode) {
        return ApiResponse.<T>builder()
                .success(true)
                .message(statusCode.getMessage())
                .data(null)
                .build();
    }
}

