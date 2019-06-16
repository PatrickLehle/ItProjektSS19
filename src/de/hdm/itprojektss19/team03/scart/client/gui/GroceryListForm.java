package de.hdm.itprojektss19.team03.scart.client.gui;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
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
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionModel;
import com.google.gwt.view.client.SingleSelectionModel;

import de.hdm.itprojektss19.team03.scart.client.ClientsideSettings;
import de.hdm.itprojektss19.team03.scart.shared.EditorServiceAsync;
import de.hdm.itprojektss19.team03.scart.shared.bo.Article;
import de.hdm.itprojektss19.team03.scart.shared.bo.GroceryList;

/**
 * 
 * @author bastiantilk, Tom
 * http://samples.gwtproject.org/samples/Showcase/Showcase.html#!CwCellTable
 * 
 *
 */
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

	public ProvidesKey<Article> KEY_PROVIDER = new ProvidesKey<Article>() {
	      @Override
	      public Object getKey(Article article) {
	        return article == null ? null : article.getId();
	      }
	    };
	    
		CellTable<Article> ArticleCt = new CellTable<Article>(KEY_PROVIDER); //Article CellTable

	
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
		hpButtons.setHorizontalAlignment(ALIGN_CENTER);
		
		vt.add(hpTitle);
		vt.add(sc);
		vt.add(hpButtons);
		
		
		
		ArticleCt.setAutoHeaderRefreshDisabled(true);
	    ArticleCt.setAutoFooterRefreshDisabled(true);
	    
	    
	  //Beispieldaten
		   List<Article> Articles = Arrays.asList(
				    new Article("Milch", 1, "Liter", 2),
				    new Article("Apfel", 1, "Stück", 2),
				    new Article("Banane", 8, "Stück", 2)
				   );
		   
		   
		   
		   ListHandler<Article> sortHandler = new ListHandler<Article>(Articles);
			  ArticleCt.addColumnSortHandler(sortHandler);
			   
			  final MultiSelectionModel<Article> selectionModel1 = new MultiSelectionModel<Article>(KEY_PROVIDER);//MultiSelectionModel
			ArticleCt.setSelectionModel(selectionModel1, DefaultSelectionEventManager.<Article> createCheckboxManager(0));
			  
				    // Initialize the columns.
			
			  selectionModel1.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			      public void onSelectionChange(SelectionChangeEvent event) {
			    	 //Article temp = selectionModel1.get
			          //Set<Article> temp = selectionModel1.getSelectedSet();
			         titelLabel.setText(selectionModel1.getSelectedSet().toString());
			         //selectionModel1.setSelected(selectionModel1.getSelectedObject(), true);
			         
			      }
			    });
			
			  initTableColumns(selectionModel1, sortHandler);
			  
			//Artikel hinzufuegen
			addBtn.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
							
							//ArticleCt.getColumn(i).getCell();
							
							Set<Article> temp2 = selectionModel1.getSelectedSet();
							//selectionModel1.setSelected(Articles.get(1), true);
							//temp.remove(i);
							//initTableColumns(selectionModel1, sortHandler);
							//System.out.println(temp2);
							if(temp2.isEmpty()) {
								Window.alert("leer");
							} else {
								Window.alert(temp2.toString());

							}
						
				}});
			
			//"Pusht" die Daten in die Tabelle
			ArticleCt.setRowData(0, Articles);

			//Fuegt das komplette Vertical Panel mit allen Panels darin dem Root Panel hinzu
			RootPanel.get("content").add(vt);
		
		
	}
		
		
private void initTableColumns(final SelectionModel<Article> selectionModel1, ListHandler<Article> sortHandler) {
			
	 //Erste Spalte fuer Checkbox/Auswahlbox  
	
	Column<Article, Boolean> checkColumn = new Column<Article, Boolean>(
	        new CheckboxCell(true, false)) {
		//new CheckboxCell(true, false)) {
	      @Override
	      public Boolean getValue(Article object) {
	    	 
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
		 	
		 	//6. Spalte fuer den Test
			 TextColumn<Article> testColumn = new TextColumn<Article>() {
				 public String getValue(Article a) {
					    return Integer.toString(a.getId());
				 }
			 	};
	
	 // Add the columns.
	ArticleCt.addColumn(checkColumn, "Gekauft");
	
	ArticleCt.addColumn(nameColumn, "Name");
	ArticleCt.addColumn(quantityColumn, "Menge");
	ArticleCt.addColumn(unitColumn, "Einheit");
	ArticleCt.addColumn(retailerColumn, "Supermarkt");
	
	ArticleCt.addColumn(testColumn, "Id");


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
