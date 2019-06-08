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
import de.hdm.itprojektss19.team03.scart.client.ClientsideSettings;
import de.hdm.itprojektss19.team03.scart.shared.EditorServiceAsync;
import de.hdm.itprojektss19.team03.scart.shared.LoginService;
import de.hdm.itprojektss19.team03.scart.shared.LoginServiceAsync;
import de.hdm.itprojektss19.team03.scart.client.gui.GroupForm;
import de.hdm.itprojektss19.team03.scart.client.gui.Navigation;
import de.hdm.itprojektss19.team03.scart.client.gui.RegistryForm;
import de.hdm.itprojektss19.team03.scart.client.gui.ShowGroceryList;
import de.hdm.itprojektss19.team03.scart.shared.bo.LoginInfo;

/**
 * Die Klasse EntryPoint beinhaltet die Methode <code>onModuleLoad()</code> 
 * die beim Start der Applikation als erstes aufgerufen wird.
 * 
 * @author PatrickLehle
 * @author DennisLehle
 *
 */
public class Scart implements EntryPoint {
	

	private LoginInfo loginInfo = null;
	private User ownProfil = null;
	private VerticalPanel loginPanel = new VerticalPanel();
	private Label searchlb = new Label();
	private Label profilLb = new Label();
	private HTML loginHTML = new HTML("<h7></h7>");
	private	HTML scartHTML = new HTML("<h8>Welcome to Scart</h8>");
	private Anchor signInLink = new Anchor("sign in with Google");
	
	
	 /**
	  * Erzeugung des <code>EditorService</code>-Objekts ist fuer die initialisierung der 
	  * Verwaltung noetig, um die Aktivitaeten der Applikation zu steuern.
	  */
	LoginServiceAsync loginService = GWT.create(LoginService.class);
	EditorServiceAsync editorVerwaltung = ClientsideSettings.getEditorVerwaltung();
	ClientsideSettings clientSettings = new ClientsideSettings();

		/**
	 	* Die EntryPoint Methode <code>onModuleLoad()</code> ist der Einstiegspunkt,
	 	* die automatisch aufgerufen wird, in dem ein Modul geladen wird. Vergleichbar
	 	* mit der <code>main()</code> Methode.
	 	*/	
	public void onModuleLoad() {
			
		loginService.login(GWT.getHostPageBaseURL() + "Scart.html", new AsyncCallback<LoginInfo>() {

				@Override
				public void onFailure(Throwable e) {
					Window.alert("Login Error: " + e.toString());
					

				}

				@Override
				public void onSuccess(LoginInfo result) {
					loginInfo = result;
					
					if (loginInfo.isLoggedIn()) {
						//User wird mit seiner Email adresse in der Datenbank identifiziert
						editorVerwaltung.getUserByGMail(loginInfo.getEmailAddress(), new AsyncCallback<User>() {

							@Override
							public void onFailure(Throwable e) {
								Window.alert("An error occurred while trying to login: ");
						
							}

							@Override
							public void onSuccess(User user) {
								//Ist der Nutzer noch registriert wird er zur Startseite weitergeleitet.
								if (user != null) {
									RootPanel.get("content").clear();
									// Die start Methode wird geladen
									opening(user);
										
								//Besitzt der user auf Scart noch keinen Account wird er zur RegistryForm weitergeleitet.
								} else {
									//Div's alle leeren.
									RootPanel.get("content").clear();
									RootPanel.get("navigator").clear();
									
									// Der User wird anhand der eingeloggten Email-Adresse erstellt
									editorVerwaltung.createUser(loginInfo.getEmailAddress(), new AsyncCallback<User>() {

										@Override
										public void onFailure(Throwable e) {
											e.getMessage();
										}

										@Override
										public void onSuccess(User result) {
											//Die RegistryForm wird hier aufgerufen.
											RootPanel.get("content").add(new RegistryForm(result));

										}

									});
								}
							}
						});

					} else {
						// Login wird geladen
						signInLink.setHref(loginInfo.getLoginUrl());
						loginPanel.add(loginHTML);
						loginPanel.add(scartHTML);
					//	loginPanel.add(loginLabel);
						loginPanel.add(signInLink);
						loginPanel.addStyleName("login");
						
						// Footer wird fuer den LoginContainer gecleared
						RootPanel.get("footer").clear();
						// Hinzufuegen des LoginPanels im <div> "content".
						RootPanel.get("content").add(loginPanel);	
					}
				}
			});
		}

