package de.hdm.itprojektss19.team03.scart.client.gui;

import java.util.Vector;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.hdm.itprojektss19.team03.scart.shared.EditorService;
import de.hdm.itprojektss19.team03.scart.shared.EditorServiceAsync;
import de.hdm.itprojektss19.team03.scart.shared.bo.Article;
import de.hdm.itprojektss19.team03.scart.shared.bo.GroceryList;
import de.hdm.itprojektss19.team03.scart.shared.bo.GroceryListArticle;
import de.hdm.itprojektss19.team03.scart.shared.bo.Group;
import de.hdm.itprojektss19.team03.scart.shared.bo.Retailer;
import de.hdm.itprojektss19.team03.scart.shared.bo.User;

/**
 * 
 * @author Marco
 *
 */
public class ShoppingListForm extends HorizontalPanel {

	private EditorServiceAsync editorService = GWT.create(EditorService.class);

	private User user;
	private Group group;
	private GroceryList groceryList;
	int GWTBugFixing;

	private FlowPanel retailersPanel = new FlowPanel();
	private VerticalPanel outerPanel = new VerticalPanel();
	private Button addRetailerButton = new Button("Laden einfügen");

	// Change GroceryList Name
	HorizontalPanel changeGlNamePanel = new HorizontalPanel();

