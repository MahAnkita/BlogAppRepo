package com.BikkadIT.Blog_Application.controllers;

//import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.BikkadIT.Blog_Application.constants.AppConstants;
import com.BikkadIT.Blog_Application.payloads.ApiResponse;
import com.BikkadIT.Blog_Application.payloads.CategoryDto;
import com.BikkadIT.Blog_Application.payloads.CategoryResponse;
import com.BikkadIT.Blog_Application.services.CategoryServiceI;

@RestController
@RequestMapping("/api")
public class CategoryController {

	
	Logger logger = LoggerFactory.getLogger(CategoryController.class);
	
	@Autowired
	private CategoryServiceI categoryServiceI;
	
	/**
	 * @author Ankita
	 * @apiNote save the Category details
	 * @since 1.0
	 * @param categorydto
	 * @return
	 */
	
	//POST- Create Category
		@PostMapping("/categories")
		public ResponseEntity<CategoryDto> saveCategory(@Valid @RequestBody CategoryDto categoryDto){
		
			logger.info("Initiating service call for save category");
			
			CategoryDto createCategory = this.categoryServiceI.createCategory(categoryDto);
			
			logger.info("Completed service call for save category");
			
			return new ResponseEntity<CategoryDto>(createCategory,HttpStatus.CREATED);
		}
		
		/**
		 * @author Ankita
		 * @apiNote Update the Category details
		 * @since 1.0
		 * @param categorydto
		 * @param categoryId
		 * @return
		 */
		
		//PUT- Update Category
		@PutMapping("/categories/{categoryId}")
		public ResponseEntity<CategoryDto>updateCategorybyId(@Valid @RequestBody CategoryDto categoryDto, @PathVariable Integer categoryId){
			
			logger.info("Initiating service call for update Category with:{}",categoryId);
			
			CategoryDto updateCategory = this.categoryServiceI.updateCategory(categoryDto, categoryId);
			
			logger.info("completed service call for update Category");
			
			return ResponseEntity.ok(updateCategory);
			}

		/**
		 * @author Ankita
		 * @apiNote delete the Category details
		 * @since 1.0
		 * @param categoryId
		 * 
		 */
		
		//Delete-Delete category
		@DeleteMapping("/categories/{categoryId}")
		public ResponseEntity<ApiResponse>deleteCategorybyId(@PathVariable Integer categoryId){
			
			logger.info("Initiating service call for delete category");

			this.categoryServiceI.deleteCategory(categoryId);
			
			logger.info("completed service call for delete category");
		
			return new ResponseEntity<ApiResponse>(new ApiResponse(AppConstants.SUCCESS,true),HttpStatus.OK);
		}
		

		/**
		 * @author Ankita
		 * @apiNote Get the Category by Id
		 * @since 1.0
		 * @param categoryId
		 * @return
		 */
		
		//Get- Get category by id
		@GetMapping("/categories/{categoryId}")
		public ResponseEntity<CategoryDto>getCategory(@PathVariable Integer categoryId){
			
			logger.info("Initiating service call for find user by category");
			
			CategoryDto category = this.categoryServiceI.getCategory(categoryId);
			
			logger.info("completed service call for find user by category");
			
			return ResponseEntity.ok(category);
		}
		

		/**
		 * @author Ankita
		 * @apiNote Get the list of all Categories 
		 * @since 1.0
		 * @return
		 */
		//Get- Get All category List
		@GetMapping("/categories")
		public ResponseEntity<CategoryResponse>findAllCategories(
				@RequestParam( value="pageSize",defaultValue=AppConstants.PAGE_SIZE, required=false)Integer pageSize,
				@RequestParam( value="pageNumber",defaultValue=AppConstants.PAGE_NUMBER, required=false)Integer pageNumber,
				@RequestParam( value="sortBy",defaultValue=AppConstants.SORT_BY_CID, required=false)String sortBy,
				@RequestParam( value="sortDir",defaultValue=AppConstants.SORT_DIR, required=false)String sortDir){
			
			logger.info("Initiating service call for find all categories");
			CategoryResponse allCategories = this.categoryServiceI.getAllCategories(pageSize,pageNumber,sortBy,sortDir);
			logger.info("completed service call for find all categories ");
			
			return ResponseEntity.ok(allCategories);
		}
}
