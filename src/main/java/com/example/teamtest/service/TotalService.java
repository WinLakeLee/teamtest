package com.example.teamtest.service;

import java.util.Random;

public class TotalService {

	private static final String[] PW_WORDS = {
		    "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
		    "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z",
		    "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"
		};
	private static final int PW_LENGTH = 10;
	
	private final Random random = new Random();
	
	public String generatePw() {
		StringBuilder sb = new StringBuilder(PW_LENGTH);
		
		for(int i = 0; i < PW_LENGTH; i++) {
			sb.append(PW_WORDS[random.nextInt(PW_WORDS.length)]);
		}
		return sb.toString();
	}
}