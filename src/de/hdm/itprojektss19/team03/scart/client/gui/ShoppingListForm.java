package de.hdm.itprojektss19.team03.scart.client.gui;

import java.util.Collections;
import java.util.Vector;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
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
		this.clear();
		this.add(new LoadingForm());
		getData();
	}

	private EditorServiceAsync editorService = GWT.create(EditorService.class);

	private User user;
	private Group group;
	private GroceryList groceryList;
	private int articleCount;
	private Vector<Retailer> retailers = new Vector<Retailer>();

	private HorizontalPanel headerPanel = new HorizontalPanel();
	private HorizontalPanel outerPanel = new HorizontalPanel();
	private FlowPanel flowPanel = new FlowPanel();
	private VerticalPanel retailerPanel;
	private DecoratorPanel decoPanel;
	private Button addRetailerButton = new Button("Laden einfügen");

	private void getData() {

		editorService.getAllRetailerByGroupId(group.getId(), retailerCallback);
	}

	public void createForms(Vector<Article> articles) {
		addRetailerButton.setStyleName("button");
		addRetailerButton.addClickHandler(new addRetailerClickHandler());
		flowPanel.clear();
		outerPanel.clear();
		outerPanel.add(addRetailerButton);
		Collections.reverse(retailers);
		for (Retailer r : retailers) {
			headerPanel = new HorizontalPanel();
			headerPanel.clear();

			Label retailerHeader = new Label(r.getRetailerName());
			Button editRetailer = new Button(
					"<image src='/images/editButton.png' width='16px' height='16px' align='center'/>");

			editRetailer.setStyleName("icon-button");
			retailerHeader.addStyleName("h3");
			headerPanel.add(retailerHeader);
			headerPanel.add(editRetailer);
			headerPanel.setVerticalAlignment(ALIGN_MIDDLE);
			editRetailer.addClickHandler(new EditRetailerClickhandler(r, headerPanel));

			// todo add articles

			retailerPanel = new VerticalPanel();
			retailerPanel.clear();
			retailerPanel.addStyleName("retailer-panel");
			retailerPanel.add(headerPanel);
			retailerPanel.add(new GroceryListForm(user, group, articles, r));
			decoPanel = new DecoratorPanel();
			decoPanel.clear();
			decoPanel.addStyleName("retailers-panel");
			decoPanel.setWidget(retailerPanel);
			flowPanel.add(decoPanel);
		}

		outerPanel.add(flowPanel);
		outerPanel.setHorizontalAlignment(ALIGN_CENTER);
		this.clear();
		this.add(outerPanel);
	}

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
			panel.add(retailerNameTextbox);
			panel.add(check);
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
			panel.add(retailerHeader);
			panel.add(editRetailer);
		}
	};

	AsyncCallback<Vector<Article>> articleCallback = new AsyncCallback<Vector<Article>>() {
		public void onFailure(Throwable t) {
			Window.alert("Failed to retrieve Articles: " + t);
		}

		public void onSuccess(Vector<Article> articles) {
			articleCount = articles.size();
			for (int i = 0; i < articleCount; i++) {

				// Retailer r = new Retailer();
				// r.setId(articles.get(i).getRetailerId());
				// r.setRetailerName((articles.get(i).getRetailerName()));
				// r.setGroup(group);
				// r.setRetailerId(articles.get(i).getRetailerId());
				//
				// if (!retailers.contains(r)) {
				// retailers.add(r);
				// }

			}

			createForms(articles);
		}
	};

	AsyncCallback<Vector<Retailer>> retailerCallback = new AsyncCallback<Vector<Retailer>>() {

		public void onFailure(Throwable t) {
			GWT.log("Failed to get Retailers: " + t);
		}

		public void onSuccess(Vector<Retailer> r) {
			retailers = r;
			editorService.findAllArticleByGroceryList(groceryList, articleCallback);
		}
	};

	AsyncCallback<Retailer> newRetailerCallback = new AsyncCallback<Retailer>() {

		public void onFailure(Throwable t) {
			GWT.log("Failed to add Retailer: " + t);
		}

		public void onSuccess(Retailer r) {
			retailers.add(r);
			getData();
		}

	};

	class addRetailerClickHandler implements ClickHandler {

		public void onClick(ClickEvent arg0) {
			Retailer r = new Retailer();
			r.setGroup(group);
			r.setRetailerName("Neuer Laden");
			editorService.createRetailer(r, newRetailerCallback);

		}
	}

}