package com.umer.springredditclone.service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.umer.springredditclone.dto.LoginRequest;
import com.umer.springredditclone.dto.RegisterRequest;
import com.umer.springredditclone.exceptions.SpringRedditException;
import com.umer.springredditclone.model.NotificationEmail;
import com.umer.springredditclone.model.User;
import com.umer.springredditclone.model.VerificationToken;
import com.umer.springredditclone.repository.UserRepository;
import com.umer.springredditclone.repository.VerificationTokenRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class AuthService {

	private static final String USER_NOT_FOUND_WITH_NAME = "User not found with name: ";
	private static final String INVALID_TOKEN = "Invalid Token";
	private final String HTTP_PROTOCOL = "http://";
	private final String HOST = "localhost";
	private final String PORT = "8080";
	private final String CONTEXT_PATH = "/api/auth/";

	private final UserRepository userRepository;
 	private final PasswordEncoder passwordEncoder;	
	private final VerificationTokenRepository verificationTokenRepository;	
	private final MailService mailService;
	private final AuthenticationManager authenticationManager; 

	@Transactional
	public void signup(RegisterRequest registerRequest) {
		User user = new User();
		user.setUsername(registerRequest.getUsername());
		user.setEmail(registerRequest.getEmail());
		user.setPassword(encodePassword(registerRequest.getPassword()));
		user.setCreated(Instant.now());
		user.setEnabled(false);

		userRepository.save(user);

		String token = generateVerificationToken(user);
		final String subject = "Please activate your account";
		final String recipientEmail = user.getEmail();
		/*
		 * final String emailBody = "Thank you for signing up to Spring Reddit, " +
		 * "please click on the below url to activate your account : " +
		 * "http://localhost:8080/api/auth/accountVerification/" + token;
		 */
		final String emailBody = "Thank you for signing up to Spring Reddit, "
				+ "please click on the below url to activate your account : " + HTTP_PROTOCOL + HOST + ":" + PORT
				+ CONTEXT_PATH + "accountVerification/" + token;
		NotificationEmail notificationEmail = new NotificationEmail(subject, recipientEmail, emailBody);
		mailService.sendMail(notificationEmail);
	}

	private String generateVerificationToken(User user) {
		String token = UUID.randomUUID().toString();
		VerificationToken verificationToken = new VerificationToken();
		verificationToken.setToken(token);
		verificationToken.setUser(user);
		verificationTokenRepository.save(verificationToken);
		return token;
	}

	private String encodePassword(String password) {
		return passwordEncoder.encode(password);
	}

	public void verifyAccount(String token) {
		final Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
		verificationToken.orElseThrow(()-> new SpringRedditException(INVALID_TOKEN));
		fetchUserAndEnable(verificationToken.get());		
	}

	@Transactional
	private void fetchUserAndEnable(VerificationToken verificationToken) {
		String username=verificationToken.getUser().getUsername();
		User user=userRepository.findByUsername(username).orElseThrow(()->new SpringRedditException(USER_NOT_FOUND_WITH_NAME+username));
		user.setEnabled(true);
		userRepository.save(user);		
	}

	public void login(LoginRequest loginRequest) {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), 
						loginRequest.getPassword()));
	}
}
