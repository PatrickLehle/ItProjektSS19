package de.hdm.itprojektss19.team03.scart.server.db;

import java.sql.Connection;
import java.sql.DriverManager;

import com.google.appengine.api.utils.SystemProperty;

/**
 * Klasse zum Datenbankverbindung aufbauen und verwalten
 * 
 * @author Marco Dell'Oso
 * TODO url Pfade anpassen, testen
 */
public class DBConnection {

    private static Connection con = null;

    // URL zur Google Cloud für Produktiv-Betrieb
    private static String googleUrl = "jdbc:google:mysql://norse-decoder-240009:europe-west1:itprojekt-ss19-scart/itprojekt-ss19-scart?user=test&password=test";
    // URL zu localer MYSQL DB für lokale Tests
    private static String localUrl = "jdbc:mysql://127.0.0.1:3306/itprojekt-ss19-scart?user=test&password=test";

    /**
     * Methode zur Herstellung einer Datenbank-Verbindungs-Instanz
     * @return  DBConncetion-Objekt.
     */
    public static Connection connection() {
        if (con == null) {
            String url = null;
            try {
                if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {
                    Class.forName("com.mysql.jdbc.GoogleDriver");
                    url = googleUrl;
                } else {
                    Class.forName("com.mysql.jdbc.Driver");
                    url = localUrl;
                }
                con = DriverManager.getConnection(url);
            } catch (Exception e) {
                con = null;
                e.printStackTrace();
                throw new RuntimeException(e.getMessage());
            }
        }

        return con;
    }

}
