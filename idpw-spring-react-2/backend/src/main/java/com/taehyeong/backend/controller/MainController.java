package com.taehyeong.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class MainController {

    @GetMapping
    public ResponseEntity<Map<String, String>> main() {

        Map<String, String> res = new HashMap<>();
        res.put("asdf", "zxcv");

        return ResponseEntity.ok(res);

    }

}
