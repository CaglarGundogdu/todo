package com.ws.todoserver.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class ApiResponse {

	private boolean success;
	private String message;
	
	@JsonInclude(Include.NON_NULL)
	private Object data;

	public ApiResponse(boolean success, String message, Object data) {
		this.success = success;
		this.message = message;
		this.data = data;
	}

	public ApiResponse(boolean success, String message) {
		this.success = success;
		this.message = message;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "ApiResponse [success=" + success + ", message=" + message + "]";
	}

}