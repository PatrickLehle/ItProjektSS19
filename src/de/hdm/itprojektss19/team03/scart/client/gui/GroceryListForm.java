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
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektss19.team03.scart.client.ClientsideSettings;
import de.hdm.itprojektss19.team03.scart.shared.EditorServiceAsync;
import de.hdm.itprojektss19.team03.scart.shared.bo.Article;
import de.hdm.itprojektss19.team03.scart.shared.bo.GroceryList;
import de.hdm.itprojektss19.team03.scart.shared.bo.Retailer;
import de.hdm.itprojektss19.team03.scart.shared.bo.User;

public class GroceryListForm extends VerticalPanel {

	private EditorServiceAsync ev = ClientsideSettings.getEditorVerwaltung();

	private User user = null;

	VerticalPanel vt = new VerticalPanel();
	HorizontalPanel hpTitle = new HorizontalPanel();
	HorizontalPanel hpButtons = new HorizontalPanel();

	Button addBtn = new Button("<image src='/images/plusButton.png' width='16px' height='16px' align='right'/>");
	Button editBtn = new Button("<image src='/images/editButton.png' width='16px' height='16px' align='center'/>");
	Button deleteBtn = new Button("<image src='/images/minusButton.png' width='16px' height='16px' align='left'/>");
	Button checkBtn = new Button();

	Boolean addBtnBoolean = false;
	Boolean editBtnBoolean = false;
	Boolean deleteBtnBoolean = false;
	Boolean checkBtnBoolean = false;

	TextBox editTb1 = new TextBox();
	TextBox editTb2 = new TextBox();
	TextBox editTb3 = new TextBox();
	TextBox editTb4 = new TextBox();

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
		hpButtons.add(checkBtn);

		addBtn.addClickHandler(new AddClickHandler());
		addBtn.setEnabled(true);

		editBtn.addClickHandler(new EditClickHandler());
		editBtn.setEnabled(true);

		deleteBtn.addClickHandler(new DeleteClickHandler());
		deleteBtn.setEnabled(true);

		checkBtn.addClickHandler(new CheckClickHandler());
		checkBtn.setEnabled(true);

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
				if (checkBtnBoolean == true) {
					for (int i = 0; i > aTable.getRowCount(); i++) {
					String first = aTable.getText(a.getId(), 0);
					String second = aTable.getText(a.getId(), 1);
					String third = aTable.getText(a.getId(), 2);
					String fourth = aTable.getText(a.getId(), 3);
					aTable.removeRow(i);
					aTable.setText(aTable.getRowCount() + 1, 0, first);
					aTable.setText(aTable.getRowCount(), 1, second);
					aTable.setText(aTable.getRowCount(), 2, third);
					aTable.setText(aTable.getRowCount(), 3, fourth);
					}
				} else if (deleteBtnBoolean == true) {
					aTable.removeRow(a.getId());
				} else if (editBtnBoolean == true) {
					String first = aTable.getText(a.getId(), 0);
					String second = aTable.getText(a.getId(), 1);
					String third = aTable.getText(a.getId(), 2);
					String fourth = aTable.getText(a.getId(), 3);
					aTable.clearCell(a.getId(), 0);
					aTable.clearCell(a.getId(), 1);
					aTable.clearCell(a.getId(), 2);
					aTable.clearCell(a.getId(), 3);
					editTb1.setText(first);
					editTb2.setText(second);
					editTb3.setText(third);
					editTb4.setText(fourth);
					aTable.setWidget(a.getId(), 0, editTb1);
					aTable.setWidget(a.getId(), 0, editTb2);
					aTable.setWidget(a.getId(), 0, editTb3);
					aTable.setWidget(a.getId(), 0, editTb4);
				} else {
					Window.alert("Ein Fehler ist aufgetreten, bitte versuchen sie es erneut.");
				}
			} else {
				Window.alert("Ein Fehler ist aufgetreten, bitte versuchen sie es erneut.");
			}
		}

	}
	
	private class CheckClickHandler implements ClickHandler {
		
		@Override
		public void onClick(ClickEvent e) {
			if (checkBtnBoolean == false) {
				checkBtnBoolean = true;
				editBtnBoolean = false;
				deleteBtnBoolean = false;
			}else if (checkBtnBoolean == true) {
				checkBtnBoolean = false;
			}else {
				Window.alert("Ein Fehler ist aufgetreten, bitte versuchen sie es erneut.");
			}
		}
	}

	private class EditClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent e) {
			if (editBtnBoolean == false) {
				checkBtnBoolean = false;
				editBtnBoolean = true;
				deleteBtnBoolean = false;
			} else if (editBtnBoolean == true) {
				String first = editTb1.getText();
				String second = editTb2.getText();
				String third = editTb3.getText();
				String fourth = editTb4.getText();
				aTable.clearCell(a.getId(), 0);
				aTable.clearCell(a.getId(), 1);
				aTable.clearCell(a.getId(), 2);
				aTable.clearCell(a.getId(), 3);
				aTable.setText(a.getId(), 0, first);
				aTable.setText(a.getId(), 1, second);
				aTable.setText(a.getId(), 2, third);
				aTable.setText(a.getId(), 3, fourth);
				editBtnBoolean = false;
			} else {
				Window.alert("Ein Fehler ist aufgetreten, bitte versuchen sie es erneut.");
			}
		}
	}

	private class DeleteClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent e) {
			if (deleteBtnBoolean == false) {
				checkBtnBoolean = false;
				editBtnBoolean = false;
				deleteBtnBoolean = true;
			} else if (deleteBtnBoolean == true) {
				deleteBtnBoolean = false;
			} else {
				Window.alert("Ein Fehler ist aufgetreten, bitte versuchen sie es erneut.");
			}
		}
	}

	// Oeffnet ArticleForm Panel zum Hinzufuegen eines Artikels
	private class AddClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent e) {
			RootPanel.get("content").clear();
			new ArticleForm(user);
		}
	}

	public GroceryListForm(GroceryList groceryList) {
		// TODO Auto-generated constructor stub
	}

	public static void setSelectedGroceryListForm(GroceryListForm selectedGroceryList) {
		// TODO Auto-generated method stub

	}
}
