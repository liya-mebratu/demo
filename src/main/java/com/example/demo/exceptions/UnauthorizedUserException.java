package com.example.demo.exceptions;

public class UnauthorizedUserException extends RuntimeException {

	public UnauthorizedUserException(String message) {
		super(message);
	}

}
