package de.hdm.itprojektss19.team03.scart.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.LinkElement;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Timer;
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

	/**
	 * Startet, sobald das Modul geladen wird. Prüft zunaechst, ob ein Nutzer
	 * eingeloggt ist
	 */
	public void onModuleLoad() {

		Window.alert("Diese Seite verwendet funktionale Cookies. Deal with it!");
		changeCSS();
		loginService.login(GWT.getHostPageBaseURL(), new AsyncCallback<LoginInfo>() {

			public void onFailure(Throwable err) {
				GWT.log("Failed to login, trying again: " + err.getMessage());
				onModuleLoad();
			}

			public void onSuccess(final LoginInfo logInfo) {
				if (logInfo.isLoggedIn()) {

					editorService.getUserByGMail(logInfo.getEmailAddress(), new AsyncCallback<User>() {

						public void onFailure(Throwable caught) {
							RootPanel.get("header").clear();
							RootPanel.get("header").add(new ToolbarForm());
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

		Window.addResizeHandler(new ResponsiveHandler());
		RootPanel.get("footer").clear();
		RootPanel.get("footer").add(footer);
	}

	/**
	 * Responsive Behaviour wird hier behandelt
	 *
	 */
	class ResponsiveHandler implements ResizeHandler {

		Timer resizeTimer = new Timer() {
			public void run() {
				changeCSS();
			}
		};

		public void onResize(ResizeEvent event) {
			resizeTimer.cancel();
			resizeTimer.schedule(250);
		}

	}

	/**
	 * Change CSS class acroding to size
	 */
	public void changeCSS() {

		LinkElement link = Document.get().createLinkElement();
		link.setRel("stylesheet");
		link.setHref("ScartMobile.css");
		link.setId("mobile");
		LinkElement link2 = Document.get().createLinkElement();
		link2.setRel("stylesheet");
		link2.setHref("Scart.css");
		link2.setId("desktop");

		if (Window.getClientWidth() < 600) {
			try {
				RootPanel.get("navigation").getWidget(0).addStyleName("hide");
				RootPanel.get("content").getElement().getStyle().setProperty("margin", "120px 0px 0px 0px");
			} catch (Exception e) {
			}
			nativeAttachToHead(link, "desktop");
		} else {
			try {
				RootPanel.get("content").getElement().getStyle().setProperty("margin", "120px 0px 0px 250px");
				RootPanel.get("navigation").getWidget(0).removeStyleName("hide");
			} catch (Exception e) {
			}
			nativeAttachToHead(link2, "mobile");
		}
	}

	/**
	 * Attach element to head
	 */
	protected static native void nativeAttachToHead(JavaScriptObject scriptElement, String remove) /*-{
		$doc.getElementsByTagName("head")[0].appendChild(scriptElement);
		if ($doc.getElementById(remove) != null) {
			$doc.getElementById(remove).remove();
		}
		;
	}-*/;

	/**
	 * Laed die LoginForm in das Content Panel
	 * 
	 * @param logURL Die logout URL wird von google gesetzt
	 */
	private void login(String logURL) {
		RootPanel.get("header").clear();
		RootPanel.get("header").add(new ToolbarForm());
		LoginForm loginForm = new LoginForm(logURL);
		RootPanel.get("content").add(loginForm);
	}

	/**
	 * Fuegt den Inhalt der Seite dem Content Panel hinzu
	 * 
	 * @param user Das User Objekt
	 */
	private void loadPage(User user) {
		ToolbarForm toolbar = new ToolbarForm(user);

		groupForm = new GroupForm(user);
		groupForm.addStyleName("navigation");
		groupForm.setHeight("100%");
		navigationPanel.add(groupForm);

		RootPanel.get("header").clear();
		RootPanel.get("header").add(toolbar);

		RootPanel.get("navigation").clear();
		RootPanel.get("navigation").add(navigationPanel);

		RootPanel.get("content").clear();
		RootPanel.get("content").add(contentPanel);

	}
}
