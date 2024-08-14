package com.Blog_Application_Web.controller;

import java.util.Random;
import java.util.UUID;

import javax.print.attribute.standard.MediaSize.Other;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.Blog_Application_Web.Repo.UserRepo;
import com.Blog_Application_Web.entity.User;
import com.Blog_Application_Web.service.EmailService;
import com.Blog_Application_Web.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class MailController {

	@Autowired
	public EmailService emailService;

	@Autowired
	public UserRepo userRepo;

	@PostMapping("/send")
	public ModelAndView sendMail(@RequestParam String email, HttpServletRequest req) {

		ModelAndView modelAndView = new ModelAndView();
		Random random = new Random();
		int num1 = random.nextInt(9);
		int num2 = random.nextInt(9);
		int num3 = random.nextInt(9);
		int num4 = random.nextInt(9);

		String to = email;
		String subject = "Password Reset";
		String otp = num1 + "" + num2 + "" + num3 + "" + num4;
		String body = otp + " is your One Time Password for reset password." + "  Dont Share With Any One";

		HttpSession session = req.getSession();
		String uniqueId = UUID.randomUUID().toString();
		session.setAttribute("email", email);
		session.setAttribute("OTP", otp);
		session.setAttribute("uniqueId", uniqueId);

		emailService.sendEmail(to, subject, body);

		modelAndView.setViewName("otpEnter");

		return modelAndView;
	}

//	@GetMapping("/otpEnter")
//
//	public String otpPage() {
//		return "otpEnter";
//	}

	@PostMapping("/otpCheck")
	public ModelAndView otpCheck(@RequestParam String otp, @RequestParam String uniqueId, HttpServletRequest req) {
	    ModelAndView modelAndView = new ModelAndView();
	    HttpSession session = req.getSession(false);

	    if (session != null) {
	        String sessionOtp = (String) session.getAttribute("OTP");
	        String sessionUniqueId = (String) session.getAttribute("uniqueId"); 

	        System.out.println("Received OTP: " + otp);
	        System.out.println("Session OTP: " + sessionOtp);
	        System.out.println("Received Unique ID: " + uniqueId);
	        System.out.println("Session Unique ID: " + sessionUniqueId);

	        if (uniqueId.equals(sessionUniqueId) && otp.equals(sessionOtp)) {
	            modelAndView.setViewName("updatePassword");
	        } else {
	            modelAndView.addObject("invalidOtp", "Invalid OTP");
	            modelAndView.setViewName("otpEnter");
	        }
	    } else {
	        modelAndView.addObject("invalidOtp", "Session expired. Please request a new OTP.");
	        modelAndView.setViewName("otpEnter");
	    }

	    return modelAndView;
	}




	@PostMapping("/forget")
	public ModelAndView update(@RequestParam String newPas, @RequestParam String reNewPas, HttpServletRequest req) {
	    ModelAndView modelAndView = new ModelAndView();

	    if (newPas.equals(reNewPas)) {
	        HttpSession session = req.getSession(false);

	        if (session != null) {
	            String sessionUniqueId = (String) session.getAttribute("uniqueId");

	                User user = userRepo.findByEmail((String) session.getAttribute("email"));
	                user.setPassword(newPas);
	                userRepo.save(user);
	                modelAndView.setViewName("login");
	        } else {
	            modelAndView.addObject("error", "Session expired. Please request a new OTP.");
	            modelAndView.setViewName("otpEnter");
	        }
	    } else {
	        modelAndView.addObject("error", "Passwords did not match.");
	        modelAndView.setViewName("updatePassword");
	    }

	    return modelAndView;
	}

}
