package com.shadow.util;

import org.apache.commons.lang3.RandomStringUtils;

public final class GenerateRandomString {

	private GenerateRandomString() {
	};

	public static String getRendomString() {
		return RandomStringUtils.randomAlphabetic(8);
	}
	
	public static String getRendomFourDigitString(){
		return RandomStringUtils.randomAlphabetic(4);
	}

}
