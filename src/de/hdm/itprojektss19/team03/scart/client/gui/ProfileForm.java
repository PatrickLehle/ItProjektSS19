package de.hdm.itprojektss19.team03.scart.client.gui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Grid;
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

	// PANELS=================================================
	HorizontalPanel contentPanel = new HorizontalPanel();
	VerticalPanel yourProfilePanel = new VerticalPanel();
	HorizontalPanel buttonPanel = new HorizontalPanel();
	VerticalPanel profilePicPanel = new VerticalPanel();
	Grid grid = new Grid(2, 2);

	EditorServiceAsync editorService = ClientsideSettings.getEditor();

	/**
	 * Konstruktor der Klasse
	 * 
	 * @param u User Objekt des eingeloggten users
	 */
	public ProfileForm(User u) {

		this.user = u;

	}

	/**
	 * Diese Methode wird automatisch bei dem Aufruf der GUI-Seite gestartet
	 */
	public void onLoad() {
		buildProfile();
	}

	/**
	 * Die Methode zum aufbauen der GUI-Seite
	 */
	public void buildProfile() {
		this.clear();
		contentPanel = new HorizontalPanel();
		yourProfilePanel = new VerticalPanel();
		buttonPanel = new HorizontalPanel();
		profilePicPanel = new VerticalPanel();

		grid = new Grid(2, 2);

		yourProfileLabel.addStyleName("h2");
		yourProfileLabel.addStyleName("align-left");
		buttonPanel.clear();

		userNameContLabel.setText(user.getUsername());
		emailAdressContLabel.setText(user.getEmail());

		grid.setWidget(0, 0, userNameDescLabel);
		grid.setWidget(0, 1, userNameContLabel);
		grid.setWidget(1, 0, emailAdressDescLabel);
		grid.setWidget(1, 1, emailAdressContLabel);
		grid.setCellPadding(2);

		buttonPanel.add(editButton);
		buttonPanel.add(deleteButton);

		editButton.setStyleName("button");
		deleteButton.setStyleName("button");

		yourProfilePanel.add(yourProfileLabel);
		yourProfilePanel.add(grid);
		contentPanel.add(profilePicPanel);
		contentPanel.add(yourProfilePanel);

		editButton.addClickHandler(new EditButtonClickHandler());
		deleteButton.addClickHandler(new DeleteButtonClickHandler());

		editorService.generateIdenticons(user.getEmail(), 100, 100, new getImageCallback(profilePicPanel));
		this.addStyleName("inner-content");
		this.add(contentPanel);
		this.add(buttonPanel);

	}

	/**
	 * ClickHandler fuer den "edit"-Button um das eigene Profil zu bearbeiten
	 *
	 */
	class EditButtonClickHandler implements ClickHandler {

		public void onClick(ClickEvent event) {
			Button saveButton = new Button("Änderung speichern");
			Button cancelButton = new Button("Abbrechen");

			buttonPanel.clear();
			userNameTB.setText(user.getUsername());
			emailAdressTB.setText(user.getEmail());

			grid.setWidget(0, 1, userNameTB);
			grid.setWidget(1, 1, emailAdressTB);

			buttonPanel.add(saveButton);
			buttonPanel.add(cancelButton);
			cancelButton.setStyleName("button");
			saveButton.setStyleName("button");
			cancelButton.addClickHandler(new NoButtonClickHandler());
			saveButton.addClickHandler(new SaveButtonClickHandler());
		}

	}

	/**
	 * ClickHandler fuer den "save"-Button, um Aenderungen abzuspeichern und
	 * anschliessend auch in der DB zu aktualisieren.
	 */
	class SaveButtonClickHandler implements ClickHandler {

		public void onClick(ClickEvent event) {

			DialogBox db = new DialogBox();
			VerticalPanel vp = new VerticalPanel();
			HorizontalPanel hp = new HorizontalPanel();
			Button yB = new Button("Ja", new YesSaveButtonClickHandler(db));
			Button nB = new Button("Nein", new NoButtonClickHandler(db));
			Label l = new HTML("<p> Möchten Sie die Änderung speichern? </p> <br>");
			db.setText("Änderung speichern");
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

	/**
	 * ClickHandler fuer den "delete"-Button um das eigene Profil zu loeschen
	 * 
	 */
	class DeleteButtonClickHandler implements ClickHandler {

		public void onClick(ClickEvent event) {

			DialogBox db = new DialogBox();
			VerticalPanel vp = new VerticalPanel();
			HorizontalPanel hp = new HorizontalPanel();
			Button yB = new Button("Ja", new YesDeleteButtonClickHandler(db));
			Button nB = new Button("Nein", new NoButtonClickHandler(db));
			Label l = new HTML("<p> Möchten Sie Ihr Profil endgültig löschen? </p><br>");
			db.setText("Profil löschen");
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

	/**
	 * ClickHandler um die Aenderungen zu bestaetigen
	 */
	class YesSaveButtonClickHandler implements ClickHandler {

		DialogBox dbox = new DialogBox();

		public YesSaveButtonClickHandler(DialogBox db) {

			this.dbox = db;

		}

		public void onClick(ClickEvent event) {

			if (!checkEmail(emailAdressTB.getText())) {
				Window.alert("Ungültige E-Mail!");
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

	/**
	 * ClickHandler um Aenderungen des eigenen Profils zu bestaetigen Anschliessend
	 * wird der User in der DB geloescht.
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

	/**
	 * ClickHandler, um Aenderungen am eigenen Profil nicht zu uebernehmen.
	 */
	class NoButtonClickHandler implements ClickHandler {

		DialogBox dbox;

		public NoButtonClickHandler(DialogBox db) {
			this.dbox = db;
		}

		public NoButtonClickHandler() {
		}

		public void onClick(ClickEvent event) {

			if (dbox != null) {
				dbox.hide();
				dbox.clear();
				dbox.removeFromParent();
				dbox.setAnimationEnabled(false);
				dbox.setGlassEnabled(false);
			}
			onLoad();

		}

	}

	/**
	 * CallBack, um das eigene Profilbild zu bekommen
	 */
	class getImageCallback implements AsyncCallback<String> {
		VerticalPanel panel;

		public getImageCallback(VerticalPanel p) {
			panel = p;
		}

		public void onFailure(Throwable t) {

			GWT.log("Failed to get Profile Image: " + t);

		}

		public void onSuccess(String s) {

			panel.setStyleName("profile-img-big");
			Image image = new Image();
			image.setUrl("data:image/png;base64," + s);
			panel.clear();
			panel.add(image);
		}

	}

	/**
	 * Callback, um das User Objekt zu updaten
	 * 
	 */
	class UpdateUserCallback implements AsyncCallback<User> {

		public void onFailure(Throwable t) {
			GWT.log("Ihr Profil wurde nicht erfolgreich geändert!: " + t);
		}

		public void onSuccess(User arg0) {
			onLoad();

		}
	}

	/**
	 * CallBack um einen User in der DB zu loeschen. Bei Erfolg erscheint ein
	 * Window-Alert.
	 */
	class DeleteUserCallback implements AsyncCallback<Void> {

		public void onFailure(Throwable arg0) {
			Window.alert("Ihr Profil wurde nicht erfolgreich gelöscht! " + arg0);

		}

		public void onSuccess(Void arg0) {
			Window.alert("Ihr Profil wurde erfolgreich gelöscht!");
			Window.Location.reload();
		}
	}

	/**
	 * Methode um die Eingabe der E-Mail zu ueberpruefen
	 * 
	 * @param email zu pruefende email
	 * @return true, wenn die E-Mail passt / false, wenn der Syntax nicht stimmt
	 */
	private Boolean checkEmail(String email) {
		if (!email.matches("^\\w+[\\w-\\.]*\\@\\w+((-\\w+)|(\\w*))\\.[a-z]{2,3}$")) {
			return false;
		} else {
			return true;
		}
	}
}
