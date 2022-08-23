package com.lakshy.blog.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lakshy.blog.exceptions.IncorrectFileFormatException;
import com.lakshy.blog.exceptions.ResourceNotFoundException;
import com.lakshy.blog.services.FileService;

@Service
public class FileServiceImpl implements FileService {

	@Override
	public String uploadImage(String path, MultipartFile file) throws IOException {
		// File name
		String name = file.getOriginalFilename();
		
		//validate the image
		String fileExtension = name.substring(name.lastIndexOf('.'));
		if(!(fileExtension.equals(".png") || fileExtension.equals(".jpg") || fileExtension.equals(".jpeg"))) 
		{
			throw new IncorrectFileFormatException(fileExtension);
		}
		
		// Random name generator file
		String randomId = UUID.randomUUID().toString();
		String modifiedFileName = randomId.concat(name.substring(name.lastIndexOf('.')));
		
		// Full path
		// File.Separator added / and \ slash depending upon OS
		String filePath = path + File.separator + modifiedFileName;
		
		//create folder if not created
		File f = new File(path);
		if(!f.exists()) {
			f.mkdir();
		}
		
		Files.copy(file.getInputStream(), Paths.get(filePath));
		
		return modifiedFileName;
	}

	@Override
	public InputStream getResources(String path, String fileName) throws FileNotFoundException {
		// serving image using rest api
		String fullPath = path + File.separator + fileName;
		
		InputStream is = new FileInputStream(fullPath);
		return is;
	}

}
