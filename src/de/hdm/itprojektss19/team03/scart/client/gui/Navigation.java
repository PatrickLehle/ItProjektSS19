package de.hdm.itprojektss19.team03.scart.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTree;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;


import de.hdm.itprojektss19.team03.scart.shared.bo.User;

public class Navigation extends VerticalPanel{

	HorizontalPanel hp = new HorizontalPanel();
	Button newGroupBtn = new Button("<image src='/images/plusButton.png' width='16px' height='16px' align='center'/>");
/**
 * 
 * @param user aktuell eingeloggter User
 */
public Navigation (final User user) {
	
	
	// Erstellung des ScrollPanel´s fuer den Baum
	ScrollPanel sc = new ScrollPanel();
	// Groeße des ScrollPanel´s wird angepasst.
	sc.setSize("200px", "550px");
	sc.setVerticalScrollPosition(10);
	
	
	// Anlegen des Baumes fuer die navTreeModel fuer  Gruppen.
	ScartTreeViewModel navTreeModel = new ScartTreeViewModel(user);
	
	// Anlegung des Baumes mit dem zuvor definierten TreeViewModel.
	CellTree navTree = new CellTree(navTreeModel, null);
	
	
	// ClickHandler fuer das Erstellen von neuen Gruppen.
	newGroupBtn.addClickHandler(new ClickHandler() {

		@Override
		public void onClick(ClickEvent event) {
			// Wird instanziiert wenn eine neue Group erstellt werden soll.
			RootPanel.get("content").clear();
			RootPanel.get("content").add(new GroupForm(user));
		}
	});
	
			// Hier wird das TreeViewModel dem ScrollPanel hinzugefuegt.
			sc.add(navTree);
			
			// Hier wird der Button fuer die Erstellung von neuen Gruppen gestylt.
			newGroupBtn.setPixelSize(100, 60);
			newGroupBtn.setStyleName("button1");
			newGroupBtn.setTitle("create new Group");
			
			// Buttons werden dem HorizontalPanel hinzugefuegt.
			hp.add(newGroupBtn);
			

			// Das HorizontalPanel wird dem VerticalPanel hinzugefuegt.
			this.add(hp);
			

			// Hier wird der Header fuer die GroceryLists gesetzt.
			this.add(new HTML("<center><h5>My GroceryLists</h5></center>"));
			
			
			// Das ScrollPanel wird dem VerticalPanel hinzugefuegt.
			this.add(sc);
}

}
