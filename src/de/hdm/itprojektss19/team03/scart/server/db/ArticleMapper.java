package de.hdm.itprojektss19.team03.scart.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import de.hdm.itprojektss19.team03.scart.shared.bo.Article;
import de.hdm.itprojektss19.team03.scart.shared.bo.Retailer;

/**
 * 
 * Die Mapper Klasse bildet ein Objekt bidirektional auf eine reationale
 * Datenbank ab.
 * 
 * @author Marco Dell'Oso
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

		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();

		try {
			Statement statement = con.createStatement();
			ResultSet rs = statement
					.executeQuery("SELECT id, name, quantity, retailer, unit FROM Articles WHERE id=" + id);

			// Es darf nur ein Ergebinis gefunden werden, da id der Primärschlüssel ist
			if (rs.next()) {
				Article article = new Article();
				article.setId(rs.getInt("id"));
				article.setName(rs.getString("name"));
				article.setQuantity(rs.getInt("quantity"));
				article.setRetailerId(rs.getInt("retailer"));
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
		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();

		Vector<Article> articles = new Vector<Article>();

		try {
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery("SELECT id, name, quantity, retailer, unit FROM articles");

			// Neues article Objekt für jede gefundene ID
			while (rs.next()) {
				Article article = new Article();
				article.setId(rs.getInt("id"));
				article.setName(rs.getString("name"));
				article.setQuantity(rs.getInt("quantity"));
				article.setRetailerId(rs.getInt("retailer"));

				articles.addElement(article);
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		return articles;
	}

	/**
	 * Sucht alle Artikel, die einem bestimmten Retailer haben, anhand der ID
	 * 
	 * @param Die ID des Retailers
	 * @return Vector mit allen gefunden Artikeln des Retailers
	 */
	public Vector<Article> findByRetailerId(int retailerId) {
		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();

		Vector<Article> articles = new Vector<Article>();

		try {
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery(
					"SELECT id, name, quantity, retailer, unit FROM articles" + "WHERE retailer=" + retailerId);

			// Neues article Objekt für jede gefundene ID
			while (rs.next()) {
				Article article = new Article();
				article.setId(rs.getInt("id"));
				article.setName(rs.getString("name"));
				article.setQuantity(rs.getInt("quantity"));
				article.setRetailerId(rs.getInt("retailer"));

				articles.addElement(article);
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		return articles;
	}

	/**
	 * Sucht alle Artikel, die einem bestimmten Retailer haben
	 * 
	 * @param Retailer Objekt
	 * @return Vector mit allen gefunden Artikeln des Retailers
	 */
	public Vector<Article> findByRetailer(Retailer retailer) {
		return findByRetailerId(retailer.getId());
	}

	/**
	 * Fügt in der Datenbank einen neuen Artikel ein
	 * 
	 * @param Artikel-Objekt das in die DB eingefügt werden soll
	 * @return Der Eingefügte Artikel mit aktueller ID
	 */
	public Article insert(Article article) {
		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();
		try {
			Statement stmt = con.createStatement();

			// Suche die aktuell höchsten ID
			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid " + "FROM articles ");

			if (rs.next()) {
				// Höchste ID um 1 erhöhen, um nächste ID zu erhalten
				article.setId(rs.getInt("maxid") + 1);
				stmt = con.createStatement();
				stmt.executeUpdate("INSERT INTO articles (id, name, quantity, retailer) " + "VALUES (" + article.getId()
						+ "," + article.getRetailerId() + ")");
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		return article;
	}

	/**
	 * Ändert einen Artikel in der Datenbank
	 * 
	 * @param Zu ändernder Artikel
	 * @return Geänderter Artikel
	 */
	public Article update(Article article) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("UPDATE articles " + "SET name=\"" + article.getName() + "\" " + "SET quantity=\""
					+ article.getQuantity() + "\" " + "SET retailer=\"" + article.getRetailerId() + "\" " + "\" "
					+ "WHERE id=" + article.getId());

		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		return article;
	}
	
	/**
	 * Löscht einen Artikel aus der Datenbank
	 * 
	 * @param Zu löschender Artikel
	 */
	public void delete(Article article) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("DELETE FROM articles " + "WHERE id=" + article.getId());

		} catch (SQLException e2) {
			e2.printStackTrace();
		}
	}

}
