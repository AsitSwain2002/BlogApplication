package com.Blog_Application_Web.service;

import org.springframework.web.servlet.ModelAndView;

import com.Blog_Application_Web.Dto.UserDto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public interface UserService {

	public ModelAndView saveUser(UserDto userDto);

	public ModelAndView login(String email, String password, HttpSession session, HttpServletRequest req);

	public UserDto findUserById(int id);
}
