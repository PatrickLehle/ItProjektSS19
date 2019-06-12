package de.hdm.itprojektss19.team03.scart.server;

import java.sql.Timestamp;
import java.util.*;


import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl;
import de.hdm.itprojektss19.team03.scart.server.db.ArticleMapper;
import de.hdm.itprojektss19.team03.scart.server.db.GroceryListArticleMapper;
import de.hdm.itprojektss19.team03.scart.server.db.GroceryListMapper;
import de.hdm.itprojektss19.team03.scart.server.db.GroupMapper;
import de.hdm.itprojektss19.team03.scart.server.db.RetailerMapper;
import de.hdm.itprojektss19.team03.scart.server.db.UserMapper;
import de.hdm.itprojektss19.team03.scart.shared.EditorService;
import de.hdm.itprojektss19.team03.scart.shared.ReportGenerator;
import de.hdm.itprojektss19.team03.scart.shared.bo.Article;
import de.hdm.itprojektss19.team03.scart.shared.bo.GroceryListArticle;
import de.hdm.itprojektss19.team03.scart.shared.bo.Group;
import de.hdm.itprojektss19.team03.scart.shared.bo.Retailer;
import de.hdm.itprojektss19.team03.scart.shared.bo.User;
import de.hdm.itprojektss19.team03.scart.shared.report.ArticleDateReport;
import de.hdm.itprojektss19.team03.scart.shared.report.ArticleDateRetailerReport;
import de.hdm.itprojektss19.team03.scart.shared.report.ArticleReport;
import de.hdm.itprojektss19.team03.scart.shared.report.ArticleRetailerReport;
import de.hdm.itprojektss19.team03.scart.shared.report.Column;
import de.hdm.itprojektss19.team03.scart.shared.report.CompositeParagraph;
import de.hdm.itprojektss19.team03.scart.shared.report.CompositeReport;
import de.hdm.itprojektss19.team03.scart.shared.report.Row;
import de.hdm.itprojektss19.team03.scart.shared.report.SimpleParagraph;
import de.hdm.itprojektss19.team03.scart.shared.report.SimpleReport;

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
		
		this.aMapper = ArticleMapper.articleMapper();
		this.uMapper = UserMapper.userMapper();
		this.glMapper = GroceryListMapper.groceryListMapper();
		this.rMapper = RetailerMapper.retailerMapper();
		this.gMapper = GroupMapper.groupMapper();
		
		EditorServiceImpl impl = new EditorServiceImpl();
		impl.init();
		this.editorService = impl;
	}
	
	protected EditorService getEditorService() {
		return this.editorService;
	}
	
	@Override
	public ArticleReport createStatisticA(User u) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * Abruf des Reports der alle Artikel einer Gruppe generiert.
	 * @param a
	 * @return
	 * @throws IllegalArgumentException
	 */
	public ArticleReport createStatisticA(User u, Group g) throws IllegalArgumentException {
		if (this.getEditorService() == null) {
			return null;
		}
		
		//Hier wird eine Instanz unseres Reports fuer Artikel angelegt.
		ArticleReport report = new ArticleReport();
		
		
		//Titel und Erstellungsdatum wird hier festgelegt
		report.setTitle("Alle Artikel von " + " " + this.getUserByGMail(u)+ " ");
		report.setCreated(new Date());
		
		//Kopfzeile unseres Reports. Ueberschriften der einzelnen Spalten
		Row head = new Row();
		head.addColumn(new Column("Artikel"));
		head.addColumn(new Column("Menge"));
		head.addColumn(new Column("Einzelhändler"));
		head.addColumn(new Column("Erstellungsdatum"));
		head.addColumn(new Column("Modifikationsdatum"));
		report.addRow(head);

		Vector<Article> allArticle = ArticleMapper.articleMapper().findAll();
		allArticle.addAll(this.getEditorService().findAllArticle());
		
		for( Article a : allArticle) {
			Row articleRow = new Row();
			
			articleRow.addColumn(new Column(String.valueOf(a.getId())));
			articleRow.addColumn(new Column(String.valueOf(this.editorService.findAllArticle())));
		
			report.addRow(articleRow);
		}
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
	
	@Override
	public CompositeReport getReport(Vector<String> choosenUsers, Timestamp choosenStartDate, Timestamp choosenEndDate,
			Timestamp choosenStartDatePl1TS, Timestamp choosenEndDatePl1TS, Vector<SimpleReport> choosenReports,
			User clientUser) throws IllegalArgumentException {
		//TO-DO FOR TOMORROW
		return null;
	}
	
//HELPING-METHODS=========================================================
	
	private String getUserByGMail(User u) throws IllegalArgumentException {

//		Group g = editorService.
		String name = "";
		User user = editorService.getUserByGMail(u.getEmail());
		name = user.getEmail();

		return name;
	}
	private String getGroupByUser(User u) throws IllegalArgumentException {

		String name = "";
		User user = editorService.getUserByGMail(u.getEmail());
		name = user.getEmail();

		return name;
	}


}
