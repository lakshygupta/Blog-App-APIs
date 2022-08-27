package com.lakshy.blog;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.lakshy.blog.config.AppConstants;
import com.lakshy.blog.entities.Role;
import com.lakshy.blog.repositories.RoleRepo;

@SpringBootApplication
public class BloggingAppApisApplication implements CommandLineRunner {
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepo roleRepo;
	
	public static void main(String[] args) {
		SpringApplication.run(BloggingAppApisApplication.class, args);
	}
	
	@Bean
	public ModelMapper modelMapper() 
	{
		return new ModelMapper();
	}

	// will run automatically when main runs and passing arguments via terminal
	@Override
	public void run(String... args) throws Exception {
		
//		System.out.println(this.passwordEncoder.encode("12345"));
		
		// Creating 2 roles at the start of application
		try {
			
			Role role = new Role();
			role.setId(AppConstants.ADMIN_USER);
			role.setName("ROLE_ADMIN");
			
			Role role1 = new Role();
			role1.setId(AppConstants.NORMAL_USER);
			role1.setName("ROLE_NORMAL");
			
			List<Role> roles = List.of(role,role1);
			
			List<Role> result = this.roleRepo.saveAll(roles);
			
			result.forEach(r -> {
				System.out.println(r.getName());
			});
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
