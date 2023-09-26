package com.authentication.framework.model;
 
public class Token {

    private boolean valid;
    private String errorMessage;
    // Add more token-related fields as needed, such as user details, token expiration, etc.

    private Token() {
        // Private constructor to enforce the use of factory methods.
    }

    public boolean isValid() {
        return valid;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    // Factory method to create a valid token.
    public static Token valid() {
        Token token = new Token();
        token.valid = true;
        return token;
    }

    // Factory method to create an invalid token with an error message.
    public static Token invalid(String errorMessage) {
        Token token = new Token();
        token.valid = false;
        token.errorMessage = errorMessage;
        return token;
    }
}
