package com.authentication.framework.plugin;

import com.authentication.framework.model.*;

public interface PluginInterface {

	Token authenticate(String accessToken);

	boolean revoke(String accessToken);

}