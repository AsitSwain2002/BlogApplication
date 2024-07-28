package com.Blog_Application_Web.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(ResourceNotFound.class)
	public ModelAndView resourceNotFound(ResourceNotFound e ) {
		ModelAndView modelAndView = new ModelAndView(); 
		MainException mainException = new MainException();
		mainException.setStatus(HttpStatus.NOT_FOUND.value());
		mainException.setMsg(e.getMessage());
		mainException.setTime(System.currentTimeMillis());
		modelAndView.addObject("exception", mainException);
		modelAndView.setViewName("errorPage");
		return modelAndView;
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ModelAndView handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
		
		ModelAndView modelAndView = new ModelAndView();
		Map<String, String> resp = new HashMap<String, String>();
		e.getBindingResult().getAllErrors().forEach((error)->{
			String fieldName = ((FieldError) error).getField();
			String message = error.getDefaultMessage();
			resp.put(fieldName, message);
		});
		modelAndView.addObject("errorMap", resp);
		modelAndView.setViewName("errorPage");
		return modelAndView;
	}
	@ExceptionHandler(Exception.class)
    public ModelAndView handleGenericException(Exception e) {
        ModelAndView modelAndView = new ModelAndView();
        MainException mainException = new MainException();
        mainException.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        mainException.setMsg("An unexpected error occurred: " + e.getMessage());
        mainException.setTime(System.currentTimeMillis());
        modelAndView.addObject("exception", mainException);
        modelAndView.setViewName("error");
        return modelAndView;
    }
}
