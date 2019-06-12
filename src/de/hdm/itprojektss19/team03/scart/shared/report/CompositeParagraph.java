package de.hdm.itprojektss19.team03.scart.shared.report;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Vector;


	/**
	 * Diese Klasse stellt eine Menge einzelner Absätze (
	 * <code>SimpleParagraph</code>-Objekte) dar. Diese werden als Unterabschnitte
	 * in einem <code>Vector</code> abgelegt verwaltet.
	 * 
	 * @author Thies
	 */
	public class CompositeParagraph extends Paragraph implements Serializable {

	  /**
	   * 
	   */
	  private static final long serialVersionUID = 1L;

	  /**
	   * Speicherort der Unterabschnitte.
	   */
	  private Vector<SimpleParagraph> subParagraphs = new Vector<SimpleParagraph>();

	/**
	 * ArrayList um die einzelnen <code>SimpleParagraph</code>-Objekte zu speichern.
	 */
	private ArrayList<SimpleParagraph> simpleParas = new ArrayList<SimpleParagraph>();
		
		/**
		 * Methode um alle SimpleParagraph-Objekte des CompositeParagraphs auszugeben.
		 * Es werden also alle "Unterabschnitte" hier ausgegeben.
		 * @return ArrayList mit allen (Sub)SimpleParagraphs
		 */
		public ArrayList<SimpleParagraph> getAllSimpleParagraphs ()
		{
			return this.simpleParas;	
		}
	  /**
	   * Einen Unterabschnitt hinzufügen.
	   * 
	   * @param p der hinzuzufügende Unterabschnitt.
	   */
	  public void addSubParagraph(SimpleParagraph p) {
	    this.subParagraphs.addElement(p);
	  }

	  /**
	   * Einen Unterabschnitt entfernen.
	   * 
	   * @param p der zu entfernende Unterabschnitt.
	   */
	  public void removeSubParagraph(SimpleParagraph p) {
	    this.subParagraphs.removeElement(p);
	  }

	  /**
	   * Auslesen sämtlicher Unterabschnitte.
	   * 
	   * @return <code>Vector</code>, der sämtliche Unterabschnitte enthält.
	   */
	  public Vector<SimpleParagraph> getSubParagraphs() {
	    return this.subParagraphs;
	  }
	  
		/**
		 * Auslesen eines einzelnen Unterabschnitts von einem <code>CompositeParagraph</code>-Objekt
		 * @param i ist der index des auszulesenden Abschnitts, wobei 0 <= i <= n und n = Anzahl der Unterabschnitte.
		 * @return das <code>SimpleParagraph</code>-Objekt and der stelle i
		 */
		public SimpleParagraph getElementAt(int i)
		{
			return this.simpleParas.get(i);
		}

	  /**
	   * Auslesen der Anzahl der Unterabschnitte.
	   * 
	   * @return Anzahl der Unterabschnitte
	   */
	  public int getNumParagraphs() {
	    return this.subParagraphs.size();
	  }

	  /**
	   * Auslesen eines einzelnen Unterabschnitts.
	   * 
	   * @param i der gewuenschte Unterabschnitt des Index
	   * @return der gewünschte Unterabschnitt.
	   */
	  public SimpleParagraph getParagraphAt(int i) {
	    return this.subParagraphs.elementAt(i);
	  }

	  /**
	   * Umwandeln eines <code>CompositeParagraph</code> in einen
	   * <code>String</code>.
	   */
	  @Override
	 public String toString() {
	    /*
	     * Wir legen einen leeren Buffer an, in den wir sukzessive sämtliche
	     * String-Repräsentationen der Unterabschnitte eintragen.
	     */
	    StringBuffer result = new StringBuffer();

	    // Schleife über alle Unterabschnitte
	    for (int i = 0; i < this.subParagraphs.size(); i++) {
	      SimpleParagraph p = this.subParagraphs.elementAt(i);

	      /*
	       * den jew. Unterabschnitt in einen String wandeln und an den Buffer hängen.
	       */
	      result.append(p.toString() + "\n");
	    }

	    /*
	     * Schließlich wird der Buffer in einen String umgewandelt und
	     * zurückgegeben.
	     */
	    return result.toString();
	  }
}
