package de.hdm.itprojektss19.team03.scart.client.gui;

import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektss19.team03.scart.shared.bo.LoginInfo;

public class LoginForm extends VerticalPanel {

	private Anchor signInLink;
	private Image googleSignIn = new Image("/images/googleLogin.png");
	private LoginInfo loginInfo = new LoginInfo();
	private Label label = new Label("Logge dich mit deinem Google Konto ein oder erstelle ein neues Profil");
	// DEFAULT CONSTRUCTOR=============================================
	/**
	 * Default Konstruktor der EditGroup-Seite
	 */
	public LoginForm() {
	}
// CONSTRUCTOR=====================================================	
	/** Login-Seite von Google
	 * 
	 * @param loginURL
	 */
	public LoginForm(String loginURL) {
		signInLink = new Anchor();
		signInLink.setHref(loginURL);

	}
	/** Methode wird automatisch bei Seitenaufruf ausgefuehrt
	 */
	public void onLoad() {
		super.onLoad();

		googleSignIn.setStylePrimaryName("signinwithgoogle");
		signInLink.getElement().appendChild(googleSignIn.getElement());
		// signInLink.setHref(loginInfo.getLoginUrl());

		this.add(label);
		this.add(signInLink);
	}
}
