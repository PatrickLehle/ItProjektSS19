package de.hdm.itprojektss19.team03.scart.client.gui;

import java.util.Vector;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektss19.team03.scart.shared.EditorService;
import de.hdm.itprojektss19.team03.scart.shared.EditorServiceAsync;
import de.hdm.itprojektss19.team03.scart.shared.bo.Article;
import de.hdm.itprojektss19.team03.scart.shared.bo.GroceryList;
import de.hdm.itprojektss19.team03.scart.shared.bo.Group;
import de.hdm.itprojektss19.team03.scart.shared.bo.Retailer;
import de.hdm.itprojektss19.team03.scart.shared.bo.User;

/**
 * 
 * @author Tom
 * @author bastiantilk
 * @author Marco
 *
 */
public class ShoppingListForm extends HorizontalPanel {

	public ShoppingListForm(User u, GroceryList gl, Group g) {
		user = u;
		groceryList = gl;
		group = g;
	}

	public void onLoad() {
		getData();
	}

	private EditorServiceAsync editorService = GWT.create(EditorService.class);

	private User user;
	private Group group;
	private GroceryList groceryList;
	private int articleCount;
	private Vector<Retailer> retailers = new Vector<Retailer>();

	private HorizontalPanel outerPanel = new HorizontalPanel();
	private FlowPanel flowPanel = new FlowPanel();
	private VerticalPanel retailerPanel;
	private DecoratorPanel decoPanel;

	private void fillTable(String retailer, Vector<Article> articles) {

	}

	private void getData() {
		editorService.findAllArticleByGroceryList(groceryList, articleCallback);
	}

	public void createForms() {
		flowPanel.clear();
		outerPanel.clear();
		for (Retailer r : retailers) {

			Label retailerHeader = new Label(r.getRetailerName());
			retailerHeader.addStyleName("h3");

			// todo add articles

			retailerPanel = new VerticalPanel();
			retailerPanel.clear();
			retailerPanel.addStyleName("retailer-panel");
			retailerPanel.add(retailerHeader);
			retailerPanel.add(new Label("hier kommen dann bald die artikel hin"));

			decoPanel = new DecoratorPanel();
			decoPanel.clear();
			decoPanel.addStyleName("retailers-panel");
			decoPanel.setWidget(retailerPanel);
			flowPanel.add(decoPanel);
		}

		outerPanel.add(flowPanel);
		outerPanel.addStyleName("inner-content");
		outerPanel.setHorizontalAlignment(ALIGN_CENTER);
		this.add(outerPanel);
	}

	AsyncCallback<Vector<Article>> articleCallback = new AsyncCallback<Vector<Article>>() {
		public void onFailure(Throwable t) {
			Window.alert("Failed to retrieve Articles: " + t);
		}

		public void onSuccess(Vector<Article> articles) {
			articleCount = articles.size();
			for (int i = 0; i < articleCount; i++) {

				Retailer r = new Retailer();
				r.setId(articles.get(i).getRetailerId());
				r.setRetailerName((articles.get(i).getRetailer().getRetailerName()));
				r.setGroup(group);
				r.setRetailerId(articles.get(i).getRetailerId());

				if (!retailers.contains(r)) {
					retailers.add(r);
				}
			}
			createForms();
		}
	};

}
