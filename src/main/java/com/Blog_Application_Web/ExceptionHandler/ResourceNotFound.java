package com.Blog_Application_Web.ExceptionHandler;

public class ResourceNotFound extends RuntimeException {
	
	public ResourceNotFound(String msg){
		super(msg);
	}

}
