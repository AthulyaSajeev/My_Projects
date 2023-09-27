package com.authentication.framework.plugin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.authentication.framework.model.Token;
import com.authentication.framework.util.ConstantLiterals;

/**
 * 
 * @author ATHULYA SAJEEV
 *
 */

public class GooglePlugin implements PluginInterface {

	@Value("${spring.google.token.revoke}")
	private String GOOGLE_TOKEN_REVOKE_URL;
 
	@Override
	public Token authenticate(String accessToken) {
		try {
			if (isValidAccessToken(accessToken)) {
				return Token.valid();
			} else {
				return Token.invalid(ConstantLiterals.AUTHENTICATION_FAILED);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Token.invalid(ConstantLiterals.AUTHENTICATION_ERROR + e.getMessage());
		}
	}

	@Override
	public boolean revoke(String accessToken) {
		try {
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.add(ConstantLiterals.Authorization, ConstantLiterals.Bearer + accessToken);
			HttpEntity<String> requestEntity = new HttpEntity<>(headers);
			String revocationUrl = GOOGLE_TOKEN_REVOKE_URL + accessToken;
			ResponseEntity<String> response = restTemplate.exchange(revocationUrl, HttpMethod.POST, requestEntity,
					String.class);
			return response.getStatusCode().is2xxSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private boolean isValidAccessToken(String accessToken) {
		return accessToken != null && !accessToken.isEmpty();
	}

}
