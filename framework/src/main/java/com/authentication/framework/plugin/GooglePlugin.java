package com.authentication.framework.plugin;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.authentication.framework.model.Token;

public class GooglePlugin implements PluginInterface {
 
	@Override
	public Token authenticate(String accessToken) {
		try {
			if (isValidAccessToken(accessToken)) {
				return Token.valid();
			} else {
				return Token.invalid("Authentication failed");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Token.invalid("Authentication error: " + e.getMessage());
		}
	}

	 @Override
	    public boolean revoke(String accessToken) {
	        try {
	        	RestTemplate restTemplate = new RestTemplate(); 
	            HttpHeaders headers = new HttpHeaders();
	            headers.add("Authorization", "Bearer " + accessToken); 
	            HttpEntity<String> requestEntity = new HttpEntity<>(headers); 
	            String revocationUrl = "https://accounts.google.com/o/oauth2/revoke?token="+accessToken; 
	            ResponseEntity<String> response = restTemplate.exchange(
	                revocationUrl,
	                HttpMethod.POST,
	                requestEntity,
	                String.class
	            ); 
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
