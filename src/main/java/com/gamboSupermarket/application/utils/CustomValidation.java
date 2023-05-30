package com.gamboSupermarket.application.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomValidation {

	private Pattern panPattern;
	private Pattern pincodePattern;
	private Matcher matcher;

	private static final String PAN_PATTERN = "[A-Z]{5}[0-9]{4}[A-Z]{1}";
	private static final String PINCODE_PATTERN = "[0-9]{6}";

	public CustomValidation() {
		panPattern = Pattern.compile(PAN_PATTERN);
		pincodePattern = Pattern.compile(PINCODE_PATTERN);
	}

	public boolean panValidate(final String password) {
		matcher = panPattern.matcher(password);
		return matcher.matches();

	}

	public boolean pincodeValidate(final String password) {
		matcher = pincodePattern.matcher(password);
		return matcher.matches();
	}

	public static boolean isNullOrEmpty(String str) {
		if (str != null && !str.trim().isEmpty())
			return false;
		return true;
	}

}
