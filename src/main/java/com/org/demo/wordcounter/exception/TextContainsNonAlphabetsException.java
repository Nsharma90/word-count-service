package com.org.demo.wordcounter.exception;

public class TextContainsNonAlphabetsException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TextContainsNonAlphabetsException() {
	}

	public TextContainsNonAlphabetsException(String msg) {
		super(msg);
	}
}
