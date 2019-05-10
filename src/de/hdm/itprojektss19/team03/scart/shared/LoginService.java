package de.hdm.itprojektss19.team03.scart.shared;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.hdm.itprojektss19.team03.scart.shared.bo.LoginInfo;

@RemoteServiceRelativePath("loginservice")
public interface LoginService extends RemoteService{
	public LoginInfo login(String requestUri);
}
