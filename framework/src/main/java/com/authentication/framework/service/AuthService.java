package com.authentication.framework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.authentication.framework.model.Token;
import com.authentication.framework.plugin.PluginInterface;
import com.authentication.framework.plugin.PluginManager;
import com.authentication.framework.util.ConstantLiterals;

/**
 * 
 * @author ATHULYA SAJEEV
 *
 */

@Service
public class AuthService {

	private final PluginManager pluginManager;

	@Autowired
	public AuthService(PluginManager pluginManager) {
		this.pluginManager = pluginManager;
	}

	public Token authenticate(String providerName, String accessToken) {
		if (!pluginManager.isProviderSupported(providerName)) {
			return Token.invalid(ConstantLiterals.PROVIDER_NOT_SUPPORTED);
		}

		PluginInterface plugin = pluginManager.getPlugin(providerName);
		if (plugin == null) {
			return Token.invalid(ConstantLiterals.INTERNAL_SERVER_ERROR);
		}

		return plugin.authenticate(accessToken);
	}

	public boolean revokeToken(String providerName, String accessToken) {
		if (!pluginManager.isProviderSupported(providerName)) {
			return false;
		}

		PluginInterface plugin = pluginManager.getPlugin(providerName);
		if (plugin == null) {
			return false;
		}

		return plugin.revoke(accessToken);
	}
}
