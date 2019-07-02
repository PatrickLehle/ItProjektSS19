package de.hdm.itprojektss19.team03.scart.client.gui;

import java.util.Vector;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
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
import de.hdm.itprojektss19.team03.scart.shared.bo.Article;
import de.hdm.itprojektss19.team03.scart.shared.bo.GroceryList;
import de.hdm.itprojektss19.team03.scart.shared.bo.Group;
import de.hdm.itprojektss19.team03.scart.shared.bo.GroupUser;
import de.hdm.itprojektss19.team03.scart.shared.bo.User;

/**
 * Die RegistryForm wird zum Login aufgerufen, wenn der User die Applikation zum
 * ersten mal verwendet.
 * 
 * @author PatrickLehle, Marco Dell'Oso
 */
public class RegistryForm extends VerticalPanel {

	private EditorServiceAsync editorService = ClientsideSettings.getEditor();
	private User user = new User();
	private Group group = new Group();

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
	 * @param logoutLink Link für den Logout
	 * @param email Attribut Email des Users
	 */
	public RegistryForm(String logoutLink, String email) {
		user.setEmail(email);
		logout.setHref(logoutLink);
		welcome.setStyleName("h1");
		infoLabel.setStyleName("text");
		nameTextbox.setStyleName("textbox-big");
		emailTextbox.setValue(email);
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

		if (checkEmail(user.getEmail()) == false) {
			Window.alert("E-Mail ist ungültig!");
		}
		;
	}

	/** Methode zum anlegen eines Neuen Users in der DB
	 * 
	 * @param u (User-Objekt des aktuellen Users)
	 */
	public void saveUser(User u) {

		u.setUsername(nameTextbox.getValue());
		editorService.createUser(u, new AsyncCallback<User>() {

			public void onSuccess(User user) {
				editorService.getUserByGMail(user.getEmail(), userCallback);
			}

			public void onFailure(Throwable e) {
				Window.alert("Registrierung fehlgeschlagen " + e.getMessage());

			}
		});
	};

	public void createGroup(User u) {

	}

	/**
	 * Ueberprueft korrekte syntax der Eingabe
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
	/** Methode um die Eingabe der E-Mail zu ueberpruefen
	 * 
	 * @param email
	 * @return true, wenn die E-Mail passt / false, wenn der Syntax nicht stimmt
	 */
	private Boolean checkEmail(String email) {
		if (!email.matches("^\\w+[\\w-\\.]*\\@\\w+((-\\w+)|(\\w*))\\.[a-z]{2,3}$")) {
			return false;
		} else {
			return true;
		}
	}

	/** Callback-Methode um eine neue Gruppe zu erstellen.
	 *  Bei Erfolg wird der (neue) User der Gruppe hinzugefuegt.
	 */
	AsyncCallback<Group> createGroupCallback = new AsyncCallback<Group>() {

		public void onFailure(Throwable t) {
			Window.alert("Failed to create new Group: " + t);
		}

		public void onSuccess(Group g) {
			group = g;
			editorService.addUserToGroup(user, g, addUserToGroupCallback);
		}
	};
	
	/** Callback-Methode um eine neue Einkaufsliste zu erstellen
	 * 
	 */
	AsyncCallback<GroceryList> groceryListCallback = new AsyncCallback<GroceryList>() {

		public void onFailure(Throwable t) {
			Window.alert("Failed to make new GroceryList: " + t);
		}

		public void onSuccess(GroceryList gl) {
			Window.Location.reload();
		}
	};

	/** Callback-Methode um einen User in eine Gruppe hinzuzufuegen.
	 * 	Bei Erfolg wird eine standart Einkaufsliste in der Gruppe erzeugt.
	 * 	User wird mit einem GroupUser-BO mit der Gruppe verknuepft
	 */
	AsyncCallback<GroupUser> addUserToGroupCallback = new AsyncCallback<GroupUser>() {

		public void onFailure(Throwable t) {
			Window.alert("Failed to add new user to new Group: " + t);
		}

		@Override
		public void onSuccess(GroupUser gU) {
			GroceryList gl = new GroceryList();
			gl.setArticles(new Vector<Article>());
			gl.setGroceryListName("Einkaufsliste");
			gl.setGroupId(group.getId());
			gl.setGroupName(group.getGroupName());
			gl.setOwnerId(user.getId());

			editorService.createGroceryList(gl, groceryListCallback);

		}
	};

	AsyncCallback<User> userCallback = new AsyncCallback<User>() {

		public void onFailure(Throwable t) {
			Window.alert("Failed to get user object: " + t);
		}

		public void onSuccess(User u) {
			Group g = new Group("Meine erste Gruppe");
			user = u;
			editorService.createGroup(g, createGroupCallback);
		}
	};

	/** ClickHandler fuer den "Registrieren"-Button
	 * 	Die Methode checkt den Sntax des Usernamen und startet eine Callback 
	 * 	um den User neun in der Db anzulegen.
	 */
	ClickHandler saveClickHandler = new ClickHandler() {

		public void onClick(ClickEvent e) {
			if (checkName(nameTextbox.getValue())) {
				saveUser(user);
			} else {
				infoLabel.setText("Der Name muss aus 3 bis 10 Buchstaben bestehen");
			}
		}
	};

}