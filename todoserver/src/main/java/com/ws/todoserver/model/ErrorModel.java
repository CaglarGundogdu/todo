package com.ws.todoserver.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class ErrorModel {

	@JsonInclude(Include.NON_NULL)
	private String request;

	@JsonInclude(Include.NON_NULL)
	private String error;

	@JsonInclude(Include.NON_NULL)
	private String message;

	public ErrorModel(String request, String error, String message) {
		super();
		this.request = request;
		this.error = error;
		this.message = message;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
