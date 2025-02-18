package com.taehyeong.backend.authentication.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@Builder
public class LoginInfo {

    private Long id;
    private String sessionId;
    private String username;
    private String roleName;
    private LocalDateTime lastAccessTime;
    private long remainingTime;
    private long expireTime;
    private String authorities;
    private boolean isSessionExpired;

}
