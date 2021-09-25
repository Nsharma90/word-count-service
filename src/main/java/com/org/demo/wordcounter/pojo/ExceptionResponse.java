package com.org.demo.wordcounter.pojo;

public class ExceptionResponse {

	private String exception;
	private int errorCode;

	public ExceptionResponse(String exception, int errorCode) {
		super();
		this.exception = exception;
		this.errorCode = errorCode;
	}

	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
}
