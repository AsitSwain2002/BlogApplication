package com.Blog_Application_Web.service;

public interface EmailService {
	
	public void sendEmail(String to, String subjectString, String body);
}
