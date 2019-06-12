package de.hdm.itprojektss19.team03.scart.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektss19.team03.scart.client.ClientsideSettings;
import de.hdm.itprojektss19.team03.scart.shared.EditorServiceAsync;
import de.hdm.itprojektss19.team03.scart.shared.bo.Article;
import de.hdm.itprojektss19.team03.scart.shared.bo.GroceryList;
import de.hdm.itprojektss19.team03.scart.shared.bo.Retailer;

public class GroceryListForm extends VerticalPanel {

	private EditorServiceAsync ev = ClientsideSettings.getEditorVerwaltung();

	// braucht man hier ein VerticalPanel, die Artikel werden sowiso in hp
	// angezeigt?
	VerticalPanel vt = new VerticalPanel();
	HorizontalPanel hpTitle = new HorizontalPanel();
	HorizontalPanel hpButtons = new HorizontalPanel();

	Button addBtn = new Button("<image src='/images/plusButton.png' width='16px' height='16px' align='center'/>");
	Button editBtn = new Button();
	Button deleteBtn = new Button();

	Boolean addBtnBoolean = false;
	Boolean editBtnBoolean = false;
	Boolean deleteBtnBoolean = false;

	FlexTable aTable = new FlexTable();
	Article a = new Article();
	Retailer r = new Retailer();

	CheckBox aCbox = new CheckBox();
	// Name der GroceryList wird ausgegeben
	Label titelLabel = new Label();

	ScrollPanel sc = new ScrollPanel();

	public void onLoad() {
		super.onLoad();

		RootPanel.get("contentHeader").clear();
		RootPanel.get("content").clear();
		RootPanel.get("footer").clear();

		sc.setSize("200px", "550px");
		sc.setVerticalScrollPosition(10);

		// Titel Label wird in Horitontales Panel eingefuegt
		hpTitle.add(titelLabel);

		// CellTable wird in das Scroll Panel hinzugefuegt
		sc.add(aTable);

		vt.add(hpTitle);
		vt.add(sc);

		aTable.setText(0, 0, "Artikel");
		aTable.setText(0, 1, "Menge");
		aTable.setText(0, 2, "Mengeneinheit");
		aTable.setText(0, 3, "Laden");

		// while Schleife das alle Artikel mit Name Quantity Unit und RetailerName
		// aufgelistet werden im Panel.
		for (int aNum = 0; aNum < a.getId(); aNum++) {
			aTable.setText(a.getId(), 0, a.getName());
			aTable.setText(a.getId(), 1, Integer.toString(a.getQuantity()));
			aTable.setText(a.getId(), 2, a.getUnit());
			aTable.setText(a.getId(), 3, r.getRetailerName());
			aTable.add(aCbox);
			aCbox.addClickHandler(new CheckTheBox());
			aCbox.setEnabled(true);
			aCbox.setTitle("aCbox" + a.getId());
		}
		vt.add(aTable);

		// Buttons werden dem untersten horizontal Panel hinzugefuegt
		hpButtons.add(addBtn);
		hpButtons.add(editBtn);
		hpButtons.add(deleteBtn);

		editBtn.addClickHandler(new EditClickHandler());
		editBtn.setEnabled(true);

		deleteBtn.addClickHandler(new DeleteClickHandler());
		deleteBtn.setEnabled(true);

		addBtn.addClickHandler(new AddClickHandler());
		addBtn.setEnabled(true);
		/**
		 * newArticleBtn.setPixelSize(100, 60); newArticleBtn.setStyleName("button1");
		 * newArticleBtn.setTitle("add Article");
		 */
		vt.add(hpButtons);

		RootPanel.get("content").add(vt);

	}

	private class CheckTheBox implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			if (aCbox.getValue() == true) {
				if (addBtnBoolean == false && editBtnBoolean == false && deleteBtnBoolean == false) {
					aTable.getRowCount();
					String first = aTable.getText(a.getId(), 0);
					String second = aTable.getText(a.getId(), 1);
					String third = aTable.getText(a.getId(), 2);
					String fourth = aTable.getText(a.getId(), 3);
					aTable.removeRow(a.getId());
					aTable.setText(aTable.getRowCount() + 1, 0, first);
					aTable.setText(aTable.getRowCount(), 1, second);
					aTable.setText(aTable.getRowCount(), 2, third);
					aTable.setText(aTable.getRowCount(), 3, fourth);
				} else if (addBtnBoolean == true) {

				} else if (editBtnBoolean == true) {

				} else if (deleteBtnBoolean == true) {

				} else {
					Window.alert("Ein Fehler ist aufgetreten, bitte versuchen sie es erneut.");
				}

			}
		}

	}

	private class EditClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent e) {
			addBtnBoolean = false;
			editBtnBoolean = true;
			deleteBtnBoolean = false;
		}
	}

	private class DeleteClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent e) {
			addBtnBoolean = false;
			editBtnBoolean = false;
			deleteBtnBoolean = true;
		}
	}

	// Oeffnet ArticleForm Panel zum Hinzufuegen eines Artikels
	private class AddClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent e) {
			addBtnBoolean = true;
			editBtnBoolean = false;
			deleteBtnBoolean = false;

		}
	}

	public GroceryListForm(de.hdm.itprojektss19.team03.scart.client.gui.GroceryListForm groceryList) {
		// TODO Auto-generated constructor stub
	}

	public static void setSelectedGroceryListForm(GroceryListForm selectedGroceryList) {
		// TODO Auto-generated method stub

	}

}
