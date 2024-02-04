package com.actionvoca.taehyeong.controller;

import com.actionvoca.taehyeong.entities.dao.NicknameDao;
import com.actionvoca.taehyeong.entities.dto.UserRegistrationDto;
import com.actionvoca.taehyeong.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * User Registration Condition Check; duplicate
     *
     */

    @PostMapping("/create")
    public void createUser(@RequestBody UserRegistrationDto userRegistrationDto) {
        userService.createUser(userRegistrationDto);
    }

    @GetMapping("/check/username/duplicate")
    public boolean checkDuplicate(@RequestBody String username) {
        return userService.checkUsernameDuplicate(username);
    }

    @GetMapping("/check/nickname/duplicate")
    public boolean checkNicknameDuplicate(@RequestBody String nickname) {
        return userService.checkNicknameDuplicate(nickname);
    }

    @GetMapping("/check/email/duplicate")
    public boolean checkEmailDuplicate(@RequestBody String email) {
        return userService.checkEmailDuplicate(email);
    }




    @GetMapping("/nickname")
    public NicknameDao getUserNickname(HttpServletRequest request) {
        return userService.getNickname(request.getHeader("Authorization"));
    }

    @ExceptionHandler
    public ResponseEntity<String> handleException(IllegalArgumentException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        // return ResponseEntity.badRequest().body(e.getMessage()); // 위와 같은 코드
    }

    /**
     *
     *
     *
     */

}