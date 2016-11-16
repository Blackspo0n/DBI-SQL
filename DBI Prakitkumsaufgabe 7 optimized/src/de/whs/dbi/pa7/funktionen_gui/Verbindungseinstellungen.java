package de.whs.dbi.pa7.funktionen_gui;

import java.io.Serializable;

/**
 * Erstellung eines Objektes zur Übergabe von Informationen zur Herstellung der Verbindung.
 * 
 * @author Markus Hausmann
 * @author Mario Kellner
 * @author Jonas Stadtler
 *
 */
public class Verbindungseinstellungen implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String bezeichnung;
	public String host;
	public String database;
	public String user;
	public String password;
	
	public Verbindungseinstellungen(String bezeichnung, String host, String database, String user, String passwort)
	{
		bezeichnung=this.bezeichnung;
		host=this.host;
		database= this.database;
		user=this.user;
		passwort=this.password;
	}
	public Verbindungseinstellungen(String bezeichnung, String host, String database, String user)
	{
		bezeichnung=this.bezeichnung;
		host=this.host;
		database= this.database;
		user=this.user;
	}

	public String getBezeichnung() {
		return bezeichnung;
	}

	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
