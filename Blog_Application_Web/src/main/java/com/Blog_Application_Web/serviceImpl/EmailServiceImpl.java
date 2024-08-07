package com.Blog_Application_Web.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.Blog_Application_Web.service.EmailService;

@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
	private JavaMailSender javaMailSender;

	@Override
	public void sendEmail(String to, String subject, String body) {

		try {

			SimpleMailMessage mail = new SimpleMailMessage();
			mail.setTo(to);
			mail.setSubject(subject);
			mail.setText(body);
			javaMailSender.send(mail);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
