package com.lakshy.blog.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException {
	String resourceName;
	String FieldName;
	long FieldValue;
	String Value;
	
	public ResourceNotFoundException(String resourceName, String fieldName, long fieldValue) {
		super(String.format("%s not found with this %s : %s ", resourceName,fieldName, fieldValue));
		this.resourceName = resourceName;
		FieldName = fieldName;
		FieldValue = fieldValue;
	}
	
	public ResourceNotFoundException(String resourceName, String fieldName, String value) {
		super(String.format("%s not found with this %s : %s ", resourceName,fieldName, value));
		this.resourceName = resourceName;
		FieldName = fieldName;
		Value = value;
	}
	
	
}
