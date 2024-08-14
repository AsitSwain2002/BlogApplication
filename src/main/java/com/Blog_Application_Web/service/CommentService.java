package com.Blog_Application_Web.service;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.Blog_Application_Web.Dto.CommentDto;
import com.Blog_Application_Web.Dto.UserDto;

@Repository
public interface CommentService {

	public String saveComment(CommentDto commentDto, int userId, int postId);

	public List<CommentDto> fetchAllCommentBy(int postId);
	
	public List<CommentDto> fetchUserByCommment(int userId);
	
	public void deleteCommentById(int id);
	

}
