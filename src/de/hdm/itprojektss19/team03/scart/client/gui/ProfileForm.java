package de.hdm.itprojektss19.team03.scart.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektss19.team03.scart.client.ClientsideSettings;
import de.hdm.itprojektss19.team03.scart.shared.EditorServiceAsync;
import de.hdm.itprojektss19.team03.scart.shared.bo.User;

/**
 * ProfileForm: ...
 * 
 * 
 * @author vanduyho
 *
 */

<<<<<<< HEAD
public class ProfileForm {

	User user = new User();
	
	//TEXTBOXES==============================================
	TextBox userNameTB = new TextBox();
	TextBox emailAdressTB = new TextBox();

	//LABELS=================================================
	Label yourProfileLabel = new Label("Dein Profil");
	Label userNameLabel = new Label("Name: ");
	Label emailAdressLabel = new Label("E-Mail: ");
	
	//BUTTONS================================================
=======
public class ProfileForm extends VerticalPanel {

	public ProfileForm(User u) {
		user = u;
		Window.alert(u.getUsername());
	}

	User user = new User();

	// TEXTBOXES==============================================
	TextBox userNameTB = new TextBox();
	TextBox emailAdressTB = new TextBox();

	// LABELS=================================================
	Label yourProfileLabel = new Label("Dein Profil");
	Label userNameLabel = new Label("Name: ");
	Label emailAdressLabel = new Label("E-Mail: ");

	// BUTTONS================================================
>>>>>>> dev

	Button editButton = new Button("Profil bearbeiten");
	Button deleteButton = new Button("Profil löschen");
	Button saveButton = new Button("Änderung speichern");
	Button nosaveButton = new Button();

<<<<<<< HEAD
	//PANELS=================================================
	VerticalPanel contentPanel = new VerticalPanel();
	
=======
	// PANELS=================================================
	VerticalPanel contentPanel = new VerticalPanel();

>>>>>>> dev
	HorizontalPanel yourProfilePanel = new HorizontalPanel();
	HorizontalPanel userNamePanel = new HorizontalPanel();
	HorizontalPanel emailAdressPanel = new HorizontalPanel();
	HorizontalPanel buttonPanel = new HorizontalPanel();
<<<<<<< HEAD
	
	EditorServiceAsync editorService = ClientsideSettings.getEditor();
	
=======

	EditorServiceAsync editorService = ClientsideSettings.getEditor();

>>>>>>> dev
	/**
	 * 
	 * onLoad-Methode: ...
	 *
	 */
<<<<<<< HEAD
	
	public void onLoad() {
		
		buildProfile();
		
	}
	
=======

	public void onLoad() {

		buildProfile();

	}

>>>>>>> dev
	/**
	 * 
	 * buildProfile-Methode: ...
	 *
	 */
<<<<<<< HEAD
	
	public void buildProfile() {
		
		yourProfilePanel.add(yourProfileLabel);
		userNamePanel.add(userNameLabel);
		emailAdressPanel.add(emailAdressLabel);
		
		buttonPanel.add(editButton);
		buttonPanel.add(deleteButton);
		
=======

	public void buildProfile() {

		yourProfilePanel.add(yourProfileLabel);
		userNamePanel.add(userNameLabel);
		emailAdressPanel.add(emailAdressLabel);

		buttonPanel.add(editButton);
		buttonPanel.add(deleteButton);

>>>>>>> dev
		contentPanel.add(yourProfilePanel);
		contentPanel.add(userNamePanel);
		contentPanel.add(emailAdressPanel);
		contentPanel.add(buttonPanel);
<<<<<<< HEAD
		
=======

>>>>>>> dev
		editButton.addClickHandler(new EditButtonClickHandler());
		saveButton.addClickHandler(new SaveButtonClickHandler());
		deleteButton.addClickHandler(new DeleteButtonClickHandler());

<<<<<<< HEAD
	}
	
=======
		this.add(contentPanel);

	}

>>>>>>> dev
	/**
	 * 
	 * ClickHandler: ...
	 *
	 */
<<<<<<< HEAD
	
=======

>>>>>>> dev
	class EditButtonClickHandler implements ClickHandler {

