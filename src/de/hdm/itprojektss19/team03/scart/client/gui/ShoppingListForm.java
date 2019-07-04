package de.hdm.itprojektss19.team03.scart.client.gui;

import java.util.Vector;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektss19.team03.scart.client.gui.ProfileForm.NoButtonClickHandler;
import de.hdm.itprojektss19.team03.scart.client.gui.ProfileForm.YesDeleteButtonClickHandler;
import de.hdm.itprojektss19.team03.scart.shared.DatabaseException;
import de.hdm.itprojektss19.team03.scart.shared.EditorService;
import de.hdm.itprojektss19.team03.scart.shared.EditorServiceAsync;
import de.hdm.itprojektss19.team03.scart.shared.bo.Article;
import de.hdm.itprojektss19.team03.scart.shared.bo.GroceryList;
import de.hdm.itprojektss19.team03.scart.shared.bo.Group;
import de.hdm.itprojektss19.team03.scart.shared.bo.Retailer;
import de.hdm.itprojektss19.team03.scart.shared.bo.User;

/**
 * 
 * @author Marco
 *
 */
public class ShoppingListForm extends HorizontalPanel {

	/**
	 * Konstruktor der ShoppingListForm-Seite
	 * 
	 * @param u (User-Objekt des aktuellen Users)
	 * @param gl (GroceryList-Objekt der aktuellen Einkaufsliste)
	 * @param g (Gruppen-Objekt der Gruppe in der sich die Einkaufsliste befindet)
	 */
	public ShoppingListForm(User u, GroceryList gl, Group g) {
		user = u;
		groceryList = gl;
		group = g;
	}

	/**
	 * Methode wird bei Seitenaufruf automatisch gestartet
	 * 
	 */
	public void onLoad() {
		this.clear();
		outerPanel.clear();
		retailersPanel.clear();
		
//Buttons, Label und Panels fuer die Aenderung der GL Namens und Loeschung
		glDeleteButton.setStyleName("button");
		glDeleteButton.addClickHandler(new DeleteGlClickHandler(groceryList));
		glChangeNameButton.setStyleName("button");
		glChangeNameButton.addClickHandler(new ChangeGlNameButtonClickHandler(groceryList, glChangeNameTextbox.getText()));
		glChangeNameLabel.setStyleName("text");
		glChangeNameTextbox.setText(groceryList.getGroceryListName());
		glChangeNameTextbox.setStyleName("textbox");
		
		//glChangeNameLabel.setVerticalAlignment(ALIGN_MIDDLE);
		//glChangeNameTextbox.setVerticalAlignment(ALIGN_MIDDLE);
		
		changeGlNamePanel.add(glChangeNameLabel); //Elemente fuer die Aenderung des GroceryList-Namen
		changeGlNamePanel.add(glChangeNameTextbox);
		changeGlNamePanel.add(glChangeNameButton);
		changeGlNamePanel.add(glDeleteButton);
		changeGlNamePanel.setStyleName("gl-change-panel");
		changeGlNamePanel.setVerticalAlignment(ALIGN_MIDDLE);
		outerPanel.add(changeGlNamePanel);

		addRetailerButton.setStyleName("button");
		addRetailerButton.addClickHandler(new addRetailerClickHandler());
		outerPanel.add(addRetailerButton);
		outerPanel.add(retailersPanel);
		this.add(outerPanel);
		editorService.getAllRetailerByGroupId(group.getId(), retailerCallback);
	}

	private EditorServiceAsync editorService = GWT.create(EditorService.class);

	private User user;
	private Group group;
	private GroceryList groceryList;

	private FlowPanel retailersPanel = new FlowPanel();
	private VerticalPanel outerPanel = new VerticalPanel();
	private Button addRetailerButton = new Button("Laden einfügen");

//Change GroceryList Name
	HorizontalPanel changeGlNamePanel = new HorizontalPanel(); //HorizontalPanel wo Label, Textbox und Button zum aendern des GroceryListnamen enthalten sind

