package de.hdm.itprojektss19.team03.scart.shared.report;

import java.util.Vector;

/**
 * Ein <code>ReportWriter</code>, der Reports mittels Plain Text formatiert. Das
 * im Zielformat vorliegende Ergebnis wird in der Variable
 * <code>reportText</code> abgelegt und kann nach Aufruf der entsprechenden
 * Prozessierungsmethode mit <code>getReportText()</code> ausgelesen werden.
 * 
 * @author Thies
 */
public class PlainTextReportWriter extends ReportWriter {

	/**
	 * Diese Variable wird mit dem Ergebnis einer Umwandlung (vgl.
	 * <code>process...</code>-Methoden) belegt. Format: Text
	 */
	private String reportText = "";

	/**
	 * Zuruecksetzen der Variable <code>reportText</code>.
	 */
	public void resetReportText() {
		this.reportText = "";
	}

	/**
	 * Header-Text produzieren.
	 * 
	 * @return Text
	 */
	public String getHeader() {
		return "";
	}

	/**
	 * Trailer-Text produzieren.
	 * 
	 * @return Text
	 */
	public String getTrailer() {
		return "___________________________________________";
	}

	/**
	 * Prozessieren des übergebenen Reports und Ablage im Zielformat. Ein Auslesen
	 * des Ergebnisses kann später mittels <code>getReportText()</code> erfolgen.
	 * 
	 * @param r
	 *            der zu prozessierende Report
	 */
	@Override
	public void process(ArticleReport r) {
		this.resetReportText();

		StringBuffer result = new StringBuffer();

		/*
		 * Nun werden Schritt fuer Schritt die einzelnen Bestandteile des Reports
		 * ausgelesen und in Text-Form ueersetzt.
		 */
		result.append("Titel " + r.getTitle() + "\n");

		Vector<Row> rows = r.getRows();

		for (Row row : rows) {
			for (int k = 0; k < row.getNumColumns(); k++) {
				result.append(row.getColumnAt(k) + "\t ; \t");
			}

			result.append("\n");

			/*
			 * Zum Schluss wird unser Arbeits-Buffer in einen String umgewandelt und der
			 * reportText-Variable zugewiesen. Dadurch wird es moeglich, anschliessend das
			 * Ergebnis mittels getReportText() auszulesen.
			 */
			this.reportText = result.toString();
		}
	}

	/**
	 * Prozessieren des uebergebenen Reports und Ablage im Zielformat. Ein Auslesen
	 * des Ergebnisses kann später mittels <code>getReportText()</code> erfolgen.
	 * 
	 * @param r
	 *            der zu prozessierende Report
	 */
	@Override
	public void process(ArticleDateReport r) {
		// Zunaechst loeschen wir das Ergebnis vorhergehender Prozessierungen.
		this.resetReportText();

		/*
		 * In diesen Buffer schreiben wir waehrend der Prozessierung sukzessiv unsere
		 * Ergebnisse.
		 */
		StringBuffer result = new StringBuffer();

		/*
		 * Nun werden Schritt fuer Schritt die einzelnen Bestandteile des Reports
		 * ausgelesen und in Text-Form ueersetzt.
		 */
		result.append("Titel " + r.getTitle() + "\n");

		Vector<Row> rows = r.getRows();

		for (Row row : rows) {
			for (int k = 0; k < row.getNumColumns(); k++) {
				result.append(row.getColumnAt(k) + "\t ; \t");
			}

			result.append("\n");

			/*
			 * Zum Schluss wird unser Arbeits-Buffer in einen String umgewandelt und der
			 * reportText-Variable zugewiesen. Dadurch wird es moeglich, anschliessend das
			 * Ergebnis mittels getReportText() auszulesen.
			 */
			this.reportText = result.toString();
		}
	}

	public void process(ArticleRetailerReport r) {

		// Zunaechst loeschen wir das Ergebnis vorhergehender Prozessierungen.
		this.resetReportText();

		/*
		 * In diesen Buffer schreiben wir waehrend der Prozessierung sukzessiv unsere
		 * Ergebnisse.
		 */
		StringBuffer result = new StringBuffer();

		/*
		 * Nun werden Schritt fuer Schritt die einzelnen Bestandteile des Reports
		 * ausgelesen und in Text-Form ueersetzt.
		 */
		result.append("Untertitel " + r.getTitle() + "\n");

		Vector<Row> rows = r.getRows();

		for (Row row : rows) {
			for (int k = 0; k < row.getNumColumns(); k++) {
				result.append(row.getColumnAt(k) + "\t ; \t");
			}

			result.append("\n");

			/*
			 * Zum Schluss wird unser Arbeits-Buffer in einen String umgewandelt und der
			 * reportText-Variable zugewiesen. Dadurch wird es moeglich, anschließend das
			 * Ergebnis mittels getReportText() auszulesen.
			 */
			this.reportText = result.toString();
		}
	}

	/**
	 * Auslesen des Ergebnisses der zuletzt aufgerufenen Prozessierungsmethode.
	 * 
	 * @return ein String bestehend aus einfachem Text
	 */
	public String getReportText() {
		return this.getHeader() + this.reportText + this.getTrailer();
	}

	@Override
	public void process(ArticleDateRetailerReport r) {
		// TODO Auto-generated method stub

	}
}
