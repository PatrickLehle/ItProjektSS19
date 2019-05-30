package de.hdm.itprojektss19.team03.scart.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import de.hdm.itprojektss19.team03.scart.server.db.DBConnection;
import de.hdm.itprojektss19.team03.scart.shared.bo.Article;
import de.hdm.itprojektss19.team03.scart.shared.bo.GroceryList;
import de.hdm.itprojektss19.team03.scart.shared.bo.GroceryListArticle;

/**
 * 
 * @author PatrickLehle
 *
 */
public class GroceryListArticleMapper {
	
	public static GroceryListArticleMapper groceryListArticleMapper = null;
	
	/**
	 * Geschützer Konstruktor der dafuer sorgt , dass es nicht mehrere
	 * Instanzen dieser Klasse gibt - Singleton
	 */
	protected GroceryListArticleMapper() {
		
	}
	
	/**
	 * Pruefung ob eine Instanz der Klasse schon existiert. Methoden dieser Klasse sollen
	 * nur ueber diese statische Methode aufgerufen werden
	 * 
	 * @return Das <code>GroceryListArticleMapper</code>-Objekt.
	 */
	public static GroceryListArticleMapper groceryListArticleMapper() {
		if (groceryListArticleMapper == null) {
			groceryListArticleMapper = new GroceryListArticleMapper();
		}
		return groceryListArticleMapper;
	}
	
	/**
	 * Zuordnen eines <code>Article</code> Objekts zu einer
	 * <code>GroceryList</code>. DIe ZUordnung wird durch einen
	 * zusammengesetzten Primaerschluessel realisiert 
	 * 
	 * @param gl
	 * 		fuer das GroceryListobjekt
	 * @param a
	 * 		fuer das Articleobjekt
	 * 
	 * @return null
	 */
	public GroceryListArticle addArticleToGroceryList(GroceryList gl, Article a) {

		Connection con = null;
		PreparedStatement stmt = null;
		String article = "INSERT INTO grocerylistarticle (grocerylistid, articleid) VALUES (?,?)";

		try {
			con = DBConnection.connection();
			stmt = con.prepareStatement(article);
			stmt.setInt(1, gl.getId());
			stmt.setInt(2, a.getId());
			stmt.executeUpdate();
			
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		return null;
	}

	/**
	 * Loeschen eines Articles zu einer zugewiesenen GroceryList.
	 * 
	 * @param gl
	 * 		GroceryList-Objekt
	 * @param a
	 * 		Article-Objekt
	 */
	public void removeArticleFromGroceryList(GroceryList gl, Article a) {

		Connection con = null;
		PreparedStatement stmt = null;

		// SQL-Anweisung zum loeschen des Tupels aus der DB
		String deleteSQL = "DELETE FROM grocerylistarticle WHERE grocerylistid=? AND articleid =?";

		try {
			con = DBConnection.connection();
			stmt = con.prepareStatement(deleteSQL);
			stmt.setInt(1, gl.getId());
			stmt.setInt(2, a.getId());
			stmt.executeUpdate();
		}
		catch (SQLException e2) {
			e2.printStackTrace();
		}
	}

	public Vector<Article> findAllArticleByGroceryList(int grocerylistId) {

		Connection con = null;
		PreparedStatement stmt = null;

		// SQL-Anweisung zum auslesen der Tupel aus der DB
		String selectByKey = "SELECT grocerylistarticle.grocerylisteid, grocerylist.name, article.id, article.name, "
				+ "article.creationdate, article.moddate, " + "FROM grocerylistarticle "
				+ "JOIN article " + "ON grocerylistarticle.articleid = article.id " + "JOIN grocerylist "
				+ "ON grocerylistarticle.grocerylistid = grocerylist.id "
				+ "WHERE grocerylistarticle.grocerylistid= " + grocerylistId;

		Vector<Article> result = new Vector<Article>();

		try {
			con = DBConnection.connection();
			stmt = con.prepareStatement(selectByKey);

			ResultSet rs = stmt.executeQuery();
			GroceryList gl = new GroceryList();
			gl.setGroceryListName("name");

			while (rs.next()) {
				Article a = new Article();
				a.setId(rs.getInt("id"));
				a.setName(rs.getString("name"));
				a.setCreationDat(rs.getTimestamp("creationdate"));
				a.setModDat(rs.getTimestamp("moddate"));
				result.addElement(a);
			}
		}
		catch (SQLException e2) {
			e2.printStackTrace();
		}
		return result;
	}

	/**
	 * Entfernt einen Article aus allen GroceryLists durch eine Loeschoperation
	 * eines Articles.
	 * 
	 * @param a
	 * 		das zu loeschende Objekt von allen existierenden Listen
	 */
	public void deleteArticleFromAllLists(Article a) {

		Connection con = null;
		PreparedStatement stmt = null;
		String deleteSQL = "DELETE FROM grocerylistarticle WHERE articleid=?";

		try {
			con = DBConnection.connection();
			stmt = con.prepareStatement(deleteSQL);
			stmt.setInt(1, a.getId());
			stmt.executeUpdate();
		}
		catch (SQLException e2) {
			e2.printStackTrace();
		}
	}

}
