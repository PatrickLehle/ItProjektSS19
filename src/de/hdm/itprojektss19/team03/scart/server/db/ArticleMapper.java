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
import de.hdm.itprojektss19.team03.scart.server.db.DBConnection;
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
	 * 
	 * Sucht einen Artikel anhand seiner ID
	 * 
	 * @param zu
	 *            Suchende id
	 * @return Das Artikel-Objekt, falls ein passendes gefunden wurde.
	 * @throws SQLException
	 */
	public Article findByKey(int id) throws SQLException {
		Connection con = DBConnection.connection();

		try {
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM article WHERE id=" + id);

			// Nur EIN Ergebnis, da id =PRIMARY-KEY
			if (rs.next()) {
				Article article = new Article();
				article.setId(rs.getInt("id"));
				article.setName(rs.getString("name"));
				article.setQuantity(rs.getInt("quantity"));
				article.setUnit(rs.getString("unit"));
				article.setRetailerId(rs.getInt("retailerId"));
				article.setCreationDat(rs.getTimestamp("creationDat"));
				article.setModDat(rs.getTimestamp("modDat"));
				article.setCheckBoolean(rs.getBoolean("boolean"));
				article.setDelDat(rs.getTimestamp("delDat"));

				return article;
			}
		} catch (SQLException e2) {
			ServersideSettings.getLogger().severe(e2.getMessage());
			throw e2;
		}
		return null;
	}

	/**
	 * Sucht alle Artikel
	 * 
	 * @return Vector mit allen gefundenen Artikeln
	 * @throws SQLException
	 */
	public Vector<Article> findAll() throws SQLException {

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
				a.setId(rs.getInt("id"));
				a.setName(rs.getString("name"));
				a.setQuantity(rs.getInt("quantity"));
				a.setUnit(rs.getString("unit"));
				a.setCreationDat(rs.getTimestamp("creationDat"));
				a.setModDat(rs.getTimestamp("modDat"));
				a.setCheckBoolean(rs.getBoolean("boolean"));
				a.setDelDat(rs.getTimestamp("delDat"));
				articles.addElement(a);
			}
		} catch (SQLException e2) {
			ServersideSettings.getLogger().severe(e2.getMessage());
			throw e2;
		}
		return articles;
	}

	public Vector<Article> findArticleByName(String name, Article a) throws SQLException {
		Connection con = null;
		PreparedStatement stmt = null;

		String select = "SELECT * FROM article WHERE name=?";

		Vector<Article> result = new Vector<Article>();

		try {
			con = DBConnection.connection();
			stmt = con.prepareStatement(select);
			stmt.setString(1, name);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Article article = new Article();
				article.setId(rs.getInt("id"));
				article.setName(rs.getString("name"));
				article.setQuantity(rs.getInt("quantity"));
				article.setUnit(rs.getString("unit"));
				article.setRetailerId(rs.getInt("retailerId"));
				article.setCreationDat(rs.getTimestamp("creationDat"));
				article.setModDat(rs.getTimestamp("modDat"));
				article.setCheckBoolean(rs.getBoolean("boolean"));
				article.setDelDat(rs.getTimestamp("delDat"));

				result.addElement(a);
			}
		} catch (SQLException e2) {
			ServersideSettings.getLogger().severe(e2.getMessage());
			throw e2;
		}
		return result;
	}

	/**
	 * Sucht alle Artikel, die einem bestimmten Retailer haben, anhand der ID
	 * 
	 * @param Die
	 *            ID des Retailers
	 * @return Vector mit allen gefunden Artikeln des Retailers
	 * @throws SQLException
	 */
	public Vector<Article> findArticleByRetailerId(int retailerId) throws SQLException {
		Connection con = DBConnection.connection();

		Vector<Article> articles = new Vector<Article>();

		try {
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM article WHERE retailerId=" + retailerId);

			// Hier wird fuer jeden gefundenen Article immer ein neues Object erstellt
			while (rs.next()) {
				Article article = new Article();
				article.setId(rs.getInt("id"));
				article.setName(rs.getString("name"));
				article.setQuantity(rs.getInt("quantity"));
				article.setUnit("unit");
				article.setRetailerId(rs.getInt("retailerId"));
				article.setCreationDat(rs.getTimestamp("creationDat"));
				article.setModDat(rs.getTimestamp("modDat"));
				article.setCheckBoolean(rs.getBoolean("boolean"));
				article.setDelDat(rs.getTimestamp("delDat"));
				articles.addElement(article);
			}
		} catch (SQLException e2) {
			ServersideSettings.getLogger().severe(e2.getMessage());
			throw e2;
		}
		// Hier wird ein Vector mit allen Articlen die gefunden wurden zurueck gegeben
		return articles;
	}

	/**
	 * F�gt in der Datenbank einen neuen Artikel ein
	 * 
	 * @param Artikel-Objekt
	 *            das in die DB eingef�gt werden soll
	 * @return Der Eingef�gte Artikel mit aktueller ID
	 * @throws SQLException
	 */
	public Article insert(Article article) throws SQLException {

		Connection con = null;
		PreparedStatement stmt = null;
		String maxIdSQL = "SELECT MAX(id) AS maxid FROM article";

		String insert = "INSERT INTO article (id, name, quantity, unit, retailerId, creationDat, modDat, boolean) VALUES (?,?,?,?,?,?,?,?)";

		try {
			con = DBConnection.connection();
			stmt = con.prepareStatement(maxIdSQL);

			// MAX ID Query ausfuehren
			ResultSet rs = stmt.executeQuery();

			// ...um diese dann um 1 inkrementiert der ID des BO zuzuweisen
			if (rs.next()) {
				article.setId(rs.getInt("maxid") + 1);
			}
			// Jetzt erfolgt der Insert
			stmt = con.prepareStatement(insert);

			// Setzen der ? Platzhalter als Values
			stmt.setInt(1, article.getId());
			stmt.setString(2, article.getName());
			stmt.setInt(3, article.getQuantity());
			stmt.setString(4, article.getUnit());
			stmt.setInt(5, article.getRetailerId());
			stmt.setTimestamp(6, article.getCreationDat());
			stmt.setTimestamp(7, article.getModDat());
			stmt.setBoolean(8, article.getCheckBoolean());
			// DelDat wird bei dem anlegen eines Artikels nicht hinterlegt

			// INSERT-Query ausfuehren
			stmt.executeUpdate();

		} catch (SQLException e2) {
			ServersideSettings.getLogger().severe(e2.getMessage());
			throw e2;
		}
		return article;
	}

	/**
	 * �ndert einen Artikel in der Datenbank
	 * 
	 * @param Zu
	 *            �ndernder Artikel
	 * @return Ge�nderter Artikel
	 * @throws SQLException
	 */
	public Article update(Article article) throws SQLException {

		Connection con = null;
		PreparedStatement stmt = null;

		String update = "UPDATE article SET name=?, quantity=?, unit=?, retailerId=?, modDat=?, boolean=?, delDat=? WHERE id=?";

		try {
			con = DBConnection.connection();
			stmt = con.prepareStatement(update);

			stmt.setString(1, article.getName());
			stmt.setInt(2, article.getQuantity());
			stmt.setString(3, article.getUnit());
			stmt.setInt(4, article.getRetailerId());
			stmt.setTimestamp(5, article.getModDat());
			stmt.setInt(6, article.getId());
			stmt.setBoolean(7, article.getCheckBoolean());
			stmt.setTimestamp(8, article.getDelDat()); // Mit der Update-Methode sollte kein Artikel als geloescht
														// markiert werden, denn
														// das lokale Artikel-Objekt wird dadurch aber nicht veraendert.

			stmt.executeUpdate();

		} catch (SQLException e2) {
			ServersideSettings.getLogger().severe(e2.getMessage());
			throw e2;
		}
		return article;
	}

	/**
	 * L�scht einen Artikel aus der Datenbank
	 * 
	 * @param Zu
	 *            l�schender Artikel
	 * @throws SQLException
	 */
	public Article delete(Article article) throws SQLException {
		Connection con = DBConnection.connection();

		PreparedStatement stmt = null;

		String update = "UPDATE article SET name=?, quantity=?, unit=?, retailerId=?, modDat=?, boolean=?, delDat=? WHERE id=?";

		try {
			/*
			 * alte delete Mthode Statement stmt = con.createStatement();
			 * stmt.executeUpdate("DELETE FROM article WHERE id=" + article.getId());
			 */
			article.setDelDat(new Timestamp(new Date().getTime()));
			con = DBConnection.connection();
			stmt = con.prepareStatement(update);

			stmt.setString(1, article.getName());
			stmt.setInt(2, article.getQuantity());
			stmt.setString(3, article.getUnit());
			stmt.setInt(4, article.getRetailerId());
			stmt.setTimestamp(5, article.getModDat());
			stmt.setInt(6, article.getId());
			stmt.setBoolean(7, article.getCheckBoolean());
			stmt.setTimestamp(8, article.getDelDat());

			stmt.executeUpdate();

			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM article WHERE id=" + article.getId());

			// Nur EIN Ergebnis, da id =PRIMARY-KEY
			if (rs.next()) {
				Article returnArticle = new Article();
				returnArticle.setId(rs.getInt("id"));
				returnArticle.setName(rs.getString("name"));
				returnArticle.setQuantity(rs.getInt("quantity"));
				returnArticle.setUnit(rs.getString("unit"));
				returnArticle.setRetailerId(rs.getInt("retailerId"));
				returnArticle.setCreationDat(rs.getTimestamp("creationDat"));
				returnArticle.setModDat(rs.getTimestamp("modDat"));
				returnArticle.setCheckBoolean(rs.getBoolean("boolean"));
				returnArticle.setDelDat(rs.getTimestamp("delDat"));

				return returnArticle;
			}

		} catch (SQLException e2) {
			ServersideSettings.getLogger().severe(e2.getMessage());
			throw e2;
		}
		return null;
	}

	/**
	 * Gibt alle Article in einem Interval zweier Timestamps zurueck
	 * 
	 * @param start
	 * @param end
	 * @return
	 * @throws SQLException
	 */
	public Vector<Article> findAllArticleByDate(Timestamp start, Timestamp end) throws SQLException {
		Connection con = DBConnection.connection();

		Vector<Article> result = new Vector<Article>();

		try {
			Statement stmt = con.createStatement();
			// Query das zwei Timestamps als interval aller Article darin zurueck gibt
			ResultSet rs = stmt
					.executeQuery("SELECT * FROM article WHERE creationDat BETWEEN '" + start + "' AND '" + end + "'");
			while (rs.next()) {
				Article a = new Article();
				a.setId(rs.getInt("id"));
				a.setName(rs.getString("name"));
				a.setQuantity(rs.getInt("quantity"));
				a.setUnit("unit");
				a.setRetailerId(rs.getInt("retailerId"));
				a.setCreationDat(rs.getTimestamp("creationDat"));
				a.setModDat(rs.getTimestamp("modDat"));
				a.setCheckBoolean(rs.getBoolean("boolean"));
				a.setDelDat(rs.getTimestamp("delDat"));

				result.addElement(a);
			}
		} catch (SQLException e2) {
			ServersideSettings.getLogger().severe(e2.getMessage());
			throw e2;
		}
		return result;
	}

	/**
	 * @bastiantilk, PatrickLehle Gibt alle Artikel eines Retailers in einem
	 * Zeitraum zurueck
	 * 
	 * @param start
	 * @param end
	 * @param r
	 * @return Vektor aller Artikel des Retailers in dem Zeitraum
	 * @throws SQLException
	 */
	public Vector<Article> findAllArticleByDateRetailer(int id, Timestamp start, Timestamp end) throws SQLException {
		// DB Connection aufbauen
		Connection con = DBConnection.connection();

		Vector<Article> result = new Vector<Article>();

		try {
			PreparedStatement stmt = con.prepareStatement("SELECT * FROM article WHERE retailerId=?=");
			stmt.setInt(1, id);
			// Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery();

			// ResultSet rs = stmt.executeQuery("SELECT * FROM article WHERE creationDat
			// BETWEEN '"+start+"' AND '"+end+"' AND retailerId=" + r.getId());
			while (rs.next()) {
				Article a = new Article();
				a.setId(rs.getInt("id"));
				a.setName(rs.getString("name"));
				a.setQuantity(rs.getInt("quantity"));
				a.setUnit("unit");
				a.setRetailerId(rs.getInt("retailerId"));
				a.setCreationDat(rs.getTimestamp("creationDat"));
				a.setModDat(rs.getTimestamp("modDat"));
				a.setCheckBoolean(rs.getBoolean("boolean"));
				a.setDelDat(rs.getTimestamp("delDat"));

				result.addElement(a);
			}
		} catch (SQLException e2) {
			ServersideSettings.getLogger().severe(e2.getMessage());
			throw e2;
		}
		return result;
	}

	public Vector<Article> findAllArticleByGroup(User u, Group g) throws SQLException {
		Connection con = DBConnection.connection();

		Vector<Article> result = new Vector<Article>();
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(
					"SELECT article.id, article.name, article.quantity, article.unit, article.creationDat, article.modDat, article.delDat FROM article, groups WHERE userId="
							+ u.getId() + "AND groupId=" + g.getId());

			while (rs.next()) {
				Article a = new Article();
				a.setId(rs.getInt("id"));
				a.setName(rs.getString("name"));
				a.setQuantity(rs.getInt("quantity"));
				a.setUnit("unit");
				a.setRetailerId(rs.getInt("retailerId"));
				a.setCreationDat(rs.getTimestamp("creationDat"));
				a.setModDat(rs.getTimestamp("modDat"));
				a.setCheckBoolean(rs.getBoolean("boolean"));
				a.setDelDat(rs.getTimestamp("delDat"));

				result.addElement(a);
			}
		} catch (SQLException e2) {
			ServersideSettings.getLogger().severe(e2.getMessage());
			throw e2;
		}
		return result;
	}

}
