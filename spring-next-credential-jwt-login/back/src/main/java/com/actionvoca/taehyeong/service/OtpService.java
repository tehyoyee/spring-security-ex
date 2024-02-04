package com.actionvoca.taehyeong.service;

import com.actionvoca.taehyeong.entities.Otp;
import com.actionvoca.taehyeong.entities.User;
import com.actionvoca.taehyeong.repository.OtpRepository;
import com.actionvoca.taehyeong.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.actionvoca.taehyeong.utils.GenerateCodeUtil.generateCode;

@Service
@Transactional
public class OtpService {

    @Autowired
    private OtpRepository otpRepository;

    @Autowired
    private UserRepository userRepository;

    public boolean checkOtp(String email, String code) {
        System.out.println(email);
        Optional<Otp> userOtp = otpRepository.findByEmail(email);
        if (userOtp.isPresent()) {
            Otp otp = userOtp.get();
            System.out.println(code + " " + otp.getCode());
            if (code.equals(otp.getCode())) {
                userOtp.get().setVerified(true);
                return true;
            }
        }
        return false;
    }

    public void createOrUpdateOtp(String email) {
        String code = generateCode();
        Optional<Otp> otp = otpRepository.findByEmail(email);
        if (checkEmailDuplicate(email)) {
            throw new IllegalArgumentException("이미 가입된 이메일입니다.");
        }
        if (otp.isPresent()) {
            Otp foundOtp = otp.get();
            foundOtp.setCode(code);
            foundOtp.setCre(LocalDateTime.now());
            foundOtp.setExp(LocalDateTime.now());
            foundOtp.setVerified(false);
        } else {
            Otp newOtp = Otp.builder()
                    .email(email)
                    .code(code)
                    .exp(LocalDateTime.now())
                    .cre(LocalDateTime.now())
                    .build();
            otpRepository.save(newOtp);
        }
    }

    private boolean checkEmailDuplicate(String email) {
        Optional<User> foundUser = userRepository.findByEmail(email);
        return foundUser.isPresent();
    }

//    private boolean verifyEmailPattern(String email) {
//        var form = "^[a-zA-Z0-9]+@[0-9a-zA-Z]+\\.[a-z]+$";
//        form.test
//
//    }

}
