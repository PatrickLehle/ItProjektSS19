package de.hdm.itprojektss19.team03.scart.server.db;

import java.sql.Connection;
import java.sql.DriverManager;

import com.google.appengine.api.utils.SystemProperty;

/**
 * Klasse zum Datenbankverbindung aufbauen und verwalten
 * 
 * @author Marco Dell'Oso, PatrickLehle, Julian Hofer
 */
public class DBConnection {

	private static Connection con = null;
	private static String googleUrl = "jdbc:google:mysql://norse-decoder-240009:europe-west1:itprojekt-ss19-scart?user=root&password=Login2019";
	private static String localUrl = "jdbc:mysql://localhost:3306/itprojektss19?user=test&password=test";

	private static final String username = "root";
	private static final String password = "Login2019";

	/**
	 * Diese Methode gibt die aufgebaute DB-Verbindung zurück
	 * 
	 * @return con
	 */
	public static Connection connection() {

		String user = "test";
		String pass = "test";

		/**
		 * Wenn es bisher keine Connection zur DB gab, ...
		 */
		if (con == null) {
			String url = null;

			try {
				if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {
					Class.forName("com.mysql.jdbc.GoogleDriver");
					url = googleUrl;
					user = username;
					pass = password;
				} 
					else {
					Class.forName("com.mysql.jdbc.Driver");
					url = localUrl;
					user = "test";
					pass = "test";
				}

				con = DriverManager.getConnection(url, user, pass);

				// Absicherung con = null
				if (con != null) {

					System.out.println("Current Connection:" + con.toString());

				} else {

					System.out.println("No connection available.");

				}
			} catch (Exception e) {
				con = null;
				e.printStackTrace();
				throw new RuntimeException(
						"Does not work!" + e.getMessage().toString() + "Infos: " + user + ", " + pass + ", " + url);
			}
		}
		return con;
	}
}