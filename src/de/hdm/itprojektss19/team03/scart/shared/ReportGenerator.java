package de.hdm.itprojektss19.team03.scart.shared;

import java.sql.Timestamp;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.hdm.itprojektss19.team03.scart.shared.bo.User;
import de.hdm.itprojektss19.team03.scart.shared.report.ArticleDateReport;
import de.hdm.itprojektss19.team03.scart.shared.report.ArticleDateRetailerReport;
import de.hdm.itprojektss19.team03.scart.shared.report.ArticleReport;
import de.hdm.itprojektss19.team03.scart.shared.report.ArticleRetailerReport;



@RemoteServiceRelativePath("reportgenerator")
public interface ReportGenerator extends RemoteService {

	void init();
	
	public ArticleReport createStatisticA(User u) throws IllegalArgumentException;
	
	public ArticleDateReport createStatisticAD(User user, Timestamp choosenStartDate, Timestamp choosenEndDate, Timestamp choosenStartDatePl1TS, Timestamp choosenEndDatePl1TS) throws IllegalArgumentException;
	
	public ArticleRetailerReport createStatisticAR(User user, int retailerId) throws IllegalArgumentException;
	
	public ArticleDateRetailerReport createStatisticADR(User user, Timestamp choosenStartDate,
			Timestamp choosenEndDate, Timestamp choosenStartDatePl1TS, Timestamp choosenEndDatePl1TS) throws IllegalArgumentException;
}