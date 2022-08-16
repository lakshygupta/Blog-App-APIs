package com.lakshy.blog.payloads;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
// use to transfer data from entities to services
// entities classes are now used to store the data only
// we can expose this DTO classes to apis and can use to get data from user

public class UserDto 
{
	private Integer id;
	
	@NotEmpty
	@Size(min=4,message = "Username must be minimum of 4 characters")
	private String name;
	
	@Email(message = "Email address is not valid")
	
	private String email;
	
	@NotEmpty
	@Size(min=3,max=15, message = "Password must be 3 - 15 characters long")
//	@Pattern(regexp = ) // add regular expression
	private String password;
	
	@NotEmpty
	private String about;
}