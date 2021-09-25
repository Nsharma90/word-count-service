package com.org.demo.wordcounter.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.org.demo.wordcounter.pojo.ExceptionResponse;

@ControllerAdvice
public class ExceptionController {

	@ExceptionHandler({ TextContainsNonAlphabetsException.class, IllegalArgumentException.class,
			TextContainsNonAlphabetsException.class })
	public ResponseEntity<ExceptionResponse> exception(Exception ex) {
		ExceptionResponse response = new ExceptionResponse(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
		return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
	}

}
