package com.Blog_Application_Web.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.Blog_Application_Web.Dto.PostDto;

public interface ImageService {

	String uploadImage(MultipartFile file , String path) throws IOException;
}
