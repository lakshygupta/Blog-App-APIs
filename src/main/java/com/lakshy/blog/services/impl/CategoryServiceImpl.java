package com.lakshy.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lakshy.blog.entities.Category;
import com.lakshy.blog.exceptions.ResourceNotFoundException;
import com.lakshy.blog.payloads.CategoryDto;
import com.lakshy.blog.repositories.CategoryRepo;
import com.lakshy.blog.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepo categoryRepo;
	
	// model mappers
	@Autowired
	private ModelMapper modelMapper;
	
	private Category DtoToCategory(CategoryDto categoryDto) 
	{
		Category category = modelMapper.map(categoryDto, Category.class);
		return category;
	}
	
	private CategoryDto CategoryToDto(Category category) 
	{
		CategoryDto categoryDto = modelMapper.map(category, CategoryDto.class);
		return categoryDto;
	}
	
	// methods
	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		Category category = this.DtoToCategory(categoryDto);
		Category savedCategory = categoryRepo.save(category);
		return this.CategoryToDto(savedCategory);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "Id", categoryId));
		
		category.setCategoryDescription(categoryDto.getCategoryDescription());
		category.setCategoryTitle(categoryDto.getCategoryTitle());
		
		Category updatedCategory= categoryRepo.save(category);
		return this.CategoryToDto(updatedCategory);
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category","Id",categoryId));
		
		categoryRepo.delete(category);
	}

	@Override
	public List<CategoryDto> getAllCategories() {
		List<Category> categories = categoryRepo.findAll();
		
		List<CategoryDto> categoriesDto = categories.stream().map(cate -> this.CategoryToDto(cate)).collect(Collectors.toList());
		return categoriesDto;
	}

	@Override
	public CategoryDto getCategoryById(Integer categoryId) {
		Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category","Id",categoryId));
		return this.CategoryToDto(category);
	}

}
