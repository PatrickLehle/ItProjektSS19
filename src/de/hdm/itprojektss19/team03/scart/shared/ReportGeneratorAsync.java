package de.hdm.itprojektss19.team03.scart.shared;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Vector;

import com.google.gwt.user.client.rpc.AsyncCallback;


import de.hdm.itprojektss19.team03.scart.shared.bo.Article;
import de.hdm.itprojektss19.team03.scart.shared.bo.Group;
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

	void createStatisticA(User u, Vector<Group> g, AsyncCallback<ArticleReport> callback);
	
	void createStatisticAD(User user, Vector<Group> groups, Timestamp choosenStartDate, Timestamp choosenEndDate, Timestamp choosenStartDatePl1TS, Timestamp choosenEndDatePl1TS, AsyncCallback<ArticleDateReport> callback);
	
	void createStatisticAR(User user, Vector<Group> g,Vector<Retailer> r, AsyncCallback<ArticleRetailerReport> callback);
	
	void createStatisticADR(User user, Vector<Group> groups, Vector<Retailer> retailers, Timestamp choosenStartDate, Timestamp choosenEndDate, Timestamp choosenStartDatePl1TS, Timestamp choosenEndDatePl1TS, AsyncCallback<ArticleDateRetailerReport> callback);


	
	
	


	
	
}
