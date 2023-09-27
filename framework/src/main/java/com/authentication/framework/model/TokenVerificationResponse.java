package com.authentication.framework.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author ATHULYA SAJEEV
 *
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TokenVerificationResponse {
	@JsonProperty("isValid")
	private boolean isValid;
	@JsonProperty("message")
	private String message;
	@JsonProperty("email")
	private String email;
	@JsonProperty("aud")
	private String aud;
	@JsonProperty("sub")
	private String sub;
	@JsonProperty("email_verified")
	private String email_verified;
	@JsonProperty("exp")
	private String exp;
	@JsonProperty("iat")
	private String iat;
	@JsonProperty("iss")
	private String iss;
	@JsonProperty("scope")
	private String scope;
	@JsonProperty("audience")
	private String audience;
	@JsonProperty("token_type")
	private String token_type;

	public TokenVerificationResponse(boolean isValid, String message, String email, String aud, String sub,
			String email_verified, String exp, String iat, String iss, String scope, String audience,
			String token_type) {
		super();
		this.isValid = isValid;
		this.message = message;
		this.email = email;
		this.aud = aud;
		this.sub = sub;
		this.email_verified = email_verified;
		this.exp = exp;
		this.iat = iat;
		this.iss = iss;
		this.scope = scope;
		this.audience = audience;
		this.token_type = token_type;
	}

	public TokenVerificationResponse(boolean isValid, String message, String userId, String email) {
		this.isValid = isValid;
		this.message = message;
		this.email = email;
	}

	public TokenVerificationResponse(boolean isValid, String message) {
		this.isValid = isValid;
		this.message = message;
	} 
}
