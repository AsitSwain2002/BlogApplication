package com.Blog_Application_Web.ExceptionHandler;

import lombok.Data;

@Data
public class MainException {

	private int status;
	private String msg;
	private long time;
}
