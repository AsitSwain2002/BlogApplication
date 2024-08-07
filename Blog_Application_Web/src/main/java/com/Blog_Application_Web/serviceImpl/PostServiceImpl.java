package com.Blog_Application_Web.serviceImpl;

import java.io.IOException;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.Blog_Application_Web.Dto.CatagoryDto;
import com.Blog_Application_Web.Dto.PostDto;
import com.Blog_Application_Web.Dto.UserDto;
import com.Blog_Application_Web.ExceptionHandler.ResourceNotFound;
import com.Blog_Application_Web.Repo.PostRepo;
import com.Blog_Application_Web.Repo.UserRepo;
import com.Blog_Application_Web.entity.Catagory;
import com.Blog_Application_Web.entity.Post;
import com.Blog_Application_Web.entity.User;
import com.Blog_Application_Web.service.CatagoryService;
import com.Blog_Application_Web.service.ImageService;
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
	private UserRepo userRepo;
	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserService userServ;  
	
	@Autowired
    private ImageService imgServ;
	
	@Autowired
	private CatagoryService catagoryService;

	@Override
	public ModelAndView savePost(PostDto postDto, HttpServletRequest req, int postId, int catagoryId , MultipartFile image , String path) throws IOException {
		ModelAndView mod = new ModelAndView();
		UserDto userDto = userServ.findUserById(postId);
		Post post = modelMapper.map(postDto, Post.class);
        CatagoryDto catagory = catagoryService.findById(catagoryId);
        
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		//Called the image service  method
		String uploadImage = imgServ.uploadImage(image, path);
		post.setImage(uploadImage);
		
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
	public PostDto updatePost(PostDto postDto, int id) {
		Post post = postRepo.findById(id).orElseThrow(() -> new ResourceNotFound("Post Not found"));
		post.setTitle(postDto.getTitle());
		post.setCatagory(postDto.getCatagory());
		post.setContent(postDto.getContent());
		post.setImage(postDto.getImage());
		
		Post savePost = postRepo.save(post);
		return modelMapper.map(savePost, PostDto.class);
	}

	@Override
	public void deletePost(int id) {
		Post post = postRepo.findById(id).orElseThrow(() -> new ResourceNotFound("Post Not Found"));
		postRepo.delete(post);
	}

	@Override
	public List<PostDto> allPOst(HttpServletRequest req, Integer pageNumber, Integer pageSize, String sortBy) {
		Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).descending());
		HttpSession sessionListPostHttpSession = req.getSession(false);
		Page<Post> findAll = postRepo.findAll(pageable);
		List<Post> content = findAll.getContent();
		List<PostDto> collect = content.stream().map((e) -> modelMapper.map(e, PostDto.class))
				.collect(Collectors.toList());
		
		sessionListPostHttpSession.setAttribute("sessionListPostHttpSession", collect);
		return collect;
	}

	@Override
	public List<PostDto> allPostByUser(int userId) {
		userRepo.findById(userId).orElseThrow(() -> new ResourceNotFound("User Not Found"));
		List<Post> post = postRepo.allPostByUser(userId);
		List<PostDto> collect = post.stream().map((e) -> modelMapper.map(e, PostDto.class)).collect(Collectors.toList());
		return collect;
	}

	@Override
	public List<PostDto> search(String keyword) {
		List<Post> searchBytitle = postRepo.searchBytitle("%" + keyword + "%");
		return searchBytitle.stream().map((e) -> modelMapper.map(e, PostDto.class)).collect(Collectors.toList());

	}


}
