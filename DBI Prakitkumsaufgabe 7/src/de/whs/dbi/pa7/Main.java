/**
 * 
 */
package de.whs.dbi.pa7;

import de.whs.dbi.pa7.database.ConnectionInformation;
import de.whs.dbi.pa7.database.DatabaseConnection;
import de.whs.dbi.pa7.database.tpsCreator;

/**
 * Unsere Hauptklasse. Hauptsächlich dient Sie als Einsteigspunkt unserer Applikation
 * und bietet gleichzeitig dem Benutzer eine Interaktionsmöglichkeit
 * über die Konsole an.
 * 
 * @author Mario Kellner
 * @author Markus Hausmann
 * @author Jonas Stadtler
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		boolean useLocal = false;
		
		System.out.println("Lokale Datenbank verwenden? [J/n]:");
		useLocal = ConsoleReader.readJn();
		
		ConnectionInformation infos = new ConnectionInformation();
		
		if(useLocal) {
			infos.setHost("127.0.0.1");
			infos.setDatabase("benchmark");
			infos.setUser("postgres");
			infos.setPassword("DBIPr");
		}
		else {
			System.out.println("Verbindungsinformationen eingeben!");
			
			System.out.println("Host:");
			infos.setHost(ConsoleReader.readString());

			System.out.println("Datenbank:");
			infos.setDatabase(ConsoleReader.readString());
			
			System.out.println("Benutzer:");
			infos.setUser(ConsoleReader.readString());
			
			System.out.println("Password:");
			infos.setPassword(ConsoleReader.readString());
		}
		
		DatabaseConnection con;
		
		try {
			con = new DatabaseConnection(infos);
			con.connect();
			
		}
		catch ( Exception err){
			System.err.println("Konnte keine Verbindung zur Datenbank aufbauen!");
			System.err.println(err.getMessage());
			err.printStackTrace();
			
			return;
		}
		
		try {
			tpsCreator tpsDBCreator = new tpsCreator(con);
		
			System.out.println("Gebe bitte die Größe (n) der Datenbank ein:");
			
			int sizeN = ConsoleReader.readInt();
			
			tpsDBCreator.autoSetup(sizeN);
			
		} catch (Exception e) {}
		
		System.out.println("Beende Anwendung...");		
	}
	
	

}
