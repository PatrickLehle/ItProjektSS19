package de.hdm.itprojektss19.team03.scart.server;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Vector;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.itprojektss19.team03.scart.server.db.ArticleMapper;
import de.hdm.itprojektss19.team03.scart.server.db.GroceryListArticleMapper;
import de.hdm.itprojektss19.team03.scart.server.db.GroceryListMapper;
import de.hdm.itprojektss19.team03.scart.server.db.GroupMapper;
import de.hdm.itprojektss19.team03.scart.server.db.GroupUserMapper;
import de.hdm.itprojektss19.team03.scart.server.db.RetailerMapper;
import de.hdm.itprojektss19.team03.scart.server.db.UserMapper;
import de.hdm.itprojektss19.team03.scart.shared.EditorService;
import de.hdm.itprojektss19.team03.scart.shared.LoginService;
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
 * Implementierung des serverseitigen RPC-Services fuer den Report
 * 
 * @author bastiantilk, PatrickLehle
 *
 */
public class ReportGeneratorImpl extends RemoteServiceServlet implements ReportGenerator {

	//INITIALIZATION========================================================
	private static final long serialVersionUID = 1L;
	private EditorService editorService = null;
	private LoginService loginInfo = null;

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
	
	/**
	 * Die Mapperklasse wird referenziert, die die <code>GroceryList</code> und <code>Article</code>mit der
	 * Datenbank vergleicht.
	 */
	private GroceryListArticleMapper gaMapper = null;
	
	/**
	 * Die Mapperklasse wird referenziert, die die <code>group</code> und <code>User</code>mit der
	 * Datenbank vergleicht.
	 */
	private GroupUserMapper guMapper = null;

	// CONSTRUCTORS==========================================================

	public ReportGeneratorImpl() throws IllegalArgumentException {

	}

	// METHODS===============================================================
	public void init() throws IllegalArgumentException {

		this.aMapper = ArticleMapper.articleMapper();
		this.uMapper = UserMapper.userMapper();
		this.glMapper = GroceryListMapper.groceryListMapper();
		this.rMapper = RetailerMapper.retailerMapper();
		this.gMapper = GroupMapper.groupMapper();
		this.gaMapper = GroceryListArticleMapper.groceryListArticleMapper();
		this.guMapper = GroupUserMapper.groupUserMapper();
		

		EditorServiceImpl impl = new EditorServiceImpl();
		impl.init();
		this.editorService = impl;

	}

	protected EditorService getEditorService() {
		return this.editorService;
	}

//ARTICLE-REPORT============================================================================================================================	
	
	/**
	 * Es wird eine Statistik ueber favourisierte Article in einzelnen Gruppen des Users generiert.
	 * 
	 * @param u beschreibt ein User Objekt
	 * @param groups beschreibt ein Vector aller Gruppen in dem der User Mitglied ist.
	 * 
	 * @return report gibt einen Report von Favourisierten, gekauften Articlen zurueck.
	 */
	public ArticleReport createStatisticA(User u, Vector<Group> groups) throws IllegalArgumentException {
		if (this.getEditorService() == null) {
			return null;
		}

		// Als erstes erstellen wir eine Instanz des ArticleReport
		ArticleReport report = new ArticleReport();

		// Nun legen wir den Titel und unser Erstellungsdatum des Reports
		report.setTitle("Alle häufig gekauften Artikel von: " + u.getEmail());
		report.setCreated(new Date());

		// Als naechstes erstellen wir die Kopfzeile unserer Reporttabelle
		Row head = new Row();
		head.addColumn(new Column("Artikel-Name"));
		head.addColumn(new Column("Einzelhändler"));
		head.addColumn(new Column("Erstellungsdatum"));
		head.addColumn(new Column("Modifikationsdatum"));
		head.addColumn(new Column("Archivierungsdatum"));

		report.addRow(head);

		//Hier werden unsere Angeforderten Daten in einen Vector hinterlegt
		Vector<Group> allGroupsByUser = groups;
		Vector<Article> allArticles = new Vector<Article>();
		Vector<Retailer> allRetailers = new Vector<Retailer>();
		
		//Hier werden die Gruppen und deren Article aufgerufen
		allArticles.addAll(this.editorService.findAllArticleByFavouriteTRUE(groups));
		
		Vector<Article> receiver = new Vector<Article>();
		
		for (int i = 0; i < allArticles.size(); i++) {
				
			for (int j = 0; j < allGroupsByUser.size(); j++) {
				
				if(allArticles.elementAt(i).getGroupId() == allGroupsByUser.elementAt(j).getId()) {
					Article a = new Article();
					a = this.editorService.getArticleById(allArticles.elementAt(j).getGroupId());
					receiver.add(a);
				}
			}
	
					Row row = new Row();
					row.addColumn(new Column(allArticles.elementAt(i).getName()));
					row.addColumn(new Column(allArticles.elementAt(i).getRetailerReport().getRetailerName()));
					row.addColumn(new Column(allArticles.elementAt(i).getCreationDat().toString()));
					row.addColumn(new Column(allArticles.elementAt(i).getModDat().toString()));
					if(allArticles.elementAt(i).getDelDat() != null) {
						row.addColumn(new Column(allArticles.elementAt(i).getDelDat().toString()));
					}
					report.addRow(row);	
					
				}
	
		return report;
	
	}

//ARTICLE-DATE-REPORT========================================================================================================================

