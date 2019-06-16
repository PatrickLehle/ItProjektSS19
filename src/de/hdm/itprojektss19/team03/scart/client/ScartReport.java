package de.hdm.itprojektss19.team03.scart.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektss19.team03.scart.shared.ReportGeneratorAsync;
import de.hdm.itprojektss19.team03.scart.client.gui.NavigationReport;
import de.hdm.itprojektss19.team03.scart.client.gui.ReportFilterForm;
import de.hdm.itprojektss19.team03.scart.client.ClientsideSettings;
import de.hdm.itprojektss19.team03.scart.shared.EditorServiceAsync;
import de.hdm.itprojektss19.team03.scart.shared.LoginServiceAsync;
import de.hdm.itprojektss19.team03.scart.shared.bo.LoginInfo;
import de.hdm.itprojektss19.team03.scart.shared.bo.User;


/**
 * Die Klasse EntryPoint defniert die Methode <code>onModuleLoad()</code>, diese wird beim 
 * Start des Reports aufgerufen. Die implementation findet statt, 
 * damit eine Klasse als Moduleingangspunkt arbeiten kann.
 * Der ReportGenerator soll nur statische HTML ausgaben taetigen.
 * 
 * @author PatrickLehle, DuyVanHo
 * @author DennisLehle
 */
public class ScartReport implements EntryPoint {
	
	private LoginInfo loginInfo = new LoginInfo();
	private VerticalPanel loginPanel = new VerticalPanel();
	private Label loginLabel = new Label("Please Sign in with your Googlemail to access the ReportGenerator");
	private Anchor signInLink = new Anchor("Sign in with Google");
	HTML loginHTML = new HTML("<h7></h7>");
	HTML scartHTML = new HTML("<h9>Scart-REPORT</h9>");

	ClientsideSettings clientSettings = new ClientsideSettings();
	LoginServiceAsync loginService = ClientsideSettings.getLoginService();
	EditorServiceAsync editorVerwaltung = ClientsideSettings.getEditorVerwaltung();
	
	/**
	 * ReportService und EditorService werden auf null gesetzt. Dient zur Absicherung.
	 */
	ReportGeneratorAsync reportGeneratorService = null;
	EditorServiceAsync editorService = null;
	
	/**
	 * Die EntryPoint Methode <code>onModuleLoad()</code> dient als Einstiegspunkt.
	 * Vergleichbar mit der Main Methode.
	 */
	public void onModuleLoad() {
		loginService.login(GWT.getHostPageBaseURL() + "ScartReport.html", new AsyncCallback<LoginInfo>() {

			@Override
			public void onFailure(Throwable error) {
				Window.alert("Login failed: " + error.toString());	
			}

			@Override
			public void onSuccess(LoginInfo result) {
				loginInfo = result;
				if(loginInfo.isLoggedIn() == true) {
					//User wird fuer die session erstellt
					User u = new User();
//					u.setId(Integer.valueOf(Cookies.getCookie("userId")));
//					u.setEmail(Cookies.getCookie("userGMail"));
					
//					//Report wird geladen
					loadReport(u);
				} else {
					loadLogin();
				}
			}

			/**
			 * loadLogin Methode laedt den Login fuer unseren Report
			 */
			private void loadLogin() {
				
				signInLink.setHref(loginInfo.getLoginUrl());
				loginPanel.add(loginHTML);
				loginPanel.add(scartHTML);
				loginPanel.add(loginLabel);
				loginPanel.add(signInLink);
				loginPanel.addStyleName("login");

				// Footer wird geclesred fuer unseren Container fuer den Login
				RootPanel.get("footer").clear();
				// LoginPanels in den <div> Container "content" hinzufuegen
				RootPanel.get("content").add(loginPanel);
				
			}

			private void loadReport(final User u) {
				
				RootPanel.get("navigator").add(new ReportFilterForm());
				HTML welcome = new HTML(
						"<div align=\"center\"> <h8> <b> &nbsp; &nbsp; Welcome to the Report Generator &nbsp; &nbsp; </b></h8> </div>");

				RootPanel.get("top").add(welcome);
				RootPanel.get("top").add(new HTML(
						"<div align=\"center\"> <image src='/images/reportStart.png' width='100px' height='100px' align='center' /></div>"));

				/**
				 * Hinzufuegen der ScartReport.html und Scart.html in den Footer.
				 */
				HorizontalPanel footer = new HorizontalPanel();
				Anchor homepage = new Anchor("Homepage", "ScartReport.html");
				HTML TextOne = new HTML(" | ");
				Anchor editorLink = new Anchor("Back 2 Scart", "Scart.html");
				HTML TextTwo = new HTML(" | 2019 Scart | ");
				
				

				footer.add(homepage);
				footer.add(TextOne);
				footer.add(editorLink);
				footer.add(TextTwo);

				// FooterPanel wird dem <div> Footer hinzugefuegt
				RootPanel.get("footer").add(footer);
				
			}
		});
	}	
}
