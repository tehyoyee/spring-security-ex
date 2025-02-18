package com.taehyeong.backend.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum StatusCode {

    OK(200, "OK"),
    MEMBER_CREATED(201, "회원가입에 성공하였습니다.");

    private final int code;
    private final String message;

    StatusCode(int code, String message) {
        this.code = code;
        this.message = message;
    }




}
