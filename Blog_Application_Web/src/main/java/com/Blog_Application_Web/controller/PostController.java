package com.Blog_Application_Web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.Blog_Application_Web.Dto.CatagoryDto;
import com.Blog_Application_Web.Dto.CommentDto;
import com.Blog_Application_Web.Dto.PostDto;
import com.Blog_Application_Web.service.CatagoryService;
import com.Blog_Application_Web.service.PostService;
import com.Blog_Application_Web.utility.PageValueSetting;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class PostController {

	@Autowired
	private PostService postService;
	
	@Autowired
	private CatagoryService catagoryService;

	@GetMapping("/addPostPage")
	public ModelAndView postPage() {
		ModelAndView modelAndView = new ModelAndView();
		List<CatagoryDto> fetchAllCatagory = catagoryService.fetchAllCatagory();
		modelAndView.addObject("catagories", fetchAllCatagory);
		modelAndView.setViewName( "addPost");
		return modelAndView;
	}

	@PostMapping("/savePost")
	public RedirectView savePost(@ModelAttribute PostDto postDto, HttpServletRequest request, @RequestParam int postId , @RequestParam int catagory) {
	    postService.savePost(postDto, request, postId , catagory);
	    
	    // Add necessary parameters for the redirect
	    Integer pageNumber = PageValueSetting.pageNumber;
	    Integer pageSize = PageValueSetting.pageSize;
	    String sortBy = PageValueSetting.sortBy;
	    
	    String redirectUrl = String.format("/home?pageNumber=%d&pageSize=%d&sortBy=%s", pageNumber, pageSize, sortBy);
	    return new RedirectView(redirectUrl);
	}

	@GetMapping("/home")
	public ModelAndView home(HttpServletRequest request, @RequestParam Integer pageNumber,
			@RequestParam Integer pageSize, @RequestParam String sortBy) {
		ModelAndView modelAndView = new ModelAndView();
		HttpSession session = request.getSession(false);
		List<PostDto> allPosts = postService.allPOst(request, pageNumber, pageSize, sortBy);

		modelAndView.addObject("sessionListPostHttpSession", allPosts);

		session.setAttribute("sessionListPostHttpSession", allPosts);
		Integer sessionCount = (Integer) session.getAttribute("count");
		Integer countInc = sessionCount + 1;
		session.setAttribute("count", countInc);
		modelAndView.setViewName("home");
		return modelAndView;
	}

	@GetMapping("/back")
	public ModelAndView backPage(HttpServletRequest request, @RequestParam Integer pageNumber,
			@RequestParam Integer pageSize, @RequestParam String sortBy) {
		ModelAndView modelAndView = new ModelAndView();
		HttpSession session = request.getSession(false);
		List<PostDto> allPosts = postService.allPOst(request, pageNumber, pageSize, sortBy);

		modelAndView.addObject("sessionListPostHttpSession", allPosts);

		session.setAttribute("sessionListPostHttpSession", allPosts);
		Integer count = (Integer) session.getAttribute("count");
		Integer resetCount = count - 1;
		session.setAttribute("resetCount", resetCount);
		modelAndView.setViewName("home");
		return modelAndView;
	}

	@GetMapping("/postPage")
	public ModelAndView fullPostPage(@RequestParam int id , HttpServletRequest req) {
		ModelAndView modelAndView = new ModelAndView();
		PostDto postDto = postService.fetchPostById(id);
		List<CommentDto> comments = postDto.getComments();
		HttpSession session = req.getSession(false);
		session.setAttribute("comments", comments);
		session.setAttribute("post", postDto);
		modelAndView.setViewName("postPage");
		return modelAndView;
	}

	@GetMapping("/search")
	public ModelAndView search(@RequestParam String search, HttpServletRequest req) {
		ModelAndView modelAndView = new ModelAndView();
		System.out.println("value :" + search);
		HttpSession session = req.getSession(false);
		List<PostDto> search1 = postService.search(search);
		session.setAttribute("sessionListPostHttpSession", search1);
		modelAndView.setViewName("home");
		return modelAndView;
	}
}
