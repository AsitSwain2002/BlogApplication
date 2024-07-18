package com.Blog_Application_Web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import com.Blog_Application_Web.Dto.CatagoryDto;
import com.Blog_Application_Web.service.CatagoryService;
import com.Blog_Application_Web.utility.PageValueSetting;

@Controller
public class CatagoryController {

	@Autowired
	private CatagoryService catagoryService;

	@GetMapping("/addCatagory")
	public String addCatagory() {
		return "addCatagory";
	}

	@PostMapping("/saveCatagory")
	public RedirectView saveCatagory(@ModelAttribute CatagoryDto catagoryDto) {
		catagoryService.saveCatagory(catagoryDto);
		Integer pageNumber = PageValueSetting.pageNumber;
	    Integer pageSize = PageValueSetting.pageSize;
	    String sortBy = PageValueSetting.sortBy;
	    
	    String redirectUrl = String.format("/home?pageNumber=%d&pageSize=%d&sortBy=%s", pageNumber, pageSize, sortBy);
	    return new RedirectView(redirectUrl);
	}
}
