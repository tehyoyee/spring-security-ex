package com.tete.back.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Test2Controller {

    @GetMapping("/asdf")
    public String asdf() {
        return "index";
    }
}