    	//Elemente zum anendern des GroceryList Namen
		//TODO: Stylenames zuordnen fuer diese Elemente
		Label glChangeNameLabel = new Label("Einkaufsliste umbenennen:");
		TextBox glChangeNameTextbox = new TextBox();
		Button glChangeNameButton = new Button("Ändern");	
		Button glDeleteButton = new Button("Einkaufsliste löschen");
		
	
	
	/**
	 * Sucht alle Retailer der aktuellen Gruppe
	 */
	private void getData() {
		editorService.getAllRetailerByGroupId(group.getId(), retailerCallback);
	}

	/**
	 * Methode zum Erstellen der GUI-Seite
	 * 
	 * @param articles (Vector mit Artikel-Objekten)
	 * @param retailer Retailer
	 */
	public void createForms(Vector<Article> articles, Retailer retailer) {
		GWT.log("run once");
		DecoratorPanel decoPanel = new DecoratorPanel();
		HorizontalPanel headerPanel = new HorizontalPanel();
		VerticalPanel retailerPanel = new VerticalPanel();

		Label retailerHeader = new Label(retailer.getRetailerName());
		Button editRetailer = new Button(
				"<image src='/images/editButton.png' width='16px' height='16px' align='center'/>");

		Button deleteButton = new Button(
				"<image src='/images/trash-can-outline.png' width='16px' height='16px' align='center'/>");

		deleteButton.setStyleName("icon-button");
		deleteButton.addClickHandler(new DeleteRetailerClickHandler(retailer));
		editRetailer.setStyleName("icon-button");
		retailerHeader.addStyleName("h3");
		headerPanel.add(retailerHeader);
		headerPanel.add(editRetailer);
		headerPanel.add(deleteButton);
		headerPanel.setVerticalAlignment(ALIGN_MIDDLE);
		editRetailer.addClickHandler(new EditRetailerClickhandler(retailer, headerPanel));

		retailerPanel.addStyleName("retailer-panel");
		retailerPanel.add(headerPanel);
		retailerPanel.add(new GroceryListForm(user, group, articles, retailer, groceryList));

		decoPanel.addStyleName("retailers-panel");
		decoPanel.setWidget(retailerPanel);

		editorService.generateIdenticons(retailer.getUser().getEmail(), 25, 25,
				new GetPictureCallback(headerPanel, retailer.getUser()));

		
		
		retailersPanel.add(decoPanel);
	}

	/**
	 * ClickHandler um einen Retailer zu entfernen
	 */
	class DeleteRetailerClickHandler implements ClickHandler {
		Retailer retailer;

		public DeleteRetailerClickHandler(Retailer r) {
			retailer = r;
		}

		public void onClick(ClickEvent arg0) {
			DialogBox db = new DialogBox();
			Button yb = new Button("Ja", new YesButtonClickHandler(retailer, db));
			Button nb = new Button("Nein", new NoButtonClickHandler(db));
			HorizontalPanel hp = new HorizontalPanel();
			VerticalPanel vp = new VerticalPanel();
			vp.add(new HTML(
					"<p>Soll der Einkaufsladen wirklich gelöscht werden? Dadruch gehen alle Artikel darin verloren.<p>"));
			db.setAnimationEnabled(true);
			db.setGlassEnabled(true);
			db.center();
			db.setText("Laden löschen");
			hp.add(yb);
			hp.add(nb);
			vp.add(hp);
			db.add(vp);
			db.show();
		}
	}

	/**
	 * ClickHandler um das Loeschen eines Retailers zu bestaetigen
	 */
	class YesButtonClickHandler implements ClickHandler {
		Retailer retailer;
		DialogBox dialogBox;

		public YesButtonClickHandler(Retailer r, DialogBox db) {
			retailer = r;
			dialogBox = db;
		}

		public void onClick(ClickEvent arg0) {
			editorService.deleteRetailer(retailer, new DeleteRetailerCallback());
			dialogBox.hide();
			dialogBox.clear();
			dialogBox.removeFromParent();
		}
	}

	/**
	 * ClickHandler um das Loeschen eines Retailers abzubrechen
	 */
	class NoButtonClickHandler implements ClickHandler {
		DialogBox dialogBox;

		public NoButtonClickHandler(DialogBox db) {
			dialogBox = db;
		}

		public void onClick(ClickEvent arg0) {
			dialogBox.hide();
			dialogBox.clear();
			dialogBox.removeFromParent();
		}

	}

