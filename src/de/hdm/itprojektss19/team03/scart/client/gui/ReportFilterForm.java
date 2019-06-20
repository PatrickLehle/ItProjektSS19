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
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.CalendarUtil;
import com.google.gwt.user.datepicker.client.DateBox;

import de.hdm.itprojektss19.team03.scart.client.ClientsideSettings;
import de.hdm.itprojektss19.team03.scart.shared.EditorServiceAsync;
import de.hdm.itprojektss19.team03.scart.shared.LoginServiceAsync;
import de.hdm.itprojektss19.team03.scart.shared.ReportGeneratorAsync;
import de.hdm.itprojektss19.team03.scart.shared.bo.Group;
import de.hdm.itprojektss19.team03.scart.shared.bo.Retailer;
import de.hdm.itprojektss19.team03.scart.shared.bo.User;
import de.hdm.itprojektss19.team03.scart.shared.report.ArticleDateReport;
import de.hdm.itprojektss19.team03.scart.shared.report.ArticleDateRetailerReport;
import de.hdm.itprojektss19.team03.scart.shared.report.ArticleReport;
import de.hdm.itprojektss19.team03.scart.shared.report.ArticleRetailerReport;
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
	EditorServiceAsync editorVerwaltung = ClientsideSettings.getEditor();
	ReportGeneratorAsync reportVerwaltung = ClientsideSettings.getReportGeneratorService();

	Group group = new Group();
	User user = new User();
	Retailer retailer = new Retailer();
	ReportForm rForm = new ReportForm();
	Vector<Group> allGroups = null;
	Vector<Retailer> allRetailer = null;
	Vector<User> allUsers = null;

	Vector<String> allGroupsS = new Vector<String>();
	Vector<String> allRetailerS = new Vector<String>();
	Vector<String> choosenGroupsS = new Vector<String>();
	Vector<String> choosenRetailerS = new Vector<String>();

	Vector<ArticleReport> articleReport = new Vector<ArticleReport>();
	Vector<ArticleDateReport> articleDateReport = new Vector<ArticleDateReport>();
	Vector<ArticleDateRetailerReport> articleDateRetailerReport = new Vector<ArticleDateRetailerReport>();

	Vector<SimpleReport> choosenReports = new Vector<SimpleReport>();

	Date cStartDate = null;
	Date cEndDate = null;

	Date today;

	@SuppressWarnings("deprecation")
	Date firstPossDate = new Date(119, 0, 1);

	@SuppressWarnings("deprecation")
	Date secondPossibleDate = new Date(119, 0, 2);

	@SuppressWarnings("deprecation")
	Date lastImpossibleDate = new Date(118, 11, 31);

	@SuppressWarnings("deprecation")
	Date dateAfterChosenStartDate = new Date(119, 0, 2);

//CONSTRUCTORS=====================================================================

	public ReportFilterForm() {
	}

	public ReportFilterForm(final User cUser) {
		this.user = cUser;
//		this.rForm = cReport;
	}

//PANELS-LABELS-BUTTONS=============================================================	

	// GROUPS-FILTER=======================
	VerticalPanel pickGroup = new VerticalPanel();
	Label chooseGroupLbl = new Label("Gruppe auswählen:");
	VerticalPanel checkBoxesGroup = new VerticalPanel();

	// TIME-FRAME-PICKER===================
	VerticalPanel pickPeriod = new VerticalPanel();
	ToggleButton disabledPeriodBtn = new ToggleButton("Filter Deaktivieren", "Filter Aktivieren");
	Label cPeriodLbl = new Label("Zeitraum auswählen:");
	HorizontalPanel startEndPnl = new HorizontalPanel();

	DateBox start = new DateBox();
	Label dash = new Label(" _ ");
	DateBox end = new DateBox();

	HorizontalPanel periodTimePickerH = new HorizontalPanel();
	Button lastWeekBtn = new Button("Letzte Woche");
	Button lastMonthBtn = new Button("Letzter Monat");

	// RETAILER-PICKER===========================
	VerticalPanel pickRetailer = new VerticalPanel();
	ToggleButton disabledRetailerBtn = new ToggleButton("Filter Deaktivieren", "Filter Aktivieren");
	Label retailerLbl = new Label("Retailer auswählen:");
	VerticalPanel checkBoxesRetailer = new VerticalPanel();
	
	ScrollPanel scroll = new ScrollPanel();

	// REPORT-BUTTON=============================
	VerticalPanel reportBtnVP = new VerticalPanel();
	Button getReportBtn = new Button("Report generieren");

