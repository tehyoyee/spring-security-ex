package com.actionvoca.taehyeong.service;

import com.actionvoca.taehyeong.authentication.dto.AuthUserDto;
import com.actionvoca.taehyeong.entities.Otp;
import com.actionvoca.taehyeong.entities.User;
import com.actionvoca.taehyeong.entities.dao.NicknameDao;
import com.actionvoca.taehyeong.entities.dto.UserRegistrationDto;
import com.actionvoca.taehyeong.entities.enums.Role;
import com.actionvoca.taehyeong.repository.OtpRepository;
import com.actionvoca.taehyeong.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional
public class UserService {

    @Value("${jwt.signing.key}")
    private String signingKey;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OtpRepository otpRepository;

    public void auth(AuthUserDto user) {
        Optional<User> o =
                userRepository.findByUsername(user.getUsername());

        if(o.isPresent()) {
            User u = o.get();
            if (!passwordEncoder.matches(user.getPassword(), u.getPassword()))
                throw new BadCredentialsException("잘못된 아이디 혹은 패스워드입니다.");
        } else {
            throw new BadCredentialsException("잘못된 아이디 혹은 패스워드입니다.");
        }
    }

    public void createUser(UserRegistrationDto userRegistrationDto) {
//        if (!checkUsernameDuplicate(userRegistrationDto.getUsername()))
//            throw new IllegalArgumentException("아이디 중복검사를 완료해주세요.");
//        if (!checkEmailVerified(userRegistrationDto.getEmail()))
//            throw new IllegalArgumentException("이메일 인증을 완료해주세요.");
//        if (!checkPassword(userRegistrationDto.getPassword()))
//            throw new IllegalArgumentException("패스워드는 8자 20자 사이 영문 및 숫자의 조합으로 구성해주세요.");
//        if (!checkNicknameDuplicate(userRegistrationDto.getNickname()))
//            throw new IllegalArgumentException("닉네임 중복검사를 완료해주세요.");

        User user = User.builder()
                .email(userRegistrationDto.getEmail())
                .username(userRegistrationDto.getUsername())
                .password(passwordEncoder.encode(userRegistrationDto.getPassword()))
                .nickname(userRegistrationDto.getNickname())
                .role(Role.ROLE_USER).build();
//        Optional<Otp> userOtp = otpRepository.findByEmail(userRegistrationDto.getEmail());
//        userOtp.ifPresent(otp -> otpRepository.delete(otp));
        userRepository.save(user);
        
    }

    public boolean checkUsernameDuplicate(String username) {
        Optional<User> found = userRepository.findByUsername(username);
        return found.isEmpty();
    }

    public boolean checkEmailVerified(String email) {
        Optional<Otp> otp = otpRepository.findByEmail(email);
        return otp.map(Otp::isVerified).orElse(false);
    }

    public boolean checkEmailDuplicate(String email) {
        Optional<User> found = userRepository.findByEmail(email);
        return found.isEmpty();
    }

    private boolean checkPassword(String password) {
        Pattern p = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d~!@#$%^&*()+|=]{8,20}$");
        Matcher m = p.matcher(password);
        return m.matches();
    }

    public boolean checkNicknameDuplicate(String nickname) {
        Optional<User> found = userRepository.findByNickname(nickname);
        return found.isEmpty();
    }


    public NicknameDao getNickname(String jwt) {
//        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        NicknameDao nicknameDao;
        System.out.println(jwt);
        SecretKey key = Keys.hmacShaKeyFor(signingKey.getBytes(StandardCharsets.UTF_8));
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jwt)
                .getBody();

        String username = String.valueOf(claims.get("username"));
        Optional<User> foundUser = userRepository.findByUsername(username);
        System.out.println("user" + username);

        if (foundUser.isPresent()) {
            System.out.println("asdfsdfsdfd");
            nicknameDao = NicknameDao.builder()
                    .nickname(foundUser.get().getNickname()).build();
        } else {
            System.out.println("zxcxc");
            nicknameDao = NicknameDao.builder()
                    .nickname("").build();
        }
        return nicknameDao;
    }

}
