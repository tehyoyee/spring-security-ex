package com.taehyeong.backend.authentication.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class SessionInfo {

    private Long userId;

    private String sessionId;

    private SessionStatus status;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private long expireTime;

    private long sessionDuration;

    private boolean isExpired;

}
