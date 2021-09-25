package com.org.demo.wordcounter.exception;

public class MultiWordSearchIsNotAllowedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MultiWordSearchIsNotAllowedException() {
	}

	public MultiWordSearchIsNotAllowedException(String msg) {
		super(msg);
	}
}
