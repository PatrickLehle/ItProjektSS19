package de.hdm.itprojektss19.team03.scart.shared.bo;

import com.google.gwt.user.client.rpc.IsSerializable;

public class LoginInfo extends BusinessObject implements IsSerializable {

	/**
	 * Realisiering einer exemplarischen LoginInfo, welche alle relevanten
	 * Informationen des eingeloggten Users enthaelt.
	 * 
	 * @see <code>IsSerializable</code>
	 * 
	 */

	private boolean loggedIn = false;
	private String loginUrl = "";
	private String logoutUrl = " ";
	private String emailAddress = "";
	
	public LoginInfo() {
		
	}

	/**
	 * Gibt TRUE zurueck, wenn ein User angemeldet ist andernfalls wird FALSE
	 * zurueckgegeben.
	 * 
	 * @return loggedIn status ob der User eingeloggt ist
	 */
	public boolean isLoggedIn() {
		return loggedIn;
	}

	/**
	 * Gibt den Login-Status zurueck.
	 * 
	 * @return loggedIn
	 */

	public boolean getLoginIn() {
		return loggedIn;
	}

	/**
	 * Setzt den Login-Status
	 * 
	 * @param loggedIn Status setzen fuer den eingeloggten User
	 */

	public void setLoginIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	/**
	 * Gibt die LoginUrl zurueck.
	 * 
	 * @return loginUrl
	 */

	public String getLoginUrl() {
		return loginUrl;
	}

	/**
	 * Setzt die LoginUrl
	 * 
	 * @param loginUrl die gesetzt werden soll
	 */
	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	/**
	 * Gibt die Logout zurueck.
	 * 
	 * @return logoutUrl
	 */

	public String getLogoutUrl() {
		return logoutUrl;
	}

	/**
	 * Setzt die LogoutUrl
	 * 
	 * @param logoutUrl fuer den Loggout
	 */
	public void setLogoutUrl(String logoutUrl) {
		this.logoutUrl = logoutUrl;
	}

	/**
	 * Gibt die Email zurueck.
	 * 
	 * @return emailAdress
	 */

	public String getEmailAddress() {
		return emailAddress;
	}

	/**
	 * Setzt die Email
	 * 
	 * @param emailAddress des Users
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

}
