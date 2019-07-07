package de.hdm.itprojektss19.team03.scart.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Vector;

import de.hdm.itprojektss19.team03.scart.server.ServersideSettings;
import de.hdm.itprojektss19.team03.scart.shared.DatabaseException;
import de.hdm.itprojektss19.team03.scart.shared.bo.Article;
import de.hdm.itprojektss19.team03.scart.shared.bo.Group;
import de.hdm.itprojektss19.team03.scart.shared.bo.Retailer;
import de.hdm.itprojektss19.team03.scart.shared.bo.User;

/**
 * 
 * Die Mapper Klasse bildet ein Objekt bidirektional auf eine reationale
 * Datenbank ab.
 * 
 * @author Marco Dell'Oso, PatrickLehle, BastianTilk
 *
 */
public class ArticleMapper {

	/**
	 * Singleton Klasse -> es gibt immer nur eine Instanz dieser Klasse.
	 */
	private static ArticleMapper articleMapper = null;

	/**
	 * Protected Construcor
	 */
	protected ArticleMapper() {
	}

	/**
	 * Statische Methode, die die Single Eingenschaft sicherstellt, indem sie nur
	 * eine neue Instanz erstellt, wenn keine vorhanden ist.
	 * 
	 * @return ArticleMapper-Objekt
	 */
	public static ArticleMapper articleMapper() {
		if (articleMapper == null) {
			articleMapper = new ArticleMapper();
		}

		return articleMapper;
	}

	/**
	 * Sucht einen Artikel anhand seiner ID
	 * 
	 * @param id beschreibt die Eindeutigkeit eines Article Objekts via id
	 * @return Das Artikel-Objekt, falls ein passendes gefunden wurde.
	 * @throws DatabaseException Entsteht durch ein Attribut, dass nicht in der
	 *             Datanbank vorhanden ist aber dennoch gesetzt wurde.
	 */
	public Article findByKey(int id) throws DatabaseException {
		Connection con = DBConnection.connection();

		try {
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM article WHERE articleId=" + id);

			// Nur EIN Ergebnis, da id =PRIMARY-KEY
			if (rs.next()) {
				Article article = new Article();
				article.setId(rs.getInt("articleId"));
				article.setName(rs.getString("articleName"));
				article.setQuantity(rs.getInt("articleQuantity"));
				article.setUnit(rs.getString("articleUnit"));
				article.setRetailerId(rs.getInt("articleRetailerId"));
				article.setCreationDat(rs.getTimestamp("articleCreationDat"));
				article.setModDat(rs.getTimestamp("articlemodDat"));
				article.setCheckBoolean(rs.getBoolean("articleBoolean")); // Boolean ob der Artikel als gekauft markiert
																			// wurde
				// oder nicht
				article.setFav(rs.getBoolean("articleFav"));
				article.setDelDat(rs.getTimestamp("articleDelDat"));

				return article;
			}
		} catch (SQLException e2) {
			ServersideSettings.getLogger().severe(e2.getMessage());
			throw new DatabaseException(e2);
		}
		return null;
	}

	public Article findById(int id) throws DatabaseException {
		Connection con = DBConnection.connection();

		try {
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM article WHERE articleId=" + id);

			// Nur EIN Ergebnis, da id =PRIMARY-KEY
			if (rs.next()) {
				Article article = new Article();
				article.setId(rs.getInt("articleId"));
				article.setName(rs.getString("articleName"));
				article.setQuantity(rs.getInt("articleQuantity"));
				article.setUnit(rs.getString("articleUnit"));
				article.setRetailerId(rs.getInt("articleRetailerId"));
				article.setOwnerId(rs.getInt("articleOwnerId"));
				article.setGroupId(rs.getInt("articleGroupId"));
				article.setCreationDat(rs.getTimestamp("articleCreationDat"));
				article.setModDat(rs.getTimestamp("articlemodDat"));
				article.setCheckBoolean(rs.getBoolean("articleBoolean"));
				article.setFav(rs.getBoolean("articleFav"));
				article.setDelDat(rs.getTimestamp("articleDelDat"));

				return article;
			}
		} catch (SQLException e2) {
			ServersideSettings.getLogger().severe(e2.getMessage());
			throw new DatabaseException(e2);
		}
		return null;
	}

