package de.hdm.itprojektss19.team03.scart.server.db;

import java.sql.Connection;
import java.sql.DriverManager;

import com.google.appengine.api.utils.SystemProperty;

/**
 * Klasse zum Datenbankverbindung aufbauen und verwalten
 * 
 * @author Marco Dell'Oso, PatrickLehle
 */
public class DBConnection {

	private static Connection con = null;
	private static String googleUrl = "jdbc:google:mysql://itpss19scart:sontactinstanz/scartdb";
	private static String localUrl = "jdbc:mysql://localhost:8000/ITPROJEKTSS19?user=teat&password=test";


	private static final String username = "test";
	private static final String password = "test";

	/**
	 * Diese Methode gibt die aufgebaute DB-Verbindung zurück
	 * 
	 * @return con
	 */
	public static Connection connection() {
		
		String user = "";
		String pass = "";

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
				} else {
					Class.forName("com.mysql.jdbc.Driver");
					url = localUrl;
					user = "patrick";
					pass = "patrick";
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
				throw new RuntimeException("Does not work!" + e.getMessage().toString()
						+ "Infos: " + user + ", " + pass + ", " + url);
			}
		}
		return con;
	}
}