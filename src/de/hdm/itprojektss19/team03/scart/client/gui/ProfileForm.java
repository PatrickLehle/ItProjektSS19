package de.hdm.itprojektss19.team03.scart.client.gui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektss19.team03.scart.client.ClientsideSettings;
import de.hdm.itprojektss19.team03.scart.shared.EditorServiceAsync;
import de.hdm.itprojektss19.team03.scart.shared.bo.User;

/**
 * ProfileForm: ...
 *  
 * @author vanduyho
 */

public class ProfileForm extends VerticalPanel {

	User user = new User();

	// TEXTBOXES==============================================
	TextBox userNameTB = new TextBox();
	TextBox emailAdressTB = new TextBox();

	// LABELS=================================================
	Label yourProfileLabel = new Label("Dein Profil");
	Label userNameDescLabel = new Label("Name:");
	Label userNameContLabel = new Label();
	Label emailAdressDescLabel = new Label("E-Mail:");
	Label emailAdressContLabel = new Label();

	// BUTTONS================================================

	Button editButton = new Button("Profil bearbeiten");
	Button deleteButton = new Button("Profil löschen");
	Button saveButton = new Button("Änderung speichern");

	// PANELS=================================================
	VerticalPanel contentPanel = new VerticalPanel();
	HorizontalPanel outerPanel = new HorizontalPanel();
	HorizontalPanel yourProfilePanel = new HorizontalPanel();
	HorizontalPanel userNamePanel = new HorizontalPanel();
	HorizontalPanel emailAdressPanel = new HorizontalPanel();
	HorizontalPanel buttonPanel = new HorizontalPanel();

	EditorServiceAsync editorService = ClientsideSettings.getEditor();
	
	/**
	 * 
	 * Konstruktor: ...
	 *
	 */
	
	public ProfileForm(User u) {
		
		this.user = u;
		
	}

//METHODS=======================================================	
	/** Diese Methode wird automatisch bei dem Aufruf der GUI-Seite gestartet
	 */
	public void onLoad() {
		buildProfile();
	}

	/** Die Methode zum aufbauen der GUI-Seite
	 */
	public void buildProfile() {
		
		yourProfilePanel.add(yourProfileLabel);
		userNamePanel.add(userNameDescLabel);
		userNameContLabel.setText(user.getEmail());
		userNamePanel.add(userNameContLabel);
		emailAdressPanel.add(emailAdressDescLabel);
		emailAdressContLabel.setText(user.getUsername());
		emailAdressPanel.add(emailAdressContLabel);

		buttonPanel.add(editButton);
		buttonPanel.add(deleteButton);

		contentPanel.add(yourProfilePanel);
		contentPanel.add(userNamePanel);
		contentPanel.add(emailAdressPanel);
		contentPanel.add(buttonPanel);

		editButton.addClickHandler(new EditButtonClickHandler());
		deleteButton.addClickHandler(new DeleteButtonClickHandler());

		editorService.generateIdenticons(user.getEmail(), 100, 100, new getImageCallback());
		this.addStyleName("inner-content");
		outerPanel.add(contentPanel);
		this.add(outerPanel);

	}

	/** ClickHandler fuer den "edit"-Button um das eigene Profil zu bearbeiten
	 *
	 */
	class EditButtonClickHandler implements ClickHandler {
		
		public void onClick(ClickEvent event) {
			
			userNameTB.setText(user.getEmail());
			userNamePanel.add(userNameTB);
			userNamePanel.remove(userNameContLabel);
			
			emailAdressTB.setText(user.getUsername());
			emailAdressPanel.add(emailAdressTB);
			emailAdressPanel.remove(emailAdressContLabel);
			
			buttonPanel.remove(editButton);
			buttonPanel.remove(deleteButton);
			contentPanel.add(saveButton);
			
			saveButton.addClickHandler(new SaveButtonClickHandler());

		}

	}

	/** ClickHandler fuer den "save"-Button, um Aenderungen abzuspeichern und
	 * anschliessend auch in der DB zu aktualisieren.
	 */
	class SaveButtonClickHandler implements ClickHandler {

