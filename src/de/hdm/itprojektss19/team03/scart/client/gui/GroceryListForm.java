package de.hdm.itprojektss19.team03.scart.client.gui;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

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
		   List<Article> Articles = Arrays.asList(
				    new Article("Milch", 1, "Liter", 2),
				    new Article("Apfel", 1, "Stück", 2));
		   
		 //Erste Spalte fuer Checkbox/Auswahlbox  
		   
		   
		//2. Spalte fuer den Name
		    TextColumn<Article> nameColumn = new TextColumn<Article>() {
		      public String getValue(Article a) {
		        return a.getName();
		      }
		    };
		    
		//3. Spalte fuer die Anzahl
		    TextColumn<Article> quantityColumn = new TextColumn<Article>() {
			      public int getValue(Article a) {
			        return a.getQuantity();
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
				 public int getValue(Article a) {
					    return a.getRetailerId();
				 }
			 	};	
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		

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
