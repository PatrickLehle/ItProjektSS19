package de.hdm.itprojektss19.team03.scart.shared.report;

import java.util.ArrayList;
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
	 * Verarbeitet ein <code>Simple-Report</code> Objekt zu einem String
	 * @param sr ist das <code>SimpleReport</code>-Objekt, das zu einem String verarbeitet werden soll.
	 */
	public void simpleReportProcess(SimpleReport sr){
		//Erst sicherstellen, dass wir mit einem leeren String beginnen
		this.resetReportText();
		
		/**
		 * StringBuffer um das Ergebnis darin zu speichern.
		 */
		StringBuffer result = new StringBuffer();

		
		/**
		 * Vector, die alle Reihen beinhaltet, die das <code>SimpleReport</code>-Objekt
		 * auch beinhalten.
		 */
		Vector<Row> allRows = sr.getRows();
		
		/**
		 * F�r jede Reihe in der Liste allRows soll:
		 * i erh�ht werden und zwar liegt i immer zwischen 0 und der Anzahl der Spalten in dieser Reihe
		 * und in jeder Iteration wird der Inhalt der sich in der Spalte i befindet, f�r die Zeile, die gerade verarbeitet wird
		 * an den StringBuffer namens result angeh�ngt.
		 * Zwischen den Spalten werden dann zwei Tabulatoren gesetzt, um diese zu trennen.
		 * see @Row, die Methode getColumnAt gibt das Element zur�ck, das sich darin befindet.
		 */
		for(Row rowToAppend : allRows)
		{
			for ( int i = 0 ; i < rowToAppend.getNumColumns() ; i++ )
			{
				result.append(rowToAppend.getColumnAt(i) + "/t ; /t");
			}
			//Nach jeder abgearbeiteten Zeile erfolgt ein Umbruch
			result.append("/n");
		}
		
		//Nun wird das Ergebnis als String in der Variable reportText gespeichert.
		this.reportText = result.toString();
		
	}

	
	public void compositeReportProcess(CompositeReport cr) {
//		//Variable leeren.
//		this.resetReportText();
//		
//		//StringBuffer, in den das Ergebnis der Verarbeitung gespeichert wird.
//		StringBuffer result = new StringBuffer();
//		
//		/**
//		 * F�r jedes i, wobei i zwischen 0 und der Anzahl aller SimpleReports in dem CompositeReport ist, wird f�r jedes i:
//		 * Ein String erstellt
//		 * Der SimpleReport an der Stelle i wird prozessiert und dabei wird das Ergebnis des Prozesses immer als String in
//		 * die Variable ReportText gespeichert.
//		 * Das Ergebnis (also der Wert der zu dieser Zeit in reportText steckt) wird an den StringBuffer geh�ngt mit einem 
//		 * anschlie�enden doppeltem Umbruch.
//		 */
//		for ( int i = 0; i < cr.getNumSubReports(); i++ )
//		{
//			simpleReportProcess(cr.getSubReportAt(i));
//			
//			// Dann auch unn�tig, siehe Zeile 80
//			// String test = new String();
//			// test = this.getReportText();
//			
//			result.append(reportText + "/n ; /n");
//			
//			// Um sicherzustellen, dass die Variable geleert wird nach jedem durchlauf
//			this.resetReportText();
//		}
		}

	@Override
	public void process(CompositeReport cr) {
//		//Variable leeren.
//		this.resetReportText();
//		
//		//StringBuffer, in den das Ergebnis der Verarbeitung gespeichert wird.
//		StringBuffer result = new StringBuffer();
//		
//		/**
//		 * F�r jedes i, wobei i zwischen 0 und der Anzahl aller SimpleReports in dem CompositeReport ist, wird f�r jedes i:
//		 * Ein String erstellt
//		 * Der SimpleReport an der Stelle i wird prozessiert und dabei wird das Ergebnis des Prozesses immer als String in
//		 * die Variable ReportText gespeichert.
//		 * Das Ergebnis (also der Wert der zu dieser Zeit in reportText steckt) wird an den StringBuffer geh�ngt mit einem 
//		 * anschlie�enden doppeltem Umbruch.
//		 */
//		for ( int i = 0; i < cr.getNumSubReports(); i++ ){
//			simpleReportProcess(cr.getSubReportAt(i));
//			
//			result.append(reportText + "/n ; /n");
//			
//			// Um sicherzustellen, dass die Variable geleert wird nach jedem durchlauf
//			this.resetReportText();
//		}
//		
	}
	}

