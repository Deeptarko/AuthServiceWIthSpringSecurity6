package com.deep.AuthServiceLatest.service.impl;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.deep.AuthServiceLatest.entity.User;
import com.deep.AuthServiceLatest.repository.UserRepository;
import com.deep.AuthServiceLatest.service.UserService;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = findByUsername(username);
		if (user.isPresent()) {
			User newUser = user.get();
	
			return new org.springframework.security.core.userdetails.User(newUser.getUsername(), newUser.getPassword(),
					newUser.getRoles().stream().map((role) -> new SimpleGrantedAuthority(role))
							.collect(Collectors.toList()));

		}
		throw new UsernameNotFoundException("User not found");
	}

	@Override
	public User saveUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

	@Override
	public Optional<User> findByUsername(String username) {
		return userRepository.findByUsername(username);
	}
}
