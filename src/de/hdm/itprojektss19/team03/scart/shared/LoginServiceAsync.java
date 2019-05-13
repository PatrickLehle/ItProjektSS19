package de.hdm.itprojektss19.team03.scart.shared;

import com.google.gwt.user.client.rpc.AsyncCallback;
import de.hdm.itprojektss19.team03.scart.shared.bo.LoginInfo;

/**
 * Asynchrones Interface zu <code>LoginService</code>
 *
 */
public interface LoginServiceAsync{

	public void login(String requestUri, AsyncCallback<LoginInfo> callback);

}
