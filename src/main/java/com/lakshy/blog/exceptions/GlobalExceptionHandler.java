package com.lakshy.blog.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.lakshy.blog.payloads.ApiResponse;

// will run for all exceptions which may come for all controller classes
@RestControllerAdvice
public class GlobalExceptionHandler {
	
	// tell which class we want to handle response
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException ex)
	{
		String message = ex.getMessage();
		ApiResponse apiResponse = new ApiResponse(message, false);
		
		return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.NOT_FOUND);
	}
}
