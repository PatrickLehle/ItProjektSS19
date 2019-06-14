package de.hdm.itprojektss19.team03.scart.client.gui;

import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.TableRowElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Element;
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

	Label titelLabel = new Label();

	ScrollPanel sc = new ScrollPanel();

	public void onLoad() {
		super.onLoad();
		vt.addStyleName("main-panel");

		sc.setSize("200px", "550px");
		sc.setVerticalScrollPosition(10);

		// Titel Label wird in Horitontales Panel eingefuegt
		hpTitle.add(titelLabel);

		// CellTable wird in das Scroll Panel hinzugefuegt
		// sc.add(aTable);

		vt.add(hpTitle);

		aTable.setText(0, 0, "Artikel");
		aTable.setText(0, 1, "Menge");
		aTable.setText(0, 2, "Mengeneinheit");
		aTable.setText(0, 3, "Laden");

		// for Schleife das alle Artikel mit Name Quantity Unit und RetailerName
		// aufgelistet werden im Panel.
		for (int aNum = 1; aNum <= 10; aNum++) {
			aTable.setText(aNum, 0, "TEST" + aNum);
			aTable.setText(aNum, 1, "100" + aNum);
			aTable.setText(aNum, 2, "UNITS" + aNum);
			aTable.setText(aNum, 3, "BESTBUY" + aNum);
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

	public static TableRowElement findNearestParentRow(Node node) {
		Node element = findNearestParentNodeByType(node, "tr");
		if (element != null) {
			return element.cast();
		}
		return null;
	}

	public static Node findNearestParentNodeByType(Node node, String nodeType) {
		while ((node != null)) {
			if (Element.is(node)) {
				Element elem = (Element) Element.as(node);

				String tagName = elem.getTagName();

				if (nodeType.equalsIgnoreCase(tagName)) {
					return elem.cast();
				}

			}
			node = node.getParentNode();
		}
		return null;
	}

	public CheckBox getCbCheck() {
		CheckBox cb = new CheckBox();
		cb.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				int rowIndex = aTable.getCellForEvent(event).getRowIndex();
				String first = aTable.getText(rowIndex, 0);
				String second = aTable.getText(rowIndex, 1);
				String third = aTable.getText(rowIndex, 2);
				String fourth = aTable.getText(rowIndex, 3);
				int rowCount = aTable.getRowCount();
				aTable.setText(rowCount, 0, first);
				aTable.setText(rowCount, 1, second);
				aTable.setText(rowCount, 2, third);
				aTable.setText(rowCount, 3, fourth);
				aTable.setText(rowCount, 4, "gekauft");
				aTable.removeRow(rowIndex);
			}
		});
		cb.setValue(false);
		return cb;
	}

	public class CheckClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent e) {
			if (checkBtnBoolean == false && editBtnBoolean == false && deleteBtnBoolean == false) {
				checkBtnBoolean = true;
				for (int aNum = 1; aNum < aTable.getRowCount(); aNum++) {
					if (aTable.getCellCount(aNum) == 4) {
						aTable.setWidget(aNum, 4, getCbCheck());
					}
				}
			} else if (checkBtnBoolean == true) {
				for (int i = 1; i < aTable.getRowCount(); i++) {
					if (aTable.getText(i, 4).isEmpty() == true) {
						aTable.removeCell(i, 4);
					}
				}
				checkBtnBoolean = false;
			} else if (editBtnBoolean == false || deleteBtnBoolean == false) {
				Window.alert("Bitte den anderen Button deaktivieren.");
			} else {
				Window.alert("Ein Fehler ist aufgetreten, bitte versuchen sie es erneut.");

			}

		}
	}

	public int globalRow;
	public Boolean cbBoolean;

	public CheckBox getCbEdit() {

		CheckBox cb = new CheckBox();
		cb.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				int rowIndex = aTable.getCellForEvent(event).getRowIndex();
				globalRow = rowIndex;
				String first = aTable.getText(rowIndex, 0);
				String second = aTable.getText(rowIndex, 1);
				String third = aTable.getText(rowIndex, 2);
				String fourth = aTable.getText(rowIndex, 3);
				if (first.isEmpty() == false && second.isEmpty() == false && third.isEmpty() == false
						&& fourth.isEmpty() == false) {
					editTb1.setText(first);
					editTb2.setText(second);
					editTb3.setText(third);
					editTb4.setText(fourth);
					aTable.setWidget(rowIndex, 0, editTb1);
					aTable.setWidget(rowIndex, 1, editTb2);
					aTable.setWidget(rowIndex, 2, editTb3);
					aTable.setWidget(rowIndex, 3, editTb4);
				}
			}
		});

		cb.setValue(false);
		return cb;
	}

	public class EditClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			if (editBtnBoolean == false && checkBtnBoolean == false && deleteBtnBoolean == false) {
				editBtnBoolean = true;
				for (int i = 1; i <= aTable.getRowCount(); i++) {
					if (aTable.getCellCount(i) == 4) {
						aTable.setWidget(i, 4, getCbEdit());
					}
				}
			} else if (editBtnBoolean == true) {
				editBtnBoolean = false;
				String first = editTb1.getText();
				String second = editTb2.getText();
				String third = editTb3.getText();
				String fourth = editTb4.getText();
				aTable.clearCell(globalRow, 0);
				aTable.clearCell(globalRow, 1);
				aTable.clearCell(globalRow, 2);
				aTable.clearCell(globalRow, 3);
				aTable.setText(globalRow, 0, first);
				aTable.setText(globalRow, 1, second);
				aTable.setText(globalRow, 2, third);
				aTable.setText(globalRow, 3, fourth);
				for (int i = 1; i <= aTable.getRowCount(); i++) {
					aTable.removeCell(i, 4);
				}
			} else if (checkBtnBoolean == false || deleteBtnBoolean == false) {
				Window.alert("Bitte anderen Button deaktivieren.");
			} else {
				Window.alert("Ein Fehler ist aufgetreten, bitte versuchen sie es erneut.");
			}
		}
	}

	public CheckBox getCbDel() {
		CheckBox cb = new CheckBox();
		cb.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				int rowIndex = aTable.getCellForEvent(event).getRowIndex();
				aTable.removeRow(rowIndex);
			}
		});
		cb.setValue(false);
		return cb;
	}

	public class DeleteClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent e) {
			if (deleteBtnBoolean == false && checkBtnBoolean == false && editBtnBoolean == false) {
				deleteBtnBoolean = true;
				for (int aNum = 1; aNum < aTable.getRowCount(); aNum++) {
					if (aTable.getCellCount(aNum) == 4) {
						aTable.setWidget(aNum, 4, getCbDel());
					}
				}
			} else if (deleteBtnBoolean == true) {
				for (int i = 1; i < aTable.getRowCount(); i++) {
					if (aTable.getText(i, 4).isEmpty() == true) {
						aTable.removeCell(i, 4);
					}
				}
				deleteBtnBoolean = false;
			} else if (checkBtnBoolean == true || editBtnBoolean == true) {
				Window.alert("Bitte anderen Button deaktivieren.");
			} else {
				Window.alert("Ein Fehler ist aufgetreten, bitte versuchen sie es erneut.");
			}
		}
	}

	// Oeffnet ArticleForm Panel zum Hinzufuegen eines Artikels
	public class AddClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent e) {
			RootPanel.get("content").clear();
			new ArticleForm(user);
		}
	}

}
