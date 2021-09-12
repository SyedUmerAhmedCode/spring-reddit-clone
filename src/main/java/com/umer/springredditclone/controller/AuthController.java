package com.umer.springredditclone.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.umer.springredditclone.dto.AuthenticationResponse;
import com.umer.springredditclone.dto.LoginRequest;
import com.umer.springredditclone.dto.RegisterRequest;
import com.umer.springredditclone.service.AuthService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "/api/auth")
@AllArgsConstructor
public class AuthController {

	private static final String ACCOUNT_ACTIVATED_SUCCESSFULLY = "Account activated successfully";
	private static final String USER_REGISTRATION_SUCCESSFUL = "User registration successful.";
	private final AuthService authService;

	@PostMapping("/signup")
	public ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequest) {
		authService.signup(registerRequest);
		final String responseBody = USER_REGISTRATION_SUCCESSFUL;
		return new ResponseEntity<String>(responseBody, HttpStatus.OK);

	}

	@GetMapping("/health")
	public String healthCheck() {
		return "ping pong";
	}

	@GetMapping("accountVerification/{token}")
	public ResponseEntity<String> verifyAccount(@PathVariable String token) {
		authService.verifyAccount(token);
		return new ResponseEntity<>(ACCOUNT_ACTIVATED_SUCCESSFULLY, HttpStatus.OK);
	}

	@PostMapping("/login")
	public AuthenticationResponse login(@RequestBody LoginRequest loginRequest) {
		return authService.login(loginRequest);
	}

}
