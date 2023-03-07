package com.BikkadIT.Blog_Application.controllers;



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
import com.BikkadIT.Blog_Application.payloads.UserDto;
import com.BikkadIT.Blog_Application.payloads.UserResponse;
import com.BikkadIT.Blog_Application.services.UserService;

@RestController
@RequestMapping("/api/")
public class UserController {
	
	Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;
	
	
	/**
	 * @author Ankita
	 * @apiNote save the user details
	 * @since 1.0
	 * @param userdto
	 * @return
	 */
	
	//POST- Create user
	@PostMapping("/users")
	public ResponseEntity<UserDto> saveUser(@Valid @RequestBody UserDto userdto){
	
		logger.info("Initiating service call for save user");
		
		UserDto dto = this.userService.createUser(userdto);
		
		logger.info("Completed service call for save user");
		return new ResponseEntity<UserDto>(dto,HttpStatus.CREATED);
	}
	
	/**
	 * /**
	 * @author Ankita
	 * @apiNote Update the user details
	 * @since 1.0
	 * @param userdto, userId 
	 * @return
	 */
	
	
	//PUT- Update User
	@PutMapping("/users/{userId}")
	public ResponseEntity<UserDto>updateUserbyId(@Valid @RequestBody UserDto userdto, @PathVariable Integer userId){
		
		logger.info("Initiating service call for update user with:{}", userId);
		UserDto updateUser = this.userService.updateUser(userdto, userId);
		logger.info("completed service call for update user");
		
		return ResponseEntity.ok(updateUser);
		}
	
	/**
	 * @author Ankita
	 * @apiNote delete the user details
	 * @since 1.0
	 * @param userId
	 * 
	 */
	//Delete-Delete User
	@DeleteMapping("/users/{userId}")
	public ResponseEntity<ApiResponse>deleteUserbyId(@PathVariable Integer userId){
		
		logger.info("Initiating service call for delete user");
		this.userService.deleteUser(userId);
		logger.info("completed service call for delete user");
		
		//return new ResponseEntity(Map.of("message","User deleted Successfully"),HttpStatus.OK);
		return new ResponseEntity<ApiResponse>(new ApiResponse(AppConstants.SUCCESS,true),HttpStatus.OK);
	}
	
	/**
	 * @author Ankita
	 * @apiNote To get the user by userId
	 * @since 1.0
	 * @param userId
	 * @return
	 */
	//Get- Get User by id
	@GetMapping("/users/{userId}")
	public ResponseEntity<UserDto>getUser(@PathVariable Integer userId){
		
		logger.info("Initiating service call for find user by userId");
		UserDto userById = this.userService.getUserById(userId);
		logger.info("completed service call for find user by userId");
		
		return ResponseEntity.ok(userById);
	}
	
	
	/**
	 * @author Ankita
	 * @apiNote Get all users list
	 * @since 1.0
	 * @return
	 */
	//Get- Get All Users List
	@GetMapping("/users")
	public ResponseEntity<UserResponse>findAllUsers(
			@RequestParam( value="pageSize",defaultValue=AppConstants.PAGE_SIZE, required=false)Integer pageSize,
			@RequestParam( value="pageNumber",defaultValue=AppConstants.PAGE_NUMBER, required=false)Integer pageNumber,
			@RequestParam( value="sortBy",defaultValue=AppConstants.SORT_BY_UID, required=false)String sortBy,
			@RequestParam( value="sortDir",defaultValue=AppConstants.SORT_DIR, required=false)String sortDir){
		
		logger.info("Initiating service call for find all users");
		UserResponse allUsers = this.userService.getAllUsers(pageSize,pageNumber,sortBy,sortDir);
		logger.info("completed service call for find all users ");
		
		return ResponseEntity.ok(allUsers);
	}
}
