package de.hdm.itprojektss19.team03.scart.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;

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
	private String signOutLink;

	private FooterForm footer = new FooterForm();
	private GroceryListForm groceryListForm = new GroceryListForm();
	private GroupForm groupForm = new GroupForm();
	private ProfilForm profilForm = new ProfilForm();

	private HorizontalPanel contentPanel = new HorizontalPanel();
	private HorizontalPanel innerContentPanel = new HorizontalPanel();
	private ScrollPanel navigationPanel = new ScrollPanel();
	private ToolbarForm toolbar = new ToolbarForm(innerContentPanel);

	// @todo: delete test buttons
	private Button button1 = new Button("grocery list");

	private static Scart articleMapper = null;

	/**
	 * startet, sobald das Modul geladen wird.
	 */
	public void onModuleLoad() {
		innerContentPanel.setSpacing(30);

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
							innerContentPanel.clear();
							innerContentPanel.add(registryFrom);
							RootPanel.get("content").clear();
							RootPanel.get("content").add(innerContentPanel);
						}

						public void onSuccess(User result) {
							loadPage();
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
		innerContentPanel.clear();
		innerContentPanel.add(loginForm);
		RootPanel.get("content").add(innerContentPanel);
	}

	/**
	 * Add content to content panel
	 */
	private void loadPage() {

		innerContentPanel.addStyleName("inner-content");
		groupForm.addStyleName("navigation");
		groupForm.setHeight("100%");
		navigationPanel.add(groupForm);
		innerContentPanel.add(button1);
		contentPanel.add(navigationPanel);
		contentPanel.add(innerContentPanel);

		RootPanel.get("content").clear();
		RootPanel.get("content").add(contentPanel);

		// @todo: delete test buttons
		button1.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent evt) {
				innerContentPanel.clear();
				innerContentPanel.add(groceryListForm);

			}
		});

	}

	public void setInnerContent(Panel panel) {
		Window.alert(this.innerContentPanel.getClass().getName());
		innerContentPanel.clear();
		innerContentPanel.add(panel);
	}

	public HorizontalPanel getInnerContentPanel() {
		return innerContentPanel;
	}

	public void setInnerContentPanel(HorizontalPanel innerContentPanel) {
		this.innerContentPanel = innerContentPanel;
	}
}
