package de.hdm.itprojektss19.team03.scart.client.gui;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Vector;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.CalendarUtil;
import com.google.gwt.user.datepicker.client.DateBox;

import de.hdm.itprojektss19.team03.scart.client.ClientsideSettings;
import de.hdm.itprojektss19.team03.scart.shared.EditorServiceAsync;
import de.hdm.itprojektss19.team03.scart.shared.LoginServiceAsync;
import de.hdm.itprojektss19.team03.scart.shared.ReportGeneratorAsync;
import de.hdm.itprojektss19.team03.scart.shared.bo.Group;
import de.hdm.itprojektss19.team03.scart.shared.bo.User;
import de.hdm.itprojektss19.team03.scart.shared.report.CompositeReport;
import de.hdm.itprojektss19.team03.scart.shared.report.HTMLReportWriter;
import de.hdm.itprojektss19.team03.scart.shared.report.SimpleReport;

/**
 * <code>ReportFilterForm</code> 
 * 
 * @author PatrickLehle
 * @author SimonJanik
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
	Vector<User> allUsers = null;
	
	Vector<String> allGroupsS = new Vector<String>();
	Vector<String> choosenGroupS  =new Vector<String>();
	
	Vector<String> allUsersS = new Vector<String>();
	Vector<String> choosenUserS  =new Vector<String>();
	
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

	//FIRST=======================
	VerticalPanel pickGroup = new VerticalPanel();
	Label chooseGroup = new Label("Gruppe auswählen");
	
	VerticalPanel selectSpecificGroupPanel = new VerticalPanel();
	
	VerticalPanel radioButtonsContainer = new VerticalPanel();
	RadioButton selectOneGroupBtn = new RadioButton("Für mich");
	RadioButton selectSpecGroupBtn = new RadioButton("Für spezifische Gruppe");
	RadioButton selectAllGroupsBtn = new RadioButton("Für all deine Gruppen");
	
	VerticalPanel selectSpecGroupPanel = new VerticalPanel();
	VerticalPanel sSGSP = new VerticalPanel();
	
	VerticalPanel pickPeriod = new VerticalPanel();
	Label cPeriodLbl = new Label("Zeitraum auswählen");
	HorizontalPanel startEndPnl = new HorizontalPanel();
	
	DateBox start = new DateBox();
	Label dash = new Label("-");
	DateBox end = new DateBox();
	
	//THIRD===========================
	HorizontalPanel periodTimePickerH = new HorizontalPanel();
	Button last3DaysBtn = new Button("Letzten 3 Tage");
	Button lastWeekBtn = new Button("Letzte Woche");
	Button lastMonthBtn = new Button("Letzter Monat");
	
	VerticalPanel checkBoxes = new VerticalPanel();
	
	VerticalPanel pickReportType = new VerticalPanel();
	RadioButton pickReportLbl = new RadioButton("Show All");
	RadioButton specReportBtn = new RadioButton("parametisierbare Auswahl");
	
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
		cPeriodLbl.addStyleName("periodLabel");
		start.addStyleName("StartPanel");
		end.addStyleName("EndPanel");
		checkBoxes.addStyleName("CheckBoxesVPanel");
		getReportBtn.addStyleName("GetReportBtn");
		
	//GRUPPE==========================
		radioButtonsContainer.add(selectOneGroupBtn);
		radioButtonsContainer.add(selectSpecGroupBtn);
		radioButtonsContainer.add(sSGSP);
		radioButtonsContainer.add(selectAllGroupsBtn);
		pickGroup.add(chooseGroup);
		pickGroup.add(radioButtonsContainer);
	//ZEITRAUM========================
		pickPeriod.add(cPeriodLbl);
		pickPeriod.add(startEndPnl);
		
		startEndPnl.add(start);
		startEndPnl.add(dash);
		startEndPnl.add(end);
		
		periodTimePickerH.add(last3DaysBtn);
		periodTimePickerH.add(lastWeekBtn);
		periodTimePickerH.add(lastMonthBtn);
		
		pickPeriod.add(periodTimePickerH);
		
		this.add(pickGroup);
		this.add(pickPeriod);
		this.add(getReportBtn);
	
		editorVerwaltung.findAllGroups(new AllGroupsCallback());
		
		//TIMEFRAME-CHECK-FOR-CHANGE===================
		Timer refresh = new Timer() {
			public void run() {
				editorVerwaltung.findAllGroups(new AllGroupsCallback());
			}
		};
		refresh.scheduleRepeating(1000);

		today = new Date();
		today.setHours(1);
		today.setMinutes(0);
		today.setSeconds(0);

		//CHOOSEN-PERIOD====================
		DateTimeFormat dateFormat = DateTimeFormat.getLongDateFormat();
		start.setFormat(new DateBox.DefaultFormat(dateFormat));
		start.getDatePicker().setYearArrowsVisible(true);
		start.getDatePicker().setYearAndMonthDropdownVisible(true);
		start.getDatePicker().setVisibleYearCount(1);
		start.addValueChangeHandler(new StartDateChangeHandler());

		end.setFormat(new DateBox.DefaultFormat(dateFormat));
		end.getDatePicker().setYearArrowsVisible(true);
		end.getDatePicker().setYearAndMonthDropdownVisible(true);
		end.getDatePicker().setVisibleYearCount(10);
		end.addValueChangeHandler(new EndDateChangeHandler());
		
		Window.alert("firstPossibleDate");
		start.setValue(firstPossDate);
		end.setValue(today);
		Window.alert("today");

		lastWeekBtn.addClickHandler(new TimePickHandler(lastWeekBtn));
		lastMonthBtn.addClickHandler(new TimePickHandler(lastMonthBtn));
		last3DaysBtn.addClickHandler(new TimePickHandler(last3DaysBtn));


		//SET-REPORT===============================
		//TO-DO: Implement 2 clickhandler for checking checkboxes/ SelectAllReportGroupsHandler/SelectSpecReportGroupHandler
//		allReportBtn.addClickHandler(new SelectAllReportGroupsHandler(allReportBtn));
//		specificReportButton.addClickHandler(new SelectSpecReportGroupHandler(specificReportButton));


		//GET-REPORT======================
		getReportBtn.addClickHandler(new getReportClickHandler());
	}
		class AllGroupsCallback implements AsyncCallback<Vector<Group>> {
			
			public void onFailure(Throwable caught) {
			}
			
			public void onSuccess(Vector<Group> result) {
				allGroups = result;

				if (allGroupsS.size() != result.size()) {
					selectSpecificGroupPanel.clear();
					allGroupsS.clear();

					for (int g = 0; g < result.size(); g++) {
						allGroupsS.add(result.elementAt(g).getGroupName());
						CheckBox temp = new CheckBox(allGroupsS.elementAt(g));
						temp.addClickHandler(new GroupCheckBoxClickHandler(temp));
						selectSpecificGroupPanel.add(temp);
					}
				}
			}
		}
		
		class SelectUserGroupHandler implements ClickHandler {
			RadioButton radioButton = null;

			public SelectUserGroupHandler(RadioButton rb) {
				this.radioButton = rb;
			}

			public void onClick(ClickEvent event) {
				if (this.radioButton == selectOneGroupBtn) {
					sSGSP.remove(selectSpecificGroupPanel);

					choosenGroupS.clear();
					choosenGroupS.add(group.getGroupName());
				}
				if (this.radioButton == selectSpecGroupBtn) {

					choosenGroupS.clear();
					sSGSP.add(selectSpecificGroupPanel);
				}else if (this.radioButton == selectAllGroupsBtn) {
					sSGSP.remove(selectSpecificGroupPanel);

					choosenGroupS.clear();
					for (int i = 0; i < allUsers.size(); i++) {
						choosenGroupS.add(allGroups.elementAt(i).getGroupName());
					}
				}
			}
		}
		
		public class TimePickHandler implements ClickHandler {
			Button timePickBtn;

			public TimePickHandler(Button sB) {
				this.timePickBtn = sB;
			}

			@SuppressWarnings("deprecation")
			public void onClick(ClickEvent event) {
				Date today = new Date();
				today.setHours(1);
				today.setMinutes(0);
				today.setSeconds(0);

				if (this.timePickBtn == lastWeekBtn) {
					Date weekAgo = new Date();
					weekAgo.setHours(1);
					weekAgo.setMinutes(0);
					weekAgo.setSeconds(0);
					CalendarUtil.addDaysToDate(weekAgo, -7);

					if (weekAgo.before(lastImpossibleDate)) {
						weekAgo = new Date(119, 0, 1);
						Window.alert("Das Startdatum ist der 1. Januar 2019. Ihre Auswahl wurde angepasst.");
					}
					start.setValue(weekAgo);
					end.setValue(today);
				}
				if (this.timePickBtn == lastMonthBtn) {
					Date monthAgo = new Date();
					monthAgo.setHours(1);
					monthAgo.setMinutes(0);
					monthAgo.setSeconds(0);
					CalendarUtil.addMonthsToDate(monthAgo, -1);

					if (monthAgo.before(lastImpossibleDate)) {
						monthAgo = new Date(119, 0, 1);
						Window.alert("Das Startdatum ist der 1. Januar 2019. Ihre Auswahl wurde angepasst.");
					}
					start.setValue(monthAgo);
					end.setValue(today);
				}
				if (this.timePickBtn == last3DaysBtn) {
					Date yearAgo = new Date();
					yearAgo.setHours(1);
					yearAgo.setMinutes(0);
					yearAgo.setSeconds(0);
					CalendarUtil.addDaysToDate(yearAgo, -3);

					if (yearAgo.before(lastImpossibleDate)) {
						yearAgo = new Date(119, 0, 1);
						Window.alert("Das Startdatum ist der 1. Januar 2019. Ihre Auswahl wurde angepasst.");
					}
					start.setValue(yearAgo);
					end.setValue(today);
				}
			}
		}
		
			public class getReportClickHandler implements ClickHandler {
				@SuppressWarnings("deprecation")
				public void onClick(ClickEvent event) {

					cStartDate = start.getValue();
					cStartDate.setHours(1);

					cEndDate = end.getValue();
					cEndDate.setHours(1);

					if (choosenGroupS == null || cStartDate == null || cEndDate == null
							|| choosenReports == null) {
						Window.alert("Bitte alle Felder ausfüllen.");
						return;
					}

					if (choosenGroupS.size() == 0 || cStartDate == null || cEndDate == null
							|| choosenReports.size() == 0) {
						Window.alert("Bitte alle Felder ausfüllen.");
						return;
					}

					if (cStartDate.equals(cEndDate)) {
						Window.alert("Das Startdatum und Enddatum dürfen nicht das selbe sein.");
						return;
					}

					else if (choosenGroupS.size() != 0 && cStartDate != null && cEndDate != null
							&& choosenReports.size() != 0) {

						Timestamp cStartDateTS = new Timestamp(cStartDate.getTime());
						Timestamp cEndDateTS = new Timestamp(cEndDate.getTime());

						Date choosenStartDatePl1 = cStartDate;
						CalendarUtil.addDaysToDate(choosenStartDatePl1, 1);
						Timestamp choosenStartDatePl1TS = new Timestamp(choosenStartDatePl1.getDate());

						Date choosenEndDatePl1 = cEndDate;
						CalendarUtil.addDaysToDate(choosenEndDatePl1, 1);
						Timestamp choosenEndDatePl1TS = new Timestamp(choosenEndDatePl1.getDate());

						reportVerwaltung.getReport(choosenGroupS, cStartDateTS, cEndDateTS,
								choosenStartDatePl1TS, choosenEndDatePl1TS, choosenReports, user,
								new DisplayReportCallback());
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
							"Das Startdatum muss mindestens einen Tag vor dem Enddatum liegen. Die Auswahl wurde angepasst.");
					return;
				}

				if (start.getValue().after(today)) {

					start.setValue(daybeforetoday);
					Window.alert(
							"Das späteste mögliche Startdatum ist der gestrige Tag. Ihre Auswahl wurde angepasst.");
					return;
				}

				if (start.getValue().before(lastImpossibleDate)) {
					start.setValue(firstPossDate);
					Window.alert(
							"Das frühestmögliche Startdatum ist der 1. Januar 2019. Ihre Auswahl wurde angepasst.");
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

					Window.alert("Das späteste Enddatum ist der heutige Tag. Ihre Auswahl wurde angepasst.");
					return;
				}

				Date dateafterchosenstartDateCOPY = start.getValue();
				CalendarUtil.addDaysToDate(dateafterchosenstartDateCOPY, 1);
			
				if (end.getValue().before(dateafterchosenstartDateCOPY)) {
					end.setValue(dateafterchosenstartDateCOPY);

					Window.alert("Das Enddatum muss hinter dem Startdatum liegen. Ihre Auswahl wurde angepasst.");
					return;
				}

				if (end.getValue().before(secondPossibleDate)) {
					end.setValue(secondPossibleDate);

					Window.alert("Das frühestmögliche Enddatum ist der 2. Januar 2019. Ihre Auswahl wurde angepasst");
					return;
				}
			}	
		}
		class DisplayReportCallback implements AsyncCallback<CompositeReport> {

			public void onFailure(Throwable caught) {
				}

			public void onSuccess(CompositeReport result) {

				HTMLReportWriter htmlWriter = new HTMLReportWriter();
				htmlWriter.process(result);
				HTML html = new HTML(htmlWriter.getReportText());
				rForm.setReport(html);
			}
		}
	}

