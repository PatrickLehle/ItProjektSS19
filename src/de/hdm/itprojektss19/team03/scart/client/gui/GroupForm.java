package de.hdm.itprojektss19.team03.scart.client.gui;

import java.util.Vector;

import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektss19.team03.scart.client.ClientsideSettings;
import de.hdm.itprojektss19.team03.scart.shared.EditorServiceAsync;
import de.hdm.itprojektss19.team03.scart.shared.bo.Group;
import de.hdm.itprojektss19.team03.scart.shared.bo.User;

/**
 * Die Klasse ist fuer die Anzeige von Gruppen zustaendig. Diese Klasse erlaubt
 * es, die Gruppen Anzuzeigen, zu Bearbeiten oder Neuanzulegen.
 * 
 * @author PatrickLehle
 *
 */
public class GroupForm extends VerticalPanel {
	
	//Setzen des Zugriffs auf das Asyne Service Interface
	EditorServiceAsync ev = ClientsideSettings.getEditorVerwaltung();
	
	//Hier werden unser Leeren Objekte hinterlegt
	Group g = new Group();
	User user = new User();
	Vector<Group> groups = new Vector<Group>();
	Group selGroup = null;
	
//TEXTBOXES=============================================================

	TextBox groupNameBox = new TextBox();
//LABELS================================================================
	
//BUTTONS===============================================================
	
	
//CONSTRUCTORS==========================================================
	
	public GroupForm( User u) {
		
	}
	/**
	 * Dieser Konstruktor wird aufgerufen, wenn eine Gruppe bereits existiert.
	 *
	 * @param user ausgewaehlte Gruppe
	 */
	public GroupForm(Group g) {
	
	}

	public void setSelectedGroup(Group selectedGroup) {
		// TODO Auto-generated method stub
		
	}

}
