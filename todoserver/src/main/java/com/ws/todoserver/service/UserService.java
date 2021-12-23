package com.ws.todoserver.service;

import com.ws.todoserver.model.entity.User;

public interface UserService {
	public User register(User user);

	public void update(User user);

	public boolean updatePassword(String email, String password);

	public boolean isEmailAlreadyTaken(String email);
	
	public Long userId(String email);
}
