package com.lakshy.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lakshy.blog.entities.User;

public interface UserRepo extends JpaRepository<User, Integer> 
{

}