	/**
	 * ClickHander um einen Retailer zu bearbeiten
	 */
	class EditRetailerClickhandler implements ClickHandler {
		Retailer retailer;
		HorizontalPanel panel;

		public EditRetailerClickhandler(Retailer r, HorizontalPanel p) {
			retailer = r;
			panel = p;
		}

		public void onClick(ClickEvent arg0) {
			TextBox retailerNameTextbox = new TextBox();
			retailerNameTextbox.setWidth(panel.getWidget(0).getOffsetWidth() + "px");
			panel.clear();
			panel.setVerticalAlignment(ALIGN_MIDDLE);
			retailerNameTextbox.setText(retailer.getRetailerName());
			retailerNameTextbox.setStyleName("h3");
			Button check = new Button(
					"<image src='/images/check-bold.png' width='16px' height='16px' align='center'/>");
			check.addClickHandler(new CheckClickhandler(retailer, retailerNameTextbox, panel));
			check.setStyleName("icon-button");
			Button deleteButton = new Button(
					"<image src='/images/trash-can-outline.png' width='16px' height='16px' align='center'/>");

			deleteButton.setStyleName("icon-button");
			deleteButton.addClickHandler(new DeleteRetailerClickHandler(retailer));
			panel.add(retailerNameTextbox);
			panel.add(check);
			panel.add(deleteButton);
		}

	};

	class CheckClickhandler implements ClickHandler {
		Retailer retailer;
		HorizontalPanel panel;
		TextBox textbox;

		public CheckClickhandler(Retailer r, TextBox tb, HorizontalPanel p) {
			retailer = r;
			textbox = tb;
			panel = p;
		}

		public void onClick(ClickEvent arg0) {
			retailer.setRetailerName(textbox.getValue());
			editorService.saveRetailer(retailer, new UpdateRetailerCallback(panel));
		}

	}

	/**
	 * Callback-Methode um einen Retailer zu loeschen Bei Erfolg wird die Seite neu
	 * geladen
	 */
	class DeleteRetailerCallback implements AsyncCallback<Retailer> {

		public void onFailure(Throwable t) {
			GWT.log("Failed to get retailers: " + t);
		}

		public void onSuccess(Retailer r) {
			onLoad();
		}

	}

	/**
	 * Callback-Methode um einen Retailer zu loeschen. Bei Erfolg wird die Seite
	 * angepasst
	 */
	class UpdateRetailerCallback implements AsyncCallback<Retailer> {
		HorizontalPanel panel;

		public UpdateRetailerCallback(HorizontalPanel p) {
			panel = p;
		}

		public void onFailure(Throwable t) {
			GWT.log("Failed to update GroceryList: " + t);
		}

		public void onSuccess(Retailer r) {
			Label retailerHeader = new Label(r.getRetailerName());
			panel.clear();
			panel.setVerticalAlignment(ALIGN_MIDDLE);
			retailerHeader.setText(r.getRetailerName());
			retailerHeader.setStyleName("h3");
			Button editRetailer = new Button(
					"<image src='/images/editButton.png' width='16px' height='16px' align='center'/>");
			editRetailer.addClickHandler(new EditRetailerClickhandler(r, panel));
			editRetailer.setStyleName("icon-button");
			Button deleteButton = new Button(
					"<image src='/images/trash-can-outline.png' width='16px' height='16px' align='center'/>");

			deleteButton.setStyleName("icon-button");
			deleteButton.addClickHandler(new DeleteRetailerClickHandler(r));
			panel.add(retailerHeader);
			panel.add(editRetailer);
			panel.add(deleteButton);
		}
	};

	/**
	 * Callback-Methode um Identicons zu erstellen. Bei Erfolg wird das Bild
	 * erneuert.
	 */
	class GetPictureCallback implements AsyncCallback<String> {

		HorizontalPanel hp = new HorizontalPanel();
		User user = new User();

		public GetPictureCallback(HorizontalPanel p, User u) {
			hp = p;
			user = u;
		}

		public void onFailure(Throwable t) {
			GWT.log("Failed to load image: " + t);
		}

