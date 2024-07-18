package com.Blog_Application_Web.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import com.Blog_Application_Web.Dto.PostDto;
import com.Blog_Application_Web.Dto.UserDto;
import com.Blog_Application_Web.ExceptionHandler.ResourceNotFound;
import com.Blog_Application_Web.Repo.UserRepo;
import com.Blog_Application_Web.entity.User;
import com.Blog_Application_Web.service.PostService;
import com.Blog_Application_Web.service.UserService;
import com.Blog_Application_Web.utility.PageValueSetting;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private PostService postService;

	@Override
	public ModelAndView saveUser(UserDto userDto) {
		ModelAndView mod = new ModelAndView();
		User user = modelMapper.map(userDto, User.class);
		user.setId(userDto.getId());
		user.setName(userDto.getName());
		user.setAge(userDto.getAge());
		user.setMobile(userDto.getMobile());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		userRepo.save(user);
		mod.setViewName("register");
		mod.addObject("success", "Registration Successful");
		return mod;
	}

	@Override
	public ModelAndView login(String email, String password, HttpSession session, HttpServletRequest req) {
		ModelAndView modelAndView = new ModelAndView();
		User user = userRepo.findByEmail(email);
		if (user.getEmail().equals(email)) {
			if (user.getPassword().equals(password)) {
				session.setAttribute("sessionUser", user);
				Integer pageNumber = PageValueSetting.pageNumber;
				Integer pageSize = PageValueSetting.pageSize;
				String sortBy = PageValueSetting.sortBy;
				Integer count = 0;
				session.setAttribute("pageNumber", pageNumber);
				session.setAttribute("pageSize", pageSize);
				session.setAttribute("sortBy", sortBy);
				session.setAttribute("count", count);
				List<PostDto> allPOst = postService.allPOst(req, pageNumber, pageSize, sortBy);
				session.setAttribute("sessionListPostHttpSession", allPOst);
				modelAndView.setViewName("home");
			} else {
				modelAndView.addObject("PasWrong", "invalid Password");
				modelAndView.setViewName("login");
			}
		} else {
			modelAndView.addObject("EmailWrong", "invalid Email");
			modelAndView.setViewName("login");
		}

		return modelAndView;
	}

	public UserDto findUserById(int id) {
		 User user = userRepo.findById(id).orElseThrow(()->new ResourceNotFound("User Not  Found"));
		 UserDto userDto = modelMapper.map(user, UserDto.class);
		 return userDto;
	}

}