	// Elemente zum anendern des GroceryList Namen
	Label glNameLabel = new Label();
	TextBox glChangeNameTextbox = new TextBox();
	Button editGroceryListButton = new Button(
			"<image src='/images/editButton.png' width='16px' height='16px' align='center'/>");
	Button deleteGroceryListButton = new Button(
			"<image src='/images/trash-can-outline.png' width='16px' height='16px' align='center'/>");

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
		GWTBugFixing = 0;
	}

	/**
	 * Methode wird bei Seitenaufruf automatisch gestartet
	 * 
	 */
	public void onLoad() {
		if (GWTBugFixing > 0) {
			return;
		}
		GWTBugFixing++;
		this.clear();
		outerPanel.clear();
		retailersPanel.clear();
		changeGlNamePanel.clear();

		// Buttons, Label und Panels fuer die Aenderung der GL Namens und Loeschung
		deleteGroceryListButton.setStyleName("icon-button");
		deleteGroceryListButton.addClickHandler(new DeleteGlClickHandler(groceryList));
		editGroceryListButton.setStyleName("icon-button");
		editGroceryListButton.addClickHandler(new ChangeGlNameButtonClickHandler(groceryList, changeGlNamePanel));
		glNameLabel.setStyleName("h1");
		glNameLabel.setText(groceryList.getGroceryListName());

		changeGlNamePanel.add(glNameLabel); // Elemente fuer die Aenderung des GroceryList-Namen
		changeGlNamePanel.add(editGroceryListButton);
		changeGlNamePanel.add(deleteGroceryListButton);
		changeGlNamePanel.setVerticalAlignment(ALIGN_MIDDLE);
		outerPanel.add(changeGlNamePanel);

		addRetailerButton.setStyleName("button");
		addRetailerButton.addClickHandler(new addRetailerClickHandler());
		outerPanel.add(addRetailerButton);
		outerPanel.add(retailersPanel);
		this.add(outerPanel);
		editorService.getAllDistinctRetailerByGroceryList(groceryList, retailerCallback);
	}

	/**
	 * Methode zum Erstellen der GUI-Seite
	 * 
	 * @param articles (Vector mit Artikel-Objekten)
	 * @param retailer Retailer
	 */
	public void createForms(Vector<Article> articles, Retailer retailer) {
		Button editRetailer = new Button(
				"<image src='/images/editButton.png' width='16px' height='16px' align='center'/>");
		Button deleteButton = new Button(
				"<image src='/images/trash-can-outline.png' width='16px' height='16px' align='center'/>");
		DecoratorPanel decoPanel = new DecoratorPanel();
		HorizontalPanel headerPanel = new HorizontalPanel();
		VerticalPanel retailerPanel = new VerticalPanel();

		Label retailerHeader = new Label(retailer.getRetailerName());

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
		retailerPanel.add(new ArticlesForm(user, group, articles, retailer, groceryList));

		decoPanel.addStyleName("retailers-panel");
		decoPanel.setWidget(retailerPanel);
		editorService.generateIdenticons(retailer.getUser(), 25, 25, new GetPictureCallback(headerPanel, retailer));

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

	/*
	 * Clickhandler, um einen Retailer zu speichern
	 */
	class CheckClickhandler implements ClickHandler {
		Retailer retailer;
		HorizontalPanel panel;
		TextBox textbox;

		public CheckClickhandler(Retailer r, TextBox tb, HorizontalPanel p) {
			retailer = r;
			textbox = tb;
			panel = p;
		}

		public void onClick(ClickEvent c) {
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
			GWTBugFixing = 0;
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
		Retailer retailer;

		public GetPictureCallback(HorizontalPanel p, Retailer r) {
			hp = p;
			retailer = r;
		}

		public GetPictureCallback(HorizontalPanel p) {
			hp = p;
		}

		public void onFailure(Throwable t) {
			GWT.log("Failed to load image: " + t);
		}

		public void onSuccess(String s) {
			Image image = new Image();
			FocusPanel f = new FocusPanel();
			HorizontalPanel p = new HorizontalPanel();
			f.setWidget(p);
			image.setUrl("data:image/png;base64," + s);
			p.addStyleName("profile-img-small");
			p.add(image);
			if (retailer != null) {
				p.setTitle(retailer.getUser().getUsername());
				f.addClickHandler(new ChangeRetailerUserMenuClickhandler(retailer));
			}
			hp.add(f);
		}

	}

	class ChangeRetailerUserMenuClickhandler implements ClickHandler {
		Retailer retailer;

		public ChangeRetailerUserMenuClickhandler(Retailer r) {
			retailer = r;
		}

		public void onClick(ClickEvent event) {
			Widget source = (Widget) event.getSource();
			int left = source.getAbsoluteLeft() + 10;
			int top = source.getAbsoluteTop() + 10;
			editorService.getAllUserByGroupId(group.getId(), new GetAllUsersByGroupCallback(left, top, retailer));

		}

	}

	class GetAllUsersByGroupCallback implements AsyncCallback<Vector<User>> {
		Retailer retailer;
		int left;
		int top;

		public GetAllUsersByGroupCallback(int l, int t, Retailer r) {
			left = l;
			top = t;
			retailer = r;
		}

		public void onFailure(Throwable t) {
			GWT.log("Failed to get pcitures: " + t);
		}

		public void onSuccess(Vector<User> userVec) {

			DecoratedPopupPanel pop = new DecoratedPopupPanel(true);
			VerticalPanel vp = new VerticalPanel();
			vp.setSpacing(2);
			pop.setPopupPosition(left, top);
			for (User u : userVec) {
				Retailer reta = new Retailer(retailer.getRetailerName(), retailer.getId(), u.getId(), group.getId(),
						groceryList.getId());

				FocusPanel f = new FocusPanel();
				HorizontalPanel hp = new HorizontalPanel();
				HorizontalPanel hpPic = new HorizontalPanel();
				Label userName = new Label(u.getUsername());
				userName.setStyleName("text");
				hp.addStyleName("pointer");
				hp.setSpacing(2);
				hp.setVerticalAlignment(ALIGN_MIDDLE);
				hp.add(hpPic);
				hp.add(userName);
				f.add(hp);
				f.addClickHandler(new ChangeRetailerUserClickhandler(reta, pop));
				vp.add(f);
				editorService.generateIdenticons(u, 25, 25, new GetPictureCallback(hpPic));
			}
			pop.setWidget(vp);
			pop.show();

		}

	}

	class ChangeRetailerUserClickhandler implements ClickHandler {
		Retailer ret;
		DecoratedPopupPanel pop;

		public ChangeRetailerUserClickhandler(Retailer re, DecoratedPopupPanel p) {
			ret = re;
			pop = p;
		}

		public void onClick(ClickEvent arg0) {
			editorService.saveRetailer(ret, new ChangeRetailerUserCallback(pop));
		}
	}

	class ChangeRetailerUserCallback implements AsyncCallback<Retailer> {
		DecoratedPopupPanel pop;

		public ChangeRetailerUserCallback(DecoratedPopupPanel p) {
			pop = p;

		}

		public void onFailure(Throwable t) {
			GWT.log("Failed to update Retailer: " + t);

		}

		public void onSuccess(Retailer arg0) {
			pop.hide();
			GWTBugFixing = 0;
			onLoad();
		}

	}

	/**
	 * Callback um Artikel Vector zu erhalten
	 */
	class ArticleCallback implements AsyncCallback<Vector<Article>> {
		Retailer retailer;

		public ArticleCallback(Retailer r) {
			retailer = r;
		}

		public void onFailure(Throwable t) {
			Window.alert("Failed to retrieve Articles: " + t);
		}

		public void onSuccess(Vector<Article> articles) {
			editorService.getRetailerById(retailer.getId(), new GetFullRetaierInfoCallback(articles));
		}
	};

	/**
	 * Callback um Retailer information zu bekommen
	 *
	 */
	class GetFullRetaierInfoCallback implements AsyncCallback<Retailer> {
		Vector<Article> articles;

		public GetFullRetaierInfoCallback(Vector<Article> a) {
			articles = a;
		}

		public void onFailure(Throwable arg0) {

		}

		public void onSuccess(Retailer r) {
			createForms(articles, r);

		}
	};

	/**
	 * Callback, um alle Favoriten einer Gruppe zu bekommen
	 *
	 */
	class FavArticleCallback implements AsyncCallback<Vector<Article>> {
		Retailer retailer;

		public FavArticleCallback(Retailer r) {
			retailer = r;
		}

		public void onFailure(Throwable t) {
			GWT.log("Failed to getFavarticle: " + t);
		}

		public void onSuccess(Vector<Article> articles) {
			int callbackCount = 0;
			for (Article a : articles) {
				callbackCount++;
				a.setRetailer(retailer);
				a.setOwnerId(user.getId());
				a.setGroupId(group.getId());
				editorService.createArticle(a, new CreateArticleCallback(articles.size(), callbackCount));
			}
			GWTBugFixing = 0;
			onLoad();

		}
	}

	/**
	 * Callback, um neue Artikel anzulegen
	 *
	 */
	class CreateArticleCallback implements AsyncCallback<Article> {
		int favCount;
		int callbackCount;

		public CreateArticleCallback(int i, int j) {
			favCount = i;
			callbackCount = j;
		}

		public void onFailure(Throwable t) {
			GWT.log("Failed to create Article: " + t);
		}

		public void onSuccess(Article a) {
			editorService.addArticleToGroceryList(groceryList, a, new AddArticleToGroceryList(favCount, callbackCount));
		}

	}

	/**
	 * Callback, im einen Artikel einer Einkaufsliste zuzufuegen
	 *
	 */
	class AddArticleToGroceryList implements AsyncCallback<GroceryListArticle> {
		int favCount;
		int callbackCount;

		public AddArticleToGroceryList(int fc, int cC) {
			favCount = fc;
			callbackCount = cC;
		}

		public void onFailure(Throwable t) {
			GWT.log("Failed to add Article to Retailer: " + t);
		}

		public void onSuccess(GroceryListArticle gla) {
			if (callbackCount == favCount) {
				onLoad();
			}
		}

	}

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
			editorService.findAllFavArticleByGroup(group, new FavArticleCallback(r));

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
			r.setGroceryListId(groceryList.getId());
			editorService.createRetailer(r, newRetailerCallback);
		}
	}

	/**
	 * ClickHandler um den Namen der Einkaufsliste zu aendern
	 */
	class ChangeGlNameButtonClickHandler implements ClickHandler {
		GroceryList gl;
		Panel panel;

		public ChangeGlNameButtonClickHandler(GroceryList g, Panel p) {
			gl = g;
			panel = p;
		}

		public void onClick(ClickEvent e) {
			Button saveButton = new Button(
					"<image src='/images/check-bold.png' width='16px' height='16px' align='center'/>");
			saveButton.setStyleName("icon-button");
			saveButton.addClickHandler(new SaveGLClickhandler(gl, panel));
			glChangeNameTextbox.setWidth(glNameLabel.getOffsetWidth() + "px");
			panel.clear();
			glChangeNameTextbox.setStyleName("h1");
			glChangeNameTextbox.addStyleName("tesxtbox");
			glChangeNameTextbox.setText(gl.getGroceryListName());
			panel.add(glChangeNameTextbox);
			panel.add(saveButton);
		}
	}

	/**
	 * Clickhanbler, um den geänderten Name einer GroceryList zu bestätigen
	 *
	 */
	class SaveGLClickhandler implements ClickHandler {
		GroceryList gl;
		Panel panel;

		public SaveGLClickhandler(GroceryList g, Panel p) {
			gl = g;
			panel = p;
		}

		public void onClick(ClickEvent arg0) {
			if (glChangeNameTextbox.getText() != null && glChangeNameTextbox.getText() != ""
					&& glChangeNameTextbox.getText().length() < 30) {
				final GroceryList failSafeGl = gl;

				gl.setGroceryListName(glChangeNameTextbox.getText());

				editorService.saveGroceryList(gl, new AsyncCallback<GroceryList>() {

					public void onFailure(Throwable arg0) {
						groceryList.setGroceryListName(failSafeGl.getGroceryListName());
						Window.alert("Fehler: Einkaufsliste konnte nicht umbenannt werden");
					}

					public void onSuccess(GroceryList arg0) {
						GWTBugFixing = 0;
						onLoad();
						GroupForm groupForm = new GroupForm(user);
						groupForm.addStyleName("navigation");
						groupForm.setHeight("100%");
						RootPanel.get("navigation").clear();
						RootPanel.get("navigation").add(groupForm);

					}
				});
			} else {
				Window.alert("Fehler: Geben Sie einen passenden Namen für die Einkaufsliste ein");
			}
		}

	}

	/**
	 * ClickHandler um eine Einkaufsliste zu loeschen. Der User muss auch Owner der
	 * Einkaufsliste sein, um diese loeschen zu koennen.
	 * 
	 * @author vanduyho (DialogBox)
	 */
	class DeleteGlClickHandler implements ClickHandler {
		public DeleteGlClickHandler(GroceryList g) {
			groceryList = g;
		}

		public void onClick(ClickEvent e) {
			// if(groceryList.getOwnerId()==user.getId()) { //If-Abfrage evtl. noetig damit
			// nicht jeder loeschen kann
			DialogBox db = new DialogBox();
			VerticalPanel vp = new VerticalPanel();
			HorizontalPanel hp = new HorizontalPanel();
			Button yB = new Button("Ja", new YesDeleteButtonClickHandler(db));
			Button nB = new Button("Nein", new NoDeleteButtonClickHandler(db));
			Label l = new HTML(
					"<h1> Einkaufsliste löschen </h1> <p> Möchten Sie diese Einkaufsliste endgültig löschen? </p> <br>");

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
			 * } else { Window.alert("Fehler: Sie sind "+user.getId()+". Besitzer ist aber "
			 * +groceryList.getOwnerId()
			 * +". Sie sind nicht der Besitzer dieser Einkaufsliste"); }
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
	 * 
	 * @param g beschreibt ein Group Objekt
	 * @param gl beschreibt ein GroceryList Objekt
	 */
	public void DeleteGroceryList(Group g, GroceryList gl) {
		final Group tempGroup = g;
		final GroceryList tempgGroceryList = gl;
		try {
			if (tempGroup != null && tempGroup.getId() > 0) {
				editorService.findAllGroceryListByGroupId(tempGroup.getId(), new AsyncCallback<Vector<GroceryList>>() {

					public void onFailure(Throwable arg0) {
						Window.alert("Fehler: Einkaufslisten der Gruppe konnten nicht gefunden werden.");
					}

					public void onSuccess(Vector<GroceryList> arg0) {
						if (arg0.size() > 1) {
							editorService.deleteAllArticlesFromGroceryList(tempgGroceryList, new AsyncCallback<Void>() {

								public void onFailure(Throwable arg0) {
									Window.alert(
											"Fehler: Die Einkaufsliste konnte vor dem löschen nicht geleert werden.");
								}

								public void onSuccess(Void arg0) {
									editorService.deleteGroceryList(tempgGroceryList, new AsyncCallback<GroceryList>() {

										public void onFailure(Throwable arg0) {
											Window.alert("Fehler: Die Einkaufsliste konnte nicht gelöscht werden.");
										}

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
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
	}

}