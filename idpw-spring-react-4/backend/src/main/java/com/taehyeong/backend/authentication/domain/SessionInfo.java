package com.taehyeong.backend.authentication.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class SessionInfo {

    private Long userId;

    private String sessionId;

    private String username;

    private String stompChannel;

    private SessionStatus status;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private long expireTime;

    private long sessionDuration;

    private boolean isExpired;

}
