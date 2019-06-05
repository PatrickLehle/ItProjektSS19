package de.hdm.itprojektss19.team03.scart.client.gui;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektss19.team03.scart.client.ClientsideSettings;
import de.hdm.itprojektss19.team03.scart.shared.EditorServiceAsync;
import de.hdm.itprojektss19.team03.scart.shared.bo.Group;
import de.hdm.itprojektss19.team03.scart.shared.bo.User;
//"Familien" Mockup in Balsamic

/**
 * Die Group-Form wird aufgerufen wenn auf der Homepage
 * eine Gruppe ausgewaehlt wird
 * 
 * @author bastiantilk
 *
 */
public class GroupForm extends VerticalPanel {

	private EditorServiceAsync ev = ClientsideSettings.getEditorVerwaltung();

	VerticalPanel hauptPanel = new VerticalPanel();
	VerticalPanel hauptPanel2 = new VerticalPanel();
	VerticalPanel hauptPanel3 = new VerticalPanel();
	
	HorizontalPanel hp = new HorizontalPanel();
	HorizontalPanel hp2 = new HorizontalPanel();

	
	public GroupForm(User user) {
		// TODO Auto-generated constructor stub
	}

	public GroupForm(Group selection) {
		// TODO Auto-generated constructor stub
	}

	public void setSelectedGroup(Group selectedGroup) {
		// TODO Auto-generated method stub
		
	}

}
