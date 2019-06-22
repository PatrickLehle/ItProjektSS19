package de.hdm.itprojektss19.team03.scart.client.gui;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.shared.bo.User;

public class ProfileForm {

	User user = new User();

	Label yourProfil = new Label("Dein Profil");
	Label userName = new Label("Name: ");
	Label emailAdress = new Label("E-Mail: ");

	Button editButton = new Button("Profil bearbeiten");
	Button deleteButton = new Button("Profil löschen");
	Button saveButton = new Button("Änderung speichern");
	Button nosaveButton = new Button();

	VerticalPanel contentPanel = new VerticalPanel();
	
	HorizontalPanel yourProfilPanel = new HorizontalPanel();
	HorizontalPanel userNamePanel = new HorizontalPanel();
	HorizontalPanel emailAdressPanel = new HorizontalPanel();
	HorizontalPanel buttonPanel = new HorizontalPanel();
	
}
