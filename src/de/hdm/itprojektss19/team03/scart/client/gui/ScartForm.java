package de.hdm.itprojektss19.team03.scart.client.gui;

import com.google.gwt.user.client.ui.VerticalPanel;
import de.hdm.itprojektss19.team03.scart.shared.bo.User;

public class ScartForm extends VerticalPanel {

	protected User currentUser = null;
	ContentForm contentForm = new ContentForm();

	/**
	 * @param currentUser
	 *            Aktuellee User wird der ScartForm übergeben, damit er darin und in
	 *            den unter Formen verwendet werden kann
	 */
	public ScartForm(User currentUser) {
		this.currentUser = currentUser;
	}

	/**
	 * onLoad wird aufgerufen, sobald die Form im Browser erstellt wurde.
	 */
	protected void onLoad() {
		super.onLoad();

		/**
		 * CSS Klassen zu den einzelnen Formen hinzufügen
		 */
		this.addStyleName("scart-form");
		contentForm.addStyleName("main-form");
		
		this.add(contentForm);
	}
}
