package com.authentication.framework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.authentication.framework.plugin.GooglePlugin;
import com.authentication.framework.plugin.PluginInterface;

/**
 * 
 * @author ATHULYA SAJEEV
 *
 */

@Configuration
public class AuthPluginConfig {

	@Bean
	public PluginInterface google() {
		return new GooglePlugin();
	}
}
