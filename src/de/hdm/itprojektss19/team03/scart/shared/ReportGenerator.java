package de.hdm.itprojektss19.team03.scart.shared;

import java.util.Date;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.hdm.itprojektss19.team03.scart.shared.bo.Article;
import de.hdm.itprojektss19.team03.scart.shared.bo.Group;
import de.hdm.itprojektss19.team03.scart.shared.bo.Retailer;
import de.hdm.itprojektss19.team03.scart.shared.bo.User;
import de.hdm.itprojektss19.team03.scart.shared.report.ArticleDateReport;
import de.hdm.itprojektss19.team03.scart.shared.report.ArticleDateRetailerReport;
import de.hdm.itprojektss19.team03.scart.shared.report.ArticleReport;
import de.hdm.itprojektss19.team03.scart.shared.report.ArticleRetailerReport;

@RemoteServiceRelativePath("reportgenerator")
public interface ReportGenerator extends RemoteService{
	
	void init();
	
	public ArticleReport createStatisticA(User u) throws IllegalArgumentException;
	
	public ArticleDateReport createStatisticAD(int UID,Article a, Date start, Date end) throws IllegalArgumentException;
	
	public ArticleRetailerReport createStatisticAR(int UID, Article a, Retailer r) throws IllegalArgumentException;
	
	public ArticleDateRetailerReport createStatisticADR(int UID, Article a, Date start, Date end, Retailer r) throws IllegalArgumentException;
	
}