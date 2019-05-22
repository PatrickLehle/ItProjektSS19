package de.hdm.itprojektss19.team03.scart.shared.report;

import java.util.Vector;

/**
 * Ein <code>ReportWriter</code>, der Reports mittels HTML formatiert. Das im
 * Zielformat vorliegende Ergebnis wird in der Variable <code>reportText</code>
 * abgelegt und kann nach Aufruf der entsprechenden Prozessierungsmethode mit
 * <code>getReportText()</code> ausgelesen werden.
 * 
 * @author Thies
 */
public class HTMLReportWriter extends ReportWriter {

	/**
	 * Diese Variable wird mit dem Ergebnis einer Umwandlung (vgl.
	 * <code>process...</code>-Methoden) belegt. Format: HTML-Text
	 */
	private String reportText = "";

	/**
	 * Zuruecksetzen der Variable <code>reportText</code>.
	 */
	public void resetReportText() {
		this.reportText = "";
	}

	/**
	 * Umwandeln eines <code>Paragraph</code>-Objekts in HTML.
	 * 
	 * @param p der Paragraph
	 * @return HTML-Text
	 */
	public String paragraph2HTML(Paragraph p) {
		if (p instanceof CompositeParagraph) {
			return this.paragraph2HTML((CompositeParagraph) p);
		} else {
			return this.paragraph2HTML((SimpleParagraph) p);
		}
	}

	/**
	 * Umwandeln eines <code>CompositeParagraph</code>-Objekts in HTML.
	 * 
	 * @param p der CompositeParagraph
	 * @return HTML-Text
	 */
	public String paragraph2HTML(CompositeParagraph p) {
		StringBuffer result = new StringBuffer();

		for (int i = 0; i < p.getNumParagraphs(); i++) {
			result.append("<p>" + p.getParagraphAt(i) + "</p>");
		}

		return result.toString();
	}

	/**
	 * Umwandeln eines <code>SimpleParagraph</code>-Objekts in HTML.
	 * 
	 * @param p der SimpleParagraph
	 * @return HTML-Text
	 */
	public String paragraph2HTML(SimpleParagraph p) {
		return "<p>" + p.toString() + "</p>";
	}

	/**
	 * HTML-Header-Text produzieren.
	 * 
	 * @return HTML-Text
	 */
	public String getHeader() {
		StringBuffer result = new StringBuffer();

		result.append("<html><head><title></title></head><body>");
		return result.toString();
	}

	/**
	 * HTML-Trailer-Text produzieren.
	 * 
	 * @return HTML-Text
	 */
	public String getTrailer() {
		return "</body></html>";
	}

	/**
	 * Prozessieren des uebergebenen Reports und Ablage im Zielformat. Ein Auslesen
	 * des Ergebnisses kann spaeter mittels <code>getReportText()</code> erfolgen.
	 * 
	 * @param r der zu prozessierende Report
	 */
	@Override
	public void process(ArticleReport r) {
		// Zunaechst loeschen wir das Ergebnis vorhergehender Prozessierungen.
		this.resetReportText();

		//Ein Stringbuffer, an den alle folgenden HTML Inhalte angehaengt werden
		StringBuffer result = new StringBuffer();
		
		// Hinzufuegen der Ueberschrift und den Kopfdaten in HTML-Form.
		result.append("<H3>" + r.getTitle() + "</H3>");
		result.append(
				"<table style=\"width:550px;border:1px solid #e6e6e6; font-family: sans-serif; margin-bottom:5px;\"></br><tr>");
		result.append("<tr><td> " + " Erstellungsdatum des Reports | " + r.created().toString() + "</td></tr>");
		
		
		Vector<Row> rows = r.getRows();
		result.append("<table style=\"width:100%\">");

		// Innerhalb der Schleife wird jede einzelne Reihe aufgerufen 
		for (int i = 0; i < rows.size(); i++) {
			Row row = rows.elementAt(i);
			result.append("</td></tr>");
			
			//Innerhalb der Schleife wird jede einzelne Spalte aufgerufen und daraufhin 
			//die einzelnen Inhalte der Spalte Reihe fuer Reihe hinzugefuegt 
			for (int k = 0; k < row.getNumColumns(); k++) {
				
				//Hier werden die Inhalte der Tabellenbezeichnung entnommen 
				if (i == 0) {
					result.append(
							"<td style=\"background:lightsteelblue;font-weight:bold; font-size: larger; font-family: sans-serif;\">"
									+ row.getColumnAt(k) + "</td>");
				}
				//Hier die Bezeichnung der Untertabelle ausgegeben. 
				else if (row.getNumColumns() == 2) {
					result.append("<td height=\"25\"; style=\"background:lightsteelblue; font-family: sans-serif;\">"
							+ row.getColumnAt(k) + "</td>");
				}
				//Hier wird die formative Trennung der einzelnen Ausgaben erkenntlich gemacht
				else if (row.getNumColumns() == 6) {
					result.append("<td height=\"50\"; style=\"border-top: 3px solid #5669b1\">"
							+ "<b style=\"font-family: sans-serif;\">" + row.getColumnAt(k) + "</b></td>");
				}

				else {
					if (i > 1) {
						result.append(
								"<td height=\"25\"; style=\"border-top: 1px solid #e6e6e6; font-family: sans-serif;\">"
										+ row.getColumnAt(k) + "</td>");

					}

					else {
						result.append("<td valign=\"top\">" + row.getColumnAt(k) + "</td>");
					}
				}

			}

			result.append("</tr>");

		}

		result.append("</table>");

		/*
		 * Zum Schluss wird unser Arbeits-Buffer in einen String umgewandelt und der
		 * reportText-Variable zugewiesen. Dadurch wird es moeglich, anschliessend das
		 * Ergebnis mittels getReportText() auszulesen.
		 */
		this.reportText = result.toString();
	}

