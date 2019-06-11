package de.hdm.itprojektss19.team03.scart.client.gui;

import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Button;

/**
 * 
 * @author vanduyho
 *
 */

public class ProfileForm extends VerticalPanel {
	
	Label newProfile = new Label("Dein Profil: ");
	Label firstName = new Label("Vorname: ");
	Label LastName = new Label("Nachname :");

	Button editButton = new Button ("Bearbeiten");
	Button deleteButton = new Button("Löschen");
		
	

}
