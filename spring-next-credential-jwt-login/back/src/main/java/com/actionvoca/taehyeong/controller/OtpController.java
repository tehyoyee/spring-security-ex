package com.actionvoca.taehyeong.controller;

import com.actionvoca.taehyeong.entities.dto.OtpCheckRequestDto;
import com.actionvoca.taehyeong.entities.dto.OtpCreateRequestDto;
import com.actionvoca.taehyeong.service.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/otp")
public class OtpController {

    @Autowired
    private OtpService otpService;

    @PostMapping("/create")
    public void sendOtp(@RequestBody OtpCreateRequestDto otpCreateRequestDto) {
        otpService.createOrUpdateOtp(otpCreateRequestDto.getEmail());
    }

    @GetMapping("/check")
    public boolean checkOtp(@RequestBody OtpCheckRequestDto otpCheckRequestDto) {
        return otpService.checkOtp(otpCheckRequestDto.getEmail(), otpCheckRequestDto.getCode());
    }

}
