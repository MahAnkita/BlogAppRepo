package com.BikkadIT.Blog_Application.services;

import com.BikkadIT.Blog_Application.payloads.CategoryDto;
import com.BikkadIT.Blog_Application.payloads.CategoryResponse;

public interface CategoryServiceI {
	
	//create
	CategoryDto createCategory(CategoryDto categorydto);

	//Update
	CategoryDto updateCategory(CategoryDto categorydto, Integer categoryId );
	
	//delete by Id
	void deleteCategory(Integer categoryId);
	
	//get by Id
	CategoryDto getCategory(Integer categoryId );
	
	//get All categories
	CategoryResponse getAllCategories(Integer pageSize,Integer pageNumber, String sortBy,String sortDir);
}
