package com.BikkadIT.Blog_Application.payloads;

import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {

	private Integer postId;
	
	@NotEmpty
	@Size(min=4,  message="Minimum Size of post title shall be 4 characters")
	private String postTitle;

	@NotEmpty
	@Size(min=15,  message="Minimum Size of post Content shall be 15 characters")
	private String postContent;
	
	private String imageName;
	
	private Date addedDate;
	
	private CategoryDto category;
	
	private UserDto user;


}
