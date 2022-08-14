package com.lakshy.blog.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lakshy.blog.exceptions.ResourceNotFoundException;
import com.lakshy.blog.entities.User;
import com.lakshy.blog.payloads.UserDto;
import com.lakshy.blog.repositories.UserRepo;
import com.lakshy.blog.services.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepo userRepo;
	
	// since we are using UserDto so we need to convert it
	// we can also use Modern mapper library instead of these two conversion methods
	// convert UserDto to User
	private User dtoToUser(UserDto userDto) 
	{
		User user = new User();
		user.setId(userDto.getId());
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setAbout(userDto.getAbout());
		user.setPassword(userDto.getPassword());
		
		return user;
	}
	
	// Convert User to UserDto
	private UserDto UserToDto(User user) 
	{
		UserDto userDto = new UserDto();
		userDto.setAbout(user.getAbout());
		userDto.setId(user.getId());
		userDto.setName(user.getName());
		userDto.setEmail(user.getEmail());
		userDto.setPassword(user.getPassword());
		return userDto;
	}

	@Override
	public UserDto createUser(UserDto userDto) {
		
		User user = this.dtoToUser(userDto);
		User savedUser = userRepo.save(user);
		
		return this.UserToDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User","Id",userId));
		
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setAbout(userDto.getAbout());
		user.setPassword(userDto.getPassword());
		
		User updatedUser = userRepo.save(user);
		UserDto userDto1 = this.UserToDto(updatedUser);
		
		return userDto1;
	}

	@Override
	public UserDto getUserById(Integer userId) {
		User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User","Id",userId));
		UserDto userDto = this.UserToDto(user);
		return userDto;
	}

	@Override
	public List<UserDto> getAllUsers() {
		List<User> userList = userRepo.findAll();
//		List<UserDto> userDtoList = new ArrayList<>();
//		for(User u : userList) {
//			userDtoList.add(this.UserToDto(u));
//		}
		// or
		List<UserDto> userDtoList = userList.stream().map(user -> this.UserToDto(user)).collect(Collectors.toList());
		return userDtoList;
	}

	@Override
	public void deleteUser(Integer userId) {
		User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User","Id",userId));
		userRepo.delete(user);

	}

}