		/**
		 * Initialisierungmethode fuer das Verwaltungssystems.
		 * 
		 * @param user
		 */
		private void opening(User user) {
			
			//Logout wird gesetzt
			HTML signOutLink = new HTML("<p><a href='" + loginInfo.getLogoutUrl() 
			+ "'><span class='glyphicon glyphicon-log-out'></span></a></p>");
			RootPanel.get("usermenu").add(signOutLink);
			
			//Naechster Schritt ist das setzen von Cookies zur identifikation eines Users.
			Cookies.setCookie("userGMail", user.getEmail()); 
			
			//Wenn der user bereits existiert, wird zusaetzlich die userID gesetzt.
			Cookies.setCookie("userID", String.valueOf(user.getId()));
			RootPanel.get("navigator").add(new Navigation(user));
			
			editorVerwaltung.getOwnProfile(user, new AsyncCallback <User>(){
				
				@Override
				public void onFailure(Throwable e){
					e.getMessage().toString();
				}
				@Override
				public void onSuccess(User result) {
					
					//Das eigene Profil wird gesetzt fuer auesseren Zugriff.
					ownProfil = result;
					
					//Labels setzen den Namen des User der eingeloggt ist.
					profilLb.setText(result.getUsername());
					profilLb.setTitle("Your Profil");
					
					// Label wird mit ClickHandler verknuepft, um auf das eigene Profil zu gelangen.
					profilLb.addClickHandler(new ownProfilClickHandler());
					profilLb.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_JUSTIFY);
					
					searchlb.setText("Search ");
					searchlb.setTitle("Search for a specific GroceryList/ Article");
					searchlb.addClickHandler(new searchClickHandler());
					searchlb.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_JUSTIFY);
					// Hier wird das UserMenu <div> container gecleared.
					RootPanel.get("usermenu").clear();
					
					// Logout wird hier erstellt.
					HTML signOutLink = new HTML("<p><a href='" + loginInfo.getLogoutUrl() 
					+ "'><span class='glyphicon glyphicon-log-out'></span></a></p>");
					signOutLink.setTitle("You're leaving? That's not smart.");
					
					RootPanel.get("usermenu").add(searchlb);
					RootPanel.get("usermenu").add(new HTML("<p><span class='glyphicon glyphicon-search'></span> &nbsp;"));
					RootPanel.get("usermenu").add(new HTML("  "));
					RootPanel.get("usermenu").add(profilLb);
					RootPanel.get("usermenu").add(new HTML("<p><span class='glyphicon glyphicon-user'></span> &nbsp;"));
					RootPanel.get("usermenu").add(signOutLink);
					
				}
				
			});
			
			
			/*
			 * Hinzufuegen der Scart.html/ScartReport.html in den Footer.
			 */
			HorizontalPanel footer = new HorizontalPanel();
			Anchor homepage = new Anchor("Homepage ", "Scart.html");
			HTML textOne = new HTML(" | ");
			HTML textTwo = new HTML(" | © 2019 Scart, Hochschule der\n" + "Medien Stuttgart | ");
			Anchor reportGeneratorLink = new Anchor (" ReportGenerator ", "ScartReport.html");
			
			footer.add(homepage);
			footer.add(textOne);
			footer.add(reportGeneratorLink);
			footer.add(textTwo);
			
			// Das footerPanel wird gestylt
			RootPanel.get("footer").setStylePrimaryName("footer");
			// Das footerPanel wird dem <div> footer hinzugefuegt
			RootPanel.get("footer").add(footer);
			// Hinzufuegen der Uebersicht fuer alle Kontakte
			RootPanel.get("content").add(new ShowGroceryList(user));
		}
		/**
		 * ClickHandler des Labels, um auf das eigene Profil aufzurufen.
		 * 
		 * @author PatrickLehle
		 *
		 */
		class ownProfilClickHandler implements ClickHandler {

			@Override
			public void onClick(ClickEvent event) {
				RootPanel.get("content").clear();
				new GroupForm(ownProfil);
				
			}
			
		}
		
		class searchClickHandler implements ClickHandler {

			@Override
			public void onClick(ClickEvent event) {
				//wird noch implementiert <3
			}
			
	}
		
}