		public void onClick(ClickEvent event) {

			DialogBox db = new DialogBox();
			VerticalPanel vp = new VerticalPanel();
			HorizontalPanel hp = new HorizontalPanel();
			Button yB = new Button("Ja", new YesSaveButtonClickHandler(db));
			Button nB = new Button("Nein", new NoButtonClickHandler(db));
			Label l = new HTML("<h1> Änderung speichern </h1> <p> Möchten Sie die Änderung speichern? </p> <br>");

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

	/** ClickHandler fuer den "delete"-Button um das eigene Profil zu loeschen
	 * 
	 */
	class DeleteButtonClickHandler implements ClickHandler {

		public void onClick(ClickEvent event) {

			DialogBox db = new DialogBox();
			VerticalPanel vp = new VerticalPanel();
			HorizontalPanel hp = new HorizontalPanel();
			Button yB = new Button("Ja", new YesDeleteButtonClickHandler(db));
			Button nB = new Button("Nein", new NoButtonClickHandler(db));
			Label l = new HTML("<h1> Profil löschen </h1> <p> Möchten Sie Ihr Profil endgültig löschen? </p> <br>");

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

	/** ClickHandler um die Aenderungen zu bestaetigen
	 */
	class YesSaveButtonClickHandler implements ClickHandler {

		DialogBox dbox = new DialogBox();

		public YesSaveButtonClickHandler(DialogBox db) {

			this.dbox = db;

		}

		public void onClick(ClickEvent event) {

			if (emailAdressTB.getText().length() > 20) {

				Window.alert("Ihre E-Mail darf nicht länger als 20 Zeichen sein!");

			} else {
				
				user.setUsername(userNameTB.getValue());
				user.setEmail(emailAdressTB.getValue());
				
				editorService.updateUser(user, new UpdateUserCallback());

			}

			dbox.hide();
			dbox.clear();
			dbox.removeFromParent();
			dbox.setAnimationEnabled(false);
			dbox.setGlassEnabled(false);

		}

	}

	/** ClickHandler um Aenderungen des eigenen Profils zu bestaetigen
	 * 	Anschliessend wird der User in der DB geloescht.
	 */
	class YesDeleteButtonClickHandler implements ClickHandler {

		DialogBox dbox = new DialogBox();

		public YesDeleteButtonClickHandler(DialogBox db) {

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
	
	/** ClickHandler um Aenderungen am eigenen Profil nicht zu uebernehmen.
	 *
	 */
	class NoButtonClickHandler implements ClickHandler {

		DialogBox dbox = new DialogBox();

		public NoButtonClickHandler(DialogBox db) {

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

	/** CallBack um das eigene Profilbild zu bekommen
	 */
	class getImageCallback implements AsyncCallback<String> {

		public void onFailure(Throwable t) {
			
			GWT.log("Failed to get Profile Image: " + t);
			
		}

		public void onSuccess(String s) {
			
			VerticalPanel panel = new VerticalPanel();
			panel.setStyleName("profile-img-big");
			Image image = new Image();
			image.setUrl("data:image/png;base64," + s);
			panel.clear();
			panel.add(image);
			outerPanel.clear();
			outerPanel.add(panel);
			outerPanel.add(contentPanel);
			
		}

	}

	class UpdateUserCallback implements AsyncCallback<User> {

		@Override
		public void onFailure(Throwable arg0) {
			// TODO Auto-generated method stub
			
			Window.alert("Ihr Profil wurde nicht erfolgreich geändert!");
			
			Window.alert(arg0.getMessage());
			
		}

		@Override
		public void onSuccess(User arg0) {

			Window.alert("Ihr Profil wurde erfolgreich geändert!");

		}
	}

	/** CallBack um einen User in der DB zu loeschen.
	 * 	Bei Erfolg erscheint ein Window-Alert.
	 */
	class DeleteUserCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable arg0) {
			// TODO Auto-generated method stub
			
			Window.alert("Ihr Profil wurde nicht erfolgreich gelöscht!");
			

		}

		@Override
		public void onSuccess(Void arg0) {
			Window.alert("Ihr Profil wurde erfolgreich gelöscht!");
		}
	}
}
