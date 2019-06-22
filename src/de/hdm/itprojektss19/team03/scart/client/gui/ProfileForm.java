package de.hdm.itprojektss19.team03.scart.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.client.GUITest2.DeleteButtonClickHandler;
import de.hdm.client.GUITest2.EditButtonClickHandler;
import de.hdm.client.GUITest2.NoButtonClickHandler;
import de.hdm.client.GUITest2.SaveButtonClickHandler;
import de.hdm.client.GUITest2.YesDeleteButtonClickHandler;
import de.hdm.client.GUITest2.YesSaveButtonClickHandler;
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
	
	class SaveButtonClickHandler implements ClickHandler {
		
		public void onClick(ClickEvent event) {
			
			DialogBox db = new DialogBox();
			TextBox t1 = new TextBox();
			TextBox t2 = new TextBox();
			VerticalPanel vp = new VerticalPanel();
			HorizontalPanel hp = new HorizontalPanel();
			Button yB = new Button("Ja", new YesSaveButtonClickHandler(db, t1, t2));
			Button nB = new Button("Nein", new NoButtonClickHandler(db));
			Label l = new HTML(
					"<h1> Änderung speichern </h1> <p> Möchten Sie die Änderung speichern? </p> <br>");
			
			vp.add(l);
			hp.add(yB);
			hp.add(nB);
			vp.add(hp);

			db.setGlassEnabled(true);
			db.setAnimationEnabled(true);
			db.center();
			db.show();

			db.add(vp);
			
		}
		
	}
	
	class DeleteButtonClickHandler implements ClickHandler {
		
		public void onClick(ClickEvent event) {
			
			DialogBox db = new DialogBox();
			TextBox t1 = new TextBox();
			TextBox t2 = new TextBox();
			VerticalPanel vp = new VerticalPanel();
			HorizontalPanel hp = new HorizontalPanel();
			Button yB = new Button("Ja", new YesDeleteButtonClickHandler(db, t1, t2));
			Button nB = new Button("Nein", new NoButtonClickHandler(db));
			Label l = new HTML(
					"<h1> Profil löschen </h1> <p> Möchten Sie Ihr Profil endgültig löschen? </p> <br>");
			
			vp.add(l);
			hp.add(yB);
			hp.add(nB);
			vp.add(hp);

			db.setGlassEnabled(true);
			db.setAnimationEnabled(true);
			db.center();
			db.show();

			db.add(vp);
			
		}
	}
	
	class YesSaveButtonClickHandler implements ClickHandler {

		DialogBox dbox = new DialogBox();
		TextBox userName = new TextBox();
		TextBox emailAdress = new TextBox();
		
		public YesSaveButtonClickHandler (DialogBox db, TextBox name, TextBox email) {
		
			this.dbox = db;
			this.userName = name;
			this.emailAdress = email;
			
		}
		
		public void onClick(ClickEvent event) {
			
			//this.userName.setText("neuer Name");
			//this.emailAdress.setText("neue E-Mail");
			
			userNamePanel.remove(userNameTB);
			TextBox userName = new TextBox();
			userName.setText("neuer Name");
			userNamePanel.add(userName);
			
			emailAdressPanel.remove(emailAdress);
			TextBox emailAdress = new TextBox();
			emailAdress.setText("neue E-Mail");
			emailAdressPanel.add(emailAdress);
			
			dbox.hide();
			dbox.clear();
			dbox.removeFromParent();
			dbox.setAnimationEnabled(false);
			dbox.setGlassEnabled(false);

		}
	}
	
	class YesDeleteButtonClickHandler implements ClickHandler {

		DialogBox dbox = new DialogBox();
		TextBox userName = new TextBox();
		TextBox emailAdress = new TextBox();
		
		public YesDeleteButtonClickHandler (DialogBox db, TextBox name, TextBox email) {
		
			this.dbox = db;
			this.userName = name;
			this.emailAdress = email;
			
		}
		
		public void onClick(ClickEvent event) {
			
			this.userName.setText("");
			this.emailAdress.setText("");
			
			dbox.hide();
			dbox.clear();
			dbox.removeFromParent();
			dbox.setAnimationEnabled(false);
			dbox.setGlassEnabled(false);

		}
	}
	
	class NoButtonClickHandler implements ClickHandler {

		DialogBox dbox = new DialogBox();
		
		public NoButtonClickHandler (DialogBox db) {
			
			this.dbox = db;
			
		}
		
		public void onClick(ClickEvent event) {

			dbox.hide();
			dbox.clear();
			dbox.removeFromParent();
			dbox.setAnimationEnabled(false);
			dbox.setGlassEnabled(false);

		}
	
	}
	
}
