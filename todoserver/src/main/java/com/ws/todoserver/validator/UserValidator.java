package com.ws.todoserver.validator;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Component;

import com.ws.todoserver.model.UserModel;

@Component
public class UserValidator {

	public enum USER_VALIDATOR_RESULT {
		SUCCESS, PASSWORD_NOT_VALID, PASSWORD_NOT_FOUND, USERNAME_NOT_VALID, USERNAME_NOT_FOUND, PASSWORD_NOT_MATCH,
	}

	private boolean isValidEmail(String email) {
		return EmailValidator.getInstance().isValid(email);
	}

	private boolean isValidPassword(String password) {
		// This validator only check password empty or not
		// intentionally no control are placed
		if (password.isEmpty())
			return false;
		
		return true;
	}

	public USER_VALIDATOR_RESULT validateLogin(UserModel user) {

		if (user.getEmail() != null && !isValidEmail(user.getEmail()))
			return USER_VALIDATOR_RESULT.USERNAME_NOT_VALID;

		if (user.getPassword() == null)
			return USER_VALIDATOR_RESULT.PASSWORD_NOT_FOUND;

		if (!isValidPassword(user.getPassword()))
			return USER_VALIDATOR_RESULT.PASSWORD_NOT_VALID;

		return USER_VALIDATOR_RESULT.SUCCESS;
	}

	public USER_VALIDATOR_RESULT validateRegistration(UserModel user) {

		if (user.getEmail() == null)
			return USER_VALIDATOR_RESULT.USERNAME_NOT_FOUND;

		if (!isValidEmail(user.getEmail()))
			return USER_VALIDATOR_RESULT.USERNAME_NOT_VALID;

		if (user.getPassword() == null)
			return USER_VALIDATOR_RESULT.PASSWORD_NOT_FOUND;

		if (!isValidPassword(user.getPassword()))
			return USER_VALIDATOR_RESULT.PASSWORD_NOT_VALID;

		
		return USER_VALIDATOR_RESULT.SUCCESS;
	}

	public USER_VALIDATOR_RESULT validateUpdatePassword(UserModel user) {

		if (user.getEmail() == null)
			return USER_VALIDATOR_RESULT.USERNAME_NOT_VALID;
		if (!isValidEmail(user.getEmail()))
			return USER_VALIDATOR_RESULT.USERNAME_NOT_VALID;
		if (user.getPassword() == null)
			return USER_VALIDATOR_RESULT.PASSWORD_NOT_VALID;
		if (!isValidPassword(user.getPassword()))
			return USER_VALIDATOR_RESULT.PASSWORD_NOT_VALID;

		return USER_VALIDATOR_RESULT.SUCCESS;
	}

	public static String getMessage(USER_VALIDATOR_RESULT result) {
		if (result == USER_VALIDATOR_RESULT.PASSWORD_NOT_VALID) {
			return "Password is not valid";
		} else if (result == USER_VALIDATOR_RESULT.PASSWORD_NOT_FOUND) {
			return "Password is required";
		} else if (result == USER_VALIDATOR_RESULT.USERNAME_NOT_VALID) {
			return "Email is not valid";
		} else if (result == USER_VALIDATOR_RESULT.USERNAME_NOT_FOUND) {
			return "Email is required";
		} else if (result == USER_VALIDATOR_RESULT.PASSWORD_NOT_MATCH) {
			return "Passwords are not match";
		}    
		return "";
	}
}






