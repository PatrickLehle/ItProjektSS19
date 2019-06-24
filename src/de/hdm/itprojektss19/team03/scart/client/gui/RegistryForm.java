package de.hdm.itprojektss19.team03.scart.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
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

	private Anchor logout = new Anchor();
	private FlowPanel buttons = new FlowPanel();
	private Label welcome = new Label("Wie es scheint bist du neu hier bei SCart, herzlich Wilkommen!");
	private Label infoLabel = new Label("Wähle einen Nutzernamen");
	private TextBox nameTextbox = new TextBox();
	private TextBox emailTextbox = new TextBox();
	private Button save = new Button("Registrieren");
	private Button logoutButton = new Button("Zurück zum Login");

	protected void onLoad() {
	}

	/**
	 * Konstruktor fuer die RegistryForm-Klasse.
	 * 
	 * @param u
	 *            user der eigenloggt ist
	 */
	public RegistryForm(User u, String logoutLink) {
		user = u;
		logout.setHref(logoutLink);
		welcome.setStyleName("h1");
		infoLabel.setStyleName("text");
		nameTextbox.setStyleName("textbox-big");
		emailTextbox.setValue(user.getEmail());
		emailTextbox.setReadOnly(true);
		emailTextbox.setStyleName("textbox-big");
		save.setStyleName("button");
		logoutButton.setStyleName("button");
		save.addClickHandler(saveClickHandler);

		logout.getElement().appendChild(logoutButton.getElement());
		buttons.add(logout);
		buttons.add(save);

		this.setVerticalAlignment(ALIGN_TOP);
		this.setHorizontalAlignment(ALIGN_CENTER);
		this.add(welcome);
		this.add(infoLabel);
		this.add(nameTextbox);
		this.add(emailTextbox);
		this.add(buttons);

		if (checkEmail(u.getEmail()) == false) {
			Window.alert("E-Mail ist ungültig!");
		}
		;
	}

	public void saveUser(User u) {
		u.setUsername(nameTextbox.getValue());
		editorService.createUser(u.getUsername(), u.getEmail(), new AsyncCallback<User>() {

			public void onSuccess(User user) {
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
	};

	private Boolean checkEmail(String email) {
		if (!email.matches("^\\w+[\\w-\\.]*\\@\\w+((-\\w+)|(\\w*))\\.[a-z]{2,3}$")) {
			return false;
		} else {
			return true;
		}
	}

	ClickHandler saveClickHandler = new ClickHandler() {

		public void onClick(ClickEvent e) {
			if (checkName(nameTextbox.getValue())) {
				saveUser(user);
			} else {
				infoLabel.setText("Der Name muss aus 3 bis 10 Buchstaben bestehen");
			}
		}
	};

	ClickHandler loginClickHandler = new ClickHandler() {

		public void onClick(ClickEvent e) {
//			Cookies.removeCookie("dev_appserver_login");
		}
	};
}