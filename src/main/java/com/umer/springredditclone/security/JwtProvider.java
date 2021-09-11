package com.umer.springredditclone.security;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;

@Service
public class JwtProvider {

	public String generateToken(Authentication authentication) {
		org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) authentication
				.getPrincipal();
		return Jwts.builder()
				.setSubject(principal.getUsername())
//				.signWith(getPrivateKey())
				.compact();
				
		
	}
}
