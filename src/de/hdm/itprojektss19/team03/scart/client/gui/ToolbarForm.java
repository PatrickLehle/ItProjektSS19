package de.hdm.itprojektss19.team03.scart.client.gui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;

public class ToolbarForm extends HorizontalPanel {

	public ToolbarForm() {
		Anchor reportGeneratorLink = new Anchor("", "ScartReport.html");
		Button reportBtn = new Button("Report Generator");
		Button profilBtn = new Button("Profil");
		final ProfilForm profilForm = new ProfilForm();

		reportBtn.addStyleName("button");
		profilBtn.addStyleName("button");
		this.addStyleName("toolbar");
		this.setVerticalAlignment(ALIGN_MIDDLE);
		this.setHorizontalAlignment(ALIGN_CENTER);

		profilBtn.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent arg0) {
				RootPanel.get("content").clear();
				RootPanel.get("content").add(profilForm);
			}
		});

		reportBtn.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent arg0) {
				Window.open(GWT.getHostPageBaseURL() + "ScartReport.html", "_blank", "");
			}
		});

		// reportGeneratorLink.getElement().appendChild(reportBtn.getElement());
		this.add(profilBtn);
		this.add(reportBtn);
	}

}
