package com.taehyeong.backend.service;

import com.taehyeong.backend.authentication.domain.SessionStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StompService {

    private final SimpMessagingTemplate messagingTemplate;

    public void alertDuplicate(String username) {
        messagingTemplate.convertAndSendToUser(username, "/queue/reply", SessionStatus.DUPLICATE);
        System.out.println("alerted");
    }


}
