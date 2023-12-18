package com.example.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@Controller
public class HelloController {

	private Logger logger =
			Logger.getLogger(HelloController.class.getName());

	@GetMapping("/")
	public String main() {
		return "main.html";
	}

	@PostMapping("/test")
	@ResponseBody
//	@CrossOrigin("http://localhost")
	public String test() {
		logger.info("Test method called");
		return "HELLO";
	}


}
