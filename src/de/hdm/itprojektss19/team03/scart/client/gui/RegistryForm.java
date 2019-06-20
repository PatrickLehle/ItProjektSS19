package de.hdm.itprojektss19.team03.scart.client.gui;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
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
	Label infoLabel = new Label("Wähle einen Nutzernamen");
	TextBox nameTextbox = new TextBox();
	TextBox emailTextbox = new TextBox();
	Button save = new Button("Registrieren");

	protected void onLoad() {
	}

	/**
	 * Konstruktor fuer die RegistryForm-Klasse.
	 * 
	 * @param u
	 *            user der eigenloggt ist
	 */
	public RegistryForm(User u) {
		user = u;
		welcome.setStyleName("h1");
		nameTextbox.setFocus(true);
		nameTextbox.setStyleName("textbox");
		emailTextbox.setValue(user.getEmail());
		emailTextbox.setReadOnly(true);
		emailTextbox.setStyleName("textbox");

		this.setVerticalAlignment(ALIGN_TOP);
		this.setHorizontalAlignment(ALIGN_CENTER);
		this.add(welcome);
		this.add(infoLabel);
		this.add(emailTextbox);
		this.add(nameTextbox);
		this.add(save);

		save.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent e) {
				if (checkName(nameTextbox.getValue())) {
					saveUser(user);
				} else {
					infoLabel.setText(
							"Der Name darf keine Sonderzeichen enthalten und muss aus 3 bis 10 Zeichen bestehen");
				}
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

	/**
	 * UEberprueft korrekte syntax der Eingabe
	 * 
	 * @param str
	 * @return true, wenn der Name passt
	 */
	private Boolean checkName(String str) {
		// \w -> nur Buchstaben oder Zahlen
		if (str.matches("^[a-zA-Z]*$") == false) {
			return false;
		} else if (str.length() > 10) {
			return false;
		} else if (str.length() < 2) {
			return false;
		} else {
			return true;
		}
	}
}