	/**
	 * Es wird eine Statistik ueber favourisierte Article in einzelnen Gruppen des Users mit Zeitraum generiert.
	 * 
	 * @param user beschreibt ein User Objekt
	 * @param groups beschreibt ein Vector aller Gruppen in dem der User Mitglied ist.
	 * @param choosenStartDate ausgewaehltes Start Datum
	 * @param choosenEndDate ausgewaehltes End Datum
	 * @param choosenStartDatePl1TS ausgewaehltes Start Datum
	 * @param choosenEndDatePl1TS ausgewaehltes End Datum
	 * 
	 * @return report gibt einen Report von favourisierten, gekauften Articlen mit dessen Zeitraum zurueck.
	 */
	public ArticleDateReport createStatisticAD(User user, Vector<Group> groups, Timestamp choosenStartDate, Timestamp choosenEndDate,
			Timestamp choosenStartDatePl1TS, Timestamp choosenEndDatePl1TS) {
		if (this.getEditorService() == null) {
			return null;
		}
	
		// Als erstes erstellen wir eine Instanz des ArticleReport
		ArticleDateReport report = new ArticleDateReport();

		// Nun legen wir den Titel und unser Erstellungsdatum des Reports
		report.setTitle("Alle häufig gekauften Artikel von: " + user.getEmail());
		report.setCreated(new Date());

		// Als naechstes erstellen wir die Kopfzeile unserer Reporttabelle
		Row head = new Row();
		head.addColumn(new Column("Artikel-Name"));
		head.addColumn(new Column("Einzelhändler"));
		head.addColumn(new Column("Erstellungsdatum"));
		head.addColumn(new Column("Modifikationsdatum"));
		head.addColumn(new Column("Archivierungsdatum"));

		report.addRow(head);

		//Hier werden unsere Angeforderten Daten in einen Vector hinterlegt
		Vector<Group> allGroupsByUser = groups;
		Vector<Article> allArticles = new Vector<Article>();
		
		//Hier werden die Gruppen und deren Article aufgerufen
		allArticles.addAll(this.editorService.findAllArticleByDateFavouriteTRUE(groups, choosenStartDate, choosenEndDate));
		
		Vector<Article> receiver = new Vector<Article>();
		
		for (int i = 0; i < allArticles.size(); i++) {
			
			for (int j = 0; j < allGroupsByUser.size(); j++) {
				
				if(allArticles.elementAt(i).getGroupId() == allGroupsByUser.elementAt(j).getId()) {
					Article a = new Article();
					a = this.editorService.getArticleById(allArticles.elementAt(j).getGroupId());
					receiver.add(a);
				}
			}
	
					Row row = new Row();
					row.addColumn(new Column(allArticles.elementAt(i).getName()));
					row.addColumn(new Column(allArticles.elementAt(i).getRetailerReport().getRetailerName()));
					row.addColumn(new Column(allArticles.elementAt(i).getCreationDat().toString()));
					row.addColumn(new Column(allArticles.elementAt(i).getModDat().toString()));
					if(allArticles.elementAt(i).getDelDat() != null) {
						row.addColumn(new Column(allArticles.elementAt(i).getDelDat().toString()));
					}
					report.addRow(row);	
				}

		return report;
	}

//ARTICLE-RETAILER-REPORT====================================================================================================================
	
