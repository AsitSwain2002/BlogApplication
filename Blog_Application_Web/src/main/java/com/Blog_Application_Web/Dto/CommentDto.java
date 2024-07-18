package com.Blog_Application_Web.Dto;

import java.time.LocalDate;

import com.Blog_Application_Web.entity.User;

import lombok.Data;

@Data
public class CommentDto {
	private int id;
	private String comment;
	private LocalDate date;
	private User user;
}
