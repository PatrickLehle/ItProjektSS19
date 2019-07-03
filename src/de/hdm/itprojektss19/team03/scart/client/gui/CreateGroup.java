package de.hdm.itprojektss19.team03.scart.client.gui;

import java.util.Vector;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
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
 * Die createGroup-Form wird aufgerufen wenn eine neue Grupper erstellt werden
 * soll
 * 
 * @author bastiantilk, Julian Hofer
 *
 */
public class CreateGroup extends VerticalPanel {
	// CONNECTION WITH EDITORSERVICE/MAPPER============================
	private EditorServiceAsync ev = ClientsideSettings.getEditor();

	// DEFAULT CONSTRUCTOR=============================================
	/**
	 * Default Konstruktor der createGroup-Seite
	 * 
	 */
	public CreateGroup() {

	}

	// CONSTRUCTOR=====================================================
	/**
	 * Konstruktor der createGroup-Seite
	 * 
	 * @param u (User-Objekt des Users der die createUser-Seite aufrufen will)
	 */
	public CreateGroup(User u) {

		this.user = u;

	}

	// TEXTBOXEN=======================================================
	TextBox groupTextbox = new TextBox();

	// LABELS==========================================================
	Label createGroupLabel = new Label("Gruppe erstellen"); // Ueberschrift
	Label nameLabel = new Label("Gruppenname: ");
	Label responseLabel = new Label("");

	// Label footerHome = new Label("Home");
	// Label footerReport = new Label("Report");

	// BUTTONS=========================================================
	Button backButton = new Button("Zurück");
	Button createGroupButton = new Button("Erstellen");

	// PANELS==========================================================
	HorizontalPanel header = new HorizontalPanel();

	VerticalPanel contentBox = new VerticalPanel(); // Content Panel

	HorizontalPanel createGroupPanel = new HorizontalPanel(); // Panel fuer Ueberschrift
	HorizontalPanel inputPanel = new HorizontalPanel(); // Label und Textbox
	HorizontalPanel buttonPanel = new HorizontalPanel(); // Buttons
	HorizontalPanel responsePanel = new HorizontalPanel(); // Server responseLabel

	// HorizontalPanel footer = new HorizontalPanel();

	// VARIABLES==========================================================
	User user = null; // User-Variable die bei dem Aufrufen dieser Seite uebergeben werden soll

	// METHODS==========================================================

