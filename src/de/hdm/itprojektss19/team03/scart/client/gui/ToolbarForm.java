package de.hdm.itprojektss19.team03.scart.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;

public class ToolbarForm extends HorizontalPanel {

	public ToolbarForm() {
		Anchor reportGeneratorLink = new Anchor (" ReportGenerator ", "ScartReport.html");
		Image img = new Image();
		Button reportBtn = new Button("Report Generator");
		img.setUrl("/images/ScartLogo.png");
		img.addStyleName("logo-toolbar");
		this.add(img);

		this.addStyleName("toolbar");
		this.setHorizontalAlignment(ALIGN_LEFT);
		this.setVerticalAlignment(ALIGN_MIDDLE);

		this.add(reportGeneratorLink);
		
	}
}
