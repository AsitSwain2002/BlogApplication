package com.Blog_Application_Web.serviceImpl;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Blog_Application_Web.Dto.CommentDto;
import com.Blog_Application_Web.Dto.PostDto;
import com.Blog_Application_Web.Dto.UserDto;
import com.Blog_Application_Web.ExceptionHandler.ResourceNotFound;
import com.Blog_Application_Web.Repo.CommentRepo;
import com.Blog_Application_Web.Repo.UserRepo;
import com.Blog_Application_Web.entity.Comments;
import com.Blog_Application_Web.entity.Post;
import com.Blog_Application_Web.entity.User;
import com.Blog_Application_Web.service.CommentService;
import com.Blog_Application_Web.service.PostService;
import com.Blog_Application_Web.service.UserService;

@Service
public class CommentServImpl implements CommentService {

	@Autowired
	private CommentRepo commentRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserService userService;
	
	@Autowired
	private PostService postServ;

	@Override
	public String saveComment(CommentDto commentDto, int userId, int postId) {
        
		PostDto post = postServ.fetchPostById(postId);
		UserDto user = userService.findUserById(userId);
		Comments comment = modelMapper.map(commentDto, Comments.class);
		comment.setComment(commentDto.getComment());
		comment.setDate(LocalDate.now());
		comment.setUser(modelMapper.map(user, User.class));
		comment.setPost(modelMapper.map(post, Post.class));
		commentRepo.save(comment);
		return "Commented Successfully";
	}

	@Override
	public List<CommentDto> fetchAllCommentBy(int postId) {
		PostDto post = postServ.fetchPostById(postId);
		List<Comments> commentList = commentRepo.findAllCommentByPost(modelMapper.map(post, Post.class));
		return commentList.stream().map((e)->modelMapper.map(e, CommentDto.class)).collect(Collectors.toList());
	}

	@Override
	public List<CommentDto> fetchUserByCommment(int userId) {
		UserDto user = userService.findUserById(userId);
		User userDto = modelMapper.map(user, User.class);
		List<Comments> findAllCommentByUser = commentRepo.findAllCommentByUser(userDto);
		return findAllCommentByUser.stream().map((e)->modelMapper.map(e,  CommentDto.class)).collect(Collectors.toList());
	}

	@Override
	public void deleteCommentById(int id) {
		Comments comment = commentRepo.findById(id).orElseThrow(()->new ResourceNotFound("Comment Not Found"));
		commentRepo.delete(comment);
	    System.out.println("Comment ID to delete: ===========================================================================================" + id);
	}

}
