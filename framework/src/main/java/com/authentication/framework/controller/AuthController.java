package com.authentication.framework.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.authentication.framework.model.GoogleTokenInfoResponse;
import com.authentication.framework.model.Token;
import com.authentication.framework.model.TokenVerificationResponse;
import com.authentication.framework.plugin.PluginInterface;
import com.authentication.framework.plugin.PluginManager;
import com.authentication.framework.util.ConstantLiterals;

/**
 * 
 * @author ATHULYA SAJEEV
 *
 */

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Value("${spring.google.token.verify}")
	private String GOOGLE_TOKEN_VERIFY_URL;

	private final PluginManager pluginManager;

	@Autowired
	public AuthController(PluginManager pluginManager) {
		this.pluginManager = pluginManager;
	}
	
	/**
	 * Method to verify token
	 * 
	 * @param providerName
	 * @param accessToken
	 * @return TokenVerificationResponse
	 * @throws Exception
	 */
	
	@PostMapping("/verify")
	public TokenVerificationResponse verifyToken(@RequestParam String providerName, @RequestParam String accessToken) {
		if (!pluginManager.isProviderSupported(providerName)) {
			return new TokenVerificationResponse(false, ConstantLiterals.BAD_REQUEST);
		}

		PluginInterface plugin = pluginManager.getPlugin(providerName);
		if (plugin == null) {
			return new TokenVerificationResponse(false, ConstantLiterals.INTERNAL_SERVER_ERROR);
		}

		Token token = plugin.authenticate(accessToken);
		if (token.isValid()) {
			try {
				RestTemplate restTemplate = new RestTemplate();
				String verificationUrl = GOOGLE_TOKEN_VERIFY_URL + accessToken;
				GoogleTokenInfoResponse response = restTemplate.getForObject(verificationUrl,
						GoogleTokenInfoResponse.class);

				if (response != null && !response.hasError()) {
					String email = response.getEmail();
					String aud = response.getAud();
					String sub = response.getSub();
					String email_verified = response.getEmail_verified();
					String exp = response.getExp();
					String iat = response.getIat();
					String iss = response.getIss();
					String scope = response.getScope();
					String audience = response.getAudience();
					String token_type = response.getToken_type();
					return new TokenVerificationResponse(true, ConstantLiterals.TOKEN_IS_VALID, email, aud, sub,
							email_verified, exp, iat, iss, scope, audience, token_type);
				} else {
					return new TokenVerificationResponse(false, ConstantLiterals.TOKEN_VERIFICATION_FAILED);
				}
			} catch (Exception e) {
				return new TokenVerificationResponse(false, ConstantLiterals.TOKEN_EXPIRED);
			}
		}
		return null;
	}

	/**
	 * Method to revoke token
	 * 
	 * @param providerName
	 * @param accessToken
	 * @return TokenVerificationResponse
	 * @throws Exception
	 */
	
	@PostMapping("/revoke")
	public ResponseEntity<String> revokeToken(@RequestParam String providerName, @RequestParam String accessToken) {
		if (!pluginManager.isProviderSupported(providerName)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ConstantLiterals.PROVIDER_NOT_SUPPORTED);
		}

		PluginInterface plugin = pluginManager.getPlugin(providerName);
		if (plugin == null) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ConstantLiterals.INTERNAL_SERVER_ERROR);
		}

		boolean revoked = plugin.revoke(accessToken);

		if (revoked) {
			return ResponseEntity.ok(ConstantLiterals.TOKEN_REVOKED_SUCCESSFULLY);
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(ConstantLiterals.TOKEN_REVOCATION_FAILED);
		}
	}
}
