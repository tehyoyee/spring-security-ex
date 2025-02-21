package com.taehyeong.backend.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum StatusCode {

    OK(200, "OK"),
    MEMBER_CREATED(201, "회원가입에 성공하였습니다."),


    LOGIN_FAILED(400, "다시 시도해주세요."),

    UNAUTHORIZED(401, "인증에 실패하였습니다."),
    SESSION_ALREADY_LOGGEDIN(400, "이미 로그인되어 있습니다."),
    SESSION_DUPLICATE(401, "중복 로그인이 감지되었습니다."),
    SESSION_EXPIRED(401, "세션이 만료되었습니다."),
    SESSION_NOT_FOUND(401, "세션 정보가 없습니다."),
    SESSION_INVALIDATED(200, "세션 비활성화 성공"),
    SESSION_DISABLE_SELF(400, "자기 자신을 종료할 수 없습니다."),

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
