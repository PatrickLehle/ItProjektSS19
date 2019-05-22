package de.hdm.itprojektss19.team03.scart.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import de.hdm.itprojektss19.team03.scart.shared.bo.Unit;
import de.hdm.itprojektss19.team03.scart.shared.bo.Retailer;
import de.hdm.itprojektss19.team03.scart.shared.bo.Unit;

/**
 * 
 * Die Mapper Klasse bildet ein Objekt bidirektional auf eine reationale
 * Datenbank ab.
 * 
 * @author Marco Dell'Oso, Thies
 * TODO Unit Object hinzufügen, soblad der Unit Mapper fertig is
 *
 */
public class UnitMapper {

	/**
	 * Singleton Klasse -> es gibt immer nur eine Instanz dieser Klasse.
	 */
	private static UnitMapper unitMapper = null;

	/**
	 * Protected Construcor
	 */
	protected UnitMapper() {
	}

	/**
	 * Statische Methode, die die Single Eingenschaft sicherstellt, indem sie nur
	 * eine neue Instanz erstellt, wenn keine vorhanden ist.
	 * 
	 * @return UnitMapper-Objekt
	 */
	public static UnitMapper unitMapper() {
		if (unitMapper == null) {
			unitMapper = new UnitMapper();
		}

		return unitMapper;
	}

	/**
	 * 
	 * Sucht eine Unit anhand seiner ID
	 * 
	 * @param zu Suchende id
	 * @return Das Unit-Objekt, falls ein passendes gefunden wurde.
	 */
	public Unit findByKey(int id) {

		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();

		try {
			Statement statement = con.createStatement();
			ResultSet rs = statement
					.executeQuery("SELECT id, name FROM unites WHERE id=" + id);

			// Es darf nur ein Ergebinis gefunden werden, da id der Primärschlüssel ist
			if (rs.next()) {
				Unit unit = new Unit();
				unit.setId(rs.getInt("id"));
				unit.setUnitName(rs.getString("name"));

				return unit;
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
			return null;
		}

		return null;

	}

	/**
	 * Sucht alle Units
	 * 
	 * @return Vector mit allen gefundenen Units
	 */
	public Vector<Unit> findAll() {
		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();

		Vector<Unit> units = new Vector<Unit>();

		try {
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery("SELECT id, name FROM units");

			// Neues unit Objekt für jede gefundene ID
			while (rs.next()) {
				Unit unit = new Unit();
				unit.setId(rs.getInt("id"));
				unit.setUnitName(rs.getString("name"));

				units.addElement(unit);
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		return units;
	}

	
	/**
	 * Fügt in der Datenbank einen neuen Unit ein
	 * 
	 * @param Unit-Objekt das in die DB eingefügt werden soll
	 * @return Die Eingefügte Unit mit aktueller ID
	 */
	public Unit insert(Unit unit) {
		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();
		try {
			Statement stmt = con.createStatement();

			// Suche die aktuell höchsten ID
			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid " + "FROM units ");

			if (rs.next()) {
				// Höchste ID um 1 erhöhen, um nächste ID zu erhalten
				unit.setId(rs.getInt("maxid") + 1);
				stmt = con.createStatement();
				stmt.executeUpdate("INSERT INTO units (id, name) " + "VALUES (" + unit.getId()
						+ "," + unit.getUnitName() + ")");
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		return unit;
	}

	/**
	 * Ändert einen Unit in der Datenbank
	 * 
	 * @param Zu ändernder Unit
	 * @return Geänderter Unit
	 */
	public Unit update(Unit unit) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("UPDATE units " + "SET name=\"" + unit.getUnitName() + "WHERE id=" + unit.getId());

		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		return unit;
	}
	
	/**
	 * Löscht einen Unit aus der Datenbank
	 * 
	 * @param Zu löschender Unit
	 */
	public void delete(Unit unit) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("DELETE FROM units " + "WHERE id=" + unit.getId());

		} catch (SQLException e2) {
			e2.printStackTrace();
		}
	}

}
