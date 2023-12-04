package com.demo.security6.controller;

import com.demo.security6.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.Authentication;

@Controller
public class MainPageController {

	@Autowired
	private ProductService productService;

	@GetMapping("/main")
	public String main(Authentication a, Model model) {
		model.addAttribute("username", a.getName());
		model.addAttribute("products", productService.findAll());
		return "main.html";
	}
}