package com.employee.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;


@ControllerAdvice
public class CustomExceptionHandler {

	@ExceptionHandler(Exception.class )
	public ResponseEntity<Object> handleOtherException(Exception exception, WebRequest request) {

		ErrorMessage errorMessage = new ErrorMessage(LocalDateTime.now(), exception.getMessage());

		return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