	/**
	 * Es wird eine Statistik ueber favourisierte Article in einzelnen Gruppen des Users mit dessen Retailer generiert.
	 * 
	 * @param u beschreibt ein User Objekt
	 * @param groups beschreibt ein Vector aller Gruppen in dem der User Mitglied ist.
	 * @param retailers beschreibt ein Retailer Objekt
	 * 
	 * @return report gibt einen Report von Favourisierten, gekauften Articlen mit dessen Retailern zurueck.
	 */
	public ArticleRetailerReport createStatisticAR(User u, Vector<Group> groups, Vector<Retailer> retailers) throws IllegalArgumentException {
		if (this.getEditorService() == null) {
			return null;
		}

		// Als erstes erstellen wir eine Instanz des ArticleReport
		ArticleRetailerReport report = new ArticleRetailerReport();

		// Nun legen wir den Titel und unser Erstellungsdatum des Reports
		report.setTitle("Alle häufig gekauften Artikel von: " + u.getEmail() );
		report.setCreated(new Date());

		// Als naechstes erstellen wir die Kopfzeile unserer Reporttabelle
		Row head = new Row();
		head.addColumn(new Column("Artikel-Name"));
		head.addColumn(new Column("Einzelhändler"));
		head.addColumn(new Column("Erstellungsdatum"));
		head.addColumn(new Column("Modifikationsdatum"));
		head.addColumn(new Column("Archivierungsdatum"));

		report.addRow(head);

		//Hier werden unsere Angeforderten Daten in einen Vector hinterlegt
		Vector<Group> allGroupsByUser = groups;
		Vector<Retailer> allRetailers = retailers;
		Vector<Article> allArticles = new Vector<Article>();
		
		//Hier werden die Article und deren Retailer aufgerufen
		allArticles.addAll(this.editorService.findAllArticleByRetailerFavouriteTRUE(groups, retailers));

		Vector<Article> receiver = new Vector<Article>();
		
		
		for (int i = 0; i < allArticles.size(); i++) {
			
			for (int j = 0; j < allGroupsByUser.size(); j++) {
				
				if(allArticles.elementAt(i).getGroupId() == allGroupsByUser.elementAt(j).getId()) {
					Article a = new Article();
					a = this.editorService.getArticleById(allArticles.elementAt(j).getGroupId());
					receiver.add(a);
				}
			}
	
					Row row = new Row();
					row.addColumn(new Column(allArticles.elementAt(i).getName()));
					row.addColumn(new Column(allArticles.elementAt(i).getRetailerReport().getRetailerName()));
					row.addColumn(new Column(allArticles.elementAt(i).getCreationDat().toString()));
					row.addColumn(new Column(allArticles.elementAt(i).getModDat().toString()));
					if(allArticles.elementAt(i).getDelDat() != null) {
						row.addColumn(new Column(allArticles.elementAt(i).getDelDat().toString()));
					}
					report.addRow(row);	
				}

		return report;
	}

//ARTICLE-DATE-RETAILER-REPORT===============================================================================================================
	
	/**
	 * Es wird eine Statistik ueber favourisierte Article in einzelnen Gruppen des Users mit dessen Retailer und Zeitraum generiert.
	 * 
	 * @param user beschreibt ein User Objekt
	 * @param groups beschreibt ein Vector aller Gruppen in dem der User Mitglied ist.
	 * @param retailers beschreibt ein Retailer Objekt
	 * @param choosenStartDate ausgewaehltes Start Datum
	 * @param choosenEndDate ausgewaehltes End Datum
	 * @param choosenStartDatePl1TS ausgewaehltes Start Datum
	 * @param choosenEndDatePl1TS ausgewaehltes End Datum
	 * 
	 * @return report gibt einen Report von favourisierten, gekauften Articlen mit dessen Retailern und des Zeitraumes zurueck.
	 */
	public ArticleDateRetailerReport createStatisticADR(User user, Vector<Group> groups, Vector<Retailer> retailers, Timestamp choosenStartDate, Timestamp choosenEndDate,
			Timestamp choosenStartDatePl1TS, Timestamp choosenEndDatePl1TS) throws IllegalArgumentException {
		if (this.getEditorService() == null) {
			return null;
		}
		// Als erstes erstellen wir eine Instanz des ArticleReport
		ArticleDateRetailerReport report = new ArticleDateRetailerReport();

		// Nun legen wir den Titel und unser Erstellungsdatum des Reports
		report.setTitle("Alle häufig gekauften Artikel von: " + user.getEmail());
		report.setCreated(new Date());

		// Als naechstes erstellen wir die Kopfzeile unserer Reporttabelle
		Row head = new Row();
		head.addColumn(new Column("Artikel-Name"));
		head.addColumn(new Column("Einzelhändler"));
		head.addColumn(new Column("Erstellungsdatum"));
		head.addColumn(new Column("Modifikationsdatum"));
		head.addColumn(new Column("Archivierungsdatum"));

		report.addRow(head);

		//Hier werden unsere Angeforderten Daten in einen Vector hinterlegt
		Vector<Group> allGroupsByUser = groups;
		Vector<Article> allArticles = new Vector<Article>();
		
		//Hier werden die Gruppen und deren Article aufgerufen
		allArticles.addAll(this.editorService.findAllArticleByDateRetailerFavouriteTRUE(groups, retailers, choosenStartDate, choosenEndDate));
		
		Vector<Article> receiver = new Vector<Article>();
		
		for (int i = 0; i < allArticles.size(); i++) {
			
			for (int j = 0; j < allGroupsByUser.size(); j++) {
				
				if(allArticles.elementAt(i).getGroupId() == allGroupsByUser.elementAt(j).getId()) {
					Article a = new Article();
					a = this.editorService.getArticleById(allArticles.elementAt(j).getGroupId());
					receiver.add(a);
				}
			}
	
					Row row = new Row();
					row.addColumn(new Column(allArticles.elementAt(i).getName()));
					row.addColumn(new Column(allArticles.elementAt(i).getRetailerReport().getRetailerName()));
					row.addColumn(new Column(allArticles.elementAt(i).getCreationDat().toString()));
					row.addColumn(new Column(allArticles.elementAt(i).getModDat().toString()));
					if(allArticles.elementAt(i).getDelDat() != null) {
						row.addColumn(new Column(allArticles.elementAt(i).getDelDat().toString()));
					}
					report.addRow(row);	
				}

		return report;
	}

}