		public void onClick(ClickEvent event) {

			userNameTB.setText(user.getUsername());
			userNamePanel.add(userNameTB);

			emailAdressTB.setText(user.getEmail());
			emailAdressPanel.add(emailAdressTB);
<<<<<<< HEAD
			
=======

>>>>>>> dev
			buttonPanel.remove(editButton);
			buttonPanel.remove(deleteButton);
			contentPanel.add(saveButton);

		}
<<<<<<< HEAD
		
	}
	
	class SaveButtonClickHandler implements ClickHandler {
		
		public void onClick(ClickEvent event) {
			
=======

	}

	class SaveButtonClickHandler implements ClickHandler {

		public void onClick(ClickEvent event) {

>>>>>>> dev
			DialogBox db = new DialogBox();
			VerticalPanel vp = new VerticalPanel();
			HorizontalPanel hp = new HorizontalPanel();
			Button yB = new Button("Ja", new YesSaveButtonClickHandler(db));
			Button nB = new Button("Nein", new NoButtonClickHandler(db));
<<<<<<< HEAD
			Label l = new HTML(
					"<h1> Änderung speichern </h1> <p> Möchten Sie die Änderung speichern? </p> <br>");
			
=======
			Label l = new HTML("<h1> Änderung speichern </h1> <p> Möchten Sie die Änderung speichern? </p> <br>");

>>>>>>> dev
			vp.add(l);
			hp.add(yB);
			hp.add(nB);
			vp.add(hp);

			db.setGlassEnabled(true);
			db.setAnimationEnabled(true);
			db.center();
			db.show();

			db.add(vp);
<<<<<<< HEAD
			
		}
		
	}
	
	class DeleteButtonClickHandler implements ClickHandler {
		
		public void onClick(ClickEvent event) {
			
=======

		}

	}

	class DeleteButtonClickHandler implements ClickHandler {

		public void onClick(ClickEvent event) {

>>>>>>> dev
			DialogBox db = new DialogBox();
			VerticalPanel vp = new VerticalPanel();
			HorizontalPanel hp = new HorizontalPanel();
			Button yB = new Button("Ja", new YesDeleteButtonClickHandler(db));
			Button nB = new Button("Nein", new NoButtonClickHandler(db));
<<<<<<< HEAD
			Label l = new HTML(
					"<h1> Profil löschen </h1> <p> Möchten Sie Ihr Profil endgültig löschen? </p> <br>");
			
=======
			Label l = new HTML("<h1> Profil löschen </h1> <p> Möchten Sie Ihr Profil endgültig löschen? </p> <br>");

>>>>>>> dev
			vp.add(l);
			hp.add(yB);
			hp.add(nB);
			vp.add(hp);

			db.setGlassEnabled(true);
			db.setAnimationEnabled(true);
			db.center();
			db.show();

			db.add(vp);
<<<<<<< HEAD
			
		}
		
	}
	
	class YesSaveButtonClickHandler implements ClickHandler {

		DialogBox dbox = new DialogBox();
		
		public YesSaveButtonClickHandler (DialogBox db) {
		
			this.dbox = db;
			
		}
		
		public void onClick(ClickEvent event) {
			
			String newEmailAdress = emailAdressTB.getText();
			
			if (newEmailAdress.length() > 20) {
				
				Window.alert("Ihre E-Mail darf nicht länger als 20 Zeichen sein!");
			
			}
			else {
				
				editorService.getUserByGMail(newEmailAdress, new FindUserByGMailCallback());
				
			}
			
=======

		}

	}

	class YesSaveButtonClickHandler implements ClickHandler {

		DialogBox dbox = new DialogBox();

		public YesSaveButtonClickHandler(DialogBox db) {

			this.dbox = db;

		}

		public void onClick(ClickEvent event) {

			String newEmailAdress = emailAdressTB.getText();

			if (newEmailAdress.length() > 20) {

				Window.alert("Ihre E-Mail darf nicht länger als 20 Zeichen sein!");

			} else {

				editorService.getUserByGMail(newEmailAdress, new FindUserByGMailCallback());

			}

>>>>>>> dev
			dbox.hide();
			dbox.clear();
			dbox.removeFromParent();
			dbox.setAnimationEnabled(false);
			dbox.setGlassEnabled(false);
<<<<<<< HEAD
			
		}
		
	}
	
