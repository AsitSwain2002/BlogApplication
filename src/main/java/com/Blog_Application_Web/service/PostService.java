package com.Blog_Application_Web.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.Blog_Application_Web.Dto.PostDto;

import jakarta.servlet.http.HttpServletRequest;

public interface PostService {

	ModelAndView savePost(PostDto postDto, HttpServletRequest req, int postId, int catagoryId, MultipartFile file , String path) throws IOException;

	PostDto updatePost(PostDto postDto, int id);

	public PostDto fetchPostById(int postId);

	void deletePost(int id);

	List<PostDto> allPOst(HttpServletRequest req, Integer pageNumber, Integer pageSize, String sortBy);

	List<PostDto> allPostByUser(int userId);

	public List<PostDto> search(String keyword);

}
