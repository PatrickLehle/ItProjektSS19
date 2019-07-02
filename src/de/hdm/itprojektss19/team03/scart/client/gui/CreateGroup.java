package de.hdm.itprojektss19.team03.scart.client.gui;

import java.util.Vector;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
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
 * @author bastiantilk
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
	 * @param u
	 *            (User-Objekt des Users der die createUser-Seite aufrufen will)
	 */
	public CreateGroup(User u) {
		/* Cookies:
		u.setId(Integer.valueOf(Cookies.getCookie("userId")));
		u.setEmail(String.valueOf(Cookies.getCookie("email")));
		*/
		
		this.user = u;
		
		onLoad();
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
	Group createGroup = new Group();
	
	
	

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

		/** Click/Enter-Handler fuer den createGroup-Button
		 *
		 */
		class MyHandler implements ClickHandler, KeyUpHandler {
			/*
			 * Wird aufgerufen sobald der User auf den createGroup-Button klickt
			 */
			public void onClick(ClickEvent event) {
				// Uebergabe des Gruppennamen an den Server/Mapper (s. Methode)
				if (checkName(groupTextbox.getText())) {
					createGroupDB(groupTextbox.getText()); // Aufruf von ceateGroup Methode in EditorServiceImpl bzw.
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
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					if (checkName(groupTextbox.getText())) {
						createGroupDB(groupTextbox.getText()); // Aufruf von createGroupDB Methode
					} else {
						responseLabel.setVisible(true);
						responseLabel.setText("Fehler: Bitte geben Sie einen passenden Namen ein");
					}
				}
			}

			/**
			 * Methode erstellt Gruppe in der DB und fuegt den aktuelen User direkt dieser
			 * Gruppe dazu
			 * 
			 * @param groupName
			 *            (aus der Textbox)
			 */
			private void createGroupDB(String groupName) { // Sorgt fuer die Erstellung derr Gruppe in der DB und der
															// verknuepfung von Group und User
				responseLabel.setVisible(true);
				responseLabel.setText("");
				
				
				// HARDCODED USER-OBJECT MUSS ENTFERNT WERDEN
				// WENN COOKIES FUNKTIONIEREN
				/*
				user.setUsername("Franz");
				user.setEmail("test@hotmail.de");
				user.setId(1);
				*/
				
				createGroup.setGroupName(groupName);

				try {
					if (user == null) {
						throw new NullPointerException();
					}

					ev.createGroup(createGroup, new AsyncCallback<Group>() {

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
							final Group tempGroup = arg0; // Verweis auf die gerade erstellte Gruppe
							final User tempUser = user; // Verweis auf User der die Seite aufgerufen hat
							ev.addUserToGroup(tempUser, tempGroup, new AsyncCallback<GroupUser>() {

								@Override
								/**
								 * Die Gruppe konnte in der DB erstellt werden, aber der aktuelle User konnte
								 * nicht der neunen Gruppe hinzugefuegt werden
								 */
								public void onFailure(Throwable arg0) {
									responseLabel.setText(
											"Fehler: Die Gruppe konnte erstellt werden, aber der aktuelle User nicht der neuen Gruppe hinzugefügt werden");
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
									ev.createGroceryList(gl, groceryListCallback);
								}
							});

						}
					});
				} catch (NullPointerException e) {
					Window.alert(e.toString() + "\n" + "Aktueller User/User-Objekt wurde nicht gesetzt");
				}
			}
		}
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

	/** Methode zum hinzufuegen eine (eigene) Einkaufsliste fuer die gerade erstellte Gruppe
	 * 
	 */
	AsyncCallback<GroceryList> groceryListCallback = new AsyncCallback<GroceryList>() {

		public void onSuccess(GroceryList gl) {
			Window.Location.reload();
		}

		public void onFailure(Throwable t) {
			Window.alert(t.getLocalizedMessage());
		}
	};

	/**
	 * Ueberprueft auf korrekten Syntax der Eingabe
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
	
	

}
