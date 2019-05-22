package de.hdm.itprojektss19.team03.scart.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

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
					.executeQuery("SELECT id, name FROM retaileres WHERE id=" + id);

			// Es darf nur ein Ergebinis gefunden werden, da id der Primärschlüssel ist
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

			// Neues retailer Objekt für jede gefundene ID
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
	 * Fügt in der Datenbank einen neuen Retailer ein
	 * 
	 * @param Retailer-Objekt das in die DB eingefügt werden soll
	 * @return Die Eingefügte Retailer mit aktueller ID
	 */
	public Retailer insert(Retailer retailer) {
		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();
		try {
			Statement stmt = con.createStatement();

			// Suche die aktuell höchsten ID
			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid " + "FROM retailers ");

			if (rs.next()) {
				// Höchste ID um 1 erhöhen, um nächste ID zu erhalten
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
	 * Ändert einen Retailer in der Datenbank
	 * 
	 * @param Zu ändernder Retailer
	 * @return Geänderter Retailer
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
	 * Löscht einen Retailer aus der Datenbank
	 * 
	 * @param Zu löschender Retailer
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

}
