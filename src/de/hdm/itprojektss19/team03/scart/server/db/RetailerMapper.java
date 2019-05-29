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
 * @author Marco Dell'Oso, Thies
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
			ResultSet rs = statement
					.executeQuery("SELECT id, name FROM retailers WHERE id=" + id);

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
			ResultSet rs = statement.executeQuery("SELECT id, name FROM retailers");

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
		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();
		try {
			Statement stmt = con.createStatement();

			// Suche die aktuell h�chsten ID
			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid " + "FROM retailers ");

			if (rs.next()) {
				// H�chste ID um 1 erh�hen, um n�chste ID zu erhalten
				retailer.setId(rs.getInt("maxid") + 1);
				stmt = con.createStatement();
				stmt.executeUpdate("INSERT INTO retailers (id, name) " + "VALUES (" + retailer.getId()
						+ "," + retailer.getRetailerName() + ")");
			}
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
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("UPDATE retailers " + "SET name=\"" + retailer.getRetailerName() + "WHERE id=" + retailer.getId());

		} catch (SQLException e2) {
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

			stmt.executeUpdate("DELETE FROM retailers " + "WHERE id=" + retailer.getId());

		} catch (SQLException e2) {
			e2.printStackTrace();
		}
	}
	
	public Retailer findById(int id) {

		Connection con = null;
		PreparedStatement stmt = null;

		// Query fuer den Select
		String selectByKey = "SELECT * FROM retailer WHERE id=? ORDER BY id";

		try {
			con = DBConnection.connection();
			stmt = con.prepareStatement(selectByKey);
			stmt.setInt(1, id);

			// Execute SQL Statement
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {

				// Ergebnis-Tupel in Objekt umwandeln
				Retailer kl = new Retailer();

				// Setzen der Attribute den Datensaetzen aus der DB entsprechend
				kl.setId(rs.getInt("id"));
				kl.setRetailerName(rs.getString("RetailerName"));
				kl.setRetailerId(rs.getInt("retailerid"));

				return kl;
			}
		}

		catch (SQLException e2) {
			e2.printStackTrace();
		}

		return null;
	}

}
