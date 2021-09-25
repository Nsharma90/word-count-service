package com.org.demo.wordcounter.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PreDestroy;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.org.demo.wordcounter.exception.TextContainsNonAlphabetsException;
import com.org.demo.wordcounter.service.Translator;
import com.org.demo.wordcounter.service.WordCountService;

@Service
public class WordCounterServiceImpl implements WordCountService {
	private Logger logger = LoggerFactory.getLogger(WordCounterServiceImpl.class);
	@Autowired
	private Translator translatorService;
	private final Integer INITIAL_COUNT = 1;
	// <englishword,count>
	private Map<String, Integer> wordMap = new HashMap<String, Integer>();

	@Override
	public void addWords(String text) throws TextContainsNonAlphabetsException,IllegalArgumentException {
		if(StringUtils.isBlank(text)) {
			throw new IllegalArgumentException("Text can not be empty");
		}
		// clean up map
		this.cleanUp();
		// validate if text contains non alphabets except space
		if (!validateIfAlphabet(text)) {
			throw new TextContainsNonAlphabetsException("Non alphabet characters are not permitted in text.");
		}
		// add to word map
		addWordsToMap(text);

	}

	@Override
	public int getWordCount(String word) throws TextContainsNonAlphabetsException,IllegalArgumentException {
		if(StringUtils.isBlank(word)) {
			throw new IllegalArgumentException("Please provide a valid word to find word count, it can not be empty");
		}
		if (!isContainMultipleWords(word)) {
			throw new TextContainsNonAlphabetsException("Service doesn't allow to find multiple words or non alphabatical characters.");
		}
		String englishWord = this.translatorService.translate(word);
		// find word in map and return count
		Integer count = this.wordMap.get(englishWord);
		if (null != count) {
			return count;
		}
		return 0;
	}

	private void addWordsToMap(String text) {
		String[] words = this.getWordsArray(text);
		for (String word : words) {
			String englishWord = this.translatorService.translate(word); // always small case
			if (StringUtils.isNotBlank(englishWord)) {
				addToMap(englishWord.toLowerCase());
			} else {
				logger.warn(word + "\'s translation is not available");
				// still adding, just to for sake of getting count
				this.addToMap(word.toLowerCase());
			}

		}
	}

	private void addToMap(String word) {
		Integer keyCount = this.wordMap.putIfAbsent(word, INITIAL_COUNT);
		// word already exists, increase current count
		if (null != keyCount) {
			this.wordMap.put(word, this.wordMap.get(word) + 1);
		}

	}

	private boolean validateIfAlphabet(String word) {
		return StringUtils.isAlphaSpace(word);
	}
	
	private boolean isContainMultipleWords(String word) {
		return StringUtils.isAlpha(word);
	}

	private String[] getWordsArray(String text) {
		return text.split(" ");
	}

	// can call this method in case we need to clean the map after just one use
	@PreDestroy
	public void cleanUp() {
		wordMap.clear();
	}

}
