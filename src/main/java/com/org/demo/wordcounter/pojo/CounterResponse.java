package com.org.demo.wordcounter.pojo;

public class CounterResponse {

	private int count;
	private String word;

	public CounterResponse(Integer count, String word) {
		super();
		this.count = count;
		this.word = word;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

}
