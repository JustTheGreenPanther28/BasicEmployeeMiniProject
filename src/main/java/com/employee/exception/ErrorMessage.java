package com.employee.exception;

import java.time.LocalDateTime;

public class ErrorMessage {

	private int status;
	private LocalDateTime time;
	private String message;

	public ErrorMessage(LocalDateTime time, String message, int status) {
		this.message = message;
		this.time = time;
		this.status = status;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
