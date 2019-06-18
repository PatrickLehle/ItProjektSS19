package de.hdm.itprojektss19.team03.scart.client.gui;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DateBox;
import de.hdm.itprojektss19.team03.scart.client.ClientsideSettings;
import de.hdm.itprojektss19.team03.scart.shared.EditorServiceAsync;
import de.hdm.itprojektss19.team03.scart.shared.bo.User;

/**
 * Die RegistryForm wird zum Login aufgerufen, wenn der User die Applikation zum
 * ersten mal verwendet.
 * 
 * @author PatrickLehle, Marco Dell'Oso
 */
public class RegistryForm extends VerticalPanel {
	
	private EditorServiceAsync ev = ClientsideSettings.getEditor();

	Label welcome = new Label("Please fill in the required fields");
	TextBox nameTb = new TextBox();
	Button save = new Button("Save");

	
	
	protected void onLoad() {
		save.setEnabled(false);
		save.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent e) {
				// TODO Auto-generated method stub

			}
		});
		
		nameTb.addChangeHandler(new ChangeHandler() {
			
			public void onChange(ChangeEvent e) {
				Window.alert(e.toString());
			}
		});
	}
	
	/**
	 * Konstruktor fuer die RegistryForm-Klasse.
	 * 
	 * @param u
	 *            user der eigenloggt ist
	 */
	public RegistryForm(User u) {
		nameTb.setTitle("Name Eingeben");
		

		this.add(welcome);
		this.add(nameTb);
		this.add(save);


	}
	
	public void saveUser(User u) {
		
	};


}
