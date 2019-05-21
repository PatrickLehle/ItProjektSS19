package de.hdm.itprojektss19.team03.scart.server;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.itprojektss19.team03.scart.shared.LoginService;
import de.hdm.itprojektss19.team03.scart.shared.bo.LoginInfo;



/**
 * 
 * @author bastiantilk
 * Loginklasse im Server Package. Verweist auf LoginInfo in shared
 */

public class LoginServiceImpl extends RemoteServiceServlet implements
LoginService{
	public static long serialVersionUID;
	
	public LoginInfo login(String requestUri) {
		UserService userService = UserServiceFactory.getUserService();
	    User user = userService.getCurrentUser();
	    LoginInfo loginInfo = new LoginInfo();
	    
	    
	    
	    if (user != null) {
	      loginInfo.setLoginIn(true);
	      loginInfo.setEmailAddress(user.getEmail());
	      //loginInfo.setNickname(user.getNickname());
	      loginInfo.setLogoutUrl(userService.createLogoutURL(requestUri));
	    } else {
	      loginInfo.setLoginIn(false);
	      loginInfo.setLoginUrl(userService.createLoginURL(requestUri));
	    }
	    return loginInfo;
	  }

}
