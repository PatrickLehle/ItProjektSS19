package de.hdm.itprojektss19.team03.scart.client.gui;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektss19.team03.scart.client.ClientsideSettings;
import de.hdm.itprojektss19.team03.scart.shared.EditorServiceAsync;
import de.hdm.itprojektss19.team03.scart.shared.bo.Group;
import de.hdm.itprojektss19.team03.scart.shared.bo.GroupUser;
import de.hdm.itprojektss19.team03.scart.shared.bo.User;

/**
 * Die createGroup-Form wird aufgerufen wenn eine neue Grupper erstellt werden soll
 * 
 * @author bastiantilk
 *
 */
public class CreateGroup extends VerticalPanel {
//CONNECTION WITH EDITORSERVICE/MAPPER============================
	private EditorServiceAsync ev = ClientsideSettings.getEditor();
	
//DEFAULT CONSTRUCTOR=============================================
	/** Default Konstruktor der createGroup-Seite
	 * 
	 */
	public CreateGroup() {

	}
//CONSTRUCTOR=====================================================
	/** Konstruktor der createGroup-Seite
	 * 
	 * @param u (User-Objekt des Users der die createUser-Seite aufrufen will)
	 */
	public CreateGroup(User u) {
		
		/* AUSKOMMENTIEREN SOBALD COOKIE-LOGIN FUNKTIONIERT
		u.setId(Integer.valueOf(Cookies.getCookie("userId")));
		u.setEmail(String.valueOf(Cookies.getCookie("email")));
		*/
		this.user = u;
		
		onLoad();
	}
//TEXTBOXEN=======================================================
	TextBox groupTextbox = new TextBox(); 
	
//LABELS==========================================================
	Label createGroupLabel = new Label("Gruppe erstellen"); //Ueberschrift
	Label nameLabel = new Label("Gruppenname: ");
	Label responseLabel = new Label("");
	
	//Label footerHome = new Label("Home");
	//Label footerReport = new Label("Report");
	
//BUTTONS=========================================================
	Button backButton = new Button("Zurück");
	Button createGroupButton = new Button("Erstellen");
	
//PANELS==========================================================
	HorizontalPanel header = new HorizontalPanel();
		
	VerticalPanel contentBox = new VerticalPanel(); //Content Panel
	
	HorizontalPanel createGroupPanel = new HorizontalPanel(); //Panel fuer Ueberschrift
	HorizontalPanel inputPanel = new HorizontalPanel(); //Label und Textbox
	HorizontalPanel buttonPanel = new HorizontalPanel(); //Buttons
	HorizontalPanel responsePanel = new HorizontalPanel(); //Server responseLabel

	//HorizontalPanel footer = new HorizontalPanel();
	
//VARIABLES==========================================================
	User user = null; //User-Variable die bei dem Aufrufen dieser Seite unbedingt uebergeben werden soll

//METHODS==========================================================

	//public CreateGroup(User user) {   ALT
/**
 * Methode wird bei dem "Aufrufen" der Klasse gestartet
 */
public void onLoad() {
		super.onLoad();
		
//STYLING==========================================================
		createGroupLabel.addStyleName("h1");
		backButton.addStyleName("button");
		createGroupButton.addStyleName("button");
		nameLabel.setStyleName("text");
		responseLabel.setStyleName("text");
		groupTextbox.setStyleName("textbox");

//ADDING BUTTONS, LABELS, TEXTBOX TO PANELS========================
	
		//Ueberschrift wird dem Panel "createGroupPanel" hinzugefuegt
		createGroupLabel.setHorizontalAlignment(ALIGN_CENTER);
		createGroupPanel.add(createGroupLabel);
		createGroupPanel.setHorizontalAlignment(ALIGN_CENTER);

		//Label und Textbox wird dem Panel "inputPanel" hinzugefuegt
		inputPanel.add(nameLabel);
		inputPanel.add(groupTextbox);
		inputPanel.setHorizontalAlignment(ALIGN_CENTER);
		
		//Zwei Buttons werden dem Panel "buttonPanel" hinzugefuegt
		buttonPanel.add(backButton);
		buttonPanel.add(createGroupButton);
		buttonPanel.setHorizontalAlignment(ALIGN_CENTER);
		
		//responseLabel wird dem Panel "responsePanel" hinzugefuegt,
		//dient zur Ausgabe der erfolgreichen/fehlgeschlagenen Serverantwort
		responseLabel.setHorizontalAlignment(ALIGN_CENTER);
		responsePanel.add(responseLabel);
		responseLabel.setVisible(false);
		responsePanel.setHorizontalAlignment(ALIGN_CENTER);
		
		//Alle Panels werden zu dem allgemeinen Panel "ContentBox" hinzugefuegt
		contentBox.add(createGroupPanel);
		contentBox.add(inputPanel);
		contentBox.add(buttonPanel);
		contentBox.add(responsePanel);
		
		/*
		footer.add(footerHome);
		footer.add(footerReport);
		*/
		
		//Diese Seite wird dem RootPanel in "content" auf der HTML-Seite uebergeben
		this.add(contentBox);
		//this.add(footer);
		
//CLICKHANDLER TO CREATE A GROUP=============================
		/** Click/Enter-Handler fuer den createGroup-Button
		 * 
		 * @author bastiantilk
		 *
		 */
		class MyHandler implements ClickHandler, KeyUpHandler {
			/*
			 * Wird aufgerufen sobald der User auf den createGroup-Button klickt
			 */
			public void onClick(ClickEvent event) {
				 //Uebergabe des Gruppennamen an den Server/Mapper (s. Methode)
				if(groupTextbox.getText() != "" && groupTextbox.getText() != null) { 
					createGroupDB(groupTextbox.getText()); //Aufruf von createGroup Methode in EditorServiceImpl bzw. entsprechenden Mapper
				} else {
					responseLabel.setVisible(true);
					responseLabel.setText("Fehler: Bitte geben Sie einen passenden Namen ein");
				}
			}
			/* Wird aufgerufen wenn ENTER gedrueckt wird
			 * 
			 */
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					if(groupTextbox.getText() != "" && groupTextbox.getText() != null) { 
						createGroupDB(groupTextbox.getText()); //Aufruf von createGroupDB Methode
					} else {
						responseLabel.setVisible(true);
						responseLabel.setText("Fehler: Bitte geben Sie einen passenden Namen ein");
					}
				}
			}
			