	class YesDeleteButtonClickHandler implements ClickHandler {

		DialogBox dbox = new DialogBox();
		
		public YesDeleteButtonClickHandler (DialogBox db) {
		
			this.dbox = db;

		}
		
		public void onClick(ClickEvent event) {
			
			editorService.deleteUser(user, new DeleteUserCallback());
			
=======

		}

	}

	class YesDeleteButtonClickHandler implements ClickHandler {

		DialogBox dbox = new DialogBox();

		public YesDeleteButtonClickHandler(DialogBox db) {

			this.dbox = db;

		}

		public void onClick(ClickEvent event) {

			editorService.deleteUser(user, new DeleteUserCallback());

>>>>>>> dev
			dbox.hide();
			dbox.clear();
			dbox.removeFromParent();
			dbox.setAnimationEnabled(false);
			dbox.setGlassEnabled(false);

		}
<<<<<<< HEAD
		
	}
	
	class NoButtonClickHandler implements ClickHandler {

		DialogBox dbox = new DialogBox();
		
		public NoButtonClickHandler (DialogBox db) {
			
			this.dbox = db;
			
		}
		
=======

	}

	class NoButtonClickHandler implements ClickHandler {

		DialogBox dbox = new DialogBox();

		public NoButtonClickHandler(DialogBox db) {

			this.dbox = db;

		}

>>>>>>> dev
		public void onClick(ClickEvent event) {

			dbox.hide();
			dbox.clear();
			dbox.removeFromParent();
			dbox.setAnimationEnabled(false);
			dbox.setGlassEnabled(false);

		}
<<<<<<< HEAD
	
	}
	
=======

	}

>>>>>>> dev
	/**
	 * 
	 * Callbacks: ...
	 *
	 */
<<<<<<< HEAD
	
	class FindUserByGMailCallback  implements AsyncCallback<User> {
		
		@Override
		public void onFailure(Throwable arg0) {
			// TODO Auto-generated method stub
			
=======

	class FindUserByGMailCallback implements AsyncCallback<User> {

		@Override
		public void onFailure(Throwable arg0) {
			// TODO Auto-generated method stub

>>>>>>> dev
		}

		@Override
		public void onSuccess(User arg0) {
			// TODO Auto-generated method stub
<<<<<<< HEAD
			
			String newUserName = userNameTB.getText();
			String newEmailAdress = emailAdressTB.getText();
			
			editorService.createUser(newUserName, newEmailAdress, new UpdateUserCallback());
			
		}
		
	}
	
	class UpdateUserCallback implements AsyncCallback <User> {
=======

			String newUserName = userNameTB.getText();
			String newEmailAdress = emailAdressTB.getText();

			editorService.createUser(newEmailAdress, new UpdateUserCallback());

		}

	}

	class UpdateUserCallback implements AsyncCallback<User> {
>>>>>>> dev

		@Override
		public void onFailure(Throwable arg0) {
			// TODO Auto-generated method stub
<<<<<<< HEAD
			
=======

>>>>>>> dev
		}

		@Override
		public void onSuccess(User arg0) {
			// TODO Auto-generated method stub
<<<<<<< HEAD
			
			buildProfile();
			
		}
			
	}
	
	class DeleteUserCallback implements AsyncCallback <Void> {
=======

			buildProfile();

		}

	}

	class DeleteUserCallback implements AsyncCallback<Void> {
>>>>>>> dev

		@Override
		public void onFailure(Throwable arg0) {
			// TODO Auto-generated method stub
<<<<<<< HEAD
			
=======

>>>>>>> dev
		}

		@Override
		public void onSuccess(Void arg0) {
			// TODO Auto-generated method stub
<<<<<<< HEAD
			
			Window.alert("Ihr Profil wurde erfolgreich gelöscht!");
			
		}
		
	}
	
=======

			Window.alert("Ihr Profil wurde erfolgreich gelöscht!");

		}

	}

>>>>>>> dev
}
