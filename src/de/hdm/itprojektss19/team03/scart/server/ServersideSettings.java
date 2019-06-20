package de.hdm.itprojektss19.team03.scart.server;

import java.util.logging.Logger;

import de.hdm.itprojektss19.team03.scart.client.ClientsideSettings;

/**
 * Logger für serverseitige aktionen
 */
public class ServersideSettings extends ClientsideSettings {

	private static final String LOGGER_NAME = "Logger";
	private static final Logger logger = Logger.getLogger(LOGGER_NAME);;

	public static Logger getLogger() {

		return logger;
	}

}
