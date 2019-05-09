package de.hdm.itprojektss19.team03.scart.shared.bo;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Realisierung eines Users. Der User wird hier mit einer Email Adresse
 * hinterlegt, mit welche er sich registriert.
 * 
 * @see <code>BusinessObject</code>
 * 
 * 
 * 
 * 
 * @author PatrickLehle
 */
public class User extends BusinessObject implements IsSerializable {

	/**
	 * Eindeutige Identifikation einer Serialisierbaren Klasse
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Instanzvariable von Klasse User
	 */
	private String username = "";

	/*
	 * Auslesen der Email
	 * 
	 * @return email - Email Adresse des Users
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * setzen der Email
	 * 
	 * @param email des Users
	 */
	public void setUsername(String username) {
		this.username = username;
	}
}
