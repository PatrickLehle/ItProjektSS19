package de.hdm.itprojektss19.team03.scart.client.gui;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
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
 * Die Group-Form wird aufgerufen wenn eine neue Grupper erstellt werden soll
 * 
 * @author bastiantilk
 *
 */
public class CreateGroup extends VerticalPanel {

//DEFAULT CONSTRUCTOR===============================================================
	public CreateGroup() {

	}
//CONSTRUCTOR===============================================================
	public CreateGroup(User u) {
		this.user = u;

	}
	
	
	
	private EditorServiceAsync ev = ClientsideSettings.getEditor();
	
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
	User user = new User();

//METHODS==========================================================
	
	//Methode wird bei dem "Aufrufen" der Klasse gestartet
	
	//public CreateGroup(User user) {   ALT
	
public void onLoad() {
		super.onLoad();
		
//STYLING==========================================================
		createGroupLabel.addStyleName("h1");
		backButton.addStyleName("button");
		createGroupButton.addStyleName("button");
		nameLabel.setStyleName("text");
		responseLabel.setStyleName("text");

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
		
		this.add(contentBox);
		//this.add(footer);
		
//CLICKHANDLER TO CREATE A GROUP=============================
		class MyHandler implements ClickHandler, KeyUpHandler {
			/*
			 * Wird aufgerufen sobald der User auf den "Erstellen" Button klickt
			 */
			public void onClick(ClickEvent event) {
				 //Uebergabe des Gruppennamen an den Server/Mapper (s. Methode)
				if(groupTextbox.getText() != "") {
					createGroupDB(groupTextbox.getText()); //Aufruf von ceateGroup Methode in EditorServiceImpl bzw. entsprechenden Mapper
				} else {
					responseLabel.setVisible(true);
					responseLabel.setText("Fehler: Bitte geben Sie einen passenden Namen ein");
				}
			}
			/*
			 * Wird aufgerufen wenn ENTER gedrueckt wird
			 */
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					if(groupTextbox.getText() != "") { 
						createGroupDB(groupTextbox.getText()); //Aufruf von ceateGroupDB Methode
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
				
				ev.createGroup(createGroup, new AsyncCallback<Group>() {
					
					public void onFailure(Throwable caught) {
						responseLabel.setText("Fehler: Gruppe konnte nicht erstellt werden");
						//responseLabel.addStyleName("serverResponseLabel");
						//responseLabel.setText(SERVER_ERROR);
					}

					
					@Override
					public void onSuccess(Group arg0) {
					final Group tempGroup = arg0;
					final User tempUser = user;
						ev.addUserToGroup(tempUser, tempGroup, new AsyncCallback<Void>() {

							@Override
							public void onFailure(Throwable arg0) {
								responseLabel.setText("Fehler: Gruppe konnte nicht erstellt werden");
								//FEHLT NOCH: Gruppe aus Datenbank loeschen (ggf. dieselbe Methode wie in DeleteGroup)
							}

							@Override
							public void onSuccess(Void arg0) {
								responseLabel.setText("Die Gruppe wurde erstellt und Sie wurden automatisch hinzugefügt");
							}
							
						});
					
					
					}
				});
			}
		}
//CLICKHANDLER FOR CREATE GROUP-BUTTON=============================			
		createGroupButton.addClickHandler(new MyHandler());
		
//CLICKHANDLER FOR BACK-BUTTON=============================		
		backButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				RootPanel.get("content").clear();
				//RootPanel.get("content").add()  "Homepage" Seite soll aufgerufen werden
			
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
