package com.Blog_Application_Web.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler
	public MainException resourceNotFound(ResourceNotFound e ) {
		MainException mainException = new MainException();
		mainException.setStatus(HttpStatus.NOT_FOUND.value());
		mainException.setMsg(e.getMessage());
		mainException.setTime(System.currentTimeMillis());
		return mainException;
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
		
		Map<String, String> resp = new HashMap<String, String>();
		e.getBindingResult().getAllErrors().forEach((error)->{
			String fieldName = ((FieldError) error).getField();
			String message = error.getDefaultMessage();
			resp.put(fieldName, message);

		});
		return resp;
	}
}
