package com.lakshy.blog.exceptions;

public class IncorrectFileFormatException extends RuntimeException {
	String fileType;

	public IncorrectFileFormatException(String fileType) {
		super(String.format("File Uploaded is not an Image (png/jpg/jpeg), Uploaded file is of type : %s",fileType));
		this.fileType = fileType;
	}
	
}
