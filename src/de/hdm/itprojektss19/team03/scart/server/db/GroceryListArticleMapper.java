package de.hdm.itprojektss19.team03.scart.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import de.hdm.itprojektss19.team03.scart.server.ServersideSettings;
import de.hdm.itprojektss19.team03.scart.shared.DatabaseException;
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
	 * Geschützer Konstruktor der dafuer sorgt , dass es nicht mehrere Instanzen
	 * dieser Klasse gibt - Singleton
	 */
	protected GroceryListArticleMapper() {

	}

	/**
	 * Pruefung ob eine Instanz der Klasse schon existiert. Methoden dieser Klasse
	 * sollen nur ueber diese statische Methode aufgerufen werden
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
	 * <code>GroceryList</code>. DIe ZUordnung wird durch einen zusammengesetzten
	 * Primaerschluessel realisiert
	 * 
	 * @param gl fuer das GroceryListobjekt
	 * @param a  fuer das Articleobjekt
	 * 
	 * @return null
	 * @throws DatabaseException Entsteht durch ein Attribut, dass nicht in der Datanbank vorhanden ist aber dennoch gesetzt wurde.
	 */
	public GroceryListArticle addArticleToGroceryList(GroceryList gl, Article a) throws DatabaseException {

		Connection con = null;
		PreparedStatement stmt = null;
		String article = "INSERT INTO grocerylistarticle (grocerylistId, articleId) VALUES (?,?)";

		try {
			System.out.println(a.getId() + " " + gl.getId());
			con = DBConnection.connection();
			stmt = con.prepareStatement(article);
			stmt.setInt(1, gl.getId());
			stmt.setInt(2, a.getId());
			stmt.executeUpdate();

		} catch (SQLException e2) {
			ServersideSettings.getLogger().severe(e2.getMessage());
			throw new DatabaseException(e2);
		}
		return null;
	}

	/**
	 * Loeschen eines Articles zu einer zugewiesenen GroceryList.
	 * 
	 * @param gl beschreibt ein GroceryList-Objekt
	 * @param a beschereibt Article-Objekt
	 * @throws DatabaseException Entsteht durch ein Attribut, dass nicht in der Datanbank vorhanden ist aber dennoch gesetzt wurde.
	 */
	public void removeArticleFromGroceryList(GroceryList gl, Article a) throws DatabaseException {

		Connection con = null;
		PreparedStatement stmt = null;

		// SQL-Anweisung zum loeschen des Tupels aus der DB
		String deleteSQL = "DELETE FROM grocerylistarticle WHERE grocerylistId=? AND articleId =?";

		try {
			con = DBConnection.connection();
			stmt = con.prepareStatement(deleteSQL);
			stmt.setInt(1, gl.getId());
			stmt.setInt(2, a.getId());
			stmt.executeUpdate();
		} catch (SQLException e2) {
			ServersideSettings.getLogger().severe(e2.getMessage());
			throw new DatabaseException(e2);
		}
	}

	public Vector<Article> findAllArticleByGroceryListId(int grocerylistId) throws DatabaseException {

		Connection con = null;
		PreparedStatement stmt = null;

		// SQL-Anweisung zum auslesen der Tupel aus der DB
		String selectByKey = "SELECT * FROM grocerylistarticle JOIN article ON grocerylistarticle.articleId = article.articleId JOIN grocerylist ON grocerylistarticle.grocerylistId "
				+ "= grocerylist.groceryListId JOIN retailer ON retailer.retailerId = article.articleId WHERE grocerylistarticle.grocerylistId= "
				+ grocerylistId;

		Vector<Article> result = new Vector<Article>();

		try {
			con = DBConnection.connection();
			stmt = con.prepareStatement(selectByKey);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Article a = new Article();
				a.setId(rs.getInt("articleId"));
				a.setName(rs.getString("articleName"));
				a.setCreationDat(rs.getTimestamp("articleCreationDat"));
				a.setModDat(rs.getTimestamp("articleModDat"));
				a.setCheckBoolean(rs.getBoolean("articleBoolean"));
				a.setRetailerId(rs.getInt("articleRetailerId"));
				a.setQuantity(rs.getInt("articleQuantity"));
				a.setUnit(rs.getString("articleUnit"));
				a.setDelDat(rs.getTimestamp("articleDelDat"));
				a.setOwnerId(rs.getInt("articleOwnerId"));
				a.setFav(rs.getBoolean("articleFav"));
				a.setGroupId(rs.getInt("articleGroupId"));
				a.setRetailerName(rs.getString("retailerName"));
				result.addElement(a);
			}
			return result;
		} catch (SQLException e2) {
			ServersideSettings.getLogger().severe(e2.getMessage());
			throw new DatabaseException(e2);
		}
	}

	/**
	 * Entfernt einen ausgewaehlten Article aus allen GroceryLists
	 * 
	 * @param a beschreibt ein Aritcle Objekt
	 *            das zu loeschende Objekt von allen existierenden Listen
	 * @throws DatabaseException Entsteht durch ein Attribut, dass nicht in der Datanbank vorhanden ist aber dennoch gesetzt wurde.
	 */
	public void deleteArticleFromAllLists(Article a) throws DatabaseException {

		Connection con = null;
		PreparedStatement stmt = null;
		String deleteSQL = "DELETE FROM grocerylistarticle WHERE articleId=?";

		try {
			con = DBConnection.connection();
			stmt = con.prepareStatement(deleteSQL);
			stmt.setInt(1, a.getId());
			stmt.executeUpdate();
		} catch (SQLException e2) {
			ServersideSettings.getLogger().severe(e2.getMessage());
			throw new DatabaseException(e2);
		}
	}
}
