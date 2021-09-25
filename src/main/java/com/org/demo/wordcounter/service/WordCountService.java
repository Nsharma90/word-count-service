package com.org.demo.wordcounter.service;

import com.org.demo.wordcounter.exception.TextContainsNonAlphabetsException;

public interface WordCountService {
	void addWords(String text) throws TextContainsNonAlphabetsException, IllegalArgumentException;

	int getWordCount(String word)
			throws TextContainsNonAlphabetsException, IllegalArgumentException, TextContainsNonAlphabetsException;
}
