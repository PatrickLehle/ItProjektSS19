package de.hdm.itprojektss19.team03.scart.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
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
//"Familien"/Add Group Mockup in Balsamic

/**
 * Die Group-Form wird aufgerufen wenn auf der Homepage
 * eine Gruppe ausgewaehlt wird
 * 
 * @author bastiantilk
 *
 */
public class CreateGroup extends VerticalPanel {

	private EditorServiceAsync ev = ClientsideSettings.getEditor();
	//Aufruf durch ev.createGroup(g, asyncCallback);
	
	
	//TEXTBOXEN=======================================================
	TextBox groupTextbox = new TextBox(); 
	
	//LABELS==========================================================
	Label createGroupLabel = new Label("Gruppe erstellen");
	Label nameLabel = new Label("Gruppenname: ");
	Label responseLabel = new Label("");
	
	Label footerHome = new Label("Home");
	Label footerReport = new Label("Report");
	
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

	public CreateGroup(User user) {
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
		
		footer.add(footerHome);
		footer.add(footerReport);
		

//		RootPanel.get("content").clear();
//		RootPanel.get("contentHeader").clear();
//		RootPanel.get("footer").clear();
		
//		RootPanel.get("contentHeader").add(new HTML("Scart"));
//		
//		RootPanel.get("content").add(contentBox); //Alle Panels werden in die "Content" div geschoben
//		
//		RootPanel.get("footer").add(footer);
		
		this.add(contentBox);
		this.add(footer);
		
		class MyHandler implements ClickHandler, KeyUpHandler {
			/**
			 * Fired when the user clicks on the sendButton.
			 */
			public void onClick(ClickEvent event) {
				 //Uebergabe des Gruppennamen an den Server/Mapper (s. Methode)
				if(groupTextbox.getText() != "") { //ggf. noch bessere Ueberpruefung ob der Input verarbeitet werden kann
					createGroupDB(groupTextbox.getText()); //Aufruf von ceateGroup Methode in EditorServiceImpl bzw. entsprechenden Mapper
				} else {
					responseLabel.setVisible(true);
					responseLabel.setText("Bitte geben Sie einen Passenden Namen ein");
				}
			}

			/**
			 * Fired when the user types in the nameField.
			 */
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					if(groupTextbox.getText() != "") { //ggf. noch bessere Ueberpruefung ob der Input verarbeitet werden kann
						createGroupDB(groupTextbox.getText()); //Aufruf von ceateGroupDB Methode
					} else {
						responseLabel.setVisible(true);
						responseLabel.setText("Bitte geben Sie einen Passenden Namen ein");
					}
				}
			}
			
			private void createGroupDB(String groupName) { //Sorgt fuer die Erstellung derr Gruppe in der DB und der verknuepfung von Group und User
				responseLabel.setVisible(true);
				responseLabel.setText("");
				Group createGroup = new Group(groupName);
				
				ev.createGroup(createGroup, new AsyncCallback<Group>() {
					
					public void onFailure(Throwable caught) {
						responseLabel.setText("Fehler: Gruppe konnte nicht erstellt werden");
						//responseLabel.addStyleName("serverResponseLabel");
						//responseLabel.setText(SERVER_ERROR);
					}

					
					@Override
					public void onSuccess(Group arg0) {
						// TODO Auto-generated method stub
					
						ev.addUserToGroup(user, arg0, new AsyncCallback<Void>() {

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
		
		createGroupButton.addClickHandler(new MyHandler());
	}

	public CreateGroup(Group selection) {
		// TODO Auto-generated constructor stub
	}

	public void setSelectedGroup(Group selectedGroup) {
		// TODO Auto-generated method stub
		
	}

}
