package de.w_hs.dbi.pa9.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Unsere Datebankklasse ermöglicht die Kommunikation
 * mit der Datenbank. Es ist ein Wrapper für das
 * Connection-Objekt der JDBC Connection
 * 
 * @author Mario Kellner
 * @author Markus Hausmann
 * @author Jonas Stadtler
 */
public class DatabaseConnection 
{
	/**
	 * Das eigentliche Connection Objekt
	 */
	public Connection databaseLink;
	
	/**
	 *  Unser Verbindungs-Model
	 */
	private ConnectionInformation ci;
	
	/**
	 * Der Konstruktur der Klasse.  Überprüft ob das Objekt im C
	 * 
	 * @param cInfo Das Connection Objekt
	 * @throws Exception Wird geworfen, wenn das Objekt was übergeben wird null ist.
	 */
	public DatabaseConnection(ConnectionInformation cInfo) throws Exception {
		if(cInfo == null) {
			throw new Exception("ConnectionInfo Object cannot be null");
		}
		
		ci = cInfo;
	}
	public void clearHistory() throws SQLException
	{
		Statement statement= databaseLink.createStatement();

		statement.executeUpdate("TRUNCATE TABLE history");
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
		databaseLink = DriverManager.getConnection(
				"jdbc:postgresql://" + ci.getHost() +"/" + ci.getDatabase() + "?useCompression=true",
				ci.getUser(), 
				ci.getPassword()
		);
	}
}