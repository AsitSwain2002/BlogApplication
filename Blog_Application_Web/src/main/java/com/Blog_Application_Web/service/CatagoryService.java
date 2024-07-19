package com.Blog_Application_Web.service;

import java.util.List;

import com.Blog_Application_Web.Dto.CatagoryDto;
import com.Blog_Application_Web.Dto.PostDto;

public interface CatagoryService {

	public String saveCatagory(CatagoryDto catagoryDto);
	
	public List<CatagoryDto> fetchAllCatagory();
	
	public CatagoryDto findById(int id);
	
	public List<PostDto> fetchPostByCatagory(int catagoryId);
}
