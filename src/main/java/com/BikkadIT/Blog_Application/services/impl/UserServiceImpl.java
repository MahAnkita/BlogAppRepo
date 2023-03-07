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

import com.BikkadIT.Blog_Application.controllers.UserController;
import com.BikkadIT.Blog_Application.entities.Category;
import com.BikkadIT.Blog_Application.entities.User;
import com.BikkadIT.Blog_Application.exceptions.ResourceNotFoundException;
import com.BikkadIT.Blog_Application.payloads.UserDto;
import com.BikkadIT.Blog_Application.payloads.UserResponse;
import com.BikkadIT.Blog_Application.repositories.UserRepository;
import com.BikkadIT.Blog_Application.services.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public UserDto createUser(UserDto userdto) {
		
		logger.info("Initiating dao call for save user");
		//User user = this.userDtoToUser(userdto);
		User user = this.modelMapper.map(userdto, User.class);
		User saveduser= (User) this.userRepository.save(user);
		UserDto userDto2 = this.modelMapper.map(saveduser, UserDto.class);
		
		logger.info("Completed dao call for save user");
		//return userToUserDto(saveduser);
		return userDto2;
	}

	@Override
	public UserDto updateUser(UserDto userdto, Integer userId) {
		
		logger.info("Initiating dao call for update user with:{}",userId);
		User user = this.userRepository.findById(userId)
				.orElseThrow(()-> new ResourceNotFoundException("User","Id",userId));
		
		user.setName(userdto.getName());
		user.setEmail(userdto.getEmail());
		user.setAbout(userdto.getAbout());
		user.setPassword(userdto.getPassword());
		
		User updateduser = this.userRepository.save(user);
		UserDto dto = this.modelMapper.map(updateduser, UserDto.class);
		
		logger.info("Completed dao call for update user");
		return dto;
	}

	@Override
	public UserDto getUserById(Integer userId) {
		
		logger.info("Initiating dao call for find user by ID user");
		User user = this.userRepository.findById(userId)
		.orElseThrow(()->new ResourceNotFoundException("User","Id",userId));
		
		UserDto userDto = this.modelMapper.map(user, UserDto.class);
		
		logger.info("Completed dao call for find user by ID user");
		//return userToUserDto(user);
		return userDto;
	}

	@Override
	public UserResponse getAllUsers(Integer pageSize,Integer pageNumber,String sortBy, String sortDir) {
		
		logger.info("Initiating dao call for Get all users");
		
		Sort sort=(sortDir.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending():Sort.by(sortBy).descending()) ;
		
		Pageable of = PageRequest.of(pageNumber, pageSize,sort);
		Page <User> pageUser =this.userRepository.findAll(of);
		List<User> allUser = pageUser.getContent();
		
		//List<User> allusers = this.userRepository.findAll();
		List<UserDto> userdtos = allUser.stream().map(user->this.modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
		logger.info("Completed dao call for get all users");
		
		UserResponse userResponse = new UserResponse();
		
		userResponse.setContent(userdtos);
		userResponse.setPageNumber(pageUser.getNumber());
		userResponse.setPageNumber(pageUser.getNumber());
		userResponse.setPageSize(pageUser.getSize());
		userResponse.setTotalElements(pageUser.getTotalElements());
		userResponse.setTotalPages(pageUser.getTotalPages());
		userResponse.setLastPage(pageUser.isLast());
		
		logger.info("Completed dao call for get all users");
		
		return userResponse;
	}

	@Override
	public void deleteUser(Integer userId) {
		
		logger.info("Initiating dao call for delete user");
		User user = this.userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","Id",userId));
		this.userRepository.deleteById(userId);
		logger.info("Completed dao call for delete user");
	}
  
//	private User userDtoToUser(UserDto userdto) {
//
//		User user = new User();
//		user.setId(userdto.getId());
//		user.setName(userdto.getName());
//		user.setEmail(userdto.getEmail());
//		user.setPassword(userdto.getPassword());
//		user.setAbout(userdto.getAbout());
//
//		return user;
//	}
//
//	private UserDto userToUserDto(User user) {
//
//		UserDto userdto = new UserDto();
//		userdto.setId(user.getId());
//		userdto.setName(user.getName());
//		userdto.setEmail(user.getEmail());
//		userdto.setPassword(user.getPassword());
//		userdto.setAbout(user.getAbout());
//
//		return userdto;
//	}
}
