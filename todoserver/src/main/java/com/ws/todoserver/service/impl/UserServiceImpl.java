package com.ws.todoserver.service.impl;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ws.todoserver.model.entity.User;
import com.ws.todoserver.repository.UserRepository;
import com.ws.todoserver.service.UserService;


@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public boolean isEmailAlreadyTaken(String email) {
		User user = userRepository.findByEmail(email);
		if (user != null) {
			return true;
		}
		return false;
	}

	public User register(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setCreateDate(new Timestamp(new Date().getTime()));
		return userRepository.save(user);
	}

	public void update(User user) {
		userRepository.save(user);
	}

	public boolean updatePassword(String email, String password) {
		try {
			User user = userRepository.findByEmail(email);
			user.setPassword(bCryptPasswordEncoder.encode(password));
			userRepository.save(user);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public Long userId(String email) {
		User user = userRepository.findByEmail(email);
		if (user != null) {
			return user.getId();
		}
		return 0l;
	}

}
