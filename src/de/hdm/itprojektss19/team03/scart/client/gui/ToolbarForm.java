package de.hdm.itprojektss19.team03.scart.client.gui;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;

public class ToolbarForm extends HorizontalPanel {

	public ToolbarForm() {
		Image img = new Image();

		img.setUrl("/images/ScartLogo.png");
		img.addStyleName("logo-toolbar");
		this.add(img);

		this.addStyleName("toolbar");
		this.add(new Button("Report Generator"));
		this.setHorizontalAlignment(ALIGN_LEFT);
		this.setVerticalAlignment(ALIGN_MIDDLE);
	}
}