//METHODS===========================================================================

	@SuppressWarnings("deprecation")
	public void onLoad() {
		super.onLoad();

		lastImpossibleDate.setHours(23);
		lastImpossibleDate.setMinutes(59);
		lastImpossibleDate.setSeconds(59);

		// STYLE-NAMES========================
		reportBtnVP.addStyleName("reportBtnVP");
		lastWeekBtn.addStyleName("LastWeekBtn");
		lastMonthBtn.addStyleName("LastMonthBtn");
		pickGroup.addStyleName("PickGroupVPanel");
		chooseGroupLbl.addStyleName("ChooseGroupLabel");
		disabledPeriodBtn.addStyleName("DisabledPeriodBtn");
		periodTimePickerH.addStyleName("PeriodTimePickerH");
		pickPeriod.addStyleName("PickPeriodVPanel");
		cPeriodLbl.addStyleName("periodLabel");
		start.addStyleName("StartPanel");
		end.addStyleName("EndPanel");
		checkBoxesGroup.addStyleName("CheckBoxesGroupVPanel");
		checkBoxesRetailer.addStyleName("CheckBoxesRetailerVP");
		disabledRetailerBtn.addStyleName("DisabledRetailerBtn");
		pickRetailer.addStyleName("RetailerVP");
		retailerLbl.addStyleName("RetailerLabel");
		getReportBtn.addStyleName("GetReportBtn");

		// GRUPPE==========================
		pickGroup.add(chooseGroupLbl);
		pickGroup.add(checkBoxesGroup);

		// ZEITRAUM========================
		pickPeriod.add(disabledPeriodBtn);
		pickPeriod.add(cPeriodLbl);
		pickPeriod.add(startEndPnl);

		startEndPnl.add(start);
		startEndPnl.add(dash);
		startEndPnl.add(end);

		periodTimePickerH.add(lastWeekBtn);
		periodTimePickerH.add(lastMonthBtn);

		pickPeriod.add(periodTimePickerH);

		// RETAILER=========================
		pickRetailer.add(disabledRetailerBtn);
		pickRetailer.add(retailerLbl);
		pickRetailer.add(checkBoxesRetailer);

		reportBtnVP.add(getReportBtn);

		// ROOTPANELS===================
		RootPanel.get("navigator").add(pickGroup);
		RootPanel.get("navigator").add(pickPeriod);
		RootPanel.get("navigator").add(pickRetailer);

		// ROOT-BUTTONS=================
		RootPanel.get("navigator").add(reportBtnVP);
//		RootPanel.get("navigator").add(getReportBtn);

//END=========================================================================================================

		editorVerwaltung.findAllGroups(new AllGroupsCallback());
		editorVerwaltung.findAllRetailer(new AllRetailersCallback());

		// TIMEFRAME-CHECK-FOR-CHANGE===================
		Timer refresh = new Timer() {
			public void run() {
				editorVerwaltung.findAllGroups(new AllGroupsCallback());
				editorVerwaltung.findAllRetailer(new AllRetailersCallback());
			}
		};
		refresh.scheduleRepeating(1000);

		today = new Date();
		today.setHours(1);
		today.setMinutes(0);
		today.setSeconds(0);

		// CHOOSEN-PERIOD====================
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

		start.setValue(firstPossDate);
		end.setValue(today);

		// CLICKHANDLER====================
		disabledPeriodBtn.addClickHandler(new DisabledPeriodHandler());
		disabledRetailerBtn.addClickHandler(new DisabledRetailerHandler());

		lastWeekBtn.addClickHandler(new TimePickHandler(lastWeekBtn));
		lastMonthBtn.addClickHandler(new TimePickHandler(lastMonthBtn));

		// GET-REPORT======================
		getReportBtn.addClickHandler(new getReportClickHandler());
	}

	class AllGroupsCallback implements AsyncCallback<Vector<Group>> {

		public void onFailure(Throwable caught) {
		}

		public void onSuccess(Vector<Group> result) {
			allGroups = result;

			if (allGroupsS.size() != result.size()) {
				allGroupsS.clear();

				for (int g = 0; g < result.size(); g++) {
					allGroupsS.add(result.elementAt(g).getGroupName());
					CheckBox temp = new CheckBox(allGroupsS.elementAt(g));
					temp.addClickHandler(new GroupCheckBoxClickHandler(temp));
					checkBoxesGroup.add(temp);
				}
			}
		}
	}

	class DisabledPeriodHandler implements ClickHandler {

		public void onClick(ClickEvent event) {
			if (disabledPeriodBtn.isDown() == true) {
				pickPeriod.setStyleName("DisabledPeriodVPonClick");
			} else if (disabledPeriodBtn.isDown() == false) {
				pickPeriod.setStyleName("PickPeriodVPanel");
			}
		}
	}

	class DisabledRetailerHandler implements ClickHandler {

		public void onClick(ClickEvent event) {
			if (disabledRetailerBtn.isDown() == true) {
				pickRetailer.setStyleName("DisabledRetailerVPonClick");
			} else if (disabledRetailerBtn.isDown() == false) {
				pickRetailer.setStyleName("RetailerVP");
			}
		}
	}

	class AllRetailersCallback implements AsyncCallback<Vector<Retailer>> {

		public void onFailure(Throwable caught) {
		}

		public void onSuccess(Vector<Retailer> result) {
			allRetailer = result;

			if (allRetailerS.size() != result.size()) {
				allRetailerS.clear();

				for (int g = 0; g < result.size(); g++) {
					allRetailerS.add(result.elementAt(g).getRetailerName());
					CheckBox temp = new CheckBox(allRetailerS.elementAt(g));
					temp.addClickHandler(new RetailerCheckBoxClickHandler(temp));
					checkBoxesRetailer.add(temp);
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
		}
	}

	public class getReportClickHandler implements ClickHandler {

		@SuppressWarnings("deprecation")
				public void onClick(ClickEvent event) {

					cStartDate = start.getValue();
					cStartDate.setHours(1);

					cEndDate = end.getValue();
					cEndDate.setHours(1);
	
					//Nur Gruppen Report (Check)
					if(choosenGroupsS.size() != 0 && disabledPeriodBtn.isDown() == true && disabledRetailerBtn.isDown() == true) {

						reportVerwaltung.createStatisticA(user, new ArticleReportCallback());
						
						RootPanel.get("content").add(scroll);
						Window.alert("did it work?");
						
					}

					//Gruppe mit Zeitraum Report (Check)
					if (choosenGroupsS.size() != 0 && disabledPeriodBtn.isDown() == false && cStartDate != null  && cEndDate != null && disabledRetailerBtn.isDown() == true) {
						Window.alert("Report von Gruppe mit Zeitraum wurde erstellt");
						
						Timestamp cStartDateTS = new Timestamp(cStartDate.getTime());
						Timestamp cEndDateTS = new Timestamp(cEndDate.getTime());

						Date choosenStartDatePl1 = cStartDate;
						CalendarUtil.addDaysToDate(choosenStartDatePl1, 1);
						Timestamp choosenStartDatePl1TS = new Timestamp(choosenStartDatePl1.getDate());

						Date choosenEndDatePl1 = cEndDate;
						CalendarUtil.addDaysToDate(choosenEndDatePl1, 1);
						Timestamp choosenEndDatePl1TS = new Timestamp(choosenEndDatePl1.getDate());

						return;
					}

					//Gruppe mit Zeitraum und Retailer (Check)
					if (choosenGroupsS.size() != 0 && disabledPeriodBtn.isDown() == false && cStartDate != null && cEndDate != null && choosenRetailerS.size() != 0 && disabledRetailerBtn.isDown() == false ) {
						Window.alert("Report von der Gruppe mit Zeitraum und des Retailers wurde erstellt");
						
						Timestamp cStartDateTS = new Timestamp(cStartDate.getTime());
						Timestamp cEndDateTS = new Timestamp(cEndDate.getTime());

						Date choosenStartDatePl1 = cStartDate;
						CalendarUtil.addDaysToDate(choosenStartDatePl1, 1);
						Timestamp choosenStartDatePl1TS = new Timestamp(choosenStartDatePl1.getDate());

						Date choosenEndDatePl1 = cEndDate;
						CalendarUtil.addDaysToDate(choosenEndDatePl1, 1);
						Timestamp choosenEndDatePl1TS = new Timestamp(choosenEndDatePl1.getDate());

						return;
						//TODO
					}
					
					//Gruppe und Retailer (Check)
					if (choosenGroupsS.size() != 0 && disabledPeriodBtn.isDown() == true && choosenRetailerS.size() != 0 && disabledRetailerBtn.isDown() == false) {
						Window.alert("Report von der Gruppe und des Retailers wurde erstellt");
						//TODO
						return;
					}

					if (cStartDate.equals(cEndDate)) {
						Window.alert("Das Startdatum und Enddatum dürfen nicht das selbe sein.");
						return;
						//TODO
					}
					
					if (choosenGroupsS.size() == 0 || cStartDate == null || cEndDate == null || choosenRetailerS.size() == 0 && disabledRetailerBtn.isDown() == false ) {
						Window.alert("Bitte füllen Sie alle Felder aus.");
						return;
						//TODO
					}
		
				}
		}
	
	

	class GroupCheckBoxClickHandler implements ClickHandler {
		CheckBox checkBox = null;

		public GroupCheckBoxClickHandler(CheckBox cB) {
			this.checkBox = cB;
		}

		public void onClick(ClickEvent event) {
			if (choosenGroupsS.contains(checkBox.getText())) {

				for (int i = 0; i < choosenGroupsS.size(); i++) {
					if (choosenGroupsS.elementAt(i) == checkBox.getText()) {
						choosenGroupsS.removeElementAt(i);
					}
				}
			} else if (choosenGroupsS.contains(checkBox.getText()) == false) {
				choosenGroupsS.add(checkBox.getText());
			}
		}
	}

	class RetailerCheckBoxClickHandler implements ClickHandler {
		CheckBox checkBox = null;

		public RetailerCheckBoxClickHandler(CheckBox cB) {
			this.checkBox = cB;
		}

		public void onClick(ClickEvent event) {
			if (choosenRetailerS.contains(checkBox.getText())) {

				for (int i = 0; i < choosenRetailerS.size(); i++) {
					if (choosenRetailerS.elementAt(i) == checkBox.getText()) {
						choosenRetailerS.removeElementAt(i);
					}
				}
			} else if (choosenRetailerS.contains(checkBox.getText()) == false) {
				choosenRetailerS.add(checkBox.getText());
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
				Window.alert("Das späteste mögliche Startdatum ist der gestrige Tag. Ihre Auswahl wurde angepasst.");
				return;
			}

			if (start.getValue().before(lastImpossibleDate)) {
				start.setValue(firstPossDate);
				Window.alert("Das frühestmögliche Startdatum ist der 1. Januar 2019. Ihre Auswahl wurde angepasst.");
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
//ARTICLE-DATE-RETAILER-CALLBACK===================================================================
	
	class ArticleDateRetailerReportCallback implements AsyncCallback<ArticleDateRetailerReport> {

		public void onFailure(Throwable caught) {
		}

		public void onSuccess(ArticleDateRetailerReport result) {

			HTMLReportWriter htmlWriter = new HTMLReportWriter();
			htmlWriter.process(result);
			HTML html = new HTML(htmlWriter.getReportText());
			rForm.setReport(html);
		}
	}
//ARTICLE-RETAILER-CALLBACK========================================================================
	
	class ArticleRetailerReportCallback implements AsyncCallback<ArticleRetailerReport> {

		public void onFailure(Throwable caught) {
		}

		public void onSuccess(ArticleRetailerReport result) {

			HTMLReportWriter htmlWriter = new HTMLReportWriter();
			htmlWriter.process(result);
			HTML html = new HTML(htmlWriter.getReportText());
			rForm.setReport(html);
		}
	}

//ARTICLE-DATE-CALLBACK============================================================================
	
	class ArticleDateReportCallback implements AsyncCallback<ArticleDateReport> {

		public void onFailure(Throwable caught) {
		}

		public void onSuccess(ArticleDateReport result) {

			HTMLReportWriter htmlWriter = new HTMLReportWriter();
			htmlWriter.process(result);
			HTML html = new HTML(htmlWriter.getReportText());
			rForm.setReport(html);
		}
	}

//ARTICLE-CALLBACK=================================================================================
	
	class ArticleReportCallback implements AsyncCallback<ArticleReport> {

		public void onFailure(Throwable caught) {
			Window.alert("didnt work");
		}

		public void onSuccess(ArticleReport result) {

			HTMLReportWriter htmlWriter = new HTMLReportWriter();
			htmlWriter.process(result);
			HTML html = new HTML(htmlWriter.getReportText());
			rForm.setReport(html);
		}
	}
}
