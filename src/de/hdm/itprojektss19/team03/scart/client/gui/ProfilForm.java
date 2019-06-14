package de.hdm.itprojektss19.team03.scart.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
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
import de.hdm.itprojektss19.team03.scart.client.gui.ProfilForm.EditButtonClickHandler.SaveButtonClickHandler.DeleteButtonClickHandler.FindUserCallBack;
import de.hdm.itprojektss19.team03.scart.shared.EditorServiceAsync;
import de.hdm.itprojektss19.team03.scart.shared.bo.User;
import src.de.hdm.itprojekt.client.gui.Override;
import src.de.hdm.itprojekt.client.gui.ProfilForm;
import src.de.hdm.itprojekt.client.gui.String;
import src.de.hdm.itprojekt.client.gui.Timer;
import src.de.hdm.itprojekt.client.gui.Vector;
import src.de.hdm.itprojekt.client.gui.ProfilForm.UpdateUserCallBack;
import src.de.hdm.itprojekt.client.gui.ProfilForm.VerifyFieldCallback;


/**
 * 
 * @author vanduyho
 *
 */

public class ProfilForm {
	
	User user = null;
	
	Label newProfil = null;
	Label userName = null;
	Label emailAdress = null;
	
	Button editButton = null;
	Button deleteButton = null;
	
	VerticalPanel contenPanel = null;
	HorizontalPanel newProfilePanel = null;
	HorizontalPanel userNamePanel = null;
	HorizontalPanel emailAdressPanel = null;
	HorizontalPanel buttonPanel = null;
	
	private Label info;
	
	private EditorServiceAsync ev = ClientsideSettings.getEditorVerwaltung();
	
	private FindUserCallBack finduserCallback;
	//private VerifyFieldCallBack verifyFieldCallback; 
	
	/**
	 * 
	 * Default-Konstruktor
	 * 
	 */
	
	public ProfilForm() {
		
		
	}
	 
	/**
	 * 
	 * Konstruktor
	 * 
	 */
	
	public ProfilForm (User u) {
		
		this.user = u;
		
		//finduserCallback = new FindUserCallBack();
		
	}
	
	/*
	 * 
	 * onLoad-Methode
	 * 
	 */

	public void onLoad() {
		
		onLoad();
		buildProfil();
		info = new Label("Bitte füllen Sie jedes Feld aus!");
		
		info.addStyleName("infoProfilLabel");
		info.addStyleName("Profil");
		
		ev.getUserById(user.getId(), finduserCallback);
		
		Timer refreshProfil = new Timer() {

			public void run() {

				ev.findUserByID(user.getId(), finduserCallback);

			}
		};

		refreshProfil.scheduleRepeating(5000);

	}
			
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
		
		//profilForm.remove(editButton);
		
		//Button deleteButton = new Button("Profil löschen", new DeleteButtonClickHandler(user));
		//profilForm.add(deleteButton);
		
		//Button saveButton = new Button("Änderungen speichern", new SaveButtonClickHandler(userName, emailAdress, profilForm));
		
	}
	
	
	class SaveButtonClickHandler implements KeyPressHandler {
		
		private TextBox userName = null;
		private TextBox emailAdress = null;
		private ProfilForm profilForm = null;
		
		public SaveButtonClickHandler(TextBox name, TextBox email, ProfilForm pf) {
			
			this.userName = name;
			this.emailAdress = email;
			this.profilForm = pf;
			
		}
		
		public void onKeyPress(KeyPressEvent event) {
			if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ENTER)
				if (this.emailAdress.getText().length()>20) {
					Window.alert("Ihre E-Mail darf nicht länger als 20 Zeichen sein!");
					return;
				}
				else { //ev.getUserByGMail(this.emailAdress, callback);
			
		}
		
		
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
			Button noButton = new Button("Nein", new NoDeleteClickHandler(db));
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
				
				ev.deleteUser(this.user, null);
				
				diabox.hide();
				diabox.clear();

			}
			
		}
		
		class DeleteUserCallBack implements AsyncCallback<Void>{
			
			private User user;
			
			public DeleteUserCallBack(User u) {
				
				this.user = u;
				
			}
			
			public void onFailure(Throwable caught) {
				
				//if (caught instanceof NotLoggedInException) {
					
					//Window.Location.reload();
					
				//}
				
			}
			
			public void onSuccess(User result) {
				
				if (result != null) {
					user = result;
					buildProfil();
					
				}
				
			}
			
		}
	
		private void buildProfil() {
			
			HorizontalPanel newProfilPanel = new HorizontalPanel();
			Label newProfil = new Label("Dein Profil");
			newProfilPanel.add(newProfil);
			
			HorizontalPanel userNamePanel = new HorizontalPanel();
			Label userName = new Label("Name: ");
			userNamePanel.add(userName);
			
			HorizontalPanel emailAdressPanel = new HorizontalPanel();
			Label emailAdress = new Label("E-Mail");
			emailAdressPanel.add(emailAdress);
			
			editButton = new Button("Profil bearbeiten", new EditButtonClickHandler(profilForm));
			
			VerticalPanel contentPanel = new VerticalPanel();
			contentPanel.add(userName);
			contentPanel.add(emailAdress);
			contentPanel.add(editButton);
			
		}
		
		class FindUserCallBack implements AsyncCallback<User> {
			
			public void onFailure(Throwable caught) {
				
			}
			
			public void onSuccess(User result) {
				
				if (user.getUsername() != result.getUsername() || user.getEmail() != result.getEmail()) {
					
					user = result;
					buildProfil();
					
				}
				
			}
			
		class VerifyFieldCallback implements AsyncCallback<String[]> {
			
			ProfilForm profilForm;
			TextBox userName;
			TextBox emailAdress;
			
			public VerifyFieldCallback(ProfilForm pf, TextBox t1, TextBox t2) {
				
				this.profilForm = pf;
				this.userName = t1;
				this.emailAdress = t2;
				
				
			}

			public void onFailure(Throwable cuaght) {
				
				
				
			}
			
			public void onSuccess(String[] result) {
				
				if (result != null) {
					
					user.setUsername(result[0]);
					user.setEmail(result[1]);
					
					ev.updateUser(user, new UpdateUserCallBack());
					
				} else {
					
					//profilForm.add(info);

					if (userName.getText().isEmpty()) {
						userName.setFocus(true);
					}
					if (emailAdress.getText().isEmpty()) {
						emailAdress.setFocus(true);
					}

				}
				
				
		class FindAllUserCallback implements AsyncCallback<Vector<String>> {
					
			ProfilForm profilForm;
			TextBox userName;
			TextBox emailAdress;
					
			public FindAllUserCallback(ProfilForm pf, TextBox t1, TextBox t2) {
			
				this.profilForm = pf;
				this.userName = t1;
				this.emailAdress = t2;
				
			}
					
			public void onFailure(Throwable caught) {
				
				Window.alert(caught.getMessage());
				
			}
			
			public void onSuccess(Vector<String> result) {
				
				for (int i = 0; i < result.size(); i++) {
					if (this.userName.getText() == result.get(i)) {
						Window.alert("Die E-Mail ist bereits vergeben!");
						return;
					}
				}
				
				String newName = userName.getText();
				String newEmail = emailAdress.getText();
				
				//ev.verifyField(new String[] { newName, newEmail},
						//new VerifyFieldCallback(this.profilForm, this.userName, this.emailAdress));

				
			}
				
				
			}
			
			
				}
				
				
			}
			
			
			
			
		}
			
			
		}
		
		
	}
	
	}

}




