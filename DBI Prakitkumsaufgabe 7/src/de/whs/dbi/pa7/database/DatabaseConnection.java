package de.whs.dbi.pa7.database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 * Unsere Datebankklasse ermöglicht die Kommunikation
 * mit der Datenbank. 
 * 
 * 
 * @author Mario Kellner
 * @author Markus Hausmann
 * @author Jonas Stadtler
 *
 */
public class DatabaseConnection 
{
	//benennen einer Verbindungsvariablen
	public Connection databaseLink;
	private ConnectionInformation ci;
	
	
	public DatabaseConnection(ConnectionInformation cInfo) throws Exception {
		if(cInfo == null) {
			throw new Exception("ConnectionInfo Oject cannot be null");
		}
		
		ci = cInfo;
	}
	
	/**
	 * 
	 * List die Informatioen aus dem Objekt ci (ConnectionInformation) und versucht eine Verbindung 
	 * zur Datenbank auf zu bauen.
	 * 
	 * @throws SQLException Wird geẃorfen, wenn der DriverManager keine Verbndung zur Datenbank aufbauen kann
	 */
	public void connect() throws SQLException
	{
		/*
		 * Der Compiler übersetzt " + var + " automatisch in ein StringBuilder Objekt
		 */
		databaseLink = DriverManager.getConnection(
				"jdbc:postgresql://" + ci.getHost() +"/" + ci.getDatabase(),
				ci.getUser(), 
				ci.getPassword()
		);
		
	}
}
