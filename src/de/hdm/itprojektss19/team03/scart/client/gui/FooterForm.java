package de.hdm.itprojektss19.team03.scart.client.gui;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;

public class FooterForm extends HorizontalPanel {

	public FooterForm() {
		Label txt = new Label("footer text");

		this.add(txt);
		this.addStyleName("footer");
		this.setHorizontalAlignment(ALIGN_CENTER);
		this.setVerticalAlignment(ALIGN_MIDDLE);
	}
}