package com.BikkadIT.Blog_Application.payloads;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {
	
	private Integer categoryId;
	
	@NotEmpty
	@Size(min=4,  message="Minimum Size of title shall be 4 characters")
	private String categoryTitle;
	
	@NotEmpty
	@Size(min=10, message="Minimum Size of Description shall be 10 characters")
	private String categoryDescription;

}
