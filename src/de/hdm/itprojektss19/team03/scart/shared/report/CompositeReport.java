package de.hdm.itprojektss19.team03.scart.shared.report;

import java.io.Serializable;

import java.util.Vector;



public abstract class CompositeReport extends Report implements Serializable {

private static final long serialVersionUID = 1L;

/**
 * Die Menge der Teil-Reports.
 */
private Vector<SimpleReport> subReports = new Vector<SimpleReport>();

/**
 * Hinzufuegen eines Teil-Reports.
 * @param r der hinzuzufuegende Teil-Report.
 */
public void addSubReport(SimpleReport r) {
	this.subReports.addElement(r);
}

/**
 * Entfernen eines Teil-Reports.
 * @param r der zu entfernende Teil-Report.
 */
public void removeSubReport(Report r) {
	this.subReports.removeElement(r);
}

/**
 * Auslesen der Anzahl von Teil-Reports.
 * @return int Anzahl der Teil-Reports.
 */
public int getNumSubReports() {
	return this.subReports.size();
}

/**
 * Auslesen eines einzelnen Teil-Reports.
 * @param i Position des Teilreports. Bei n Elementen laeuft der Index i von 0
 * bis n-1.
 * 
 * @return Position des Teil-Reports.
 */	
public Report getSubReportAt(int i) {
	return this.subReports.elementAt(i);
}

}
