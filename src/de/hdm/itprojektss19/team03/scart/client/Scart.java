package de.hdm.itprojektss19.team03.scart.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektss19.team03.scart.shared.bo.User;
import de.hdm.itprojektss19.team03.scart.shared.bo.LoginInfo;

/**
 * Die Klasse EntryPoint beinhaltet die Methode <code>onModuleLoad()</code> 
 * die beim Start der Applikation als erstes aufgerufen wird.
 * 
 * @author PatrickLehle
 *
 */
public class Scart implements EntryPoint {
	
	private LoginInfo loginInfo = null;
	private User ownProfil = null;
	private Label searchlb = new Label();
	private VerticalPanel loginPanel = new VerticalPanel();
	private Label profilLb = new Label();
	private Label loginLabel = new Label("Welcome to Scart");
	private HTML loginHTML = new HTML("<h7></h7>");
	private	HTML sontactHTML = new HTML("<h8>SCART</h8>");
	private Anchor signInLink = new Anchor("sign in with Google");

	@Override
	public void onModuleLoad() {
		// TODO Auto-generated method stub
		
	}

}
