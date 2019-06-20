package de.hdm.itprojektss19.team03.scart.shared;

import java.io.Serializable;

public class DatabaseException extends Exception implements Serializable {
	public DatabaseException(Exception e) {
		super(e.getMessage());
	}

	public DatabaseException() {

	}

}
