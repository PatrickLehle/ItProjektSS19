package de.hdm.itprojektss19.team03.scart.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektss19.team03.scart.client.gui.FooterForm;
import de.hdm.itprojektss19.team03.scart.client.gui.GroceryListForm;
import de.hdm.itprojektss19.team03.scart.client.gui.GroupForm;
import de.hdm.itprojektss19.team03.scart.client.gui.ProfilForm;
import de.hdm.itprojektss19.team03.scart.client.gui.RegistryForm;
import de.hdm.itprojektss19.team03.scart.client.gui.ToolbarForm;
import de.hdm.itprojektss19.team03.scart.shared.EditorServiceAsync;
import de.hdm.itprojektss19.team03.scart.shared.LoginService;
import de.hdm.itprojektss19.team03.scart.shared.LoginServiceAsync;
import de.hdm.itprojektss19.team03.scart.shared.bo.LoginInfo;
import de.hdm.itprojektss19.team03.scart.shared.bo.User;

/**
 * Die Klasse EntryPoint beinhaltet die Methode <code>onModuleLoad()</code> die
 * beim Start der Applikation als erstes aufgerufen wird.
 * 
 * @author PatrickLehle
 * @author DennisLehle
 * @author Marco Dell'Oso
 *
 */
public class Scart implements EntryPoint {

	private ToolbarForm toolbar = new ToolbarForm();
	private FooterForm footer = new FooterForm();
	private Button button1 = new Button("grocery list");
	private Button button2 = new Button("profile ");
	private Button button3 = new Button("group ");
	
	private GroceryListForm glf = new GroceryListForm();
	private GroupForm gf = new GroupForm();
	private ProfilForm pf = new ProfilForm();
	
	public void onModuleLoad() {
		button1.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent evt) {
				RootPanel.get("content").clear();
				RootPanel.get("content").add(glf);
				
			}
		});
		
		button2.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent evt) {
				RootPanel.get("content").clear();
				RootPanel.get("content").add(pf);
				
			}
		});
		
		button3.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent evt) {
				RootPanel.get("content").clear();
				RootPanel.get("content").add(gf);
				
			}
		});
		
		
		
		RootPanel.get("header").add(toolbar);
		RootPanel.get("content").add(button1);
		RootPanel.get("content").add(button2);
		RootPanel.get("content").add(button3);
		RootPanel.get("footer").add(footer);
	}

}
