package de.hdm.itprojektss19.team03.scart.shared;

import java.util.Date;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.itprojektss19.team03.scart.shared.bo.Article;
import de.hdm.itprojektss19.team03.scart.shared.bo.Retailer;
import de.hdm.itprojektss19.team03.scart.shared.report.ArticleDateReport;
import de.hdm.itprojektss19.team03.scart.shared.report.ArticleDateRetailerReport;
import de.hdm.itprojektss19.team03.scart.shared.report.ArticleReport;
import de.hdm.itprojektss19.team03.scart.shared.report.ArticleRetailerReport;


/**
 * Asynchrones Interfaces zu <code>ReportGenerator</code>
 *
 */
public interface ReportGeneratorAsync {

	
	void init(AsyncCallback<Void> callback);

	void createStatisticA(int UID, Article a, AsyncCallback<ArticleReport> callback);
	
	void createStatisticAD(int UID, Article a, Date start, Date end, AsyncCallback<ArticleDateReport> callback);
	
	void createStatisticAR(int UID, Article a, Retailer r, AsyncCallback<ArticleRetailerReport> callback);
	
	void createStatisticADR(int UID, Article a, Date start, Date end, Retailer r, AsyncCallback<ArticleDateRetailerReport> callback);


	
	
	


	
	
}
