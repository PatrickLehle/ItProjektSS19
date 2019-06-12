package de.hdm.itprojektss19.team03.scart.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektss19.team03.scart.client.ClientsideSettings;
import de.hdm.itprojektss19.team03.scart.shared.EditorServiceAsync;
import de.hdm.itprojektss19.team03.scart.shared.bo.User;
import src.de.hdm.itprojekt.client.gui.EditorAdministrationAsync;
import src.de.hdm.itprojekt.client.gui.ProfilForm.DeleteUserCallBack;
import src.de.hdm.itprojekt.client.gui.ProfilForm.FindUserCallBack;
import src.de.hdm.itprojekt.client.gui.ProfilForm.VerifyFieldCallback;

/**
 * 
 * @author vanduyho
 *
 */

public class ProfilForm {
	
	User user = null;
	
	Label newProfile = null;
	Label userName = null;
	Label emailAdress = null;
	
	Button editButton = null;
	Button deleteButton = null;
	
	VerticalPanel contenPanel = null;
	HorizontalPanel newProfilePanel = null;
	HorizontalPanel userNamePanel = null;
	HorizontalPanel emailAdressPanel = null;
	HorizontalPanel buttonPanel = null;
	
	private EditorServiceAsync ev = ClientsideSettings.getEditorVerwaltung();
	
	/**
	 * 
	 * Konstruktor
	 * 
	 */
	
	public ProfilForm (User u) {
		
		this.user = u;
		
	}

	/*
	 * 
	 * ClickHandler
	 * 
	 */

	class EditButtonClickHandler implements ClickHandler {
		
		ProfilForm profilForm = null;
	
		public EditButtonClickHandler (ProfilForm pf) {
		
			this.profilForm = pf;
			
	}
	
	public void onClick (ClickEvent event) {
		
		userNamePanel.remove(userName);
		TextBox userName = new TextBox();
		userName.setText(user.getUsername());
		userNamePanel.add(userName);
		
		emailAdressPanel.remove(emailAdress);
		TextBox emailAdress = new TextBox();
		emailAdress.setText(user.getEmail());
		emailAdressPanel.add(emailAdress);
		
	}
	
	class DeleteButtonClickHandler implements ClickHandler {
		
		User user;
		
		public DeleteButtonClickHandler(User u) {
			
			this.user = u;
		
	}
	
		public void onClick (ClickEvent event) {
			
			DialogBox db = new DialogBox();
			VerticalPanel vp = new VerticalPanel();
			HorizontalPanel hp = new HorizontalPanel();
			Button yesButton = new Button("Ja", new YesDeleteClickHandler(user, db));
			Button noButton = new Button("Nein", new NoDeleteClickHandler(user, db));
			Label deleteMessage = new HTML(					
					"<h1> Profil löschen </h1> <p> Möchten Sie Ihr Profil endgültig löschen? </p> <br>"
					);
			vp.add(deleteMessage);
			hp.add(yesButton);
			hp.add(noButton);
			vp.add(hp);
			
			db.center();
			db.show();
			
			db.add(vp);
			
		}
		
		class NoDeleteClickHandler implements ClickHandler {
			
			private DialogBox diabox;
			
			public NoDeleteClickHandler(DialogBox db) {
				this.diabox = db;
			}
			
			public void onClick(ClickEvent event) {
				diabox.hide();
				diabox.clear();
			}
			
		}
		
		
		class YesDeleteClickHandler implements ClickHandler {
			
			User user;
			private DialogBox diabox;
			
			public YesDeleteClickHandler(User u, DialogBox db) {
				
				this.user = u;
				this.diabox = db;
			}
			
			public void onClick(ClickEvent event) {
				
				ev.deleteUser(user.getId(), new DeleteUserCallBack(this.user));
				
				diabox.hide();
				diabox.clear();
					
			}
			
		}
		
		class DeleteUserCallBack implements AsyncCallback<Void>{
			
			private User user;
			
			public DeleteUserCallBack(User u) {
				
				this.user = u;
				
			}
			
			public void onFailure (Throwable caught) {
				
				if (caught instanceof NotLoggedInException) {
					
					Window.Location.reload();
					
				}
				
			}
			
			
		}
		
	}
	
	}

}



