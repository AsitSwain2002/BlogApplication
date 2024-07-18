package com.Blog_Application_Web.service;

import java.util.List;

import com.Blog_Application_Web.Dto.CatagoryDto;

public interface CatagoryService {

	public String saveCatagory(CatagoryDto catagoryDto);
	
	public List<CatagoryDto> fetchAllCatagory();
	
	public CatagoryDto findById(int id);
}
