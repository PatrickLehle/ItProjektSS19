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

public class ProfileForm {

	User user = new User();

	//TEXTBOXES==============================================
	TextBox userNameTB = new TextBox();
	TextBox emailAdressTB = new TextBox();

	//LABELS=================================================
	Label yourProfileLabel = new Label("Dein Profil");
	Label userNameDescLabel = new Label("Name: ");
	Label emailAdressDescLabel = new Label("E-Mail: ");

	//BUTTONS================================================

	Button editButton = new Button("Profil bearbeiten");
	Button deleteButton = new Button("Profil löschen");
	Button saveButton = new Button("Änderung speichern");
	Button nosaveButton = new Button();

	//PANELS=================================================
	VerticalPanel contentPanel = new VerticalPanel();

	HorizontalPanel yourProfilePanel = new HorizontalPanel();
	HorizontalPanel userNamePanel = new HorizontalPanel();
	HorizontalPanel emailAdressPanel = new HorizontalPanel();
	HorizontalPanel buttonPanel = new HorizontalPanel();

	EditorServiceAsync editorService = ClientsideSettings.getEditor();

	/**
	 *
	 * onLoad-Methode: ...
	 *
	 */

	public void onLoad() {

		buildProfile();
		
		editorService.getUserById(user.getId(), new FindUserCallback());

	}

	/**
	 *
	 * buildProfile-Methode: ...
	 *
	 */

	public void buildProfile() {

		yourProfilePanel.add(yourProfileLabel);
		userNamePanel.add(userNameDescLabel);
		Label userNameContLabel = new Label(user.getUsername());
		userNamePanel.add(userNameContLabel);
		emailAdressPanel.add(emailAdressDescLabel);
		Label emailAdressContLabel = new Label(user.getEmail());
		emailAdressPanel.add(emailAdressContLabel);

		buttonPanel.add(editButton);
		buttonPanel.add(deleteButton);

		contentPanel.add(yourProfilePanel);
		contentPanel.add(userNamePanel);
		contentPanel.add(emailAdressPanel);
		contentPanel.add(buttonPanel);

		editButton.addClickHandler(new EditButtonClickHandler());
		saveButton.addClickHandler(new SaveButtonClickHandler());
		deleteButton.addClickHandler(new DeleteButtonClickHandler());

	}

	/**
	 *
	 * ClickHandler: ...
	 *
	 */

	class EditButtonClickHandler implements ClickHandler {

		public void onClick(ClickEvent event) {

			userNameTB.setText(user.getUsername());
			userNamePanel.add(userNameTB);

			emailAdressTB.setText(user.getEmail());
			emailAdressPanel.add(emailAdressTB);

			buttonPanel.remove(editButton);
			buttonPanel.remove(deleteButton);
			contentPanel.add(saveButton);

		}

	}

	class SaveButtonClickHandler implements ClickHandler {

		public void onClick(ClickEvent event) {

			DialogBox db = new DialogBox();
			VerticalPanel vp = new VerticalPanel();
			HorizontalPanel hp = new HorizontalPanel();
			Button yB = new Button("Ja", new YesSaveButtonClickHandler(db));
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
			VerticalPanel vp = new VerticalPanel();
			HorizontalPanel hp = new HorizontalPanel();
			Button yB = new Button("Ja", new YesDeleteButtonClickHandler(db));
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

			dbox.hide();
			dbox.clear();
			dbox.removeFromParent();
			dbox.setAnimationEnabled(false);
			dbox.setGlassEnabled(false);

		}

	}

	class YesDeleteButtonClickHandler implements ClickHandler {

		DialogBox dbox = new DialogBox();

		public YesDeleteButtonClickHandler (DialogBox db) {

			this.dbox = db;

		}

		public void onClick(ClickEvent event) {

			editorService.deleteUser(user, new DeleteUserCallback());

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

	/**
	 *
	 * Callbacks: ...
	 *
	 */

	class FindUserByGMailCallback  implements AsyncCallback<User> {

		@Override
		public void onFailure(Throwable arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess(User arg0) {
			// TODO Auto-generated method stub

			String newUserName = userNameTB.getText();
			String newEmailAdress = emailAdressTB.getText();

			editorService.createUser(newUserName, newEmailAdress, new UpdateUserCallback());

		}

	}

	class UpdateUserCallback implements AsyncCallback<User> {

		@Override
		public void onFailure(Throwable arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess(User arg0) {
			// TODO Auto-generated method stub

			buildProfile();

		}

	}

	class DeleteUserCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess(Void arg0) {
			// TODO Auto-generated method stub

			Window.alert("Ihr Profil wurde erfolgreich gelöscht!");

		}

	}
	
	class FindUserCallback implements AsyncCallback<User> {

		@Override
		public void onFailure(Throwable arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSuccess(User arg0) {
			// TODO Auto-generated method stub
			
			buildProfile();
			
		}
		
	}

}

