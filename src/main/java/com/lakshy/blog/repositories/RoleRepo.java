package com.lakshy.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lakshy.blog.entities.Role;

public interface RoleRepo extends JpaRepository<Role, Integer> {

}
