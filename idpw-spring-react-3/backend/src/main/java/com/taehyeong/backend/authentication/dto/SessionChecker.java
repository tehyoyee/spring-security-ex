package com.taehyeong.backend.authentication.dto;

import com.taehyeong.backend.authentication.domain.SessionStatus;
import lombok.Builder;

@Builder
public record SessionChecker(boolean isExpired, SessionStatus sessionStatus) {
}
