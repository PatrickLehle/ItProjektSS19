package de.hdm.itprojektss19.team03.scart.client.gui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;

import de.hdm.itprojektss19.team03.scart.shared.bo.User;

public class ToolbarForm extends HorizontalPanel {

	/**
	 * Default Konsturktor, falls kein User angemeldet ist
	 */
	public ToolbarForm() {
		this.addStyleName("toolbar");
	}

	/**
	 * Konstruktor, der aufgerufen wird, falls ein User angemeldet ist
	 * 
	 * @param user User Obejekt des angemeldeten Users
	 */
	public ToolbarForm(User user) {
		Anchor reportGeneratorLink = new Anchor("", "ScartReport.html");
		Button reportBtn = new Button("Report Generator");
		Button profilBtn = new Button("Profil");
		Button menutBtn = new Button("<image src='/images/menu.png' width='16px' height='16px' align='center'/>");
		menutBtn.setStyleName("icon-button");
		menutBtn.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent arg0) {
				if (RootPanel.get("navigation").getWidget(0).getStyleName() == "hide") {
					RootPanel.get("navigation").getWidget(0).removeStyleName("hide");
					if (Window.getClientWidth() >= 600) {
						RootPanel.get("content").getElement().getStyle().setProperty("margin", "120px 0px 0px 300px");
					}
				} else {
					RootPanel.get("navigation").getWidget(0).addStyleName("hide");

					RootPanel.get("content").getElement().getStyle().setProperty("margin", "120px 0px 0px 0px");
				}
			}

		});

		final ProfileForm profileForm = new ProfileForm(user);

		reportBtn.addStyleName("button");
		profilBtn.addStyleName("button");
		this.addStyleName("toolbar");
		this.setVerticalAlignment(ALIGN_MIDDLE);
		this.setHorizontalAlignment(ALIGN_LEFT);

		profilBtn.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent arg0) {
				RootPanel.get("content").clear();
				RootPanel.get("content").add(profileForm);
			}
		});

		reportBtn.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent arg0) {
				Window.open(GWT.getHostPageBaseURL() + "ScartReport.html", "_blank", "");
			}
		});
		this.add(menutBtn);
		// reportGeneratorLink.getElement().appendChild(reportBtn.getElement());
		this.add(profilBtn);
		this.add(reportBtn);
	}

}
