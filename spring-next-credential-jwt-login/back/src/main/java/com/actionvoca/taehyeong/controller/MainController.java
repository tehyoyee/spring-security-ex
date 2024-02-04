package com.actionvoca.taehyeong.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @GetMapping("/")
    public String asdf() {
        return "asdf";
    }
}
