package com.myclass.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ValidationExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		// Lấy ra đối tượng BindingResult quản lý toàn bộ valid error của hệ thống 
		BindingResult bindingResult = ex.getBindingResult();
		// Lấy ra danh sách error
		List<FieldError> errors =  bindingResult.getFieldErrors();
		// Lưu error message vào list
		List<String> listErrors = new ArrayList<String>();
		for (FieldError fieldError : errors) {
			listErrors.add(fieldError.getDefaultMessage());
		}
		
		// Sửa lại response body cho chuyên nghiệp
		Map<String, Object> body = new LinkedHashMap<String, Object>();
		body.put("timestamp", new Date());
		body.put("status", status.value());
		body.put("errors", listErrors);
		
		return new ResponseEntity<Object>(body, headers, status);
		
	}
}
