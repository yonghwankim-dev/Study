package com.example.secondservice;

import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/second-service")
@Slf4j
public class SecondServiceController {
	Environment env;

	public SecondServiceController(Environment env) {
		this.env = env;
	}

	@GetMapping("/welcome")
	public String welcome(){
		return "welcome to the Second Service";
	}

	@GetMapping("/check")
	public String check(){
		return "Hi, there. This is a message from Second Service.";
	}
}
