package de.hdm.itprojektss19.team03.scart.client.gui;

import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektss19.team03.scart.shared.bo.LoginInfo;

public class LoginForm extends VerticalPanel {

	private Anchor signInLink;
	private Image googleSignIn = new Image("/images/googleLogin.png");
	private LoginInfo loginInfo = new LoginInfo();
	private Label label = new Label("Logge dich mit deinem Google Konto ein oder erstelle ein neues Profil");

	/**
	 * Login-Seite ueber Google
	 * 
	 * @param loginURL Url, ueber die die Anmeldung mit Google laeuft
	 */
	public LoginForm(String loginURL) {
		signInLink = new Anchor();
		signInLink.setHref(loginURL);

	}

	/**
	 * Methode wird automatisch bei Seitenaufruf ausgefuehrt
	 */
	public void onLoad() {
		Label scart = new Label("Shop smart, with Scart!");
		HorizontalPanel loginPanel = new HorizontalPanel();
		scart.setStyleName("h1");
		googleSignIn.setStylePrimaryName("signinwithgoogle");
		signInLink.getElement().appendChild(googleSignIn.getElement());
		loginPanel.addStyleName("align-center");
		this.setHorizontalAlignment(ALIGN_CENTER);

		loginPanel.add(signInLink);
		this.addStyleName("align-center");
		this.add(scart);
		this.add(label);
		this.add(loginPanel);
	}
}
