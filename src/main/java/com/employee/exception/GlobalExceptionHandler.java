package com.employee.exception;

import java.time.LocalDateTime;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(EmployeeNotFoundException.class)
	public ResponseEntity<?> handleNotFound(EmployeeNotFoundException e) {
		ErrorMessage errMsg = new ErrorMessage(LocalDateTime.now(), e.getMessage(), 404);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errMsg);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<?> handleConflict(DataIntegrityViolationException e) {
		ErrorMessage errMsg = new ErrorMessage(LocalDateTime.now(), "Linked records exist, cannot delete", 409);
		return ResponseEntity.status(HttpStatus.CONFLICT).body(errMsg);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException e) {
		ErrorMessage errMsg = new ErrorMessage(LocalDateTime.now(), "Invalid value", 400);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errMsg);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleGeneral(Exception e) {
		e.printStackTrace();
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());

	}
}