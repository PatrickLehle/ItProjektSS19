package de.hdm.itprojektss19.team03.scart.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;

import de.hdm.itprojektss19.team03.scart.client.gui.FooterForm;
import de.hdm.itprojektss19.team03.scart.client.gui.GroupForm;
import de.hdm.itprojektss19.team03.scart.client.gui.LoginForm;
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
 * @author Marco Dell'Oso
 *
 */
public class Scart implements EntryPoint {

	private LoginServiceAsync loginService = GWT.create(LoginService.class);
	private EditorServiceAsync editorService = GWT.create(EditorService.class);

	private FooterForm footer = new FooterForm();
	private GroupForm groupForm;

	private HorizontalPanel contentPanel = new HorizontalPanel();
	private ScrollPanel navigationPanel = new ScrollPanel();
	private ToolbarForm toolbar = new ToolbarForm();

	/**
	 * startet, sobald das Modul geladen wird.
	 */
	public void onModuleLoad() {

		loginService.login(GWT.getHostPageBaseURL(), new AsyncCallback<LoginInfo>() {

			public void onFailure(Throwable err) {
				Window.alert(err.getMessage());
			}

			public void onSuccess(final LoginInfo logInfo) {
				/**
				 * Check if the user is logged in
				 */
				if (logInfo.isLoggedIn()) {

					editorService.getUserByGMail(logInfo.getEmailAddress(), new AsyncCallback<User>() {

						public void onFailure(Throwable caught) {
							RegistryForm registryFrom = new RegistryForm(logInfo.getLogoutUrl(),
									logInfo.getEmailAddress());
							RootPanel.get("content").clear();
							RootPanel.get("content").add(registryFrom);
						}

						public void onSuccess(User result) {
							loadPage(result);
						}

					});

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
		RootPanel.get("content").add(loginForm);
	}

	/**
	 * Add content to content panel
	 */
	private void loadPage(User user) {

		groupForm = new GroupForm(user);
		groupForm.addStyleName("navigation");
		groupForm.setHeight("100%");
		navigationPanel.add(groupForm);

		RootPanel.get("navigation").clear();
		RootPanel.get("navigation").add(navigationPanel);

		RootPanel.get("content").clear();
		RootPanel.get("content").add(contentPanel);

	}
}
