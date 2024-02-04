package com.actionvoca.taehyeong.entities.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserRegistrationDto {

    private String username;

    private String password;

    private String email;

    private String nickname;

}
