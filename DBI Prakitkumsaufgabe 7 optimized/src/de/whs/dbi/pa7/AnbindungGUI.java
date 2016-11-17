/**
 * 
 */
package de.whs.dbi.pa7;

import de.whs.dbi.pa7.database.ConnectionInformation;
import de.whs.dbi.pa7.database.DatabaseConnection;
import de.whs.dbi.pa7.database.TpsCreator;
import de.whs.dbi.pa7.database.TpsCreatorInterface;
import de.whs.dbi.pa7.database.TpsCreatorOldStatements;

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
public class AnbindungGUI {
	/**
	 * Datenbank Verbindungsobjekt
	 */
	static DatabaseConnection con;
	
	/**
	 * Ersteller der Datenbank
	 */
	static TpsCreatorInterface tpsDBCreator;
	
	/**
	 * Vom Benutzer angegebene Größe der tps-Datenbank
	 */
	static int sizeN;
	
	/**
	 * Gibt an, ob die heardcodierten Verbindungsinformationen benutzt werden.
	 */
	static boolean useLocal = false;
	
	/**
	 * Unserer Einstiegspunkt füt unsere Anwendung.
	 * Hier werden die nötigen Informationen von dem Benutzer abgefragt
	 * und der Benchmark Klasse übergeben.
	 * 
	 * 
	 * @param args Wird irgoriert
	 */
	
	public static void verbinden(String host, String database, String user, String password) {
		
		
		ConnectionInformation infos = new ConnectionInformation();
		
		
			infos.setHost(host);
			infos.setDatabase(database);
			infos.setUser(user);
			infos.setPassword(password);
		
		

		try {
			// erstelle die Datenbankverbindung und verbinde
			System.out.print("Verbinde zur Datenbank ... ");
			
			con = new DatabaseConnection(infos);
			con.connect();
			
			System.out.println("[OK]");
			
			// Nutzer fragen, ob er die unoptimierte Programmversion nutzen möchte
			System.out.println("Optimierte Programmversion benutzen?? [j/n]:");
			
			// erstellen des jeweiligen TpsCreatorObjekts
			if(!ConsoleReader.readJn()) {
				tpsDBCreator = new TpsCreatorOldStatements(con);
			}
			else {

				tpsDBCreator = new TpsCreator(con);
			}
		}
		catch ( Exception err){
			
			//Fehlerfall

			err.printStackTrace();
			System.err.println(err.getMessage());
			System.err.println("Konnte keine Verbindung zur Datenbank aufbauen!");
			
			return;
		}
		
		
		// Nutzer die größe der Datenbank angeben lassen
		System.out.println("Gebe bitte die Größe (n) der Datenbank ein:");	
		sizeN = ConsoleReader.readInt();			
		
		
		
		System.out.println("Beende Anwendung...");		
	}
	public static void version(boolean neu)
	{
		try
		{
			// Nutzer fragen, ob er die unoptimierte Programmversion nutzen möchte
			System.out.println("Optimierte Programmversion benutzen?? [j/n]:");
			
			// erstellen des jeweiligen TpsCreatorObjekts
			if(!neu) {
				tpsDBCreator = new TpsCreatorOldStatements(con);
			}
			else {
	
				tpsDBCreator = new TpsCreator(con);
			}
		}
		catch ( Exception err)
		{
			
			//Fehlerfall
	
			err.printStackTrace();
			System.err.println(err.getMessage());
			System.err.println("Konnte keine Verbindung zur Datenbank aufbauen!");
			
			return;
		}
		}
	
	/**
	 * Zeigt das Benchmarkmenü und wertet die Nutzereingaben
	 * aus.
	 */
	public static void benchmarkMenu(int i) {
		
		
		// Übergabe der Benchmarkparameter
		switch(i) {
		case 1:
			Benchmark.BenchmarkTask(sizeN, tpsDBCreator, true, false, true);
			break;
		case 2:
			Benchmark.BenchmarkTask(sizeN, tpsDBCreator, true, false, false);
			break;
		case 3:
			Benchmark.BenchmarkTask(sizeN, tpsDBCreator, true, true, true);
			break;
		case 4:
			Benchmark.BenchmarkTask(sizeN, tpsDBCreator, true, true, false);
			break;
		case 5:
			Benchmark.BenchmarkTask(sizeN, tpsDBCreator, false, false, true);
			break;
		case 6:
			Benchmark.BenchmarkTask(sizeN, tpsDBCreator, false, false, false);
			break;
		case 7:
			Benchmark.BenchmarkTask(sizeN, tpsDBCreator, false, true, true);
			break;
		case 8:
			Benchmark.BenchmarkTask(sizeN, tpsDBCreator, false, true, false);
			break;
		default:
			System.out.println("Ungültige Menü Option");
		}
	}
}


