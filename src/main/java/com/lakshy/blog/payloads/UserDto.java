package com.lakshy.blog.payloads;

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
	private String name;
	private String email;
	private String password;
	private String about;
}
