package com.Blog_Application_Web.serviceImpl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.Blog_Application_Web.Dto.CatagoryDto;
import com.Blog_Application_Web.Dto.CommentDto;
import com.Blog_Application_Web.Dto.PostDto;
import com.Blog_Application_Web.Dto.UserDto;
import com.Blog_Application_Web.ExceptionHandler.ResourceNotFound;
import com.Blog_Application_Web.Repo.PostRepo;
import com.Blog_Application_Web.Repo.UserRepo;
import com.Blog_Application_Web.entity.Catagory;
import com.Blog_Application_Web.entity.Post;
import com.Blog_Application_Web.entity.User;
import com.Blog_Application_Web.service.CatagoryService;
import com.Blog_Application_Web.service.PostService;
import com.Blog_Application_Web.service.UserService;
import com.Blog_Application_Web.utility.PageValueSetting;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepo postRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserService userServ;

	@Autowired
	private CatagoryService catagoryService;

	@Override
	public ModelAndView savePost(PostDto postDto, HttpServletRequest req, int postId, int catagoryId) {
		ModelAndView mod = new ModelAndView();
		UserDto userDto = userServ.findUserById(postId);
		Post post = modelMapper.map(postDto, Post.class);
        CatagoryDto catagory = catagoryService.findById(catagoryId);
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImage("default.jpg");
		post.setDate(new Date());
		post.setUser(modelMapper.map(userDto, User.class));
		post.setCatagory(modelMapper.map(catagory, Catagory.class));
		postRepo.save(post);
		mod.addObject("post", post);
		Integer pageNumber = PageValueSetting.pageNumber;
		Integer pageSize = PageValueSetting.pageSize;
		String sortBy = PageValueSetting.sortBy;

		// Directly call the method within the same service
		allPOst(req, pageNumber, pageSize, sortBy);
		mod.setViewName("home");
		return mod;
	}

	public PostDto fetchPostById(int postId) {
		Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFound("Post Not Found"));
		return modelMapper.map(post, PostDto.class);
	}

	@Override
	public ModelAndView updatePost(PostDto postDto, int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deletePost(int id) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<PostDto> allPOst(HttpServletRequest req, Integer pageNumber, Integer pageSize, String sortBy) {
		Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).descending());

		Page<Post> findAll = postRepo.findAll(pageable);
		List<Post> content = findAll.getContent();
		List<PostDto> collect = content.stream().map((e) -> modelMapper.map(e, PostDto.class))
				.collect(Collectors.toList());
		HttpSession sessionListPostHttpSession = req.getSession(false);
		sessionListPostHttpSession.setAttribute("sessionListPostHttpSession", collect);
		return collect;
	}

	@Override
	public List<PostDto> allPostByUser(int userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PostDto> search(String keyword) {
		List<Post> searchBytitle = postRepo.searchBytitle("%" + keyword + "%");
		return searchBytitle.stream().map((e) -> modelMapper.map(e, PostDto.class)).collect(Collectors.toList());

	}

}
