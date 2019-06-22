package de.hdm.itprojektss19.team03.scart.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.client.GUITest2.DeleteButtonClickHandler;
import de.hdm.client.GUITest2.EditButtonClickHandler;
import de.hdm.client.GUITest2.SaveButtonClickHandler;
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
	
	
	public void onLoad() {
		
		buildProfil();
		
	}
	
	public void buildProfil() {
		
		
		yourProfilPanel.add(yourProfil);
		userNamePanel.add(userName);
		emailAdressPanel.add(emailAdress);
		buttonPanel.add(editButton);
		buttonPanel.add(deleteButton);
		contentPanel.add(yourProfilPanel);
		contentPanel.add(userNamePanel);
		contentPanel.add(emailAdressPanel);
		contentPanel.add(buttonPanel);
		
		editButton.addClickHandler(new EditButtonClickHandler());
		saveButton.addClickHandler(new SaveButtonClickHandler());
		deleteButton.addClickHandler(new DeleteButtonClickHandler());

	}
	
	class EditButtonClickHandler implements ClickHandler {

		public void onClick(ClickEvent event) {

			//userNamePanel.remove(userName);
			TextBox userNameTB = new TextBox();
			userNameTB.setText("");
			userNamePanel.add(userNameTB);

			//emailAdressPanel.remove(emailAdress);
			TextBox emailAdressTB = new TextBox();
			emailAdressTB.setText("alte E-Mail");
			emailAdressPanel.add(emailAdressTB);
			
			buttonPanel.remove(editButton);
			buttonPanel.remove(deleteButton);
			contentPanel.add(saveButton);

		}
		
	}
	
}