	/**
	 * Methode wird bei dem Aufrufen der Klasse/des Widgets gestartet
	 */
	public void onLoad() {
		super.onLoad();

		// STYLING==========================================================
		createGroupLabel.addStyleName("h1");
		backButton.addStyleName("button");
		createGroupButton.addStyleName("button");
		nameLabel.setStyleName("text");
		responseLabel.setStyleName("text");
		groupTextbox.setStyleName("textbox");

		// ADDING BUTTONS, LABELS, TEXTBOX TO PANELS========================

		// Ueberschrift wird dem Panel "createGroupPanel" hinzugefuegt
		createGroupLabel.setHorizontalAlignment(ALIGN_CENTER);
		createGroupPanel.add(createGroupLabel);
		createGroupPanel.setHorizontalAlignment(ALIGN_CENTER);

		// Label und Textbox wird dem Panel "inputPanel" hinzugefuegt
		inputPanel.add(nameLabel);
		inputPanel.add(groupTextbox);
		inputPanel.setHorizontalAlignment(ALIGN_CENTER);

		// Zwei Buttons werden dem Panel "buttonPanel" hinzugefuegt
		buttonPanel.add(backButton);
		buttonPanel.add(createGroupButton);
		buttonPanel.setHorizontalAlignment(ALIGN_CENTER);

		// responseLabel wird dem Panel "responsePanel" hinzugefuegt,
		// dient zur Ausgabe der erfolgreichen/fehlgeschlagenen Serverantwort
		responseLabel.setHorizontalAlignment(ALIGN_CENTER);
		responsePanel.add(responseLabel);
		responseLabel.setVisible(false);
		responsePanel.setHorizontalAlignment(ALIGN_CENTER);

		// Alle Panels werden zu dem allgemeinen Panel "ContentBox" hinzugefuegt
		contentBox.add(createGroupPanel);
		contentBox.add(inputPanel);
		contentBox.add(buttonPanel);
		contentBox.add(responsePanel);

		/*
		 * footer.add(footerHome); footer.add(footerReport);
		 */

		// Diese Seite wird dem RootPanel in "content" auf der HTML-Seite uebergeben
		this.add(contentBox);
		// this.add(footer);

		// CLICKHANDLER FOR CREATE GROUP-BUTTON=============================
		createGroupButton.addClickHandler(new MyHandler());

		// CLICKHANDLER FOR BACK-BUTTON=============================

		/**
		 * Wenn der backBUtton gedruckt wird, kommt man zu der Homepage zurueck
		 * 
		 */
		backButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				// RootPanel.get("content").clear();
				Window.Location.replace("/Scart.html");
			}
		});

	}

	/**
	 * Click/Enter-Handler fuer den createGroup-Button
	 *
	 */
	class MyHandler implements ClickHandler {
		/*
		 * Wird aufgerufen sobald der User auf den createGroup-Button klickt
		 */
		public void onClick(ClickEvent event) {
			// Uebergabe des Gruppennamen an den Server/Mapper (s. Methode)

			if (checkName(groupTextbox.getText())) {
				String gname = groupTextbox.getText();
				createGroupDB(gname); // Aufruf von ceateGroup Methode in EditorServiceImpl bzw.
										// entsprechenden Mapper
			} else {
				responseLabel.setVisible(true);
				responseLabel.setText("Fehler: Bitte geben Sie einen passenden Namen ein");
			}
		}

		/*
		 * Wird aufgerufen wenn ENTER gedrueckt wird
		 * 
		 */
//			public void onKeyUp(KeyUpEvent event) {
//				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
//					if (checkName(groupTextbox.getText())) {
//						createGroupDB(groupTextbox.getText()); // Aufruf von createGroupDB Methode
//					} else {
//						responseLabel.setVisible(true);
//						responseLabel.setText("Fehler: Bitte geben Sie einen passenden Namen ein");
//					}
//				}
	}

	/**
	 * Ueberprueft auf korrekten Syntax der Eingabe
	 * 
	 * @param str
	 * @return true, wenn der Name passt / false, wenn der Name nicht passt
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

	/**
	 * Methode erstellt Gruppe in der DB und fuegt den aktuelen User direkt dieser
	 * Gruppe dazu
	 * 
	 * @param groupName (aus der Textbox)
	 */
	public void createGroupDB(String groupName) { // Sorgt fuer die Erstellung derr Gruppe in der DB und der

		Group createGroup = new Group();// verknuepfung von Group und User
		responseLabel.setVisible(true);
		responseLabel.setText("");

		GWT.log(groupName + "Gruppenname 1");

		createGroup.setGroupName(groupName);

		try {
			if (user == null) {
				throw new NullPointerException();
			} else {

				ev.createGroup(createGroup, new CreateGroupCallback());

			}

		} catch (NullPointerException e) {
			Window.alert(e.toString() + "\n" + "Aktueller User/User-Objekt wurde nicht gesetzt");
		}

	}

	// Callbacks

	/**
	 * Klasse zum hinzufuegen einer (eigene) Einkaufsliste fuer die gerade erstellte
	 * Gruppe
	 * 
	 */
	class GroceryListCallback implements AsyncCallback<GroceryList> {

		public void onSuccess(GroceryList gl) {
			Window.Location.reload();
		}

		public void onFailure(Throwable t) {
			Window.alert(t.getLocalizedMessage());
		}
	}

	class CreateGroupCallback implements AsyncCallback<Group> {

		/**
		 * Gruppe konnte nicht in der DB erstellt werden
		 * 
		 */
		public void onFailure(Throwable caught) {
			Window.alert("Group could not be created: " + caught);

		}

		@Override
		/**
		 * Gruppe konnte in der DB erstellt werden
		 * 
		 */

		public void onSuccess(Group arg0) {
			Group tempGroup = arg0; // Verweis auf die gerade erstellte Gruppe
			User tempUser = user; // Verweis auf User der die Seite aufgerufen hat
			ev.addUserToGroup(tempUser, tempGroup, new AddUserCallback());
		}
	}

	class AddUserCallback implements AsyncCallback<GroupUser> {
		public void onFailure(Throwable arg0) {
			responseLabel.setText(
					"Fehler: Die Gruppe konnte erstellt werden und der aktuelle User nicht der neuen Gruppe hinzugefügt werden");
			// FEHLT NOCH: Gruppe aus Datenbank loeschen (ggf. dieselbe Methode wie in
			// DeleteGroup)
		}

		@Override
		/**
		 * Die Gruppe konnte erstellt werden und der aktuelle User der Gruppe
		 * hinzugefuegt werden
		 */
		public void onSuccess(GroupUser gU) {
			GroceryList gl = new GroceryList();
			gl.setArticles(new Vector<Article>());
			gl.setGroceryListName("Einkaufsliste");
			gl.setGroupId(gU.getGroupId());
			gl.setOwnerId(gU.getUserId());
			ev.createGroceryList(gl, new GroceryListCallback());
		}
	}

}
