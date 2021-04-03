package com.umer.springredditclone.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.umer.springredditclone.dto.RegisterRequest;
import com.umer.springredditclone.service.AuthService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path= "/api/auth")
@AllArgsConstructor
public class AuthController {

	private final AuthService authService;

	@PostMapping("/signup")
	public ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequest) {
		authService.signup(registerRequest);
		final String responseBody = "User registration successful.";
		return new ResponseEntity<String>(responseBody, HttpStatus.OK);

	}

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String healthCheck() {
		return "ping pong";
	}

}
