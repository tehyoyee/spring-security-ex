package com.taehyeong.backend.response;//package com.taehyeong.backend.response;
//
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//@NoArgsConstructor
//@AllArgsConstructor
//@Getter
//@Setter
//public class Status {
//
//    int code;
//
//    String message;
//
//}


import lombok.Getter;

@Getter
public enum Status {

    OK(200, "OK"),
    LOGIN_TIMEOUT(440, "세션이 만료되었습니다.");

    private final int code;
    private final String message;

    Status(int code, String message) {
        this.code = code;
        this.message = message;
    }
}