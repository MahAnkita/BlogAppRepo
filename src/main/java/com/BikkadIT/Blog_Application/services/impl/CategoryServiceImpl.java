package com.BikkadIT.Blog_Application.services.impl;

import java.util.List;

import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import com.BikkadIT.Blog_Application.entities.Category;
import com.BikkadIT.Blog_Application.exceptions.ResourceNotFoundException;
import com.BikkadIT.Blog_Application.payloads.CategoryDto;
import com.BikkadIT.Blog_Application.payloads.CategoryResponse;
import com.BikkadIT.Blog_Application.repositories.CategoryRepo;
import com.BikkadIT.Blog_Application.services.CategoryServiceI;

@Service
public class CategoryServiceImpl implements CategoryServiceI{

	Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);
	
	@Autowired
	private CategoryRepo repo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CategoryDto createCategory(CategoryDto categorydto) {

		logger.info("Initiating dao call for save category");
		Category cat = this.modelMapper.map(categorydto, Category.class);
		Category addedCat = this.repo.save(cat);
		logger.info("completed dao call for save category");
		
		return this.modelMapper.map(addedCat,CategoryDto.class );
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categorydto, Integer categoryId) {
		
		logger.info("Initiating dao call for update category with:{}",categoryId);
		 Category cat = this.repo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","CategoryId",categoryId));
		 
		 cat.setCategoryTitle(categorydto.getCategoryTitle());
		 cat.setCategoryDescription(categorydto.getCategoryDescription());
		 
		 Category Updatedcat = this.repo.save(cat);
		 
		 logger.info("completed dao call for update category");
		 
		 return this.modelMapper.map(Updatedcat, CategoryDto.class);
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		
		logger.info("Initiating dao call for delete category");
		 Category cat = this.repo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","CategoryId",categoryId));
		 this.repo.delete(cat);
		 logger.info("completed dao call for delete category");
	}

	@Override
	public CategoryDto getCategory(Integer categoryId) {
		
		logger.info("Initiating dao call for get category by ID");
		Category cat = this.repo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","CategoryId",categoryId));
		logger.info("completed dao call for get category by ID");
		
		return this.modelMapper.map(cat, CategoryDto.class);
	}

	@Override
	public CategoryResponse getAllCategories(Integer pageSize,Integer pageNumber,String sortBy, String sortDir) {
		
		logger.info("Initiating dao call for Delete All categories");
		
		Sort sort=(sortDir.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending():Sort.by(sortBy).descending()) ;
		
		Pageable of = PageRequest.of(pageNumber, pageSize,sort);
		Page <Category> pageCat =this.repo.findAll(of);
		List<Category> allcats = pageCat.getContent();
		
		//List<Category> list = this.repo.findAll();
		List<CategoryDto> collect = allcats.stream().map((cat)->this.modelMapper.map(cat, CategoryDto.class)).collect(Collectors.toList());
		
		CategoryResponse categoryResponse= new CategoryResponse();
		
		categoryResponse.setContent(collect);
		categoryResponse.setPageNumber(pageCat.getNumber());
		categoryResponse.setPageSize(pageCat.getSize());
		categoryResponse.setTotalElements(pageCat.getTotalElements());
		categoryResponse.setTotalPages(pageCat.getTotalPages());
		categoryResponse.setLastPage(pageCat.isLast());
		
		logger.info("completed dao call for get all categories");
		
		return categoryResponse;
	}

}
