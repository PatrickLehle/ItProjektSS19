package de.hdm.itprojektss19.team03.scart.shared.bo;

import java.io.Serializable;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * <p>
 * Die Klasse <code>BusinessObject</code> stellt die Basisklasse aller in diesem
 * Projekt fuer die Umsetzung des Fachkonzepts relevanten Klassen dar.
 * </p>
 * <p>
 * Zentrales Merkmal ist, dass jedes <code>BusinessObject</code> eine Nummer
 * besitzt, die man in einer relationalen Datenbank auch als Primaerschluessel
 * bezeichnen wuerde. Fernen ist jedes <code>BusinessObject</code> als
 * {@link Serializable} gekennzeichnet. Durch diese Eigenschaft kann jedes
 * <code>BusinessObject</code> automatisch in eine textuelle Form ueberfuehrt
 * und z.B. zwischen Client und Server transportiert werden. Bei GWT RPC ist
 * diese textuelle Notation in JSON (siehe http://www.json.org/) kodiert.
 * </p>
 * 
 * @author thies
 * @version 1.0
 */
public abstract class BusinessObject implements Serializable, IsSerializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Die eindeutige Identifikationsnummer einer Instanz dieser Klasse.
	 */
	protected int id;

	public BusinessObject() {

	}

	public BusinessObject(int id) {
		this.id = id;
	}

	/**
	 * Auslesen der ID.
	 * 
	 * @return id des objekts
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * Setzen der ID
	 * 
	 * @param id
	 *            der Objekts
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Erzeugen einer einfachen textuellen Darstellung der jeweiligen Instanz. Dies
	 * kann selbstverstaendlich in Subklassen ueberschrieben werden.
	 */
	@Override
	public String toString() {
		/*
		 * Wir geben den Klassennamen gefolgt von der ID des Objekts zurueck.
		 */
		return this.getClass().getName() + " #" + this.id;
	}

	/**
	 * <p>
	 * Feststellen der <em>inhaltlichen</em> Gleichheit zweier
	 * <code>BusinessObject</code>-Objekte. Die Gleichheit wird in diesem Beispiel
	 * auf eine identische ID beschraenkt.
	 * </p>
	 * <p>
	 * <b>ACHTUNG:</b> Die inhaltliche Gleichheit nicht mit dem Vergleich der
	 * <em>Identitaet</em> eines Objekts mit einem anderen verwechseln!!! Dies
	 * wuerde durch den Operator <code>==</code> bestimmt. Bei Unklarheit hierzu
	 * koennen Sie nocheinmal in die Definition des Sprachkerns von Java schauen.
	 * Die Methode <code>equals(...)</code> ist fuer jeden Referenzdatentyp
	 * definiert, da sie bereits in der Klasse <code>Object</code> in einfachster
	 * Form realisiert ist. Dort ist sie allerdings auf die simple Bestimmung der
	 * Gleicheit der Java-internen Objekt-ID der verglichenen Objekte beschraenkt.
	 * In unseren eigenen Klassen koennen wir diese Methode ueberschreiben und ihr
	 * mehr Intelligenz verleihen.
	 * </p>
	 */
	@Override
	public boolean equals(Object o) {
		/*
		 * Abfragen, ob ein Objekt ungleich NULL ist und ob ein Objekt gecastet werden
		 * kann, sind immer wichtig!
		 */
		if (o != null && o instanceof BusinessObject) {
			BusinessObject bo = (BusinessObject) o;
			try {
				if (bo.getId() == this.id)
					return true;
			} catch (IllegalArgumentException e) {
				/*
				 * Wenn irgendetwas schief geht, dann geben wir sicherheitshalber false zurueck.
				 */
				return false;
			}
		}
		/*
		 * Wenn bislang keine Gleichheit bestimmt werden konnte, dann muessen
		 * schliesslich false zurueckgeben.
		 */
		return false;
	}

	/**
	 * <p>
	 * Erzeugen einer ganzen Zahl, die fuer das <code>BusinessObject</code>
	 * charakteristisch ist.
	 * </p>
	 * <p>
	 * Zusammen mit <code>equals</code> sollte diese Methode immer definiert werden.
	 * Manche Java-Klassen verwendenden <code>hashCode</code>, um initial ein Objekt
	 * (z.B. in einer Hashtable) zu identifizieren. Erst danach wuerde mit
	 * <code>equals</code> festgestellt, ob es sich tatsaechlich um das gesuchte
	 * Objekt handelt.
	 */
	@Override
	public int hashCode() {
		return this.id;
	}

}
