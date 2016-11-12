package de.whs.dbi.pa7.database;


/**
 * Dieses Model beinhaltet alle nötigen Informationen 
 * um sich zu einer Postgre Datenbank zu verbinden
 * 
 * @author Mario Kellner
 * @author Markus Hausmann
 * @author Jonas Stadtler
 *
 */
public class ConnectionInformation {
	public String host = null;
	public String database = null;
	public String user = null;
	public String password = null;
	
	
	// Getter & Setter
	
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