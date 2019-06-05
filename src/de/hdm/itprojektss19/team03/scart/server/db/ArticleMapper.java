package de.hdm.itprojektss19.team03.scart.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Vector;

import de.hdm.itprojektss19.team03.scart.server.db.DBConnection;
import de.hdm.itprojektss19.team03.scart.shared.bo.Article;
import de.hdm.itprojektss19.team03.scart.shared.bo.Retailer;
import de.hdm.itprojektss19.team03.scart.shared.bo.User;

/**
 * 
 * Die Mapper Klasse bildet ein Objekt bidirektional auf eine reationale
 * Datenbank ab.
 * 
 * @author Marco Dell'Oso, PatrickLehle, BastianTilk(findAllArticleByDateRetailer)
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
	 * @param zu Suchende id
	 * @return Das Artikel-Objekt, falls ein passendes gefunden wurde.
	 */
	public Article findByKey(int id) {
		Connection con = DBConnection.connection();

		try {
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM article WHERE id=" + id);

			//Nur EIN Ergebnis, da id =PRIMARY-KEY
			if (rs.next()) {
				Article article = new Article();
				article.setId(rs.getInt("id"));
				article.setName(rs.getString("name"));
				article.setQuantity(rs.getInt("quantity"));
				article.setUnit(rs.getString("unit"));
				article.setRetailerId(rs.getInt("retailerId"));
				article.setCreationDat(rs.getTimestamp("creationDat"));
				article.setModDat(rs.getTimestamp("modDat"));
				
				return article;
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
			return null;
		}
		return null;
	}

	/**
	 * Sucht alle Artikel
	 * 
	 * @return Vector mit allen gefundenen Artikeln
	 */
	public Vector<Article> findAll() {
		
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
	
				articles.addElement(a);
			}
		}
		catch (SQLException e2) {
			e2.printStackTrace();
			return null;
		}
		return articles;
	}
	
	public Vector<Article> findArticleByName(String name, Article a){
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

				result.addElement(a);
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
			return null;
		}
		return result;
	}

	/**
	 * Sucht alle Artikel, die einem bestimmten Retailer haben, anhand der ID
	 * 
	 * @param Die ID des Retailers
	 * @return Vector mit allen gefunden Artikeln des Retailers
	 */
	public Vector<Article> findArticleByRetailerId(int retailerId) {
		Connection con = DBConnection.connection();

		Vector<Article> articles = new Vector<Article>();

		try {
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM article WHERE retailerId=" + retailerId);

			//Hier wird fuer jeden gefundenen Article immer ein neues Object erstellt
			while (rs.next()) {
				Article article = new Article();
				article.setId(rs.getInt("id"));
				article.setName(rs.getString("name"));
				article.setQuantity(rs.getInt("quantity"));
				article.setUnit("unit");
				article.setRetailerId(rs.getInt("retailerId"));
				article.setCreationDat(rs.getTimestamp("creationDat"));
				article.setModDat(rs.getTimestamp("modDat"));
				articles.addElement(article);
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		//Hier wird ein Vector mit allen Articlen die gefunden wurden zurueck gegeben
		return articles;
	}
	
	/**
	 * F�gt in der Datenbank einen neuen Artikel ein
	 * 
	 * @param Artikel-Objekt das in die DB eingef�gt werden soll
	 * @return Der Eingef�gte Artikel mit aktueller ID
	 */
	public Article insert(Article article) {

		Connection con = null;
		PreparedStatement stmt = null;
		String maxIdSQL = "SELECT MAX(id) AS maxid FROM article";

		String insert = "INSERT INTO article (id, name, quantity, unit, retailerId, creationDat, modDat) VALUES (?,?,?,?,?,?,?)";

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
			
			// INSERT-Query ausfuehren
			stmt.executeUpdate();

		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		return article;
	}

	/**
	 * �ndert einen Artikel in der Datenbank
	 * 
	 * @param Zu �ndernder Artikel
	 * @return Ge�nderter Artikel
	 */
	public Article update(Article article) {
		
		Connection con = null;
		PreparedStatement stmt = null;

		String update = "UPDATE article SET name=?, quantity=?, unit=?, retailerId=?, modDat=? WHERE id=?";

		try {
			con = DBConnection.connection();
			stmt = con.prepareStatement(update);
		
			stmt.setString(1, article.getName());
			stmt.setInt(2, article.getQuantity());
			stmt.setString(3, article.getUnit());
			stmt.setInt(4, article.getRetailerId());
			stmt.setTimestamp(5, article.getModDat());
			stmt.setInt(6, article.getId());
			
			stmt.executeUpdate();
		}
		catch (SQLException e2) {
			e2.printStackTrace();
		}
		return article;
	}
	
	/**
	 * L�scht einen Artikel aus der Datenbank
	 * 
	 * @param Zu l�schender Artikel
	 */
	public void delete(Article article) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM article WHERE id=" + article.getId());

		} catch (SQLException e2) {
			e2.printStackTrace();
		}
	}
	/**
	 * Gibt alle Article in einem Interval zweier Timestamps zurueck
	 * @param start
	 * @param end
	 * @return
	 */
	public Vector<Article> findAllArticleByDate(Timestamp start, Timestamp end){
		Connection con = DBConnection.connection();
		
		Vector<Article> result = new Vector<Article>();
		
		try {
			Statement stmt = con.createStatement();
			//Query das zwei Timestamps als interval aller Article darin zurueck gibt
			ResultSet rs = stmt.executeQuery("SELECT * FROM article WHERE creationDat BETWEEN '"+start+ "' AND '"+end+"'");
			while (rs.next()) {
				Article a = new Article();
				a.setId(rs.getInt("id"));
				a.setName(rs.getString("name"));
				a.setQuantity(rs.getInt("quantity"));
				a.setUnit("unit");
				a.setRetailerId(rs.getInt("retailerId"));
				a.setCreationDat(rs.getTimestamp("creationDat"));
				a.setModDat(rs.getTimestamp("modDat"));
				
				result.addElement(a);
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		return result;
	}
	/**
	 * @bastiantilk
	 * Gibt alle Artikel eines Retailers in einem Zeitraum zurueck
	 * @param start
	 * @param end
	 * @param r
	 * @return Vektor aller Artikel des Retailers in dem Zeitraum
	 */
	public Vector<Article> findAllArticleByDateRetailer(Timestamp start, Timestamp end, Retailer r){
		//unfertig
		Connection con = DBConnection.connection();
		
		Vector<Article> result = new Vector<Article>();
		
		try {
			Statement stmt = con.createStatement();
			
			ResultSet rs = stmt.executeQuery("SELECT * FROM article WHERE creationDat BETWEEN '"+start+ "' AND '"+end+"'"+ " AND retailerId=" + r.getId());
			while (rs.next()) {
				Article a = new Article();
				a.setId(rs.getInt("id"));
				a.setName(rs.getString("name"));
				a.setQuantity(rs.getInt("quantity"));
				a.setUnit("unit");
				a.setRetailerId(rs.getInt("retailerId"));
				a.setCreationDat(rs.getTimestamp("creationDat"));
				a.setModDat(rs.getTimestamp("modDat"));
				
				result.addElement(a);
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		return result;
	}
}
