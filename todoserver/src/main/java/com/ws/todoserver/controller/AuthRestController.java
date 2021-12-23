package com.ws.todoserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ws.todoserver.model.ApiResponse;
import com.ws.todoserver.model.UserModel;
import com.ws.todoserver.model.entity.User;
import com.ws.todoserver.security.jwt.JwtUtil;
import com.ws.todoserver.service.UserService;
import com.ws.todoserver.validator.UserValidator;
import com.ws.todoserver.validator.UserValidator.USER_VALIDATOR_RESULT;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class AuthRestController {

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private UserValidator userValidator;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private UserService userService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@PostMapping(value = "/authenticate")
	public ResponseEntity<ApiResponse> login(@RequestBody UserModel user) throws Exception {
		USER_VALIDATOR_RESULT result = userValidator.validateLogin(user);
		if (result != USER_VALIDATOR_RESULT.SUCCESS) {
			return new ResponseEntity<>(new ApiResponse(false, UserValidator.getMessage(result)), HttpStatus.OK);
		}
		try {
			UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
					user.getEmail(), user.getPassword());
			Authentication auth = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
			SecurityContextHolder.getContext().setAuthentication(auth);
		} catch (BadCredentialsException ex) {

			return new ResponseEntity<>(new ApiResponse(false, "Invalid email or password"), HttpStatus.OK);
		}

		final UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
		final String jwt = jwtUtil.generateToken(userDetails);
		user.setToken(jwt);
		user.setPassword(null);
		return new ResponseEntity<>(new ApiResponse(true, "Authenticated", user), HttpStatus.OK);
	}

	@PostMapping(value = "/register")
	public ResponseEntity<ApiResponse> register(@RequestBody UserModel user) {
		USER_VALIDATOR_RESULT result = userValidator.validateRegistration(user);
		if (result != USER_VALIDATOR_RESULT.SUCCESS) {
			return new ResponseEntity<>(new ApiResponse(false, UserValidator.getMessage(result)), HttpStatus.OK);
		}
		boolean isAlreadyUsed = userService.isEmailAlreadyTaken(user.getEmail());
		if (isAlreadyUsed) {
			return new ResponseEntity<>(new ApiResponse(false, "Email is already used"), HttpStatus.OK);
		}
		User created = new User();
		created.setEmail(user.getEmail());
		created.setPassword(user.getPassword());

		created = userService.register(created);
		
		return new ResponseEntity<>(new ApiResponse(true, "Registrated"), HttpStatus.OK);
	}

	@PostMapping(value = "/invalidate")
	public ResponseEntity<ApiResponse> logout() throws Exception {
		Object userDetails = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (userDetails instanceof UserDetails) {
			String email = ((UserDetails) userDetails).getUsername();
			if (!email.isEmpty()) {
				jwtUtil.invalidateRelatedTokens(email);
				return new ResponseEntity<>(new ApiResponse(true, "Invalidated"), HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(new ApiResponse(false, "Invalidation Failed"), HttpStatus.OK);
	}

	@PostMapping(value = "/check")
	public ResponseEntity<ApiResponse> check(@RequestParam("email") String email) {
		boolean isAlreadyUsed = userService.isEmailAlreadyTaken(email);
		if (isAlreadyUsed) {
			return new ResponseEntity<>(new ApiResponse(false, "Email is already taken"), HttpStatus.OK);
		}
		return new ResponseEntity<>(new ApiResponse(true, "Email is valid"), HttpStatus.OK);
	}
}
