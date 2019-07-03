package de.hdm.itprojektss19.team03.scart.client.gui;

import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektss19.team03.scart.client.ClientsideSettings;
import de.hdm.itprojektss19.team03.scart.shared.EditorServiceAsync;
import de.hdm.itprojektss19.team03.scart.shared.bo.User;

/**
 * Die ReportForm beinhaltet alle notwendigen GUI-GWT-Elemente,
 * die fuer unseren Report benoetigt werden.
 * 
 * @author PatrickLehle
 *
 */
public class ReportForm extends VerticalPanel{
	
	EditorServiceAsync editorV = ClientsideSettings.getEditor();
	
	User user = null;
	
//CONSTRUCTORS========================================================
	
	public ReportForm() {
		
	}
	
	public ReportForm(User u) {
		this.user = u;
		user.setId(Integer.valueOf(Cookies.getCookie("userId")));
		user.setEmail(Cookies.getCookie("email"));
	}
	
//LABELS==============================================================
	
	Label reportLabel  = new Label("Report-Generator");
	
//PANELS==============================================================

	HorizontalPanel headerHP = new HorizontalPanel();
	HorizontalPanel mainHP = new HorizontalPanel();
	VerticalPanel filterVP = new VerticalPanel();
	ScrollPanel scrollP = new ScrollPanel();

//HTML================================================================
	
	HTML reportHTML = new HTML("<b>Angeforderter Report</b>");

//METHODS=============================================================
	
	public void onLoad() {
		super.onLoad();
		
		editorV.getUserById(user.getId(), new findUserCallBack());
		
	//SETTING-STYLE-NAME==========================
		this.addStyleName("ReportForm");
		headerHP.addStyleName("ReportHeader");
		mainHP.setStyleName("MainReport");
		filterVP.addStyleName("NavPanel");
		scrollP.addStyleName("Scroll");
	
	//BUTTON======================================
		Button editor = new Button("Editor", new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				Window.Location.replace("/Scart.html");
			}
		});
	
	//ADD=========================================
		headerHP.add(editor);
		scrollP.add(reportHTML);
		ReportFilterForm reportFForm = new ReportFilterForm(user);
		filterVP.add(reportFForm);
		mainHP.add(filterVP);
		mainHP.add(scrollP);
		mainHP.setWidth("100%");
		mainHP.setCellWidth(filterVP, "20%");
		mainHP.setHorizontalAlignment(ALIGN_CENTER);

		RootPanel.get().add(headerHP);
		RootPanel.get().add(mainHP);
		
		
//		this.add(header);
//		this.add(main);
	}

//SETTER===============================================================
	
	public void setReport(HTML rHTML) {
		if(rHTML != null) {
			this.reportHTML = rHTML;
			scrollP.clear();
			scrollP.add(reportHTML);
			RootPanel.get("content").add(scrollP);
		}
	}

//CLASS================================================================
	
	class findUserCallBack implements AsyncCallback<User>{
		
		public void onFailure(Throwable caught) {
	
		}
		
		public void onSuccess(User result) {
			if(result != null && result.getId() == user.getId()) {
				user = result;
			}
		}
	}
	
}
