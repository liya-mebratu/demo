package com.example.demo.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

	// Handle specific exceptions
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<Map<String, String>> handleResourceNotFoundException(ResourceNotFoundException ex,
			WebRequest request) {

		Map<String, String> errorResponse = new HashMap<>();
		errorResponse.put("error", "Resource not found");
		errorResponse.put("message", ex.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
	}

	// Handle unauthorized exceptions
	@ExceptionHandler(UnauthorizedUserException.class)
	// public ResponseEntity<Map<String, String>>
	// handleUnauthorizedUserException(UnauthorizedUserException ex) {
	public ResponseEntity<ErrorResponse> handleUnauthorizedUserException(UnauthorizedUserException ex) {

//		Map<String, String> errorResponse = new HashMap<>();
//		errorResponse.put("error", "Unauthorised user " + ex.getMessage());

		ErrorResponse response = new ErrorResponse();
		response.setError("Unauthorised");
		response.setMessage("Unauthorised user " + ex.getMessage());

		return ResponseEntity.status(HttpStatusCode.valueOf(401)).body(response);
	}

	// Handle validation exceptions
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getFieldErrors()
				.forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
	}

	// Handle all other exceptions
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, String>> handleGlobalException(Exception ex, WebRequest request) {
		Map<String, String> errorResponse = new HashMap<>();
		errorResponse.put("error", "An unexpected error occurred");
		errorResponse.put("details", ex.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
	}

	public class ErrorResponse extends Object {
		private String error;
		private String errorCode;
		private String message;

		public String getError() {
			return error;
		}

		public void setError(String error) {
			this.error = error;
		}

		public String getErrorCode() {
			return errorCode;
		}

		public void setErrorCode(String errorCode) {
			this.errorCode = errorCode;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

	}
}
