package de.hdm.itprojektss19.team03.scart.server;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Vector;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.itprojektss19.team03.scart.server.db.ArticleMapper;
import de.hdm.itprojektss19.team03.scart.server.db.GroceryListMapper;
import de.hdm.itprojektss19.team03.scart.server.db.GroupMapper;
import de.hdm.itprojektss19.team03.scart.server.db.RetailerMapper;
import de.hdm.itprojektss19.team03.scart.server.db.UserMapper;
import de.hdm.itprojektss19.team03.scart.shared.EditorService;
import de.hdm.itprojektss19.team03.scart.shared.LoginService;
import de.hdm.itprojektss19.team03.scart.shared.ReportGenerator;
import de.hdm.itprojektss19.team03.scart.shared.bo.Article;
import de.hdm.itprojektss19.team03.scart.shared.bo.GroceryList;
import de.hdm.itprojektss19.team03.scart.shared.bo.Group;
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
public class ReportGeneratorImpl extends RemoteServiceServlet implements ReportGenerator {

	// INITIALIZATION========================================================
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

		// Als erstes erstellen wir eine Instanz des ArticleReport
		ArticleReport report = new ArticleReport();

		// Nun legen wir den Titel und unser Erstellungsdatum des Reports
		report.setTitle("Alle Artikel von einer Gruppe ");
		report.setCreated(new Date());

		// Als naechstes erstellen wir die Kopfzeile unserer Reporttabelle
		Row head = new Row();
		head.addColumn(new Column("Artikel-Name"));
		head.addColumn(new Column("Einzelhändler"));
		head.addColumn(new Column("Erstellungsdatum"));
		head.addColumn(new Column("Modifikationsdatum"));

		report.addRow(head);

		// Hier werden die Gruppen eines Users via des zusammengesetzten Schluessels
		// identifiziert
		Vector<Group> groupsByUser = this.editorService.findAllGroupsByUserId(u.getId());
		Vector<GroceryList> allGroceryLists = this.editorService.findAllGroceryLists();
		Vector<Article> allArticles = this.editorService.findAllArticle();

		Vector<Article> receiver = new Vector<Article>();

		for (int i = 0; i < groupsByUser.size(); i++) {
			for (int j = 0; j < allGroceryLists.size(); j++) {
				for (int k = 0; k < allArticles.size(); k++) {
					if (groupsByUser.elementAt(i).getId() == allGroceryLists.elementAt(j).getGroupId()) {
						receiver.addAll(
								getEditorService().findAllArticleByGroceryList(allGroceryLists.elementAt(k).getId()));
					}
					Row row = new Row();
					row.addColumn(new Column(receiver.elementAt(k).getName()));
					row.addColumn(new Column(receiver.elementAt(k).getRetailer()));
					row.addColumn(new Column(receiver.elementAt(k).getCreationDat().toString()));
					row.addColumn(new Column(receiver.elementAt(k).getModDat().toString()));
					row.addColumn(new Column(""));

					report.addRow(row);
				}
			}
		}
		return report;
	}

	public ArticleDateReport createStatisticAD(User user, Timestamp choosenStartDate, Timestamp choosenEndDate,
			Timestamp choosenStartDatePl1TS, Timestamp choosenEndDatePl1TS) {
		if (this.getEditorService() == null) {
			return null;
		}
		return null;
	}

	public ArticleRetailerReport createStatisticAR(User user, int retailerId) throws IllegalArgumentException {
		if (this.getEditorService() == null) {
			return null;
		}
		return null;
	}

	public ArticleDateRetailerReport createStatisticADR(User user, Timestamp choosenStartDate, Timestamp choosenEndDate,
			Timestamp choosenStartDatePl1TS, Timestamp choosenEndDatePl1TS) throws IllegalArgumentException {
		if (this.getEditorService() == null) {
			return null;
		}
		return null;
	}

}
