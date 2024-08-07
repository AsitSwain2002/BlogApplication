package com.Blog_Application_Web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.Blog_Application_Web.Dto.UserDto;
import com.Blog_Application_Web.service.PostService;
import com.Blog_Application_Web.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class UserController {

	@Autowired
	private UserService userServ;;

	@GetMapping("/registerPage")
	public String showRegisterForm() {
		return "register";
	}

	@PostMapping("/register")
	public RedirectView register(@Valid @ModelAttribute UserDto userDto) {
		return userServ.saveUser(userDto);
	}

	@GetMapping("/loginPage")
	public String showLogin() {
		return "login";
	}

	@PostMapping("/login")
	public ModelAndView login(@RequestParam String email, @RequestParam String password, HttpSession session,
			HttpServletRequest req) {
		return userServ.login(email, password, session, req);
	}

	@GetMapping("/logoutPage")
	public String logoutPage() {
		return "logout";
	}

	@GetMapping("/logout")
	public String logOut(HttpServletRequest req) {
		HttpSession session = req.getSession(false);
		session.removeAttribute("sessionUser");
		return "login";
	}
	
	@GetMapping("/forgotPage")
	public String forget() {
		return "forgetPassword";
	}


}