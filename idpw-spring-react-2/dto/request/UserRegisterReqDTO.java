package com.taehyeong.backend.dto.request;


import com.taehyeong.backend.entity.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public record UserRegisterReqDTO(String username, String password) {
}
