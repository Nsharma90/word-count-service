package com.org.test.wordcounter.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import com.org.demo.wordcounter.exception.TextContainsNonAlphabetsException;
import com.org.demo.wordcounter.service.impl.TranslatorImpl;
import com.org.demo.wordcounter.service.impl.WordCounterServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class WordCounterServiceTest {
	@InjectMocks
	private WordCounterServiceImpl wordCounterService;
	@Mock
	private TranslatorImpl translatorMock;

	@Test
	public void getCountWhenOnlyAlphabetsAreAddedTest() throws Exception {
		String sentence = getTestText();
		mockTranslatorService(sentence);
		// add a test string
		wordCounterService.addWords(sentence);
		int expectedCountForFlower = 5;

		testWordCount("Flower", expectedCountForFlower);
		// should return same
		testWordCount("blume", expectedCountForFlower);
		// should be same too
		testWordCount("Flor", expectedCountForFlower);

		int countForDevelopment = 1;
		// find the word development
		testWordCount("development", countForDevelopment);

	}

	@Test(expected = TextContainsNonAlphabetsException.class)
	public void addWordsWhenNonAlphabetCharacterAddedInSentenceTest() throws Exception {
		String sentence = getTestText();
		sentence = sentence.concat("."); // modified text contains period at the end
		try {
			wordCounterService.addWords(sentence);
		} catch (TextContainsNonAlphabetsException e) {
			String expectedMessage = "Non alphabet characters are not permitted in text.";
			assertEquals(expectedMessage, e.getMessage());
			throw e;
		}

	}

	@Test(expected = IllegalArgumentException.class)
	public void addWordsWhenEmptySentenseIsSendToWordCountServiceTest() throws Exception {

		try {
			wordCounterService.addWords(StringUtils.EMPTY);
		} catch (IllegalArgumentException e) {
			String expectedMessage = "Text can not be empty";
			assertEquals(expectedMessage, e.getMessage());
			throw e;
		}
	}

	@Test(expected = TextContainsNonAlphabetsException.class)
	public void getWordCountWhenNonAlphabetCharacterAddedInSentenceTest() throws Exception {
		try {
		String sentence = getTestText();
		mockTranslatorService(sentence);
		wordCounterService.addWords(sentence);
		wordCounterService.getWordCount("Search this");
		}catch(TextContainsNonAlphabetsException e) {
			String expectedMessage = "Service doesn't allow to find multiple words or non alphabatical characters.";
			assertEquals(expectedMessage, e.getMessage());
			throw e;
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void addWordsTestWhenEmptyStringIsSendToCountOccurrenceTest() throws Exception {
		try {
			String sentence = getTestText();
			mockTranslatorService(sentence);
			wordCounterService.addWords(sentence);
			wordCounterService.getWordCount(StringUtils.EMPTY); //sending empty string to find word count
			}catch(IllegalArgumentException e) {
				String expectedMessage = "Please provide a valid word to find word count, it can not be empty";
				assertEquals(expectedMessage, e.getMessage());
				throw e;
			}
	}

	@Test
	public void cleanupTestWhenClientSendNewAddWordsRequest() throws Exception {
		String sentence = getTestText();
		mockTranslatorService(sentence);
		// add a test string
		wordCounterService.addWords(sentence);
		int countForModel = 2;
		// find the word development
		testWordCount("model", countForModel);
		
		//map should get cleaned up just after the respone, so any call to count method should return 1 which is recently added.
		int expectedCountAfterCleanup = 1;
		String moreWords = "adding one more model word";
		mockTranslatorService(moreWords);
		wordCounterService.addWords(moreWords);
		testWordCount("model", expectedCountAfterCleanup);
	}
	
	private void testWordCount(String inputWord, int expectedCount) throws Exception {

		// count occurrence of a flower
		int flowerCountActual = wordCounterService.getWordCount(inputWord);
		// translatorMock.

		assertEquals(expectedCount, flowerCountActual);
	}

	private String getTestText() {
		return "The ABC model is a simple model that describes the genes responsible for the development of flower Flor is Spanish word for flower"
				+ " German people called Flower as blume";

	}

	private void mockTranslatorService(String sentence) {
		List<String> words = getAllWordsFromTestString(sentence);

		words.forEach(word -> mockTranslation(word));

	}

	private void mockTranslation(String word) {
		Mockito.when(translatorMock.translate(word)).thenAnswer(new Answer<String>() {

			@Override
			public String answer(InvocationOnMock invocation) throws Throwable {
				if (word.equalsIgnoreCase("flor") || word.equalsIgnoreCase("blume")) {
					return "flower";
				} else {
					return word.toLowerCase();
				}
			}

		});
	}

	private List<String> getAllWordsFromTestString(String sentence) {
		List<String> words = new ArrayList();
		Collections.addAll(words, sentence.split(" "));
		return words;
	}

	public WordCounterServiceTest() {
		// wordCounterService = new WordCounterServiceImpl();
		// translatorMock = Mockito.mock(TranslatorImpl.class);

	}

}
