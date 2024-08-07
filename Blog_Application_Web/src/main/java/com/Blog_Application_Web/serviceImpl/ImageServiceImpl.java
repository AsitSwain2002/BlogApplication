package com.Blog_Application_Web.serviceImpl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.Blog_Application_Web.Dto.PostDto;
import com.Blog_Application_Web.Repo.PostRepo;
import com.Blog_Application_Web.entity.Post;
import com.Blog_Application_Web.service.ImageService;

import lombok.extern.java.Log;

@Service
public class ImageServiceImpl implements ImageService {

	@Override
	public String uploadImage(MultipartFile image, String path) throws IOException {
       
		String name = image.getOriginalFilename();
		System.out.println("================================================================="+name);
		String filePath = path + File.separator + image;
		File file2 = new File(path);
		if(!file2.exists()) {
			file2.mkdir();
		}
		Files.copy(image.getInputStream(), Paths.get(filePath));
		return name;
	} 

}
