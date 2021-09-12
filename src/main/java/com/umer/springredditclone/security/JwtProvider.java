package com.umer.springredditclone.security;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.annotation.PostConstruct;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.umer.springredditclone.exceptions.SpringRedditException;

import io.jsonwebtoken.Jwts;

@Service
public class JwtProvider {

	private static final String AN_EXCEPTION_OCCURRED_WHILE_LOADING_KEY_STORE = "An exception occurred while loading key store";
	private static final String KEYSTORE_PASSWORD = "secret";
	private static final String KEYSTORE_ALIAS = "/springblog.jks";
	private static final String JKS_KEYSTORE = "JKS";
	private KeyStore keyStore;

	@PostConstruct
	public void init() {
		try {
			keyStore = KeyStore.getInstance(JKS_KEYSTORE);
			InputStream resourceAsInputStream = getClass().getResourceAsStream(KEYSTORE_ALIAS);
			keyStore.load(resourceAsInputStream, KEYSTORE_PASSWORD.toCharArray());
		} catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | IOException e) {
			throw new SpringRedditException(AN_EXCEPTION_OCCURRED_WHILE_LOADING_KEY_STORE + e.getStackTrace());
		}
	}

	public String generateToken(Authentication authentication) {
		org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) authentication
				.getPrincipal();
		return Jwts.builder().setSubject(principal.getUsername())
				.signWith(getPrivateKey())
				.compact();

	}
	
	private PrivateKey getPrivateKey() {
		try {
			return (PrivateKey)keyStore.getKey(KEYSTORE_ALIAS, KEYSTORE_PASSWORD.toCharArray());
		} catch (UnrecoverableKeyException | KeyStoreException | NoSuchAlgorithmException e) {
			throw new SpringRedditException(AN_EXCEPTION_OCCURRED_WHILE_LOADING_KEY_STORE + e.getStackTrace());
		}		
	}
}