	/**
	 * Sucht alle Artikel
	 * 
	 * @return Vector mit allen gefundenen Artikeln
	 * @throws DatabaseException Entsteht durch ein Attribut, dass nicht in der
	 *             Datanbank vorhanden ist aber dennoch gesetzt wurde.
	 */
	public Vector<Article> findAll() throws DatabaseException {

		Connection con = null;
		PreparedStatement stmt = null;
		String all = "SELECT * FROM article";

		Vector<Article> articles = new Vector<Article>();

		try {
			con = DBConnection.connection();
			stmt = con.prepareStatement(all);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Article a = new Article();
				a.setId(rs.getInt("articleId"));
				a.setName(rs.getString("articleName"));
				a.setQuantity(rs.getInt("articleQuantity"));
				a.setUnit(rs.getString("articleUnit"));
				a.setCreationDat(rs.getTimestamp("articleCreationDat"));
				a.setModDat(rs.getTimestamp("articlemodDat"));
				a.setCheckBoolean(rs.getBoolean("articleBoolean"));
				a.setFav(rs.getBoolean("articleFav"));
				a.setDelDat(rs.getTimestamp("articleDelDat"));
				articles.addElement(a);
			}
		} catch (SQLException e2) {
			ServersideSettings.getLogger().severe(e2.getMessage());
			throw new DatabaseException(e2);
		}
		return articles;
	}

	/**
	 * 
	 * @param name des Artikels der gesucht werden soll
	 * @param groupId beschreibt eine Eundeutige Gruppe via id
	 * @return Vektor mit allen Artikel-Objekten die den Namen haben nach dem
	 *         gesucht wurde
	 * @throws DatabaseException Entsteht durch ein Attribut, dass nicht in der
	 *             Datanbank vorhanden ist aber dennoch gesetzt wurde.
	 */
	public Vector<Article> findArticleByName(String name, int groupId) throws DatabaseException {
		Connection con = null;
		PreparedStatement stmt = null;

		String select = "SELECT * FROM article WHERE articleName=? AND articleGroupId=?";

		Vector<Article> result = new Vector<Article>();

		try {
			con = DBConnection.connection();
			stmt = con.prepareStatement(select);
			stmt.setString(1, name);
			stmt.setInt(2, groupId);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Article article = new Article();
				article.setId(rs.getInt("articleId"));
				article.setName(rs.getString("articleName"));
				article.setQuantity(rs.getInt("articleQuantity"));
				article.setUnit(rs.getString("articleUnit"));
				article.setRetailerId(rs.getInt("articleRetailerId"));
				article.setCreationDat(rs.getTimestamp("articleCreationDat"));
				article.setModDat(rs.getTimestamp("articlemodDat"));
				article.setCheckBoolean(rs.getBoolean("articleBoolean"));
				article.setFav(rs.getBoolean("articleFav"));
				article.setDelDat(rs.getTimestamp("articleDelDat"));

				result.addElement(article);
			}
		} catch (SQLException e2) {
			ServersideSettings.getLogger().severe(e2.getMessage());
			throw new DatabaseException(e2);
		}
		return result;
	}

	/**
	 * Sucht alle Artikel, die einem bestimmten Retailer haben, anhand der ID
	 * 
	 * @param retailerId beschreibt die Eindeutigkeit eines Retailer Objekts via id
	 * @return Vector mit allen gefunden Artikeln des Retailers
	 * @throws DatabaseException Entsteht durch ein Attribut, dass nicht in der
	 *             Datanbank vorhanden ist aber dennoch gesetzt wurde.
	 */
	public Vector<Article> findArticleByRetailerId(int retailerId) throws DatabaseException {
		Connection con = DBConnection.connection();

		Vector<Article> articles = new Vector<Article>();

		try {
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM article WHERE articleRetailerId=" + retailerId);

			// Hier wird fuer jeden gefundenen Article immer ein neues Object erstellt
			while (rs.next()) {
				Article article = new Article();
				article.setId(rs.getInt("articleId"));
				article.setName(rs.getString("articleName"));
				article.setQuantity(rs.getInt("articleQuantity"));
				article.setUnit(rs.getString("articleUnit"));
				article.setRetailerId(rs.getInt("articleRetailerId"));
				article.setCreationDat(rs.getTimestamp("articleCreationDat"));
				article.setModDat(rs.getTimestamp("articlemodDat"));
				article.setCheckBoolean(rs.getBoolean("articleBoolean"));
				article.setFav(rs.getBoolean("articleFav"));
				article.setDelDat(rs.getTimestamp("articleDelDat"));
				articles.addElement(article);
			}
		} catch (SQLException e2) {
			ServersideSettings.getLogger().severe(e2.getMessage());
			throw new DatabaseException(e2);
		}
		// Hier wird ein Vector mit allen Articlen die gefunden wurden zurueck gegeben
		return articles;
	}

	/**
	 * Fuegt in der Datenbank einen neuen Artikel ein
	 * 
	 * @param article beschreibt ein Article Objekt
	 * @return Der Eingefuegte Artikel mit aktueller ID
	 * @throws DatabaseException Entsteht durch ein Attribut, dass nicht in der
	 *             Datanbank vorhanden ist aber dennoch gesetzt wurde.
	 */
	public Article insert(Article article) throws DatabaseException {
		Connection con = null;
		PreparedStatement stmt = null;

		// Query fuer die Abfrage der hoechsten ID (Primaerschluessel) in der Datenbank
		String maxIdSQL = "SELECT MAX(article.articleId) AS maxid FROM article";

		// SQL-Anweisung zum Einfuegen des neuen Nutzertupels in die DB
		String insertSQL = "INSERT INTO article (articleName, articleQuantity, articleUnit, articleRetailerId, articleOwnerId, articleGroupId, articleBoolean, articleFav) VALUES (?,?,?,?,?,?,?,?)";

		try {

			// Aufbau der DB-Verbindung
			con = DBConnection.connection();
			stmt = con.prepareStatement(maxIdSQL);

			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				article.setId(rs.getInt("maxid") + 1);
			}
			// Jetzt erfolgt das Einfuegen des Objekts
			stmt = con.prepareStatement(insertSQL);

			// Setzen der ? als Platzhalter fuer den Wert
			stmt.setString(1, article.getName());
			stmt.setInt(2, article.getQuantity());
			stmt.setString(3, article.getUnit());
			stmt.setInt(4, article.getRetailerId());
			stmt.setInt(5, article.getOwnerId());
			stmt.setInt(6, article.getGroupId());
			stmt.setBoolean(7, article.getCheckBoolean());
			stmt.setBoolean(8, article.getFav());
			// Ausfuehren des SQL Statement
			stmt.executeUpdate();

		} catch (SQLException e2) {
			ServersideSettings.getLogger().severe(e2.getMessage());
			throw new DatabaseException(e2);
		}
		return article;
	}

	/**
	 * Aendert einen Artikel in der Datenbank
	 * 
	 * @param article beschreibt ein Article Objekt
	 * @return Geaenderter Artikel
	 * @throws DatabaseException Entsteht durch ein Attribut, dass nicht in der
	 *             Datanbank vorhanden ist aber dennoch gesetzt wurde.
	 */
	public Article update(Article article) throws DatabaseException {

		Connection con = null;
		PreparedStatement stmt = null;

		String update = "UPDATE article SET articleName=?, articleQuantity=?, articleUnit=?, "
				+ "articleRetailerId=?, articleOwnerId=?, articleModDat=?, articleBoolean=?, "
				+ "articleFav=?, articleDelDat=? WHERE articleId=" + article.getId();

		try {
			System.out.println(article.getId() + " article update id");

			// NullPointerException wenn nicht alles bei der uebergabe des Objektes
			// angegeben wird
			con = DBConnection.connection();
			stmt = con.prepareStatement(update);

			stmt.setString(1, article.getName());
			stmt.setInt(2, article.getQuantity());
			stmt.setString(3, article.getUnit());
			stmt.setInt(4, article.getRetailerId());
			stmt.setInt(5, article.getOwnerId());
			stmt.setTimestamp(6, article.getModDat());
			stmt.setBoolean(7, article.getCheckBoolean());
			stmt.setBoolean(8, article.getFav());
			stmt.setTimestamp(9, article.getDelDat());

			stmt.executeUpdate();

		} catch (SQLException e2) {
			ServersideSettings.getLogger().severe(e2.getMessage());
			throw new DatabaseException(e2);
		}
		return article;
	}

	/**
	 * Loescht einen Artikel aus der Datenbank
	 * 
	 * @param article beschreibt ein Article Objekt
	 * @return Es wird null zurueck gegeben, wenn der Artikel entfernt wurde.
	 * @throws DatabaseException Entsteht durch ein Attribut, dass nicht in der
	 *             Datanbank vorhanden ist aber dennoch gesetzt wurde.
	 */
	public Article delete(Article article) throws DatabaseException {
		Connection con = DBConnection.connection();

		PreparedStatement stmt = null;

		String update = "UPDATE article SET articleModDat=?, articleDelDat=? WHERE articleId=" + article.getId();

		try {

			article.setDelDat(new Timestamp(new Date().getTime()));
			con = DBConnection.connection();
			stmt = con.prepareStatement(update);

			stmt.setTimestamp(1, article.getModDat());
			stmt.setTimestamp(2, article.getDelDat());

			stmt.executeUpdate();

		} catch (SQLException e2) {
			ServersideSettings.getLogger().severe(e2.getMessage());
			throw new DatabaseException(e2);
		}
		return null;
	}

	/**
	 * Gibt alle Article in einem Interval zweier Timestamps zurueck
	 * 
	 * @param start Datum
	 * @param end Datum
	 * @return Article-Vector
	 * @throws DatabaseException Entsteht durch ein Attribut, dass nicht in der
	 *             Datanbank vorhanden ist aber dennoch gesetzt wurde.
	 */
	public Vector<Article> findAllArticleByDate(Timestamp start, Timestamp end) throws DatabaseException {
		Connection con = DBConnection.connection();

		Vector<Article> result = new Vector<Article>();

		try {
			Statement stmt = con.createStatement();
			// Query das zwei Timestamps als interval aller Article darin zurueck gibt
			ResultSet rs = stmt.executeQuery(
					"SELECT * FROM article WHERE articleCreationDat BETWEEN '" + start + "' AND '" + end + "'");
			while (rs.next()) {
				Article a = new Article();
				a.setId(rs.getInt("articleId"));
				a.setName(rs.getString("articleName"));
				a.setQuantity(rs.getInt("articleQuantity"));
				a.setUnit(rs.getString("articleUnit"));
				a.setRetailerId(rs.getInt("articleRetailerId"));
				a.setCreationDat(rs.getTimestamp("articleCreationDat"));
				a.setModDat(rs.getTimestamp("articlemodDat"));
				a.setCheckBoolean(rs.getBoolean("articleBoolean"));
				a.setFav(rs.getBoolean("articleFav"));
				a.setDelDat(rs.getTimestamp("articleDelDat"));

				result.addElement(a);
			}
		} catch (SQLException e2) {
			ServersideSettings.getLogger().severe(e2.getMessage());
			throw new DatabaseException(e2);
		}
		return result;
	}

	/**
	 * Gibt alle Artikel eines Retailers in einem Zeitraum zurueck
	 * 
	 * @param retailerId beschreibt die Eindeutigkeit eines Retailer Objekts via id
	 * @param start Datum
	 * @param end Datum
	 * @return Vektor aller Artikel des Retailers in dem Zeitraum
	 * @throws DatabaseException Entsteht durch ein Attribut, dass nicht in der
	 *             Datanbank vorhanden ist aber dennoch gesetzt wurde.
	 */
	public Vector<Article> findAllArticleByDateRetailer(int retailerId, Timestamp start, Timestamp end)
			throws DatabaseException {
		// DB Connection aufbauen
		Connection con = DBConnection.connection();

		Vector<Article> result = new Vector<Article>();

		try {
			PreparedStatement stmt = con.prepareStatement("SELECT * FROM article WHERE articleRetailerId=?");
			stmt.setInt(1, retailerId);
			// Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery();

			// ResultSet rs = stmt.executeQuery("SELECT * FROM article WHERE creationDat
			// BETWEEN '"+start+"' AND '"+end+"' AND retailerId=" + r.getId());
			while (rs.next()) {
				Article a = new Article();
				a.setId(rs.getInt("articleId"));
				a.setName(rs.getString("articleName"));
				a.setQuantity(rs.getInt("articleQuantity"));
				a.setUnit(rs.getString("articleUnit"));
				a.setRetailerId(rs.getInt("articleRetailerId"));
				a.setCreationDat(rs.getTimestamp("articleCreationDat"));
				a.setModDat(rs.getTimestamp("articlemodDat"));
				a.setCheckBoolean(rs.getBoolean("articleBoolean"));
				a.setFav(rs.getBoolean("articleFav"));
				a.setDelDat(rs.getTimestamp("articleDelDat"));

				result.addElement(a);
			}
		} catch (SQLException e2) {
			ServersideSettings.getLogger().severe(e2.getMessage());
			throw new DatabaseException(e2);
		}
		return result;
	}

	/**
	 * Gibt alle Artikel einer Gruppe zurueck
	 * 
	 * @param u User Objekt
	 * @param g Gruppen Objekt
	 * @return Artikel-Vektor
	 * @throws DatabaseException Entsteht durch ein Attribut, dass nicht in der
	 *             Datanbank vorhanden ist aber dennoch gesetzt wurde.
	 */
	public Vector<Article> findAllArticleByGroup(User u, Group g) throws DatabaseException {
		Connection con = DBConnection.connection();

		Vector<Article> result = new Vector<Article>();
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(
					"SELECT article.articleId, article.articleName, article.articleQuantity, article.articleUnit, article.articleCreationDat, article.articleModDat, article.articleDelDat FROM article, groups WHERE articleUserId="
							+ u.getId() + "AND groupId=" + g.getId());

			while (rs.next()) {
				Article a = new Article();
				a.setId(rs.getInt("articleId"));
				a.setName(rs.getString("articleName"));
				a.setQuantity(rs.getInt("articleQuantity"));
				a.setUnit(rs.getString("articleUnit"));
				a.setRetailerId(rs.getInt("articleRetailerId"));
				a.setCreationDat(rs.getTimestamp("articleCreationDat"));
				a.setModDat(rs.getTimestamp("articlemodDat"));
				a.setCheckBoolean(rs.getBoolean("articleBoolean"));
				a.setFav(rs.getBoolean("articleFav"));
				a.setDelDat(rs.getTimestamp("articleDelDat"));

				result.addElement(a);
			}

			return result;
		} catch (SQLException e2) {
			ServersideSettings.getLogger().severe(e2.getMessage());
			throw new DatabaseException(e2);
		}
	}

	/**
	 * Gibt alle Artikel einer Gruppe zurueck
	 * 
	 * @param g Gruppen Objekt
	 * @return Artikel-Vektor
	 * @throws DatabaseException Entsteht durch ein Attribut, dass nicht in der
	 *             Datanbank vorhanden ist aber dennoch gesetzt wurde.
	 */
	public Vector<Article> findAllFavArticleByGroup(Group g) throws DatabaseException {
		Connection con = DBConnection.connection();

		Vector<Article> result = new Vector<Article>();
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT DISTINCT articleName, articleUnit FROM "
					+ "article WHERE articleBoolean = 0 AND article.articleFav = TRUE AND article.articleGroupId = "
					+ g.getId());

			while (rs.next()) {
				Article a = new Article();
				a.setName(rs.getString("articleName"));
				a.setQuantity(1);
				a.setUnit(rs.getString("articleUnit"));
				a.setCheckBoolean(false);
				a.setFav(true);
				a.setDelDat(null);

				result.addElement(a);
			}

			return result;
		} catch (SQLException e2) {
			ServersideSettings.getLogger().severe(e2.getMessage());
			throw new DatabaseException(e2);
		}
	}

	/**
	 * 
	 * @param groupId beschreibt die Eindeutigkeit einer gruppe via id
	 * @return gibt einen Vector von Articlen zurueck
	 * @throws DatabaseException Entsteht durch ein Attribut, dass nicht in der
	 *             Datanbank vorhanden ist aber dennoch gesetzt wurde.
	 */
	public Vector<Article> findAllArticleByGroupId(int groupId) throws DatabaseException {
		Connection con = DBConnection.connection();

		Vector<Article> result = new Vector<Article>();
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(
					"SELECT article.articleId, article.articleName, article.articleQuantity, article.articleUnit, article.articleRetailerId,article.articleOwnerId, article.articleCreationDat, article.articleModDat, article.articleBoolean, article.articleFav, article.articleDelDat FROM article WHERE articleGroupId="
							+ groupId);

			while (rs.next()) {
				Article a = new Article();
				a.setId(rs.getInt("articleId"));
				a.setName(rs.getString("articleName"));
				a.setQuantity(rs.getInt("articleQuantity"));
				a.setUnit(rs.getString("articleUnit"));
				a.setRetailerId(rs.getInt("articleRetailerId"));
				a.setOwnerId(rs.getInt("articleOwnerId"));
				a.setCreationDat(rs.getTimestamp("articleCreationDat"));
				a.setModDat(rs.getTimestamp("articlemodDat"));
				a.setCheckBoolean(rs.getBoolean("articleBoolean"));
				a.setFav(rs.getBoolean("articleFav"));
				a.setDelDat(rs.getTimestamp("articleDelDat"));

				result.addElement(a);
			}
		} catch (SQLException e2) {
			ServersideSettings.getLogger().severe(e2.getMessage());
			throw new DatabaseException(e2);
		}
		return result;
	}

	/**
	 * 
	 * @param ownerId beschreibt die Eindeutigkeit eines Owners via id
	 * @return gibt einen Vector Article zurueck
	 * @throws DatabaseException Entsteht durch ein Attribut, dass nicht in der
	 *             Datanbank vorhanden ist aber dennoch gesetzt wurde.
	 */
	public Vector<Article> findAllArticleByOwnerId(int ownerId) throws DatabaseException {
		Connection con = DBConnection.connection();

		Vector<Article> result = new Vector<Article>();
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(
					"SELECT article.articleId, article.articleName, article.articleQuantity, article.articleUnit, article.articleRetailerId, article.articleOwnerId, article.articleCreationDat, article.articleModDat, article.articleBoolean, article.articleFav, article.articleDelDat FROM article WHERE articleOwnerId="
							+ ownerId);

			while (rs.next()) {
				Article a = new Article();
				a.setId(rs.getInt("articleId"));
				a.setName(rs.getString("articleName"));
				a.setQuantity(rs.getInt("articleQuantity"));
				a.setUnit(rs.getString("articleUnit"));
				a.setRetailerId(rs.getInt("articleRetailerId"));
				a.setOwnerId(rs.getInt("articleOwnerId"));
				a.setCreationDat(rs.getTimestamp("articleCreationDat"));
				a.setModDat(rs.getTimestamp("articlemodDat"));
				a.setCheckBoolean(rs.getBoolean("articleBoolean"));
				a.setFav(rs.getBoolean("articleFav"));
				a.setDelDat(rs.getTimestamp("articleDelDat"));

				result.addElement(a);
			}
		} catch (SQLException e2) {
			ServersideSettings.getLogger().severe(e2.getMessage());
			throw new DatabaseException(e2);
		}
		return result;
	}

	// REPORT-ARTICLE=============================================================================================
	/**
	 * 
	 * @param groups alle Gruppen die uebergeben werden
	 * @return gibt einen Vector Article zurueck die Favourisiert wurden.
	 * @throws DatabaseException Entsteht durch ein Attribut, dass nicht in der
	 *             Datanbank vorhanden ist aber dennoch gesetzt wurde.
	 */
	public Vector<Article> findAllArticleByFavouriteTRUE(Vector<Group> groups) throws DatabaseException {
		Connection con = DBConnection.connection();

		Vector<Article> result = new Vector<Article>();
		try {
			Statement stmt = con.createStatement();
			String s = new String();
			for (int i = 1; i < groups.size(); i++) {
				s = s + " OR " + groups.get(i).getId();
			}

			ResultSet rs = stmt.executeQuery(
					"SELECT * FROM article JOIN retailer ON article.articleRetailerId = retailer.retailerId JOIN groups ON article.articleGroupId = groups.groupId "
							+ "WHERE articleDelDat IS NOT NULL AND articleFav = TRUE AND (article.articleGroupId = "
							+ groups.get(0).getId() + s + ")");

			while (rs.next()) {
				Group group = new Group();
				Retailer retailer = new Retailer();
				group.setGroupName(rs.getString("groupName"));
				group.setId(rs.getInt(17));
				retailer.setId(rs.getInt("retailerId"));
				retailer.setRetailerName(rs.getString("retailerName"));
				retailer.setGroup(group);
				Article a = new Article();
				a.setId(rs.getInt("articleId"));
				a.setName(rs.getString("articleName"));
				a.setQuantity(rs.getInt("articleQuantity"));
				a.setUnit(rs.getString("articleUnit"));
				a.setOwnerId(rs.getInt("articleOwnerId"));
				a.setCreationDat(rs.getTimestamp("articleCreationDat"));
				a.setModDat(rs.getTimestamp("articleModDat"));
				a.setCheckBoolean(rs.getBoolean("articleBoolean"));
				a.setFav(rs.getBoolean("articleFav"));
				a.setDelDat(rs.getTimestamp("articleDelDat"));
				a.setRetailer(retailer);

			}
		} catch (SQLException e2) {
			ServersideSettings.getLogger().severe(e2.getMessage());
			throw new DatabaseException(e2);
		}
		return result;
	}

	// X-REPORT-ARTICLE-DATE======================================================================================
	/**
	 * 
	 * @param groups Gruppen Vetor des Users
	 * @param start Datum
	 * @param end Datum
	 * @return gibt einen Vector Article zurueck die Favourisiert sind und im
	 *         gesuchten Zeitraum liegen
	 * @throws DatabaseException Entsteht durch ein Attribut, dass nicht in der
	 *             Datanbank vorhanden ist aber dennoch gesetzt wurde.
	 */
	public Vector<Article> findAllArticleByDateFavouriteTRUE(Vector<Group> groups, Timestamp start, Timestamp end)
			throws DatabaseException {
		Connection con = DBConnection.connection();

		Vector<Article> result = new Vector<Article>();
		try {
			Statement stmt = con.createStatement();

			String s = new String();

			for (int i = 1; i < groups.size(); i++) {
				s = s + " OR " + groups.get(i).getId();
			}

			ResultSet rs = stmt.executeQuery(
					"SELECT * FROM article JOIN retailer ON article.articleRetailerId = retailer.retailerId JOIN groups ON article.articleGroupId = groups.groupId "
							+ "WHERE articleDelDat IS NOT NULL AND articleFav = TRUE AND (article.articleGroupId = "
							+ groups.get(0).getId() + s + ") AND articleDelDat BETWEEN '" + start + "' AND '" + end
							+ "'");

			while (rs.next()) {
				Group group = new Group();
				Retailer retailer = new Retailer();
				group.setGroupName(rs.getString("groupName"));
				group.setId(rs.getInt("groupId"));
				retailer.setId(rs.getInt("retailerId"));
				retailer.setRetailerName(rs.getString("retailerName"));
				retailer.setGroup(group);
				Article a = new Article();
				a.setId(rs.getInt("articleId"));
				a.setName(rs.getString("articleName"));
				a.setQuantity(rs.getInt("articleQuantity"));
				a.setUnit(rs.getString("articleUnit"));
				a.setOwnerId(rs.getInt("articleOwnerId"));
				a.setCreationDat(rs.getTimestamp("articleCreationDat"));
				a.setModDat(rs.getTimestamp("articleModDat"));
				a.setCheckBoolean(rs.getBoolean("articleBoolean"));
				a.setFav(rs.getBoolean("articleFav"));
				a.setDelDat(rs.getTimestamp("articleDelDat"));
				a.setRetailer(retailer);

				result.addElement(a);
			}
		} catch (SQLException e2) {
			ServersideSettings.getLogger().severe(e2.getMessage());
			throw new DatabaseException(e2);
		}
		return result;
	}

	// X-REPORT-ARTICLE-DATE-RETAILER======================================================================================

	/**
	 * 
	 * @param groups ausgewaehlte Gruppen
	 * @param retailers ausgewaehlte Retailer
	 * @param start Datum
	 * @param end Datum
	 * @return gibt einen Vector Article zurueck die Favourisiert sind und im
	 *         gesuchten Zeitraum liegen und den richtigen Retailer besitzen.
	 * @throws DatabaseException Entsteht durch ein Attribut, dass nicht in der
	 *             Datanbank vorhanden ist aber dennoch gesetzt wurde.
	 */
	public Vector<Article> findAllArticleByDateRetailerFavouriteTRUE(Vector<Group> groups, Vector<Retailer> retailers,
			Timestamp start, Timestamp end) throws DatabaseException {
		Connection con = DBConnection.connection();

		Vector<Article> result = new Vector<Article>();
		try {
			Statement stmt = con.createStatement();

			String s = new String();
			String r = new String();
			for (int i = 1; i < groups.size(); i++) {
				s = s + " OR " + groups.get(i).getId();
			}
			for (int j = 1; j < retailers.size(); j++) {
				r = r + " OR " + retailers.get(j).getId();
			}

			ResultSet rs = stmt.executeQuery(
					"SELECT * FROM article JOIN retailer ON article.articleRetailerId = retailer.retailerId JOIN groups ON article.articleGroupId = groups.groupId "
							+ "WHERE articleDelDat IS NOT NULL AND articleFav = TRUE AND (article.articleGroupId = "
							+ groups.get(0).getId() + s + ") AND (retailer.retailerId= " + retailers.get(0).getId() + r
							+ ") AND articleDelDat BETWEEN '" + start + "' AND '" + end + "'");

			while (rs.next()) {
				Group group = new Group();
				Retailer retailer = new Retailer();
				group.setGroupName(rs.getString("groupName"));
				group.setId(rs.getInt("groupId"));
				retailer.setId(rs.getInt("retailerId"));
				retailer.setRetailerName(rs.getString("retailerName"));
				retailer.setGroup(group);
				Article a = new Article();
				a.setId(rs.getInt("articleId"));
				a.setName(rs.getString("articleName"));
				a.setQuantity(rs.getInt("articleQuantity"));
				a.setUnit(rs.getString("articleUnit"));
				a.setOwnerId(rs.getInt("articleOwnerId"));
				a.setCreationDat(rs.getTimestamp("articleCreationDat"));
				a.setModDat(rs.getTimestamp("articleModDat"));
				a.setCheckBoolean(rs.getBoolean("articleBoolean"));
				a.setFav(rs.getBoolean("articleFav"));
				a.setDelDat(rs.getTimestamp("articleDelDat"));
				a.setRetailer(retailer);

				result.addElement(a);
			}
		} catch (SQLException e2) {
			ServersideSettings.getLogger().severe(e2.getMessage());
			throw new DatabaseException(e2);
		}
		return result;
	}

	// REPORT-ARTICLE-RETAILER======================================================================================

	/**
	 * 
	 * @param groups ausgewaehlte Gruppen
	 * @param retailers ausgewaehlte Retailer
	 * @return gibt einen Vector Article zurueck die Favourisiert sind und den
	 *         richtigen Retailer besitzen.
	 * @throws DatabaseException Entsteht durch ein Attribut, dass nicht in der
	 *             Datanbank vorhanden ist aber dennoch gesetzt wurde.
	 */
	public Vector<Article> findAllArticleByRetailerFavouriteTRUE(Vector<Group> groups, Vector<Retailer> retailers)
			throws DatabaseException {
		Connection con = DBConnection.connection();

		Vector<Article> result = new Vector<Article>();
		try {
			Statement stmt = con.createStatement();
			String s = new String();
			String r = new String();
			for (int i = 1; i < groups.size(); i++) {
				s = s + " OR " + groups.get(i).getId();
			}
			for (int j = 1; j < retailers.size(); j++) {
				r = r + " OR " + retailers.get(j).getId();
			}

			ResultSet rs = stmt.executeQuery(
					"SELECT * FROM article JOIN retailer ON article.articleRetailerId = retailer.retailerId JOIN groups ON article.articleGroupId = groups.groupId "
							+ "WHERE articleDelDat IS NOT NULL AND articleFav = TRUE AND (article.articleGroupId = "
							+ groups.get(0).getId() + s + ") AND (retailer.retailerId= " + retailers.get(0).getId() + r
							+ ")");

			while (rs.next()) {
				Group group = new Group();
				Retailer retailer = new Retailer();
				group.setGroupName(rs.getString("groupName"));
				group.setId(rs.getInt("groupId"));
				retailer.setId(rs.getInt("retailerId"));
				retailer.setRetailerName(rs.getString("retailerName"));
				retailer.setGroup(group);
				Article a = new Article();
				a.setId(rs.getInt("articleId"));
				a.setName(rs.getString("articleName"));
				a.setQuantity(rs.getInt("articleQuantity"));
				a.setUnit(rs.getString("articleUnit"));
				a.setOwnerId(rs.getInt("articleOwnerId"));
				a.setCreationDat(rs.getTimestamp("articleCreationDat"));
				a.setModDat(rs.getTimestamp("articleModDat"));
				a.setCheckBoolean(rs.getBoolean("articleBoolean"));
				a.setFav(rs.getBoolean("articleFav"));
				a.setDelDat(rs.getTimestamp("articleDelDat"));
				a.setRetailer(retailer);

				result.addElement(a);
			}
		} catch (SQLException e2) {
			ServersideSettings.getLogger().severe(e2.getMessage());
			throw new DatabaseException(e2);
		}
		return result;
	}

}
