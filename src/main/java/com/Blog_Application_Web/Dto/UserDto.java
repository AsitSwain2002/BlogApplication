package com.Blog_Application_Web.Dto;


import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Valid
public class UserDto {

	private int id;
	@NotNull
	@Size(min = 4, max = 15, message = "Name Must between 4 and 15 letter")
	private String name;
	@NotNull
	private int age;
	@NotNull
	private long mobile;
	@Email
	private String email;
	@NotNull
	@Size(min = 4, max = 10, message = "Invalid Password")
	private String password;
	private String image;
}
