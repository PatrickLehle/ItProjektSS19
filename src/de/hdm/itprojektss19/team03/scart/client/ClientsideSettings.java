package de.hdm.itprojektss19.team03.scart.client;

import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.itprojektss19.team03.scart.shared.CommonSettings;
import de.hdm.itprojektss19.team03.scart.shared.EditorService;
import de.hdm.itprojektss19.team03.scart.shared.EditorServiceAsync;
import de.hdm.itprojektss19.team03.scart.shared.LoginService;
import de.hdm.itprojektss19.team03.scart.shared.LoginServiceAsync;
import de.hdm.itprojektss19.team03.scart.shared.ReportGenerator;
import de.hdm.itprojektss19.team03.scart.shared.ReportGeneratorAsync;
import de.hdm.itprojektss19.team03.scart.shared.bo.User;

/**
 * Die Klasse ClientsideSettings beinhaltet Eigenschaften und Dienste, die für
 * alle Client-seitigen Klassen relevant sind.
 * 
 * @author PatrickLehle, TomHager, MarcoDell'Oso
 * @author DennisLehle
 * @author Peter Thies
 */

public class ClientsideSettings extends CommonSettings {

	/**
	 * Anlegen eines leeren Users zur Speicherung des aktuellen Users.
	 */
	public User user = null;

	public static Boolean mobile;

	/**
	 * Setzen eines Users für die Bearbeitung in der Scart Verwaltung
	 * 
	 * @param u User
	 */
	public final void setCurrentUser(User u) {
		this.user = u;
	}

	/**
	 * Auslesen eines Users für die Bearbeitung in der Scart Verwaltung
	 * 
	 * @return user der gerade eingeloggt ist.
	 */
	public User getCurrentUser() {
		return user;
	}

	public static Boolean getMobile() {
		return mobile;
	}

	public final void setMobile(Boolean mobile) {
		ClientsideSettings.mobile = mobile;
	}

	/**
	 * Remote Service Proxy zur Verbindungsaufnahme mit dem Server-seitigen Dienst
	 * namens <code>EditorService</code>.
	 */
	private static EditorServiceAsync editorVerwaltung = null;

	/**
	 * Remote Service Proxy zur Verbindungsaufnahme mit dem Server-seitigen Dienst
	 * namens <code>LoginService</code>.
	 */
	private static LoginServiceAsync loginService = null;

	/**
	 * Remote Service Proxy zur Verbindungsaufnahme mit dem Server-seitigen Dienst
	 * namens <code>ReportGenerator</code>.
	 */
	private static ReportGeneratorAsync reportGeneratorVerwaltung = null;

	/**
	 * Name des Client-seitigen Loggers.
	 */
	private static final String LOGGER_NAME = "Scart Web Client";

	/**
	 * Instanz des Client-seitigen Loggers.
	 */
	private static final Logger log = Logger.getLogger(LOGGER_NAME);

	/**
	 * <p>
	 * Auslesen des applikationsweiten (Client-seitig!) zentralen Loggers.
	 * </p>
	 * 
	 * @return die Logger-Instanz für die Server-Seite.
	 */
	public static Logger getLogger() {
		return log;
	}

	/**
	 * <p>
	 * Anlegen und Auslesen der applikationsweit eindeutigen Verwaltung. Diese
	 * Methode erstellt die Verwaltung, sofern sie noch nicht existiert. Bei
	 * wiederholtem Aufruf dieser Methode wird stets das bereits zuvor angelegte
	 * Objekt zurueckgegeben.
	 * </p>
	 * 
	 * <p>
	 * Der Aufruf dieser Methode erfolgt im Client z.B. durch
	 * <code>EditorSerivceAsync editorSerivce = ClientSideSettings.getEditorService()</code>
	 * .
	 * </p>
	 * 
	 * @return eindeutige Instanz des Typs <code>EditorServiceAsync</code>
	 * @author Peter Thies
	 * @since 28.02.2012
	 */
	public static EditorServiceAsync getEditor() {

		// Gab es bislang noch keine Verwaltung-Instanz, wird eine erstellt.
		if (editorVerwaltung == null) {

			// Instanziieren des EditorService's.
			editorVerwaltung = GWT.create(EditorService.class);
		}

		// Rueckgabe des EditorService's.
		return editorVerwaltung;
	}

	/**
	 * Anlegen und Auslesen der applikationsweit eindeutigen Verwaltung. Diese
	 * Methode erstellt die Verwaltung, sofern sie noch nicht existiert. Bei
	 * wiederholtem Aufruf dieser Methode wird stets das bereits zuvor angelegte
	 * Objekt zurueckgegeben.
	 * 
	 * Der Aufruf dieser Methode erfolgt im Client z.B. durch
	 * <code>LoginSerivceAsync loginSerivce = ClientSideSettings.getLoginService()</code>
	 * 
	 * @return eindeutige Instanz des Typs <code>LoginServiceAsync</code>
	 */
	public static LoginServiceAsync getLoginService() {

		// Gab es bislang noch keine Verwaltung-Instanz, wird eine erstellt.
		if (loginService == null) {

			// Instantiieren des EditorService's.
			loginService = GWT.create(LoginService.class);
		}

		// Rueckgabe des EditorService's.
		return loginService;
	}

	/**
	 * <p>
	 * Anlegen und Auslesen des applikationsweit eindeutigen ReportGenerators. Diese
	 * Methode erstellt den ReportGenerator, sofern dieser noch nicht existiert. Bei
	 * wiederholtem Aufruf dieser Methode wird stets das bereits zuvor angelegte
	 * Objekt zurueckgegeben.
	 * </p>
	 * 
	 * <p>
	 * Der Aufruf dieser Methode erfolgt im Client z.B. durch
	 * <code>ReportGeneratorServiceAsync reportGeneratorSerivce = ClientSideSettings.getReportGeneratorService()</code>
	 * .
	 * </p>
	 * 
	 * @return eindeutige Instanz des Typs <code>ReportGeneratorServiceAsync</code>
	 * @author Peter Thies
	 * @since 28.02.2012
	 */
	public static ReportGeneratorAsync getReportGeneratorService() {

		// Gab es bislang noch keine ReportGenerator-Instanz, wird eine neue erstellt.
		// Auch ein sogenannter <Singleton>
		if (reportGeneratorVerwaltung == null) {

			reportGeneratorVerwaltung = GWT.create(ReportGenerator.class);
			final AsyncCallback<Void> initReportGeneratorServiceCallback = new AsyncCallback<Void>() {

				@Override
				public void onFailure(Throwable caught) {
					ClientsideSettings.getLogger().severe("Der ReportGenerator konnte nicht initialisiert werden!");
				}

				@Override
				public void onSuccess(Void result) {
					ClientsideSettings.getLogger().info("Der ReportGenerator wurde initialisiert.");
				}
			};

			reportGeneratorVerwaltung.init(initReportGeneratorServiceCallback);
		}

		// Rueckgabe des ReportGeneratorService's.
		return reportGeneratorVerwaltung;
	}

}
