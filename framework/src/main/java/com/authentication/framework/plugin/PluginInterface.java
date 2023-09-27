package com.authentication.framework.plugin;

import com.authentication.framework.model.*;

/**
 * 
 * @author ATHULYA SAJEEV
 *
 */

public interface PluginInterface {

	Token authenticate(String accessToken);

	boolean revoke(String accessToken);

}