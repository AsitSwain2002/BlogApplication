package com.Blog_Application_Web.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Blog_Application_Web.Dto.CatagoryDto;
import com.Blog_Application_Web.Dto.PostDto;
import com.Blog_Application_Web.ExceptionHandler.ResourceNotFound;
import com.Blog_Application_Web.Repo.CatagoryRepo;
import com.Blog_Application_Web.entity.Catagory;
import com.Blog_Application_Web.entity.Post;
import com.Blog_Application_Web.service.CatagoryService;

@Service
public class CatagoryServiceImpl implements CatagoryService{


	@Autowired
	private CatagoryRepo catagoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	@Override
	public String saveCatagory(CatagoryDto catagoryDto) {
		catagoryRepo.save(modelMapper.map(catagoryDto, Catagory.class));
		return "Catagory Saved Successfully";
	}
	@Override
	public List<CatagoryDto> fetchAllCatagory() {
		List<Catagory> catagories = catagoryRepo.findAll();
		return  catagories.stream().map((e)->modelMapper.map(e, CatagoryDto.class)).collect(Collectors.toList());
	}
	@Override
	public CatagoryDto findById(int id) {
		Catagory catagory = catagoryRepo.findById(id).orElseThrow(() -> new ResourceNotFound("Catagory Not found"));
		return modelMapper.map(catagory, CatagoryDto.class);
	}
	@Override
	public List<PostDto> fetchPostByCatagory(int catagoryId) {
		List<Post> posts = catagoryRepo.findPostByCatagoryId(catagoryId);
		return posts.stream().map((e) -> modelMapper.map(e, PostDto.class)).collect(Collectors.toList());
	}

}
