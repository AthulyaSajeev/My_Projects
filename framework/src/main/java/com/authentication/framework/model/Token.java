package com.authentication.framework.model;

/**
 * 
 * @author ATHULYA SAJEEV
 *
 */

public class Token {

	private boolean valid;
	private String errorMessage; 

	private Token() { 
	}

	public boolean isValid() {
		return valid;
	}

	public String getErrorMessage() {
		return errorMessage;
	}
 
	public static Token valid() {
		Token token = new Token();
		token.valid = true;
		return token;
	}
 
	public static Token invalid(String errorMessage) {
		Token token = new Token();
		token.valid = false;
		token.errorMessage = errorMessage;
		return token;
	}
}
