package com.taehyeong.backend.dto.member.request;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MemberCreateDTO {

    private String username;

    private String password;

}
