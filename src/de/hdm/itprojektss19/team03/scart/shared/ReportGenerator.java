package de.hdm.itprojektss19.team03.scart.shared;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Vector;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;


import de.hdm.itprojektss19.team03.scart.shared.bo.Article;

import de.hdm.itprojektss19.team03.scart.shared.bo.Retailer;
import de.hdm.itprojektss19.team03.scart.shared.bo.User;
import de.hdm.itprojektss19.team03.scart.shared.report.ArticleDateReport;
import de.hdm.itprojektss19.team03.scart.shared.report.ArticleDateRetailerReport;
import de.hdm.itprojektss19.team03.scart.shared.report.ArticleReport;
import de.hdm.itprojektss19.team03.scart.shared.report.ArticleRetailerReport;
import de.hdm.itprojektss19.team03.scart.shared.report.CompositeReport;
import de.hdm.itprojektss19.team03.scart.shared.report.SimpleReport;


@RemoteServiceRelativePath("reportgenerator")
public interface ReportGenerator extends RemoteService{
	
	void init();
	
//	  public CompositeReport getReport(Vector<String> cUsers, 
//		  		Timestamp cStartDate, Timestamp cEndDate, 
//		  		Timestamp cStartDatePl1TS, Timestamp cEndDatePl1TS,
//		  		Vector<SimpleReport> choosenReports, User user) throws IllegalArgumentException;
	
	public ArticleReport createStatisticA(User u) throws IllegalArgumentException, SQLException;
	
	public ArticleDateReport createStatisticAD(int UID,Article a, Date start, Date end) throws IllegalArgumentException;
	
	public ArticleRetailerReport createStatisticAR(int UID, Article a, Retailer r) throws IllegalArgumentException;
	
	public ArticleDateRetailerReport createStatisticADR(Vector<String> choosenUsers, Timestamp choosenStartDate, Timestamp choosenEndDate, 
			Timestamp choosenStartDatePl1TS, Timestamp choosenEndDatePl1TS,Vector<SimpleReport> choosenReports, User clientUser) throws IllegalArgumentException;
	
}