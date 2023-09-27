package com.authentication.framework.plugin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author ATHULYA SAJEEV
 *
 */

@Component
public class PluginManager {

	private final Map<String, PluginInterface> plugins;

	@Autowired
	public PluginManager(Map<String, PluginInterface> pluginMap) {
		this.plugins = new HashMap<>();
		pluginMap.forEach(this::registerPlugin);
	}

	public void registerPlugin(String providerName, PluginInterface plugin) {
		plugins.put(providerName, plugin);
	}

	public PluginInterface getPlugin(String providerName) {
		return plugins.get(providerName);
	}

	public boolean isProviderSupported(String providerName) {
		return plugins.containsKey(providerName);
	} 
}