	/**
	 * Prozessieren des übergebenen Reports und Ablage im Zielformat. Ein Auslesen
	 * des Ergebnisses kann später mittels <code>getReportText()</code> erfolgen.
	 * 
	 * @param r
	 *            der zu prozessierende Report
	 */
	@Override
	public void process(ArticleDateReport r) {
		// Zunaechst loeschen wir das Ergebnis vorhergehender Prozessierungen.
				this.resetReportText();

				//Ein Stringbuffer, an den alle folgenden HTML Inhalte angehaengt werden
				StringBuffer result = new StringBuffer();
				
				// Hinzuf�gen der Ueberschrift und den Kopfdaten in HTML-Form.
				result.append("<H3>" + r.getTitle() + "</H3>");
				result.append(
						"<table style=\"width:550px;border:1px solid #e6e6e6; font-family: sans-serif; margin-bottom:5px;\"></br><tr>");
				result.append("<tr><td> " + " Erstellungsdatum des Reports | " + r.created().toString() + "</td></tr>");
				
				
				Vector<Row> rows = r.getRows();
				result.append("<table style=\"width:100%\">");

				// Innerhalb der Schleife wird jede einzelne Reihe aufgerufen 
				for (int i = 0; i < rows.size(); i++) {
					Row row = rows.elementAt(i);
					result.append("</td></tr>");
					
					//Innerhalb der Schleife wird jede einzelne Spalte aufgerufen und daraufhin 
					//die einzelnen Inhalte der Spalte Reihe fuer Reihe hinzugefuegt 
					for (int k = 0; k < row.getNumColumns(); k++) {
						
						//Hier werden die Inhalte der Tabellenbezeichnung entnommen 
						if (i == 0) {
							result.append(
									"<td style=\"background:lightsteelblue;font-weight:bold; font-size: larger; font-family: sans-serif;\">"
											+ row.getColumnAt(k) + "</td>");
						}
						//Hier die Bezeichnung der Untertabelle ausgegeben. 
						else if (row.getNumColumns() == 2) {
							result.append("<td height=\"25\"; style=\"background:lightsteelblue; font-family: sans-serif;\">"
									+ row.getColumnAt(k) + "</td>");
						}
						//Hier wird die formative Trennung der einzelnen Ausgaben erkenntlich gemacht
						else if (row.getNumColumns() == 6) {
							result.append("<td height=\"50\"; style=\"border-top: 3px solid #5669b1\">"
									+ "<b style=\"font-family: sans-serif;\">" + row.getColumnAt(k) + "</b></td>");
						}

						else {
							if (i > 1) {
								result.append(
										"<td height=\"25\"; style=\"border-top: 1px solid #e6e6e6; font-family: sans-serif;\">"
												+ row.getColumnAt(k) + "</td>");

							}

							else {
								result.append("<td valign=\"top\">" + row.getColumnAt(k) + "</td>");
							}
						}

					}

					result.append("</tr>");

				}

				result.append("</table>");

				/*
				 * Zum Schluss wird unser Arbeits-Buffer in einen String umgewandelt und der
				 * reportText-Variable zugewiesen. Dadurch wird es moeglich, anschliessend das
				 * Ergebnis mittels getReportText() auszulesen.
				 */
				this.reportText = result.toString();
			}


