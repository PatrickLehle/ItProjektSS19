package de.hdm.itprojektss19.team03.scart.client.gui;


import com.google.gwt.user.client.ui.VerticalPanel;
import de.hdm.itprojektss19.team03.scart.client.ClientsideSettings;
import de.hdm.itprojektss19.team03.scart.shared.EditorServiceAsync;
import de.hdm.itprojektss19.team03.scart.shared.bo.GroceryList;
import de.hdm.itprojektss19.team03.scart.shared.bo.User;


/**
 * Diese Klasse zeigt <code>GroceryList</code> Objekte eines Users an. Die Objekte werden
 * in einer CellTable hinzugefuegt und koennen dort angezeigt oder geleoscht werden.
 * 
 * @author PatrickLehle
 *
 */
public class ShowGroceryList extends VerticalPanel {
	
//INITIALISAZTION==============================================================

	//Asynchrone Interface
	private EditorServiceAsync ev = ClientsideSettings.getEditor();

	GroceryList gl = new GroceryList();
	
//CONSTRUCTORS=================================================================
	
	public ShowGroceryList(final User u) {
	
	}
	
	//Code folgt in kuerze <3
	
//BUTTONS=================================================================
	
//LABELS==================================================================
	
//PANELS=================================================================


}
