package de.hdm.itprojektss19.team03.scart.server;

import java.util.*;


import com.google.gwt.user.server.rpc.RemoteServiceServlet;


import de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl;
import de.hdm.itprojektss19.team03.scart.server.db.ArticleMapper;
import de.hdm.itprojektss19.team03.scart.server.db.GroceryListMapper;
import de.hdm.itprojektss19.team03.scart.server.db.GroupMapper;
import de.hdm.itprojektss19.team03.scart.server.db.RetailerMapper;
import de.hdm.itprojektss19.team03.scart.server.db.UserMapper;
import de.hdm.itprojektss19.team03.scart.shared.EditorService;
import de.hdm.itprojektss19.team03.scart.shared.ReportGenerator;
import de.hdm.itprojektss19.team03.scart.shared.bo.Article;
import de.hdm.itprojektss19.team03.scart.shared.bo.Group;
import de.hdm.itprojektss19.team03.scart.shared.bo.Retailer;
import de.hdm.itprojektss19.team03.scart.shared.bo.User;
import de.hdm.itprojektss19.team03.scart.shared.report.ArticleDateReport;
import de.hdm.itprojektss19.team03.scart.shared.report.ArticleDateRetailerReport;
import de.hdm.itprojektss19.team03.scart.shared.report.ArticleReport;
import de.hdm.itprojektss19.team03.scart.shared.report.ArticleRetailerReport;
import de.hdm.itprojektss19.team03.scart.shared.report.Column;
import de.hdm.itprojektss19.team03.scart.shared.report.Row;

/**
 * 
 * @author bastiantilk, PatrickLehle
 *
 */
public class ReportGeneratorImpl extends RemoteServiceServlet implements
ReportGenerator{

//INITIALIZATION========================================================
	private static final long serialVersionUID = 1L;
	private EditorService editorService = null;
	
	/**
	 * Die Mapperklasse wird referenziert, die die <code>User</code>-mit der
	 * Datenbank vergleicht.
	 */
	private UserMapper uMapper = null;

	/**
	 * Die Mapperklasse wird referenziert, die die <code>Group</code>-mit der
	 * Datenbank vergleicht.
	 */
	private GroupMapper gMapper = null;

	/**
	 * Die Mapperklasse wird referenziert, die die <code>Retailer</code>-mit der
	 * Datenbank vergleicht.
	 */
	private RetailerMapper rMapper = null;

	/**
	 * Die Mapperklasse wird referenziert, die die <code>ArticleMapper</code> mit
	 * der Datenbank vergleicht.
	 */
	private ArticleMapper aMapper = null;

	/**
	 * Die Mapperklasse wird referenziert, die die <code>GroceryList</code> mit der
	 * Datenbank vergleicht.
	 */
	private GroceryListMapper glMapper = null;
	


//CONSTRUCTORS==========================================================
	
	public ReportGeneratorImpl() throws IllegalArgumentException{
		
	}
	
//METHODS===============================================================
	public void init() throws IllegalArgumentException{
		
		EditorServiceImpl impl = new EditorServiceImpl();
		impl.init();
		this.editorService = impl;
	}
	
	protected EditorService getEditorService() {
		return this.editorService;
	}
	
	
	public ArticleReport createStatisticA(User u) throws IllegalArgumentException {
		if (this.getEditorService() == null) {
			return null;
		}
		
		//Hier wird eine Instanz unseres Reports fuer Artikel angelegt.
		ArticleReport report = new ArticleReport();
		
		report.setTitle("Alle Artikel von " + " " + this.getGroupByUser(u) + " ");
		
		Row head = new Row();
		head.addColumn(new Column(" "));
		return report;
	
	}
	
	public ArticleDateReport createStatisticAD(int UID, Article a, Date start, Date end) {
	if (this.getEditorService() == null) {
		return null;
	}
	return null;
	}
	
	public ArticleRetailerReport createStatisticAR(int UID, Article a, Retailer r) throws IllegalArgumentException {
		if (this.getEditorService() == null) {
			return null;
		}
		return null;
	}
	
	public ArticleDateRetailerReport createStatisticADR(int UID, Article a, Date start, Date end, Retailer r)
			throws IllegalArgumentException {
		if (this.getEditorService() == null) {
			return null;
		}
		return null;
	} 
	
//HELPING-METHODS=========================================================
	
	private String getGroupByUser(User u) throws IllegalArgumentException {

		String name = "";
		User user = editorService.getUserByGMail(u.getEmail());
		name = user.getEmail();

		return name;
	}
}