	@Override
	public void process(ArticleDateRetailerReport r) {
		// Zunaechst loeschen wir das Ergebnis vorhergehender Prozessierungen.
				this.resetReportText();

				//Ein Stringbuffer, an den alle folgenden HTML Inhalte angehaengt werden
				StringBuffer result = new StringBuffer();
				
				// Hinzufuegen der Ueberschrift und den Kopfdaten in HTML-Form.
				result.append("<H3>" + r.getTitle() + "</H3>");
				result.append(
						"<table style=\"width:550px;border:1px solid #e6e6e6; font-family: sans-serif; margin-bottom:5px;\"></br><tr>");
				result.append("<tr><td> " + " Erstellungsdatum des Reports | " + r.created().toString() + "</td></tr>");
				
				
				Vector<Row> rows = r.getRows();
				result.append("<table style=\"width:100%\">");

				// Innerhalb der Schleife wird jede einzelne Reihe aufgerufen 
				for (int i = 0; i < rows.size(); i++) {
					Row row = rows.elementAt(i);
					result.append("</td></tr>");
					
					//Innerhalb der Schleife wird jede einzelne Spalte aufgerufen und daraufhin 
					//die einzelnen Inhalte der Spalte Reihe fuer Reihe hinzugefuegt 
					for (int k = 0; k < row.getNumColumns(); k++) {
						
						//Hier werden die Inhalte der Tabellenbezeichnung entnommen 
						if (i == 0) {
							result.append(
									"<td style=\"background:lightsteelblue;font-weight:bold; font-size: larger; font-family: sans-serif;\">"
											+ row.getColumnAt(k) + "</td>");
						}
						//Hier die Bezeichnung der Untertabelle ausgegeben. 
						else if (row.getNumColumns() == 2) {
							result.append("<td height=\"25\"; style=\"background:lightsteelblue; font-family: sans-serif;\">"
									+ row.getColumnAt(k) + "</td>");
						}
						//Hier wird die formative Trennung der einzelnen Ausgaben erkenntlich gemacht
						else if (row.getNumColumns() == 6) {
							result.append("<td height=\"50\"; style=\"border-top: 3px solid #5669b1\">"
									+ "<b style=\"font-family: sans-serif;\">" + row.getColumnAt(k) + "</b></td>");
						}

						else {
							if (i > 1) {
								result.append(
										"<td height=\"25\"; style=\"border-top: 1px solid #e6e6e6; font-family: sans-serif;\">"
												+ row.getColumnAt(k) + "</td>");

							}

							else {
								result.append("<td valign=\"top\">" + row.getColumnAt(k) + "</td>");
							}
						}

					}

					result.append("</tr>");

				}

				result.append("</table>");

				/*
				 * Zum Schluss wird unser Arbeits-Buffer in einen String umgewandelt und der
				 * reportText-Variable zugewiesen. Dadurch wird es moeglich, anschliessend das
				 * Ergebnis mittels getReportText() auszulesen.
				 */
				this.reportText = result.toString();
			}


	@Override
	public void process(ArticleRetailerReport r) {
		// Zunaechst loeschen wir das Ergebnis vorhergehender Prozessierungen.
				this.resetReportText();

				//Ein Stringbuffer, an den alle folgenden HTML Inhalte angehaengt werden
				StringBuffer result = new StringBuffer();
				
				// Hinzufuegen der Ueberschrift und den Kopfdaten in HTML-Form.
				result.append("<H3>" + r.getTitle() + "</H3>");
				result.append(
						"<table style=\"width:550px;border:1px solid #e6e6e6; font-family: sans-serif; margin-bottom:5px;\"></br><tr>");
				result.append("<tr><td> " + " Erstellungsdatum des Reports | " + r.created().toString() + "</td></tr>");
				
				
				Vector<Row> rows = r.getRows();
				result.append("<table style=\"width:100%\">");

				// Innerhalb der Schleife wird jede einzelne Reihe aufgerufen 
				for (int i = 0; i < rows.size(); i++) {
					Row row = rows.elementAt(i);
					result.append("</td></tr>");
					
					//Innerhalb der Schleife wird jede einzelne Spalte aufgerufen und daraufhin 
					//die einzelnen Inhalte der Spalte Reihe fuer Reihe hinzugefuegt 
					for (int k = 0; k < row.getNumColumns(); k++) {
						
						//Hier werden die Inhalte der Tabellenbezeichnung entnommen 
						if (i == 0) {
							result.append(
									"<td style=\"background:lightsteelblue;font-weight:bold; font-size: larger; font-family: sans-serif;\">"
											+ row.getColumnAt(k) + "</td>");
						}
						//Hier die Bezeichnung der Untertabelle ausgegeben. 
						else if (row.getNumColumns() == 2) {
							result.append("<td height=\"25\"; style=\"background:lightsteelblue; font-family: sans-serif;\">"
									+ row.getColumnAt(k) + "</td>");
						}
						//Hier wird die formative Trennung der einzelnen Ausgaben erkenntlich gemacht
						else if (row.getNumColumns() == 6) {
							result.append("<td height=\"50\"; style=\"border-top: 3px solid #5669b1\">"
									+ "<b style=\"font-family: sans-serif;\">" + row.getColumnAt(k) + "</b></td>");
						}

						else {
							if (i > 1) {
								result.append(
										"<td height=\"25\"; style=\"border-top: 1px solid #e6e6e6; font-family: sans-serif;\">"
												+ row.getColumnAt(k) + "</td>");

							}

							else {
								result.append("<td valign=\"top\">" + row.getColumnAt(k) + "</td>");
							}
						}

					}

					result.append("</tr>");

				}

				result.append("</table>");

				/*
				 * Zum Schluss wird unser Arbeits-Buffer in einen String umgewandelt und der
				 * reportText-Variable zugewiesen. Dadurch wird es moeglich, anschliessend das
				 * Ergebnis mittels getReportText() auszulesen.
				 */
				this.reportText = result.toString();
			}

	/**
	 * Auslesen des Ergebnisses der zuletzt aufgerufenen Prozessierungsmethode.
	 * 
	 * @return ein String im HTML-Format
	 */
	public String getReportText() {
		return this.getHeader() + this.reportText + this.getTrailer();
	}
}
