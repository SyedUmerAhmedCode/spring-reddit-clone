package com.umer.springredditclone.service;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.umer.springredditclone.exceptions.SpringRedditException;
import com.umer.springredditclone.model.User;
import com.umer.springredditclone.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

	private static final String NO_USER_FOUND_WITH_NAME = "No user found with name: ";
	private static final String USER = "USER";
	private final UserRepository userRepository;

	@Override
	@Transactional(/* readOnly=true */)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		final Optional<User> userOptional = userRepository.findByUsername(username);
		User user = userOptional.orElseThrow(() -> new SpringRedditException(NO_USER_FOUND_WITH_NAME + username));

		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				user.isEnabled(), true, true, true, getAuthorities(USER));
	}

	private Collection<? extends GrantedAuthority> getAuthorities(String role) {
		return Collections.singletonList(new SimpleGrantedAuthority(role));
	}

}
