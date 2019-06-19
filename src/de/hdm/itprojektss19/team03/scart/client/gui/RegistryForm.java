package de.hdm.itprojektss19.team03.scart.client.gui;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektss19.team03.scart.client.ClientsideSettings;
import de.hdm.itprojektss19.team03.scart.shared.EditorServiceAsync;
import de.hdm.itprojektss19.team03.scart.shared.bo.User;

/**
 * Die RegistryForm wird zum Login aufgerufen, wenn der User die Applikation zum
 * ersten mal verwendet.
 * 
 * @author PatrickLehle, Marco Dell'Oso
 */
public class RegistryForm extends VerticalPanel {

	private EditorServiceAsync editorService = ClientsideSettings.getEditor();
	private final User user;

	Label welcome = new Label("Wie es scheint bist du neu hier bei SCart, herzlich Wilkommen!");
	TextBox nameTextbox = new TextBox();
	TextBox emailTextbox = new TextBox();
	Button save = new Button("Registrieren");

	protected void onLoad() {
		nameTextbox.addChangeHandler(new ChangeHandler() {

			/**
			 * Der ChangeHandler des Namen Textfeldes prueft, ob der Name den richtlinien
			 * entspricht
			 */
			public void onChange(ChangeEvent e) {
				// \w -> nur Buchstaben oder Zahlen
				if (nameTextbox.getValue().matches("^[a-zA-Z]*$") == false) {
					nameTextbox.setValue("");
					save.setEnabled(false);
					Window.alert("Es sind nur Buchstaben erlaubt!");
				} else if (nameTextbox.getValue().length() > 10) {
					nameTextbox.setValue("");
					save.setEnabled(false);
					Window.alert("Es sind maximal 10 Zeichen erlaubt!");
				} else {
					save.setEnabled(true);
				}

			}
		});
	}

	/**
	 * Konstruktor fuer die RegistryForm-Klasse.
	 * 
	 * @param u
	 *            user der eigenloggt ist
	 */
	public RegistryForm(User u) {
		user = u;
		nameTextbox.setFocus(true);
		nameTextbox.setTitle("Wähle einen Nutzernamen");
		emailTextbox.setValue(user.getEmail());
		emailTextbox.setReadOnly(true);
		save.setEnabled(false);

		this.add(welcome);
		this.add(emailTextbox);
		this.add(nameTextbox);
		this.add(save);

		save.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent e) {
				saveUser(user);
			}
		});

	}

	public void saveUser(User u) {
		u.setUsername(nameTextbox.getValue());
		editorService.createUser(u.getUsername(), u.getEmail(), new AsyncCallback<User>() {

			public void onSuccess(User arg0) {
				Window.alert("Register");
			}

			public void onFailure(Throwable e) {
				Window.alert("Registrierung fehlgeschlagen " + e.getMessage());

			}
		});
	};

}
