package de.hdm.itprojektss19.team03.scart.client.gui;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionModel;
import com.google.gwt.view.client.SingleSelectionModel;

import de.hdm.itprojektss19.team03.scart.client.ClientsideSettings;
import de.hdm.itprojektss19.team03.scart.shared.EditorServiceAsync;
import de.hdm.itprojektss19.team03.scart.shared.bo.Article;
import de.hdm.itprojektss19.team03.scart.shared.bo.GroceryList;

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

	CellTable<Article> ArticleCt = new CellTable<Article>(); //Article CellTable
	
	//Name der GroceryList wird ausgegeben
	Label titelLabel = new Label();
	
	ScrollPanel sc = new ScrollPanel();

	public void onLoad() {
		super.onLoad();

		RootPanel.get("contentHeader").clear();
		RootPanel.get("content").clear();
		RootPanel.get("footer").clear();
		
		//RootPanel.get("content").add(vt);
		
		
		sc.setSize("200px", "550px");
		sc.setVerticalScrollPosition(10);
		
		hpTitle.add(titelLabel); //Titel Label wird in Horitontales Panel eingefuegt
		
		sc.add(ArticleCt); //CellTable wird in das Scroll Panel hinzugefuegt
		
		hpButtons.add(addBtn); //Buttons werden dem horizontal Panel unten hinzugefuegt
		hpButtons.add(editBtn);
		hpButtons.add(deleteBtn);
		
		vt.add(hpTitle);
		vt.add(sc);
		vt.add(hpButtons);
		
		RootPanel.get("content").add(vt);
		
		//CellTable
		
		// A simple data type that represents a contact.
		/*
		  class Article {
		    private final String address;
		    private final String name;

		    public Article(String name, String address) {
		      this.name = name;
		      this.address = address;
		    }
		  }
		  */
		
		
		/** Add a selection model to handle user selection.
	    final SelectionModel<String> selectionModel = new MultiSelectionModel<String>();
	    ArticleCt.setSelectionModel(selectionModel);
	    selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
	      public void onSelectionChange(SelectionChangeEvent event) {
	        String selected = selectionModel.getSelectedObject();
	        if (selected != null) {
	          Window.alert("You selected: " + selected);
	        }
	      }
	    });
	    */
		
		ArticleCt.setAutoHeaderRefreshDisabled(true);
	    ArticleCt.setAutoFooterRefreshDisabled(true);
	    
	    
	    //Beispieldaten
		   List<Article> Articles = Arrays.asList(
				    new Article("Milch", 1, "Liter", 2),
				    new Article("Apfel", 1, "Stück", 2));
		   
		   
		   
		  ListHandler<Article> sortHandler = new ListHandler<Article>(Articles);
		  ArticleCt.addColumnSortHandler(sortHandler);
		   
		   final SelectionModel<Article> selectionModel1 = new MultiSelectionModel<Article>();
			    ArticleCt.setSelectionModel(selectionModel1,
			        DefaultSelectionEventManager.<Article> createCheckboxManager());
		
			    // Initialize the columns.
		initTableColumns(selectionModel1, sortHandler);
		
		ArticleCt.setRowData(0, Articles);

		RootPanel.get("content").add(vt);
	}
		
		
private void initTableColumns(final SelectionModel<Article> selectionModel1, ListHandler<Article> sortHandler) {
			
		 //Erste Spalte fuer Checkbox/Auswahlbox  
		Column<Article, Boolean> checkColumn = new Column<Article, Boolean>(
		        new CheckboxCell(true, false)) {
		      @Override
		      public Boolean getValue(Article object) {
		        // Get the value from the selection model.
		        return selectionModel1.isSelected(object);
		      }
		    };
		   
		//2. Spalte fuer den Name
		    TextColumn<Article> nameColumn = new TextColumn<Article>() {
		      public String getValue(Article a) {
		        return a.getName();
		      }
		    };
		    
		//3. Spalte fuer die Anzahl
		    TextColumn<Article> quantityColumn = new TextColumn<Article>() {
			      public String getValue(Article a) {
			        return Integer.toString(a.getQuantity());
			      }
			    };
		    
		//4. Spalte fuer die Einheit
			 TextColumn<Article> unitColumn = new TextColumn<Article>() {
				   public String getValue(Article a) {
				     return a.getUnit();
				   }
				};	    
		    
		//5. Spalte fuer den Retailer
			 TextColumn<Article> retailerColumn = new TextColumn<Article>() {
				 public String getValue(Article a) {
					    return Integer.toString(a.getRetailerId());
				 }
			 	};	
		
		 // Add the columns.
		ArticleCt.addColumn(checkColumn, "Gekauft");
		ArticleCt.addColumn(nameColumn, "Name");
		ArticleCt.addColumn(quantityColumn, "Menge");
		ArticleCt.addColumn(unitColumn, "Einheit");
		ArticleCt.addColumn(retailerColumn, "Supermarkt");
		


		// Vector in das HorizontalePanel hinzufuegen/ Artikel als Liste anzeigen
		// hp.getElement(articleList);
		// getArticles().iterator().next().getName());
		// if bedingung

		//hpF.add(editBtn);
		editBtn.addClickHandler(new EditClickHandler());
		editBtn.setEnabled(true);
		//hpF.add(deleteBtn);
		deleteBtn.addClickHandler(new DeleteClickHandler());
		deleteBtn.setEnabled(true);
		//hpF.add(addBtn);
		addBtn.addClickHandler(new AddClickHandler());
		addBtn.setEnabled(true);
		/**
		 * newArticleBtn.setPixelSize(100, 60); newArticleBtn.setStyleName("button1");
		 * newArticleBtn.setTitle("add Article");
		 */

	}


	private class EditClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent e) {
		}
	}

	private class DeleteClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent e) {
		}
	}

	// Oeffnet ArticleForm Panel zum Hinzufuegen eines Artikels
	private class AddClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent e) {

		}
	}

	public GroceryListForm(de.hdm.itprojektss19.team03.scart.client.gui.GroceryListForm groceryList) {
		// TODO Auto-generated constructor stub
	}

	public static void setSelectedGroceryListForm(GroceryListForm selectedGroceryList) {
		// TODO Auto-generated method stub

	}

}