		public void onSuccess(String s) {
			Image image = new Image();
			HorizontalPanel p = new HorizontalPanel();
			image.setUrl("data:image/png;base64," + s);
			p.addStyleName("profile-img-small");
			p.add(image);
			p.setTitle(user.getUsername());
			hp.add(p);
		}

	}

	class ArticleCallback implements AsyncCallback<Vector<Article>> {
		Retailer retailer;

		public ArticleCallback(Retailer r) {
			retailer = r;
		}

		public void onFailure(Throwable t) {
			Window.alert("Failed to retrieve Articles: " + t);
		}

		public void onSuccess(Vector<Article> articles) {
			createForms(articles, retailer);
		}
	};
	/**
	 * Callback um die Retailer der Gruppe aus der DB zu finden. Bei Erfolg werden
	 * die Artikel der Einkaufsliste gesucht.
	 */
	AsyncCallback<Vector<Retailer>> retailerCallback = new AsyncCallback<Vector<Retailer>>() {

		public void onFailure(Throwable t) {
			GWT.log("Failed to get Retailers: " + t);
		}

		public void onSuccess(Vector<Retailer> r) {
			for (Retailer retailer : r) {
				editorService.findAllArticleByGroceryListIdAndRetailerId(groceryList.getId(), retailer.getId(),
						new ArticleCallback(retailer));
			}
		}
	};
	/**
	 * Callback-Methode um einen neuen Retailer in der DB anzulegen
	 */
	AsyncCallback<Retailer> newRetailerCallback = new AsyncCallback<Retailer>() {
		public void onFailure(Throwable t) {
			GWT.log("Failed to add Retailer: " + t);
		}

		public void onSuccess(Retailer r) {
			onLoad();
		}
	};

	/**
	 * ClickHandler um einen neuen Retailer anzulegen.
	 */
	class addRetailerClickHandler implements ClickHandler {
		public void onClick(ClickEvent arg0) {
			Retailer r = new Retailer();
			r.setGroup(group);
			r.setUser(user);
			r.setRetailerName("Neuer Laden");
			editorService.createRetailer(r, newRetailerCallback);
		}
	}
	
	/**
	 * ClickHandler um den Namen der Einkaufsliste zu aendern
	 */
	public class ChangeGlNameButtonClickHandler implements ClickHandler {
		public ChangeGlNameButtonClickHandler(GroceryList g, String glName) {
			groceryList = g;
		}
		@Override
		public void onClick(ClickEvent e) {
			if(glChangeNameTextbox.getText() != null && glChangeNameTextbox.getText() !="" && glChangeNameTextbox.getText().length() < 100) {
				final GroceryList failSafeGl = groceryList;

				groceryList.setGroceryListName(glChangeNameTextbox.getText());

				editorService.saveGroceryList(groceryList, new AsyncCallback<GroceryList>() {
				@Override
				public void onFailure(Throwable arg0) {

					groceryList.setGroceryListName(failSafeGl.getGroceryListName());
					Window.alert("Fehler: Einkaufsliste konnte nicht umbenannt werden");
				}
				@Override
				public void onSuccess(GroceryList arg0) {
					Window.alert("Einkaufsliste wurde in "+arg0.getGroceryListName()+" umbenannt");
					//ggf. noch temporaere Loesung
					Window.Location.replace("/Scart.html");
					
					//RootPanel.get("navigation").clear();
					//RootPanel.get("navigation").add(new GroupForm(user));
				}
		});
			} else {
				Window.alert("Fehler: Geben Sie einen passenden Namen für die Einkaufsliste ein");
			}
	}
}
	/**
	 * ClickHandler um eine Einkaufsliste zu loeschen.
	 * Der User muss auch Owner der Einkaufsliste sein, um diese loeschen zu koennen.
	 * @author vanduyho (DialogBox)
	 */
	public class DeleteGlClickHandler implements ClickHandler {
		public DeleteGlClickHandler(GroceryList g) {
			groceryList = g;
		}
		@Override
		public void onClick(ClickEvent e) {
			//if(groceryList.getOwnerId()==user.getId()) {   //If-Abfrage evtl. noetig damit nicht jeder loeschen kann
			DialogBox db = new DialogBox();
			VerticalPanel vp = new VerticalPanel();
			HorizontalPanel hp = new HorizontalPanel();
			Button yB = new Button("Ja", new YesDeleteButtonClickHandler(db));
			Button nB = new Button("Nein", new NoDeleteButtonClickHandler(db));
			Label l = new HTML("<h1> Einkaufsliste löschen </h1> <p> Möchten Sie diese Einkaufsliste endgültig löschen? </p> <br>");

			vp.add(l);
			hp.add(yB);
			hp.add(nB);
			vp.add(hp);

			db.setGlassEnabled(true);
			db.setAnimationEnabled(true);
			db.center();
			db.show();

			db.add(vp);
			/*
			} else {
				Window.alert("Fehler: Sie sind "+user.getId()+". Besitzer ist aber "+groceryList.getOwnerId()+". Sie sind nicht der Besitzer dieser Einkaufsliste");
			}
			*/
	}
}
	/**
	 * ClickHandler um das Loeschen der Einkaufsliste zu bestaetigen
	 */
	class YesDeleteButtonClickHandler implements ClickHandler {
		DialogBox dbox = new DialogBox();
		public YesDeleteButtonClickHandler(DialogBox db) {
			this.dbox = db;
		}
		
