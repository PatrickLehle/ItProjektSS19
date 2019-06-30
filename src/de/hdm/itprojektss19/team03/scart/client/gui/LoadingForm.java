package de.hdm.itprojektss19.team03.scart.client.gui;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;

public class LoadingForm extends HorizontalPanel {

	// Images
	Image loading1 = new Image();
	Image loading2 = new Image();
	Image loading3 = new Image();

	public LoadingForm() {
		loading1.setUrl("/images/fruits-banana.gif");
		loading2.setUrl("/images/fruits-grape.gif");
		loading3.setUrl("/images/fruits-melon.gif");
		this.add(loading1);
		this.add(loading2);
		this.add(loading3);
		this.setHorizontalAlignment(ALIGN_CENTER);
		this.setVerticalAlignment(ALIGN_MIDDLE);
	}
}
