package de.hdm.itprojektss19.team03.scart.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;

import de.hdm.itprojektss19.team03.scart.client.gui.FooterForm;
import de.hdm.itprojektss19.team03.scart.client.gui.GroceryListForm;
import de.hdm.itprojektss19.team03.scart.client.gui.GroupForm;
import de.hdm.itprojektss19.team03.scart.client.gui.LoginForm;
import de.hdm.itprojektss19.team03.scart.client.gui.ProfilForm;
import de.hdm.itprojektss19.team03.scart.client.gui.RegistryForm;
import de.hdm.itprojektss19.team03.scart.client.gui.ToolbarForm;
import de.hdm.itprojektss19.team03.scart.shared.EditorService;
import de.hdm.itprojektss19.team03.scart.shared.EditorServiceAsync;
import de.hdm.itprojektss19.team03.scart.shared.LoginService;
import de.hdm.itprojektss19.team03.scart.shared.LoginServiceAsync;
import de.hdm.itprojektss19.team03.scart.shared.bo.LoginInfo;
import de.hdm.itprojektss19.team03.scart.shared.bo.User;

/**
 * Die Scart classe implementiert EntryPoint, d.h. sie wird als erstes
 * aufgerufen, sobald die Anwendung gestartet wird.
 * 
 * @author PatrickLehle
 * @author Marco Dell'Oso
 *
 */
public class Scart implements EntryPoint {

	private LoginServiceAsync loginService = GWT.create(LoginService.class);
	private EditorServiceAsync editorService = GWT.create(EditorService.class);

	private User user = new User();

	private ToolbarForm toolbar = new ToolbarForm();
	private FooterForm footer = new FooterForm();
	private GroceryListForm groceryListForm = new GroceryListForm();
	private GroupForm groupForm = new GroupForm();
	private ProfilForm profilForm = new ProfilForm();

	private HorizontalPanel contentPanel = new HorizontalPanel();

	// @todo: delete test buttons
	private Button button1 = new Button("grocery list");
	private Button button2 = new Button("profile ");

	/**
	 * startet, sobald das Modul geladen wird.
	 */
	public void onModuleLoad() {
		contentPanel.setSpacing(30);

		loginService.login(GWT.getHostPageBaseURL(), new AsyncCallback<LoginInfo>() {

			public void onFailure(Throwable err) {
				Window.alert(err.getMessage());
			}

			public void onSuccess(LoginInfo logInfo) {
				/**
				 * Check if the user is logged in
				 */
				if (logInfo.isLoggedIn()) {
					user.setEmail(logInfo.getEmailAddress());
					editorService.getUserByGMail(logInfo.getEmailAddress(), newUserCallback);
				} else {
					login(logInfo.getLoginUrl());
				}
			}

		});

		/**
		 * Add header and footer to the (root-)Panels they belong to
		 */
		RootPanel.get("header").clear();
		RootPanel.get("header").add(toolbar);
		RootPanel.get("footer").clear();
		RootPanel.get("footer").add(footer);
	}

	/**
	 * Load the login Form into content panel
	 */
	private void login(String logURL) {
		LoginForm loginForm = new LoginForm(logURL);
		RootPanel.get("content").clear();
		RootPanel.get("content").add(loginForm);
	}

	/**
	 * Add content to content panel
	 */
	private void loadPage() {

		contentPanel.add(groupForm);
		contentPanel.add(button1);
		contentPanel.add(button2);
		RootPanel.get("content").clear();
		RootPanel.get("content").add(contentPanel);

		// @todo: delete test buttons
		button1.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent evt) {
				RootPanel.get("content").clear();
				RootPanel.get("content").add(groceryListForm);

			}
		});

		button2.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent evt) {
				RootPanel.get("content").clear();
				RootPanel.get("content").add(profilForm);

			}
		});
	}

	AsyncCallback<User> newUserCallback = new AsyncCallback<User>() {

		public void onFailure(Throwable t) {
			User u = new User();
			user.setEmail("test@t.de");
			RegistryForm registerForm = new RegistryForm(u);
			RootPanel.get("content").clear();
			RootPanel.get("content").add(registerForm);

		}

		public void onSuccess(User u) {
			loadPage();
		}
	};

}
