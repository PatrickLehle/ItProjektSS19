package de.hdm.itprojektss19.team03.scart.client.gui;

import java.util.Date;
import java.util.Vector;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.CalendarUtil;
import com.google.gwt.user.datepicker.client.DateBox;

import de.hdm.itprojektss19.team03.scart.client.ClientsideSettings;
import de.hdm.itprojektss19.team03.scart.shared.EditorServiceAsync;
import de.hdm.itprojektss19.team03.scart.shared.LoginServiceAsync;
import de.hdm.itprojektss19.team03.scart.shared.ReportGeneratorAsync;
import de.hdm.itprojektss19.team03.scart.shared.bo.Group;
import de.hdm.itprojektss19.team03.scart.shared.bo.User;
import de.hdm.itprojektss19.team03.scart.shared.report.SimpleReport;

/**
 * <code>ReportFilterForm</code> 
 * 
 * @author PatrickLehle
 *
 */
public class ReportFilterForm extends VerticalPanel {

//INITIALISATION===========================================================
	
	LoginServiceAsync loginService = ClientsideSettings.getLoginService();
	EditorServiceAsync editorVerwaltung = ClientsideSettings.getEditorVerwaltung();
	ReportGeneratorAsync reportVerwaltung = ClientsideSettings.getReportGeneratorService();
	
	Group group = new Group();
	User user = new User();
	ReportForm rForm = new ReportForm();
	Vector<Group> allGroups = null;
	
	Vector<String> allGroupsS = new Vector<String>();
	Vector<String> choosenGroupS  =new Vector<String>();
	
	Date cStartDate = null;
	Date cEndDate = null;
	
	Date today;
	
	@SuppressWarnings("deprecation")
	Date firstPossDate = new Date(119,0,1);
	
	@SuppressWarnings("deprecation")
	Date secondPossibleDate = new Date(119, 0, 2);
	
	@SuppressWarnings("deprecation")
	Date lastImpossibleDate = new Date(118, 11, 31);
	
	@SuppressWarnings("deprecation")
	Date dateAfterChosenStartDate = new Date(119, 0, 2);
	
	Vector<SimpleReport> choosenReports = new Vector<SimpleReport>();

	
//CONSTRUCTORS=====================================================================
	
	public ReportFilterForm() {
	}
	
	public ReportFilterForm(Group cGroup, ReportForm cReport) {
		this.group = cGroup;
		this.rForm = cReport;
	}
	
//PANELS-LABELS-BUTTONS=============================================================	

	VerticalPanel pickGroup = new VerticalPanel();
	Label chooseGroup = new Label("Gruppe auswählen");
	
	VerticalPanel selectSpecificGroupPanel = new VerticalPanel();
	
	VerticalPanel pickPeriod = new VerticalPanel();
	Label periodLbl = new Label("Zeitraum auswählen");
	HorizontalPanel startEndPnl = new HorizontalPanel();
	
	DateBox start = new DateBox();
	Label dash = new Label("-");
	DateBox end = new DateBox();
	
	HorizontalPanel periodShortcutsH = new HorizontalPanel();
	Button last3DaysBtn = new Button("Letzten 3 Tage");
	Button lastWeekBtn = new Button("Letzte Woche");
	Button lastMonthBtn = new Button("Letzter Monat");
	
	VerticalPanel checkBoxes = new VerticalPanel();
	

	Button getReportBtn = new Button("Report generieren");
	
//METHODS===========================================================================
	
