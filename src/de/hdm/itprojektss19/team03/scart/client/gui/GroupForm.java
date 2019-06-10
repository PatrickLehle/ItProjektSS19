package de.hdm.itprojektss19.team03.scart.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
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
import de.hdm.itprojektss19.team03.scart.shared.bo.User;
//"Familien"/Add Group Mockup in Balsamic

/**
 * Die Group-Form wird aufgerufen wenn auf der Homepage
 * eine Gruppe ausgewaehlt wird
 * 
 * @author bastiantilk
 *
 */
public class GroupForm extends VerticalPanel {

	private EditorServiceAsync ev = ClientsideSettings.getEditorVerwaltung();
	
	
	//TEXTBOXEN=======================================================
	TextBox groupTextbox = new TextBox(); 
	
	//LABELS==========================================================
	Label createGroupLabel = new Label("Gruppe erstellen");
	Label nameLabel = new Label("Gruppenname: ");
	Label responseLabel = new Label("");
	
	/**
	Label groups = new Label("Gruppe");
	Label SLName = new Label("Einkaufsliste: "); //Muss spaeter "Einkaufsliste: (Gruppenname)" anzeigen
	Label users = new Label("Benutzer");
	*/
	
	//BUTTONS=========================================================
	Button backButton = new Button("Zurück");
	Button createGroupButton = new Button("Erstellen");
	
	/**
		Button back = new Button("Zurück");
		Button removeGroup = new Button("Gruppe entfernen");
		Button addSL = new Button("EL hinzufügen");
		Button removeSL = new Button("EL entfernen");
		Button mySL = new Button("Meine EL");
		Button userAdd = new Button("Hinzufügen");
		Button userRemove = new Button("entfernen");
	*/
	//PANELS==========================================================
	
	
	HorizontalPanel header = new HorizontalPanel();
		
	VerticalPanel contentBox = new VerticalPanel(); //Content Panel
	
	HorizontalPanel createGroupPanel = new HorizontalPanel(); //Panel fuer Ueberschrift
	HorizontalPanel inputPanel = new HorizontalPanel(); //Label und Textbox
	HorizontalPanel buttonPanel = new HorizontalPanel(); //Buttons
	HorizontalPanel responsePanel = new HorizontalPanel(); //Server responseLabel


	HorizontalPanel footer = new HorizontalPanel();

	public GroupForm(User user) {
		// TODO Auto-generated constructor stub
		
		//Elemente werden dem Panel hinzugefuegt
		createGroupLabel.setHorizontalAlignment(ALIGN_CENTER);
		createGroupPanel.add(createGroupLabel);
		createGroupPanel.setHorizontalAlignment(ALIGN_CENTER);

		
		inputPanel.add(nameLabel);
		inputPanel.add(groupTextbox);
		inputPanel.setHorizontalAlignment(ALIGN_CENTER);

		
		buttonPanel.add(backButton);
		buttonPanel.add(createGroupButton);
		buttonPanel.setHorizontalAlignment(ALIGN_CENTER);
		
		//Zur Ausgabe der erfolgreichen/fehlgeschlagenen Serverantwort
		responseLabel.setHorizontalAlignment(ALIGN_CENTER);
		responsePanel.add(responseLabel);
		responseLabel.setVisible(false);
		responsePanel.setHorizontalAlignment(ALIGN_CENTER);
		
		//Alle Panels werden zur in die ContentBox hinzugefuegt
		contentBox.add(createGroupPanel);
		contentBox.add(inputPanel);
		contentBox.add(buttonPanel);
		contentBox.add(responsePanel);
		

		RootPanel.get("content").clear();
		RootPanel.get("contentHeader").clear();
		RootPanel.get("footer").clear();
		
		RootPanel.get("contentHeader").add(new HTML("Scart"));
		
		RootPanel.get("content").add(contentBox); //Alle Panels werden in die "Content" div geschoben
		
		RootPanel.get("footer").add(footer);
		
		
		
		createGroupButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if(groupTextbox.getText() != "") { //ggf. noch bessere Ueberpruefung ob der Input verarbeitet werden kann
					return; //Aufruf von ceateGroup Methode in EditorServiceImpl bzw. entsprechenden Mapper
				} else {
					return; //Fehlerverarbeitung bzw. Meldung das Input nicht passend ist
				}
		}
		});
		
		
		
		class MyHandler implements ClickHandler, KeyUpHandler {
			/**
			 * Fired when the user clicks on the sendButton.
			 */
			public void onClick(ClickEvent event) {
				SendNameToServer(); //Uebergabe des Gruppennamen an den Server/Mapper (s. Methode)
			}

			/**
			 * Fired when the user types in the nameField.
			 */
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					SendNameToServer();
				}
			}
			
			private void SendNameToServer() {
				
				
				responseLabel.setVisible(true);
				responseLabel.setText("");
				
				//Methode mit AsyncCallback
				//OnFailure
				
				//OnSuccess
			}
			
		}
		
	}

	public GroupForm(Group selection) {
		// TODO Auto-generated constructor stub
	}

	public void setSelectedGroup(Group selectedGroup) {
		// TODO Auto-generated method stub
		
	}

}
