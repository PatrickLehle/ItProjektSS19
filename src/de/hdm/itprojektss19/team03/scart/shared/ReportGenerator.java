package de.hdm.itprojektss19.team03.scart.shared;

import java.sql.Timestamp;
import java.util.Vector;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.hdm.itprojektss19.team03.scart.shared.bo.Group;
import de.hdm.itprojektss19.team03.scart.shared.bo.Retailer;
import de.hdm.itprojektss19.team03.scart.shared.bo.User;
import de.hdm.itprojektss19.team03.scart.shared.report.ArticleDateReport;
import de.hdm.itprojektss19.team03.scart.shared.report.ArticleDateRetailerReport;
import de.hdm.itprojektss19.team03.scart.shared.report.ArticleReport;
import de.hdm.itprojektss19.team03.scart.shared.report.ArticleRetailerReport;



@RemoteServiceRelativePath("reportgenerator")
public interface ReportGenerator extends RemoteService {

	void init();
	
	public ArticleReport createStatisticA(User u, Vector<Group> g) throws IllegalArgumentException;
	
	public ArticleDateReport createStatisticAD(User user, Vector<Group> groups, Timestamp choosenStartDate, Timestamp choosenEndDate, Timestamp choosenStartDatePl1TS, Timestamp choosenEndDatePl1TS) throws IllegalArgumentException;
	
	public ArticleRetailerReport createStatisticAR(User user, Vector<Group> groups, Vector<Retailer> retailers) throws IllegalArgumentException;
	
	public ArticleDateRetailerReport createStatisticADR(User user, Vector<Group> groups, Vector<Retailer> retailers, Timestamp choosenStartDate, Timestamp choosenEndDate, Timestamp choosenStartDatePl1TS, Timestamp choosenEndDatePl1TS) throws IllegalArgumentException;
}