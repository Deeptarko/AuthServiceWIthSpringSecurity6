package com.deep.AuthServiceLatest.service;

import java.util.Optional;

import com.deep.AuthServiceLatest.entity.User;

public interface UserService {
	public User saveUser(User user);
	public Optional<User> findByUsername(String username);
}