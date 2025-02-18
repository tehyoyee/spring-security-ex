package com.taehyeong.backend.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum StatusCode {

    OK(200, "OK"),
    MEMBER_CREATED(201, "회원가입에 성공하였습니다."),

    DUPLICATE_LOGIN(401, "중복 로그인이 감지되었습니다."),
    UNAUTHORIZED(401, "인증에 실패하였습니다."),
    FORBIDDEN(401, "권한이 없습니다."),
    MEMBER_NOT_FOUND(404, "회원을 찾을 수 없습니다."),
    LOGIN_TIMEOUT(440, "세션이 만료되었습니다."),




    ;

    private final int code;
    private final String message;

    StatusCode(int code, String message) {
        this.code = code;
        this.message = message;
    }




}
