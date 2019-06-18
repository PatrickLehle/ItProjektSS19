package de.hdm.itprojektss19.team03.scart.shared;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Vector;

import com.google.gwt.user.client.rpc.AsyncCallback;


import de.hdm.itprojektss19.team03.scart.shared.bo.Article;
import de.hdm.itprojektss19.team03.scart.shared.bo.Retailer;
import de.hdm.itprojektss19.team03.scart.shared.bo.User;
import de.hdm.itprojektss19.team03.scart.shared.report.ArticleDateReport;
import de.hdm.itprojektss19.team03.scart.shared.report.ArticleDateRetailerReport;
import de.hdm.itprojektss19.team03.scart.shared.report.ArticleReport;
import de.hdm.itprojektss19.team03.scart.shared.report.ArticleRetailerReport;
import de.hdm.itprojektss19.team03.scart.shared.report.CompositeReport;
import de.hdm.itprojektss19.team03.scart.shared.report.SimpleReport;


/**
 * Asynchrones Interfaces zu <code>ReportGenerator</code>
 *
 */
public interface ReportGeneratorAsync {

	
	void init(AsyncCallback<Void> callback);
	
//	void getReport(Vector<String> choosenUsers, 
//			Timestamp choosenStartDate, Timestamp choosenEndDate, 
//			Timestamp choosenStartDatePl1TS, Timestamp choosenEndDatePl1TS,
//			Vector<SimpleReport> choosenReports, User clientUser, 
//			AsyncCallback<CompositeReport> callback); 

	void createStatisticA(User u, AsyncCallback<ArticleReport> callback);
	
	void createStatisticAD(int UID, Article a, Date start, Date end, AsyncCallback<ArticleDateReport> callback);
	
	void createStatisticAR(int UID, Article a, Retailer r, AsyncCallback<ArticleRetailerReport> callback);
	
	void createStatisticADR(Vector<String> choosenUsers, Timestamp choosenStartDate, Timestamp choosenEndDate, 
			Timestamp choosenStartDatePl1TS, Timestamp choosenEndDatePl1TS,
			Vector<SimpleReport> choosenReports, User clientUser,AsyncCallback<ArticleDateRetailerReport> callback);


	
	
	


	
	
}
