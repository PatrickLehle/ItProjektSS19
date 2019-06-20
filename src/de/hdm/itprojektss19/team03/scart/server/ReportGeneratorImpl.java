package de.hdm.itprojektss19.team03.scart.server;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;


import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.itprojektss19.team03.scart.server.EditorServiceImpl;
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
import de.hdm.itprojektss19.team03.scart.shared.bo.GroceryList;
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
	
//	public CompositeReport getReport(Vector<String> cGroupString, Timestamp cStartDate,
//			Timestamp cEndDate, Timestamp cStartDatePl1TS, Timestamp cEndPl1TS, Vector<SimpleReport> cReports, User user) {
//		
//		
//		CompositeReport compositeReport = new CompositeReport();
//		
//		SimpleReport header = new SimpleReport();
//		
//	}
	
	
	@Override
	public ArticleReport createStatisticA(User u) throws IllegalArgumentException, SQLException {
		if(this.getEditorService() == null) {
			return null;
		}
		
	// Als erstes erstellen wir eine Instanz des ArticleReport
		ArticleReport report = new ArticleReport();
		
		//Nun legen wir den Titel und unser Erstellungsdatum des Reports
		try {
			report.setTitle("Alle Artikel von"+ this.getUserByGMail(u) + " ");
		} catch (Exception e) {
			e.printStackTrace();
		}
		report.setCreated(new Date());
		
	//Als naechstes erstellen wir die Kopfzeile unserer Reporttabelle
		Row head = new Row();
		head.addColumn(new Column("Artikel-Name"));
		head.addColumn(new Column("Menge"));
		head.addColumn(new Column("Einheit"));
		head.addColumn(new Column("Einzelhändler"));
		head.addColumn(new Column("Erstellungsdatum"));
		head.addColumn(new Column("Modifikationsdatum"));
		
		report.addRow(head);

		//Hier werden die Gruppen eines Users via des zusammengesetzten Schluessels identifiziert
		Vector<Group> groupsByUser = this.editorService.findAllGroupsByUserId(u.getId());
		Vector<Group> receive = new Vector<Group>();
		Vector<User> user = new Vector<User>();
		
		
		groupsByUser.addAll(this.getEditorService().findAllGroupsByUserId(u.getId()));
	
	//	groupsByUser = GroupUserMapper.groupUserMapper().findAllGroupsByUser(u.getId());
		
		for(int i =0; i<groupsByUser.size(); i++) {
			for(int j=0; j<user.size();j++) {
				if(groupsByUser.elementAt(i).getId() == user.elementAt(j).getId()) {
					Group group = new Group();
					group = this.editorService.getGroupById(groupsByUser.elementAt(j).getId());
					receive.add(group);
				}
			}
		}
		
		//Hier werden die Article einer GroceryList via des zsmgesetzten Schluessels identifiziert
		Vector<Article> articleFromGL = new Vector<Article>();
		Vector<Article> receive2 = new Vector<Article>();
		Vector<GroceryList> allGroceryLists = new Vector<GroceryList>();
		
	//	articleFromGL.addAll(this.getEditorService().findAllGroceryListByGroupId();
		
		for(int i =0; i<articleFromGL.size(); i++) {
			for(int j=0; j<allGroceryLists.size();j++) {
				if(articleFromGL.elementAt(i).getId() == allGroceryLists.elementAt(j).getId()) {
					Article article = new Article();
					article = this.editorService.getArticleById(articleFromGL.elementAt(i).getId());
					receive2.add(article);			
				}
			}
		}
		
//		for(int i =0; i< receive.size();i++) {
//			Row newRow = new Row();
//			newRow.addColumn(new Column(receive.elementAt(i).getGroupName()));
//			newRow.addColumn(c);
		return report;
	
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
		
		
//		//Titel und Erstellungsdatum wird hier festgelegt
//		report.setTitle("Alle Artikel von " + " " + this.getUserByGMail(u)+ " ");
//		report.setCreated(new Date());
//		
//		//Kopfzeile unseres Reports. Ueberschriften der einzelnen Spalten
//		Row head = new Row();
//		head.addColumn(new Column("Artikel"));
//		head.addColumn(new Column("Menge"));
//		head.addColumn(new Column("Einzelhändler"));
//		head.addColumn(new Column("Erstellungsdatum"));
//		head.addColumn(new Column("Modifikationsdatum"));
//		report.addRow(head);
//
////		Vector<Article> allArticle = ArticleMapper.articleMapper().findAll();
////		Vector<Group> allGroupsByUser = GroupMapper.groupMapper().findAll();
//		allArticle.addAll(this.getEditorService().findAllArticle());
//		
//		
//		for( Article a : allArticle) {
//			Row articleRow = new Row();
//			
//			articleRow.addColumn(new Column(String.valueOf(a.getId())));
//			articleRow.addColumn(new Column(String.valueOf(this.editorService.findAllArticle())));
//		
//			report.addRow(articleRow);
//		}
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
	
	@SuppressWarnings("deprecation")
	@Override
	public ArticleDateRetailerReport createStatisticADR(Vector<String> choosenUsers, Timestamp choosenStartDate,
			Timestamp choosenEndDate, Timestamp choosenStartDatePl1TS, Timestamp choosenEndDatePl1TS,
			Vector<SimpleReport> choosenReports, User clientUser) throws IllegalArgumentException {
//		
//	//	CompositeReport compositeReport = new CompositeReport();
//		
//		ArticleDateRetailerReport result = new ArticleDateRetailerReport();
//
//		//SimpleReport header = new SimpleReport();
//
//		SimpleParagraph headerPar1 = new SimpleParagraph();
//		headerPar1.setText("Report");
//
//
//		@SuppressWarnings("deprecation")
//		String tempD2 = (choosenEndDate.getDate()) + "." + (choosenEndDate.getMonth() + 1) + "."
//				+ (choosenEndDate.getYear() + 1900);
//
//		SimpleParagraph headerPar2 = new SimpleParagraph();
//		headerPar2.setText(("Vom " +
//				(choosenStartDate.getDate()) + "." + (choosenStartDate.getMonth() + 1) + "."
//				+ (choosenStartDate.getYear() + 1900) + " bis zum " + tempD2));
//
//
//
//		choosenStartDatePl1TS.setTime(choosenStartDate.getTime() + (1000 * 60 * 60 * 24));
//		choosenEndDatePl1TS.setTime(choosenEndDate.getTime() + (1000 * 60 * 60 * 24));
//
//		CompositeParagraph headerPar = new CompositeParagraph();
//		headerPar.addSubParagraph(headerPar1);
//		headerPar.addSubParagraph(headerPar2);
//
//		result.setHeaderData(headerPar);
//
////		---------------------------------------------------------------------------------		
////		Beziehen der User-Objekte
//		Vector<User> choosenUsersS = new Vector<User>();
//		if (choosenUsersString.size() != 0) {
//			for (int i = 0; i < choosenUsersString.size(); i++) {
//				choosenUsers.add(userMapper.findByNickname(choosenUsersString.elementAt(i)));
//			}
//
////		---------------------------------------------------------------------------------
//////	Befüllen des gesamten Reports mit den angeforderten Reports über angeforderte User
//
//			for (int i = 0; i < choosenUsers.size(); i++) {
//
////			Setzen des Headers der für einen user angeforderten Reports
//				SimpleReport userReportHeader = new SimpleReport();
//
//				SimpleParagraph uDSP = new SimpleParagraph();
//				uDSP.setValue("Report von " + choosenUsers.elementAt(i).getFirstName() + " "
//						+ choosenUsers.elementAt(i).getLastName() + " " + "(" + choosenUsers.elementAt(i).getNickName()
//						+ ")");
//
//				CompositeParagraph uDP = new CompositeParagraph();
//				uDP.addSubParagraph(uDSP);
//
//				userReportHeader.setHeaderData(uDP);
//
//
//				result.addsimpleReport(userReportHeader);
//
//				if (choosenReports.size() != 0) {
//					for (int u = 0; u < choosenReports.size(); u++) {
//
//						if (choosenReports.elementAt(u).getClass() == SubReport.class) {
//							compositeReport.addsimpleReport(this.createSubReport1(choosenUsers.elementAt(i),
//									choosenStartDate, choosenEndDate, choosenStartDatePl1TS, choosenEndDatePl1TS));
//
//							compositeReport.addsimpleReport(this.createSubReport2(choosenUsers.elementAt(i),
//									choosenStartDate, choosenEndDate, choosenStartDatePl1TS, choosenEndDatePl1TS));
//						}
//
//						if (choosenReports.elementAt(u).getClass() == PostReport.class) {
//							compositeReport.addsimpleReport(this.createPostReport1(choosenUsers.elementAt(i),
//									choosenStartDate, choosenEndDate, choosenStartDatePl1TS, choosenEndDatePl1TS));
//						}
//
//						if (choosenReports.elementAt(u).getClass() == CommentReport.class) {
//							compositeReport.addsimpleReport(this.createCommentReport1(choosenUsers.elementAt(i),
//									choosenStartDate, choosenEndDate, choosenStartDatePl1TS, choosenEndDatePl1TS));
//
//							compositeReport.addsimpleReport(this.createCommentReport2(choosenUsers.elementAt(i),
//									choosenStartDate, choosenEndDate, choosenStartDatePl1TS, choosenEndDatePl1TS));
//						}
//
//						if (choosenReports.elementAt(u).getClass() == LikeReport.class) {
//							compositeReport.addsimpleReport(this.createLikeReport1(choosenUsers.elementAt(i),
//									choosenStartDate, choosenEndDate, choosenStartDatePl1TS, choosenEndDatePl1TS));
//
//							compositeReport.addsimpleReport(this.createLikeReport2(choosenUsers.elementAt(i),
//									choosenStartDate, choosenEndDate, choosenStartDatePl1TS, choosenEndDatePl1TS));
//						}
//
//					}
//				}
//
////			Einfügen einer Trennlinie
//				SimpleReport userReportSeperator = new SimpleReport();
//
//				result.addsimpleReport(userReportSeperator);
//
//			}
//		}

		return null ;
	}
	
//	@Override
//	public CompositeReport getReport(Vector<String> choosenGroupss, Timestamp choosenStartDate, Timestamp choosenEndDate,
//			Timestamp choosenStartDatePl1TS, Timestamp choosenEndDatePl1TS, Vector<SimpleReport> choosenReports,
//			User clientUser) throws IllegalArgumentException {
//		if (this.getEditorService() == null) {
//			return null;
//		}
//		return null;
//	}
	
//HELPING-METHODS=========================================================
	
	private String getUserByGMail(User u) throws IllegalArgumentException, SQLException {

//		Group g = editorService.
		String name = "";
		User user = editorService.getUserByGMail(u.getEmail());
		name = user.getEmail();

		return name;
	}
	private Vector<Group> getGroupByUser(int userId) throws IllegalArgumentException, SQLException {

		Vector<Group> groupsByUser = new Vector<Group>();
		Vector<Group> receive = new Vector<Group>();
		Vector<User> user = new Vector<User>();
		
		groupsByUser = GroupUserMapper.groupUserMapper().findAllGroupsByUserId(userId);
		
		for(int i =0; i<groupsByUser.size(); i++) {
			for(int j=0; j<user.size();j++) {
				if(groupsByUser.elementAt(i).getId() == user.elementAt(j).getId()) {
					Group group = new Group();
					group = this.editorService.getGroupById(groupsByUser.elementAt(j).getId());
					receive.add(group);
				}
			}
		}	
		return receive;
	}
}
