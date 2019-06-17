package de.hdm.itprojektss19.team03.scart.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;

import de.hdm.itprojektss19.team03.scart.client.gui.FooterForm;
import de.hdm.itprojektss19.team03.scart.client.gui.GroupForm;
import de.hdm.itprojektss19.team03.scart.client.gui.LoginForm;
import de.hdm.itprojektss19.team03.scart.client.gui.RegistryForm;
import de.hdm.itprojektss19.team03.scart.client.gui.ScartForm;
import de.hdm.itprojektss19.team03.scart.client.gui.ToolbarForm;
import de.hdm.itprojektss19.team03.scart.shared.EditorServiceAsync;
import de.hdm.itprojektss19.team03.scart.shared.LoginService;
import de.hdm.itprojektss19.team03.scart.shared.LoginServiceAsync;
import de.hdm.itprojektss19.team03.scart.shared.bo.LoginInfo;
import de.hdm.itprojektss19.team03.scart.shared.bo.User;

/**
 * 
 * @author PatrickLehle
 * @author Marco Dell'Oso
 *
 */
public class Scart implements EntryPoint {

	 /**
	  * Erzeugung des <code>EditorService</code>-Objekts ist fuer die initialisierung der 
	  * Verwaltung noetig, um die Aktivitaeten der Applikation zu steuern.
	  */
	LoginServiceAsync loginService = GWT.create(LoginService.class);
	EditorServiceAsync editorService = ClientsideSettings.getEditor();
	ClientsideSettings clientSettings = new ClientsideSettings();

	private User ownProfil = null;
	private LoginInfo loginInfo = null;
	ToolbarForm toolbar = new ToolbarForm();
	FooterForm footer = new FooterForm();
	GroupForm groupForm = new GroupForm(ownProfil);

	/**
	 * Die EntryPoint Methode <code>onModuleLoad()</code> ist der Einstiegspunkt,
	 * die automatisch aufgerufen wird, in dem ein Modul geladen wird. Vergleichbar
	 * mit der <code>main()</code> Methode.
	 */
	public void onModuleLoad() {
		RootPanel.get("header").add(toolbar);
		RootPanel.get("footer").add(footer);
		RootPanel.get("main").add(groupForm);
//		loginService.login(GWT.getHostPageBaseURL() + "Scart.html", new LoginCallback());
	}

//	public class LoginCallback implements AsyncCallback<LoginInfo> {
//		public void onFailure(Throwable e) {
//			Window.alert("Login Failed " + e.getMessage());
//		}
//
//		public void onSuccess(LoginInfo logInfo) {
//
//			if (logInfo != null) {
//
//				// falls user eingeloggt ist
//				if (logInfo.isLoggedIn()) {
//
//					editorService.getUserByGMail(logInfo.getEmailAddress(), new AsyncCallback<User>() {
//
//						public void onFailure(Throwable e) { 
//							Window.alert("An error occurred while trying to login: " + e);
//
//						}
//
//						public void onSuccess(User u) {
//
//							// Ist der Nutzer registriert wird er zur Startseite weitergeleitet.
//							if (u != null) {
//								opening(u);
//
//								// Besitzt der user auf Scart noch keinen Account wird er zur RegistryForm
//								// weitergeleitet.
//							} else {
//								RootPanel.get("main").clear();
//								RootPanel.get("main").add(new RegistryForm(u));
//							}
//
//						}
//
//					});
//
//					// falls user nicht eingeloggt, login laden
//				} else {
//					RootPanel.get("main").clear();
//					RootPanel.get("main").add(new LoginForm(logInfo.getLoginUrl()));
//					return;
//				}
//
//			}
//		}
//
//		/**
//		 * Initialisierungmethode fuer das Verwaltungssystems.
//		 * 
//		 * @param user
//		 */
//		private void opening(User user) {
//
//			// Naechster Schritt ist das setzen von Cookies zur identifikation eines Users.
//			Cookies.setCookie("userGMail", user.getEmail());
//
//			// Wenn der user bereits existiert, wird zusaetzlich die userID gesetzt.
//			Cookies.setCookie("userID", String.valueOf(user.getId()));
//
//			editorService.getOwnProfile(user, new AsyncCallback<User>() {
//
//				@Override
//				public void onFailure(Throwable e) {
//					Window.alert("fail1" + e.getMessage().toString());
//				}
//
//				@Override
//				public void onSuccess(User result) {
//
//					ownProfil = result;
//					RootPanel.get("main").clear();
//					RootPanel.get("main").add(new ScartForm(result));
//
//				}
//
//			});
//
//		}
//	}
}
