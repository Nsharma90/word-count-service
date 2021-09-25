package com.org.demo.wordcounter.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.org.demo.wordcounter.exception.TextContainsNonAlphabetsException;
import com.org.demo.wordcounter.pojo.CounterResponse;
import com.org.demo.wordcounter.service.WordCountService;

@RestController
public class WordCounterController {
	private Logger logger = LoggerFactory.getLogger(WordCounterController.class);

	@Autowired
	private WordCountService wordService;

	@PostMapping("/counter")
	public ResponseEntity<CounterResponse> countWords(@RequestParam("text") String text,
			@RequestParam("find") String word)
			throws TextContainsNonAlphabetsException, IllegalArgumentException, TextContainsNonAlphabetsException {

		this.wordService.addWords(text);
		// return count of word
		int count = this.wordService.getWordCount(word);
		return ResponseEntity.ok().body(generateCounterResponse(count, word));

	}

	@GetMapping("/")
	public ResponseEntity<String> home() {
		return new ResponseEntity<String>("Welcome", HttpStatus.OK);
	}

	private CounterResponse generateCounterResponse(int count, String word) {
		return new CounterResponse(count, word);
	}
}