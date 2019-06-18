package de.hdm.itprojektss19.team03.scart.client.gui;

import com.google.gwt.user.client.Window;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektss19.team03.scart.client.ClientsideSettings;
import de.hdm.itprojektss19.team03.scart.shared.EditorServiceAsync;
import de.hdm.itprojektss19.team03.scart.shared.bo.Group;

/**
 * Die ReportForm beinhaltet alle notwendigen GUI-GWT-Elemente,
 * die fuer unseren Report benoetigt werden.
 * 
 * @author PatrickLehle
 *
 */
public class ReportForm extends VerticalPanel{
	
	EditorServiceAsync editorV = ClientsideSettings.getEditor();
	
	Group group = null;
	
//CONSTRUCTORS========================================================
	
	public ReportForm() {
		
	}
	
	public ReportForm(Group g) {
		this.group = g;
	}
	
//LABELS==============================================================
	
	Label reportLabel  = new Label("Report-Generator");
	
//PANELS==============================================================

	HorizontalPanel header = new HorizontalPanel();
	HorizontalPanel main = new HorizontalPanel();
	HorizontalPanel filter = new HorizontalPanel();
	ScrollPanel scroll = new ScrollPanel();

//HTML================================================================
	
	HTML reportHTML = new HTML("<b>Angeforderter Report</b>");

//METHODS=============================================================
	
	public void onLoad() {
		super.onLoad();
		
		editorV.getGroupById(group.getId(), new findGroupCallBack());
		
	//SETTING-STYLE-NAME==========================
		this.addStyleName("ReportForm");
		header.addStyleName("ReportHeader");
		main.setStyleName("MainReport");
		filter.addStyleName("NavPanel");
		scroll.addStyleName("Scroll");
	
	//BUTTON======================================
		Button editor = new Button("Editor", new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				Window.Location.replace("/Scart.html");
			}
		});
	
	//ADD=========================================
		header.add(editor);
		scroll.add(reportHTML);
		ReportFilterForm reportFForm = new ReportFilterForm(group, this);
		filter.add(reportFForm);
		main.add(filter);
		main.add(scroll);
		main.setWidth("100%");
		main.setCellWidth(filter, "20%");
		main.setHorizontalAlignment(ALIGN_CENTER);
		
		this.add(header);
		this.add(main);
	}

//SETTER===============================================================
	
	public void setReport(HTML rHTML) {
		if(rHTML != null) {
			this.reportHTML = rHTML;
			scroll.clear();
			scroll.add(reportHTML);
		}
	}

//CLASS================================================================
	
	class findGroupCallBack implements AsyncCallback<Group>{
		
		public void onFailure(Throwable caught) {
	
		}
		
		public void onSuccess(Group result) {
			if(result != null && result.getId() == group.getId()) {
				group = result;
			}
		}
	}
	
}