		public void onClick(ClickEvent event) {
			
			DeleteGroceryList(group, groceryList);
			
			dbox.hide();
			dbox.clear();
			dbox.removeFromParent();
			dbox.setAnimationEnabled(false);
			dbox.setGlassEnabled(false);
		}
	}

	/**
	 * ClickHandler um Einkaufsliste nicht zu loeschen.
	 */
	class NoDeleteButtonClickHandler implements ClickHandler {
		DialogBox dbox = new DialogBox();
		public NoDeleteButtonClickHandler(DialogBox db) {
			this.dbox = db;
		}
		public void onClick(ClickEvent event) {
			dbox.hide();
			dbox.clear();
			dbox.removeFromParent();
			dbox.setAnimationEnabled(false);
			dbox.setGlassEnabled(false);
		}
	}
	
	/**
	 * Methode um eine Einkaufsliste sicher aus der Datenbank zu loeschen
	 * @param g beschreibt ein Group Objekt
	 * @param gl beschreibt ein GroceryList Objekt
	 */
	public void DeleteGroceryList(Group g, GroceryList gl) {
		final Group tempGroup = g;
		final GroceryList tempgGroceryList = gl;
		try {
			if(tempGroup != null && tempGroup.getId()>0) {
				editorService.findAllGroceryListByGroupId(tempGroup.getId(), new AsyncCallback<Vector<GroceryList>>() {
					@Override
					public void onFailure(Throwable arg0) {
						Window.alert("Fehler: Einkaufslisten der Gruppe konnten nicht gefunden werden.");
					}
					@Override
					public void onSuccess(Vector<GroceryList> arg0) {
						if(arg0.size()>1) {
							editorService.deleteAllArticlesFromGroceryList(tempgGroceryList, new AsyncCallback<Void>() {
								@Override
								public void onFailure(Throwable arg0) {
									Window.alert("Fehler: Die Einkaufsliste konnte vor dem löschen nicht geleert werden.");
								}
								@Override
								public void onSuccess(Void arg0) {
									editorService.deleteGroceryList(tempgGroceryList, new AsyncCallback<GroceryList>() {
										@Override
										public void onFailure(Throwable arg0) {
											Window.alert("Fehler: Die Einkaufsliste konnte nicht gelöscht werden.");
										}
										@Override
										public void onSuccess(GroceryList arg0) {
											Window.alert("Die Einkaufsliste wurde gelöscht");
											Window.Location.replace("/Scart.html");
										}
									});
								}
							});
						} else {
							Window.alert("Die letzte Einkaufsliste einer Gruppe darf nicht gelöscht "
									+ "werden. Erstellen Sie eine andere Einkaufsliste, wenn Sie "
									+ "diese löschen wollen.");
						}
					}
				});
				} else {
					Window.alert("Das Group-Objekt ist null");
				}
		} catch(IllegalArgumentException e) { 
			e.printStackTrace();
			throw new IllegalArgumentException(e); 
		}
	}
	
}