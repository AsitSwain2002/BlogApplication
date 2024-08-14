package com.Blog_Application_Web.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
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

	@Value("${image.path}")
	private String path;

	@GetMapping("/addPostPage")
	public ModelAndView postPage() {
		ModelAndView modelAndView = new ModelAndView();
		List<CatagoryDto> fetchAllCatagory = catagoryService.fetchAllCatagory();
		modelAndView.addObject("catagories", fetchAllCatagory);
		modelAndView.setViewName("addPost");
		return modelAndView;
	}

	@PostMapping("/savePost")
	public RedirectView savePost(@ModelAttribute PostDto postDto, HttpServletRequest request, @RequestParam int postId,
			@RequestParam int catagory , @RequestParam MultipartFile image) throws IOException {
		
	  postService.savePost(postDto, request, postId, catagory, image , path);

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
		int size = allPosts.size();
		session.setAttribute("sessionListPostHttpSession", allPosts);
		session.setAttribute("postSize", size);
		modelAndView.setViewName("home");
		return modelAndView;
	}

	@GetMapping("/next")
	public ModelAndView nextPage(HttpSession session, HttpServletRequest req) {
		Integer pageNumber = (Integer) session.getAttribute("pageNumber");
		Integer pageSize = (Integer) session.getAttribute("pageSize");
		String sortBy = (String) session.getAttribute("sortBy");
		Integer count = (Integer) session.getAttribute("count");

		pageNumber = (pageNumber == null) ? 0 : pageNumber;
		pageNumber++;

		List<PostDto> allPost = postService.allPOst(req, pageNumber, pageSize, sortBy);
		session.setAttribute("pageNumber", pageNumber);
		int size = allPost.size();
		session.setAttribute("sessionListPostHttpSession", allPost);
		session.setAttribute("postSize", size);
		session.setAttribute("count", count + 1); // Increment count or set based on logic

		return new ModelAndView("home");
	}

	@GetMapping("/back")
	public ModelAndView previousPage(HttpSession session, HttpServletRequest req) {
		Integer pageNumber = (Integer) session.getAttribute("pageNumber");
		Integer pageSize = (Integer) session.getAttribute("pageSize");
		String sortBy = (String) session.getAttribute("sortBy");
		Integer count = (Integer) session.getAttribute("count");
		if (count < 0) {
			pageNumber = 0;
		}

		pageNumber = (pageNumber == null || pageNumber <= 0) ? 0 : pageNumber - 1;

		List<PostDto> allPost = postService.allPOst(req, pageNumber, pageSize, sortBy);
		session.setAttribute("pageNumber", pageNumber);
		int size = allPost.size();
		session.setAttribute("sessionListPostHttpSession", allPost);
		session.setAttribute("postSize", size);
		session.setAttribute("count", count - 1); // Decrement count or set based on logic

		return new ModelAndView("home");
	}

	@GetMapping("/postPage")
	public ModelAndView fullPostPage(@RequestParam int id, HttpServletRequest req) {
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
		HttpSession session = req.getSession(false);
		List<PostDto> search1 = postService.search(search);
		session.setAttribute("sessionListPostHttpSession", search1);
		modelAndView.setViewName("home");
		return modelAndView;
	}

	@GetMapping("/profile")
	public ModelAndView profilePage() {
		ModelAndView modelAndView = new ModelAndView("profile");
		return modelAndView;
	}

	@GetMapping("/allPostByuser")
	public ModelAndView fetchAllPostByUser(@RequestParam int id, HttpServletRequest req) {
		ModelAndView modelAndView = new ModelAndView();
		List<PostDto> allPostByUser = postService.allPostByUser(id);
		int postSize = allPostByUser.size();
		HttpSession session = req.getSession(false);
		session.setAttribute("UserAllPost", allPostByUser);
		session.setAttribute("postSize", postSize);
		modelAndView.setViewName("allPostUser");
		return modelAndView;
	}

	@GetMapping("/updatePostPage")
	public ModelAndView showUpdatePage(@RequestParam int id) {
		PostDto post = postService.fetchPostById(id);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("post", post);
		modelAndView.setViewName("updatePost");
		return modelAndView;
	}

//	
//		
	@PostMapping("/updatePost")
	public ModelAndView updatePost(@ModelAttribute PostDto postDto, @RequestParam int id) {
		ModelAndView modelAndView = new ModelAndView();
		postService.updatePost(postDto, id);
		modelAndView.setViewName("allPostUser");
		return modelAndView;
	}

	@GetMapping("/deletePost")
	public ModelAndView deleteById(@RequestParam int id, @RequestParam Integer userId, HttpServletRequest req) {
		ModelAndView modelAndView = new ModelAndView();
		postService.deletePost(id);
		List<PostDto> allPostByUser = postService.allPostByUser(userId);
		HttpSession session = req.getSession(false);
		session.setAttribute("UserAllPost", allPostByUser);
		modelAndView.setViewName("allPostUser");
		return modelAndView;
	}
}
