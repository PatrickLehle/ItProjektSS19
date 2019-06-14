package de.hdm.itprojektss19.team03.scart.client.gui;

import com.google.gwt.user.client.ui.*;

public class LoginForm extends VerticalPanel {

	private Anchor signInLink;

	public LoginForm() {}

	public LoginForm(String str) {
		signInLink = new Anchor();
		signInLink.setHref(str);

	}

	public void onLoad() {
		super.onLoad();
		
		Label header = new Label("Shop smart, with SCart!");
		Image logo = new Image("/images/ScartLogo.png");
		VerticalPanel loginPanel = new VerticalPanel();
		Image googleSignIn = new Image("/images/googleLogin.png");

		loginPanel.setStyleName("login-panel");
		loginPanel.setVerticalAlignment(ALIGN_MIDDLE);
		loginPanel.setHorizontalAlignment(ALIGN_CENTER);
		logo.setStyleName("logo");
		header.setStyleName("login-text");
		googleSignIn.setStylePrimaryName("signinwithgoogle");
		signInLink.getElement().appendChild(googleSignIn.getElement());
		
		loginPanel.add(logo);
		loginPanel.add(header);
		loginPanel.add(signInLink);

		this.add(loginPanel);

	}
}