	@SuppressWarnings("deprecation")
	public void onLoad() {
		super.onLoad();
		
		lastImpossibleDate.setHours(23);
		lastImpossibleDate.setMinutes(59);
		lastImpossibleDate.setSeconds(59);
		
	//STYLE-NAMES========================
		pickGroup.addStyleName("PickGroupVPanel");
		chooseGroup.addStyleName("ChooseGroupLabel");
		selectSpecificGroupPanel.addStyleName("SpecificGroupVPanel");
		pickPeriod.addStyleName("PickPeriodVPanel");
		periodLbl.addStyleName("periodLabel");
		start.addStyleName("StartPanel");
		end.addStyleName("EndPanel");
		checkBoxes.addStyleName("CheckBoxesVPanel");
		getReportBtn.addStyleName("GetReportBtn");
		
	//GRUPPE==========================
		
		
	//ZEITRAUM========================
		pickPeriod.add(periodLbl);
		pickPeriod.add(startEndPnl);
		
		startEndPnl.add(start);
		startEndPnl.add(dash);
		startEndPnl.add(end);
		
		periodShortcutsH.add(last3DaysBtn);
		periodShortcutsH.add(lastWeekBtn);
		periodShortcutsH.add(lastMonthBtn);
		
		pickPeriod.add(periodShortcutsH);
		
		this.add(pickGroup);
		this.add(pickPeriod);
		this.add(getReportBtn);

		//Muss noch bearbeitet werden. cannot be resolve to a type error...
		//editorVerwaltung.findAllGroups(new AllGroupsCallback());
		
		class AllGroupsCallback implements AsyncCallback <Vector<Group>> {

			public void onFailure(Throwable caught) {

			}

			public void onSuccess(Vector<Group> result) {
				allGroups = result;

				if (allGroupsS.size() != result.size()) {
					selectSpecificGroupPanel.clear();
					allGroupsS.clear();

					for (int g = 0; g < result.size(); g++) {
						allGroupsS.add(result.elementAt(g).getGroupName());
						CheckBox tempCheckBox = new CheckBox(allGroupsS.elementAt(g));
						tempCheckBox.addClickHandler(new GroupCheckBoxClickHandler(tempCheckBox));
						selectSpecificGroupPanel.add(tempCheckBox);
					}
				}
			}
		
		
		class GroupCheckBoxClickHandler implements ClickHandler {
			CheckBox checkBox = null;

			public GroupCheckBoxClickHandler(CheckBox cB) {
				this.checkBox = cB;
			}

			public void onClick(ClickEvent event) {
				if (choosenGroupS.contains(checkBox.getText())) {

					for (int i = 0; i < choosenGroupS.size(); i++) {
						if (choosenGroupS.elementAt(i) == checkBox.getText()) {
							choosenGroupS.removeElementAt(i);
						}
					}
				}
				else if (choosenGroupS.contains(checkBox.getText()) == false) {
					choosenGroupS.add(checkBox.getText());
				}
			}
		}
	}
}	
		class StartDateChangeHandler implements ValueChangeHandler<Date> {
			public StartDateChangeHandler() {

			}

			@Override
			public void onValueChange(ValueChangeEvent<Date> event) {

				
				Date daybeforetoday = today;
				CalendarUtil.addDaysToDate(daybeforetoday, -1);
				
				Date daybeforeenddate;
				
				daybeforeenddate = end.getValue();
				CalendarUtil.addDaysToDate(daybeforeenddate, -1);
	
				if (start.getValue().after(end.getValue())) {
					start.setValue(daybeforeenddate);
					Window.alert(
							"Das Startdatum muss mindestens einen Tag vor dem Enddatum liegen. Die Auswahl wurde entsprechend angepasst.");
					return;
				}

				if (start.getValue().after(today)) {

					start.setValue(daybeforetoday);
					Window.alert(
							"Das späteste mögliche Startdatum ist der gestrige Tag. Die Auswahl wurde entsprechend angepasst");
					return;
				}

				if (start.getValue().before(lastImpossibleDate)) {
					start.setValue(firstPossDate);
					Window.alert(
							"Das frühestmögliche Startdatum ist der 1. Januar 2019. Die Auswahl wurde entsprechend angepasst");
					return;
				}
			}
		}

		class EndDateChangeHandler implements ValueChangeHandler<Date> {

			public EndDateChangeHandler() {
			}

			@Override
			public void onValueChange(ValueChangeEvent<Date> event) {

				if (end.getValue().after(today)) {
					end.setValue(today);

					Window.alert("Das späteste mögliche Enddatum ist der heutige Tag. Die Auswahl wurde entsprechend angepasst.");
					return;
				}

				Date dateafterchosenstartDateCOPY = start.getValue();
				CalendarUtil.addDaysToDate(dateafterchosenstartDateCOPY, 1);
			
				if (end.getValue().before(dateafterchosenstartDateCOPY)) {
					end.setValue(dateafterchosenstartDateCOPY);

					Window.alert("Das Enddatum muss hinter dem Startdatum liegen. Die Auswahl wurde entsprechend angepasst.");
					return;
				}

				if (end.getValue().before(secondPossibleDate)) {
					end.setValue(secondPossibleDate);

					Window.alert("Das frühestmögliche Enddatum ist der 2. Januar 2019. Die Auswahl wurde entsprechend angepasst.");
					return;
				}
			}
		}
				
	}

