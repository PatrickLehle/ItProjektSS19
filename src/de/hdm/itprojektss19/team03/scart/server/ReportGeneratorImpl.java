package de.hdm.itprojektss19.team03.scart.server;

import java.util.*;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl;
import de.hdm.itprojektss19.team03.scart.shared.EditorService;
import de.hdm.itprojektss19.team03.scart.shared.ReportGenerator;
import de.hdm.itprojektss19.team03.scart.shared.bo.Article;
import de.hdm.itprojektss19.team03.scart.shared.bo.Retailer;
import de.hdm.itprojektss19.team03.scart.shared.report.ArticleDateReport;
import de.hdm.itprojektss19.team03.scart.shared.report.ArticleDateRetailerReport;
import de.hdm.itprojektss19.team03.scart.shared.report.ArticleReport;
import de.hdm.itprojektss19.team03.scart.shared.report.ArticleRetailerReport;

/**
 * 
 * @author bastiantilk, PatrickLehle
 *
 */
public class ReportGeneratorImpl extends RemoteServiceServlet implements
ReportGenerator{
	
	private static final long serialVersionUID = 1L;
	private EditorService editorService = null;
	
	/**
	 * *************************************************************************
	 * INITIALISIERUNG
	 * *************************************************************************
	 */

	public ReportGeneratorImpl() throws IllegalArgumentException{
		
		EditorServiceImpl impl = new EditorServiceImpl();
		impl.init();
		this.editorService = impl;
	}
	protected EditorService getEditorService() {
		return this.editorService;
	}
	
	public void init() {
		
	}
	
//	public Vector<Article> getStatistic(int UID) {
//		if (this.getEditorService() == null) {
//			return null;
//		}
//		return null;
//	}
//	
//	public Vector<Article> getStatistic(int UID, Date start, Date end) {
//		if (this.getEditorService() == null) {
//			return null;
//		}
//		return null;
//	} 
//	
//	public Vector<Article> getStatistic(int UID, Retailer retailer) {
//		if (this.getEditorService() == null) {
//			return null;
//		}
//		return null;
//	}
//	
// Vector<Article> getStatistic(int UID, Date start, Date end, Retailer retailer) {
//		if (this.getEditorService() == null) {
//			return null;
//		}
//		return null;
//	}
	@Override
	public ArticleReport createStatisticA(int UID, Article a) throws IllegalArgumentException {
		if (this.getEditorService() == null) {
			return null;
		}
		return null;
	}
	@Override
	public ArticleDateReport createStatisticAD(int UID, Article a, Date start, Date end) {
	if (this.getEditorService() == null) {
		return null;
	}
	return null;
	}
	@Override
	public ArticleRetailerReport createStatisticAR(int UID, Article a, Retailer r) throws IllegalArgumentException {
		if (this.getEditorService() == null) {
			return null;
		}
		return null;
	}
	@Override
	public ArticleDateRetailerReport createStatisticADR(int UID, Article a, Date start, Date end, Retailer r)
			throws IllegalArgumentException {
		if (this.getEditorService() == null) {
			return null;
		}
		return null;
	} 
}
