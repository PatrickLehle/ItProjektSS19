package de.hdm.itprojektss19.team03.scart.shared.report;

/**
 * <p>
 * Diese Klasse wird benoetigt, um auf dem Client die ihm vom Server zur
 * Verfuegung gestellten <code>Report</code>-Objekte in ein menschenlesbares
 * Format zu ueberfuehren.
 * </p>
 * <p>
 * Das Zielformat kann prinzipiell beliebig sein. Methoden zum Auslesen der in
 * das Zielformat ueberfuehrten Information wird den Subklassen ueberlassen. In
 * dieser Klasse werden die Signaturen der Methoden deklariert, die fuer die
 * Prozessierung der Quellinformation zustaendig sind.
 * </p>
 * 
 * @author Thies
 */
public abstract class ReportWriter {

	/**
	 * Uebersetzen eines <code>ArticleReport</code> in das
	 * Zielformat.
	 * 
	 * @param r der zu uebersetzende Report
	 */
	public abstract void process(ArticleReport r);

	/**
	 * Uebersetzen eines <code>ArticleDateReport</code> in das Zielformat.
	 * 
	 * @param r der zu uebersetzende Report
	 */
	public abstract void process(ArticleDateReport r);

	/**
	 * Uebersetzen eines <code>ArticleRetailerReport</code> in das
	 * Zielformat.
	 * 
	 * @param r der zu uebersetzende Report
	 */
	public abstract void process(ArticleRetailerReport r);

	/**
	 * Uebersetzen eines <code>ArticleDateRetailerReport</code> in das Zielformat.
	 * 
	 * @param r der zu uebersetzende Report
	 */
	public abstract void process(ArticleDateRetailerReport r);
	
	/**
	 * Uebersetzen eines <code>CompositeReport</code> in das Zielformat.
	 * 
	 * @param cr der zu uebersetzende Report
	 */
//	public abstract void process(CompositeReport cr);
	
	/**
	 * Uebersetzen eines <code>simpleReportProcess</code> in das Zielformat.
	 * 
	 * @param sr
	 */
//	public abstract void simpleReportProcess(SimpleReport sr);

}
