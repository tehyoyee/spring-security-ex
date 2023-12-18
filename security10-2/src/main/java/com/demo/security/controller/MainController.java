package com.demo.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController {

	@GetMapping("/main")
	public String main() {
		return "main.html";
	}
	@PostMapping("/main")
	public String postMain() {
		return "main.html";
	}

	@PostMapping("/ciao")
	public String postCiao() {
		return "Postt Ciao";
	}

}