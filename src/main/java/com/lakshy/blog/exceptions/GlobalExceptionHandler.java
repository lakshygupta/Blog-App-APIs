package com.lakshy.blog.exceptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

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
	
	// invalid password in login api
	@ExceptionHandler(ApiException.class)
	public ResponseEntity<ApiResponse> apiExceptionHandler(ApiException ex)
	{
		String message = ex.getMessage();
		ApiResponse apiResponse = new ApiResponse(message, true);
		
		return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.BAD_REQUEST);
	}
	
	// not a custom created exception but raised by Validator on violation
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
		Map<String, String> resp = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError)error).getField();
			String message = error.getDefaultMessage();
			resp.put(fieldName, message);
		});
		
		return new ResponseEntity<Map<String, String>>(resp,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<Map<String, String>> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex)
	{
		Map<String,String> resp = new HashMap<>();
		resp.put("message", ex.getMessage());
		resp.put("http request", ex.getMethod());
		
		return new ResponseEntity<Map<String,String>>(resp,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<Map<String,String>> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex)
	{
		Map<String, String> resp = new HashMap<>();
		resp.put("message", ex.getMessage());
		resp.put("name", ex.getName());
		resp.put("value", " request parameter  " + (String)ex.getValue());
		return new ResponseEntity<Map<String,String>>(resp,HttpStatus.BAD_REQUEST);
	}
	
	// file is not an image handler
	@ExceptionHandler(IncorrectFileFormatException.class)
	public ResponseEntity<ApiResponse> IncorrectFileFormatExceptionHandler(IncorrectFileFormatException ex)
	{
		String message = ex.getMessage();
		ApiResponse apiResponse = new ApiResponse(message, false);
		
		return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.NOT_FOUND);
	}
	
	// file not selected exception
	@ExceptionHandler(StringIndexOutOfBoundsException.class)
	public ResponseEntity<Map<String,String>> handleStringIndexOutOfBoundsExceptionException(StringIndexOutOfBoundsException ex)
	{
		Map<String, String> resp = new HashMap<>();
		resp.put("message", ex.getMessage());
		resp.put("name", "Image File Not Found in Request");
		return new ResponseEntity<Map<String,String>>(resp,HttpStatus.BAD_REQUEST);
	}
	
	
}
