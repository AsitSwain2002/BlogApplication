package com.Blog_Application_Web.Dto;

import java.util.Date;
import java.util.List;


import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PostDto {

	private int id;
	private String title;
	@Size(max = 20000)
	private String content;
	private String image;
	private Date date;
	private List<CommentDto> comments;
}
