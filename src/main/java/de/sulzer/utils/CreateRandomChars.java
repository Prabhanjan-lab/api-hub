package de.sulzer.utils;

import java.util.Random;

public class CreateRandomChars {

	/*
	 * Letter 'T' intentionally not included, because of search operations in
	 * ORU campaign, recall and service42, in order to avoid misleading findings
	 * regarding IDs which start with 'TA'. IDs and random titles/names etc.
	 * would otherwise lead to false findings or confusing mixings.
	 */
	public static final String LETTERS35 = "ABCDEFGHIJKLMNOPQRSUVWXYZ1234567890";

	public static final String NUMBERS10 = "0123456789";

	/**
	 * TODO making it private as soon as possible
	 *
	 * @param candidateChars
	 * @param length
	 * @return
	 */
	public static synchronized String generateRandomChars(String candidateChars, int length) {
	    StringBuilder sb = new StringBuilder();
	    Random random = new Random();
	    for (int i = 0; i < length; i++) {
	        sb.append(candidateChars.charAt(random.nextInt(candidateChars.length())));
	    }

	    return sb.toString();
	}

	public static synchronized String getLetters35(int length) {
		return generateRandomChars(LETTERS35, length);
	}

	public static synchronized String getNumbers10(int length) {
		return generateRandomChars(NUMBERS10, length);
	}

}