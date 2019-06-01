package de.hdm.itprojektss19.team03.scart.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import de.hdm.itprojektss19.team03.scart.server.db.DBConnection;
import de.hdm.itprojektss19.team03.scart.shared.bo.Retailer;

/**
 * 
 * Die Mapper Klasse bildet ein Objekt bidirektional auf eine reationale
 * Datenbank ab.
 * 
 * @author Marco Dell'Oso, Patrick Lehle
 * @author Thies
 *
 */
public class RetailerMapper {

	/**
	 * Singleton Klasse -> es gibt immer nur eine Instanz dieser Klasse.
	 */
	private static RetailerMapper retailerMapper = null;

	/**
	 * Protected Construcor
	 */
	protected RetailerMapper() {
	}

	/**
	 * Statische Methode, die die Single Eingenschaft sicherstellt, indem sie nur
	 * eine neue Instanz erstellt, wenn keine vorhanden ist.
	 * 
	 * @return RetailerMapper-Objekt
	 */
	public static RetailerMapper retailerMapper() {
		if (retailerMapper == null) {
			retailerMapper = new RetailerMapper();
		}

		return retailerMapper;
	}

	/**
	 * 
	 * Sucht eine Retailer anhand seiner ID
	 * 
	 * @param zu Suchende id
	 * @return Das Retailer-Objekt, falls ein passendes gefunden wurde.
	 */
	public Retailer findByKey(int id) {

		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();

		try {
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery("SELECT id, name FROM retailer WHERE id=" + id);

			// Es darf nur ein Ergebinis gefunden werden, da id der Prim�rschl�ssel ist
			if (rs.next()) {
				Retailer retailer = new Retailer();
				retailer.setId(rs.getInt("id"));
				retailer.setRetailerName(rs.getString("name"));
				return retailer;
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
			return null;
		}
		return null;
	}

	/**
	 * Sucht alle Retailers
	 * 
	 * @return Vector mit allen gefundenen Retailers
	 */
	public Vector<Retailer> findAll() {
		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();

		Vector<Retailer> retailers = new Vector<Retailer>();

		try {
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery("SELECT id, name FROM retailer");

			// Neues retailer Objekt f�r jede gefundene ID
			while (rs.next()) {
				Retailer retailer = new Retailer();
				retailer.setId(rs.getInt("id"));
				retailer.setRetailerName(rs.getString("name"));

				retailers.addElement(retailer);
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		return retailers;
	}
	
	/**
	 * F�gt in der Datenbank einen neuen Retailer ein
	 * 
	 * @param Retailer-Objekt das in die DB eingef�gt werden soll
	 * @return Die Eingef�gte Retailer mit aktueller ID
	 */
	public Retailer insert(Retailer retailer) {
		
		Connection con = null;
		PreparedStatement stmt = null;
		String maxIdSQL = "SELECT MAX(id) AS maxid FROM retailer";
		String insertSQL = "INSERT INTO retailer (id, name) VALUES (?,?)";

		try {
			con = DBConnection.connection();
			stmt = con.prepareStatement(maxIdSQL);
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next()) {
				retailer.setId(rs.getInt("maxid") + 1);
			}
			stmt = con.prepareStatement(insertSQL);
			stmt.setInt(1, retailer.getId());
			stmt.setString(2, retailer.getRetailerName());
			stmt.executeUpdate();

		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		return retailer;
	}

	/**
	 * �ndert einen Retailer in der Datenbank
	 * 
	 * @param Zu �ndernder Retailer
	 * @return Ge�nderter Retailer
	 */
	public Retailer update(Retailer retailer) {

		Connection con = null;
		PreparedStatement stmt = null;
		String update = "UPDATE retailer SET name=? WHERE id=?";

		try {
			con = DBConnection.connection();
			stmt = con.prepareStatement(update);

			stmt.setString(1, retailer.getRetailerName());
			stmt.setInt(2, retailer.getId());
			stmt.executeUpdate();
		}
		catch (SQLException e2) {
			e2.printStackTrace();
		}
		return retailer;
	}
	
	/**
	 * L�scht einen Retailer aus der Datenbank
	 * 
	 * @param Zu l�schender Retailer
	 */
	public void delete(Retailer retailer) {
		Connection con = DBConnection.connection();
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM retailer WHERE id=" + retailer.getId());

		} catch (SQLException e2) {
			e2.printStackTrace();
		}
	}
	
	public Retailer findById(int id) {

		Connection con = null;
		PreparedStatement stmt = null;
		String selectByKey = "SELECT * FROM retailer WHERE id=? ORDER BY id";

		try {
			con = DBConnection.connection();
			stmt = con.prepareStatement(selectByKey);
			stmt.setInt(1, id);

			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				Retailer r = new Retailer();
				r.setId(rs.getInt("id"));
				r.setRetailerName(rs.getString("name"));
				return r;
			}
		}
		catch (SQLException e2) {
			e2.printStackTrace();
		}
		return null;
	}

}
