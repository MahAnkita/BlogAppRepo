package com.BikkadIT.Blog_Application.services;

import com.BikkadIT.Blog_Application.payloads.UserDto;
import com.BikkadIT.Blog_Application.payloads.UserResponse;

public interface UserService {
	
	 UserDto createUser(UserDto userdto);
	 
	 UserDto updateUser(UserDto user, Integer userId);
	 
	 UserDto getUserById(Integer userId);
	 
	 UserResponse getAllUsers(Integer pageSize,Integer pageNumber, String sortBy,String sortDir);
	 
	 void deleteUser(Integer userId);
	

}
