package de.hdm.itprojektss19.team03.scart.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektss19.team03.scart.shared.bo.User;

/**
 * 
 * @author vanduyho
 *
 */

public class ProfilForm {
	
	User user = null;
	
	Label newProfile = null;
	Label userName = null;
	Label emailAdress = null;
	
	Button editButton = null;
	Button deleteButton = null;
	
	VerticalPanel contenPanel = null;
	HorizontalPanel newProfilePanel = null;
	HorizontalPanel userNamePanel = null;
	HorizontalPanel emailAdressPanel = null;
	HorizontalPanel buttonPanel = null;
	
	/**
	 * 
	 * Konstruktor
	 * 
	 */
	
	public ProfilForm (User u) {
		
		this.user = u;
		
	}

	/*
	 * 
	 * ClickHandler
	 * 
	 */

	class EditButtonClickHandler implements ClickHandler {
		
		ProfilForm profilForm = null;
	
		private EditButtonClickHandler (ProfilForm pf) {
		
			this.profilForm = pf;
			
	}
	
	
	public void onClick (ClickEvent event) {
		
		userNamePanel.remove(userName);
		TextBox userName = new TextBox();
		userName.setText(user.getUsername());
		userNamePanel.add(userName);
		
		emailAdressPanel.remove(emailAdress);
		TextBox emailAdress = new TextBox();
		emailAdress.setText(user.getEmail());
		emailAdressPanel.add(emailAdress);
		
	}
	
	
}



