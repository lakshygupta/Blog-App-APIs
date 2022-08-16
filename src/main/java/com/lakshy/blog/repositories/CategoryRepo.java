package com.lakshy.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lakshy.blog.entities.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

}
