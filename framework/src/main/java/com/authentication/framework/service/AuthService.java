package com.authentication.framework.service;
  
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.authentication.framework.model.Token;
import com.authentication.framework.plugin.PluginInterface;
import com.authentication.framework.plugin.PluginManager;

@Service
public class AuthService {

    private final PluginManager pluginManager;

    @Autowired
    public AuthService(PluginManager pluginManager) {
        this.pluginManager = pluginManager;
    }

    public Token authenticate(String providerName, String accessToken) {
        if (!pluginManager.isProviderSupported(providerName)) {
            return Token.invalid("Provider not supported");
        }

        PluginInterface plugin = pluginManager.getPlugin(providerName);
        if (plugin == null) {
            return Token.invalid("Internal server error");
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
