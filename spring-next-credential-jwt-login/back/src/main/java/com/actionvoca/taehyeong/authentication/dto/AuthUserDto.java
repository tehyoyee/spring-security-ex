package com.actionvoca.taehyeong.authentication.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
@Setter
@Getter
public class AuthUserDto {

    private String username;
    private String password;

}