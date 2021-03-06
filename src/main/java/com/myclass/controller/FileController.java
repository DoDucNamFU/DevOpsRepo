package com.myclass.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("api/file")
public class FileController {
	
	@Value("${file.upload-dir}")
	private String uploadDir;
	
	@PostMapping("upload")
	public Object upload(@RequestParam MultipartFile file) {
		try {
			// LẤY RA FILE HÌNH
			String fileName = file.getOriginalFilename();
			// LƯU FILE VÀO THƯ MỤC
			Path filePath = Paths.get(uploadDir + fileName);
			Files.write(filePath, file.getBytes());
			
			return new ResponseEntity<Object>(fileName, HttpStatus.OK);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
	}
}
