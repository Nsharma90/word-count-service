package com.org.demo.wordcounter.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.org.demo.wordcounter.service.Translator;

@Service
public class TranslatorImpl implements Translator {
	private Map<String, String> dictionary = new HashMap();

	@Override
	public String translate(String word) throws IllegalArgumentException {
		if (StringUtils.isEmpty(word)) {
			throw new IllegalArgumentException("word can not be empty!!");
		}

		String englishCounterpart = this.dictionary.get(word.toLowerCase());
		
		return englishCounterpart==null?word:englishCounterpart;
	}

	public TranslatorImpl() {
		init();
	}

	private void init() {
		this.dictionary.put("flower", "flower");
		this.dictionary.put("flor", "flower");
		this.dictionary.put("blume", "flower");
	}
}
