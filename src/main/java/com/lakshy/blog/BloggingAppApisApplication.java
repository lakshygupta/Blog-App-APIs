package com.lakshy.blog;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class BloggingAppApisApplication implements CommandLineRunner {
	
	@Autowired
	private PasswordEncoder passwordEncoder;

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
		System.out.println(this.passwordEncoder.encode("12345"));
	}

}