			/** Methode erstellt Gruppe in der DB und fuegt den aktuelen User direkt dieser Gruppe dazu
			 * 
			 * @param groupName (aus der Textbox)
			 */
			private void createGroupDB(String groupName) { //Sorgt fuer die Erstellung derr Gruppe in der DB und der verknuepfung von Group und User
				responseLabel.setVisible(true);
				responseLabel.setText("");
				final Group createGroup = new Group(groupName);
				
				
				// HARDCODED USER-OBJECT MUSS ENTFERNT WERDEN
				// WENN COOKIES FUNKTIONIEREN
				/*
				user.setUsername("Franz");
				user.setEmail("test@hotmail.de");
				user.setId(1);
				*/
				
				try {
					if(user == null ) {
						throw new NullPointerException();
					}
				
				
				ev.createGroup(createGroup, new AsyncCallback<Group>() {
					
					/** Gruppe konnte nicht in der DB erstellt werden
					 * 
					 */
					public void onFailure(Throwable caught) {
						responseLabel.setText("Fehler: Gruppe konnte nicht erstellt werden");
						//responseLabel.addStyleName("serverResponseLabel");
						//responseLabel.setText(SERVER_ERROR);
					}

					
					@Override
					/** Gruppe konnte in der DB erstellt werden
					 * 
					 */
					public void onSuccess(Group arg0) {
					final Group tempGroup = arg0; //Verweis auf die gerade erstellte Gruppe
					final User tempUser = user; //Verweis auf User der die Seite aufgerufen hat
						ev.addUserToGroup(tempUser, tempGroup, new AsyncCallback<Void>() {

							@Override
							/** Die Gruppe konnte in der DB erstellt werden, aber der aktuelle
							 *  User konnte nicht der neunen Gruppe hinzugefuegt werden
							 */
							public void onFailure(Throwable arg0) { 
								responseLabel.setText("Fehler: Die Gruppe konnte erstellt werden, aber der aktuelle User nicht der neuen Gruppe hinzugefügt werden");
								//FEHLT NOCH: Gruppe aus Datenbank loeschen (ggf. dieselbe Methode wie in DeleteGroup)
							}

							@Override
							/** Die Gruppe konnte erstellt werden 
							 *  und der aktuelle User der Gruppe hinzugefuegt werden
							 */
							public void onSuccess(Void arg0) {
								responseLabel.setText("Die Gruppe '"+tempGroup.getGroupName()+"' wurde erstellt und Sie wurden automatisch der Gruppe hinzugefügt");
								Window.alert("Die Gruppe '"+tempGroup.getGroupName()+"' wurde erstellt und Sie wurden automatisch hinzugefügt.");
							}
						});
					
					}
				});
			} catch (NullPointerException e) {
				Window.alert(e.toString()+ "\n"+ "Aktueller User/User-Objekt wurde nicht gesetzt");
			}
			}
		}
//CLICKHANDLER FOR CREATE GROUP-BUTTON=============================			
		createGroupButton.addClickHandler(new MyHandler());
		
//CLICKHANDLER FOR BACK-BUTTON=============================	
		
		/** Wenn der backBUtton gedruckt wird, kommt man zu der Homepage zurueck
		 * 
		 */
		backButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				//RootPanel.get("content").clear();
				Window.Location.replace("/Scart.html");
			}
		});
	}

	public CreateGroup(Group selection) {
		// TODO Auto-generated constructor stub
	}

	public void setSelectedGroup(Group selectedGroup) {
		// TODO Auto-generated method stub
		
	}

}
