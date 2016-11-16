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
public class Main {
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
	
	public static void main(String[] args) {
		
		System.out.println("Lokale Datenbank verwenden? [j/n]:");
		useLocal = ConsoleReader.readJn();
		
		ConnectionInformation infos = new ConnectionInformation();
		
		if(useLocal) {
			// Lokale Informationen unserer Gruppe.
			// Diese Zugangsdaten werden nicht bei einer anderen Gruppe funktionieren.
			infos.setHost("127.0.0.1");
			infos.setDatabase("benchmark");
			infos.setUser("postgres");
			infos.setPassword("DBIPr");
		}
		else {
			
			//Verbindungnsinformationen werden vom User angegeben.
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
		
		// Zeige das Benchmarkmenü
		benchmarkMenu();
		
		System.out.println("Beende Anwendung...");		
	}
	
	/**
	 * Zeigt das Benchmarkmenü und wertet die Nutzereingaben
	 * aus.
	 */
	public static void benchmarkMenu() {
		int menureturn = renderMenu();
		
		// Übergabe der Benchmarkparameter
		switch(menureturn) {
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
	
	
	/**
	 * Rendert das Benchmarkmenü.
	 * 
	 * @return Nutzereingabe
	 */
	public static int renderMenu() {
		System.out.println("======================- Benchmark Menu -===========================");
		System.out.println("= Wähle bitte eine der folgende Optionen:                         =");
		System.out.println("= (1) Benchmark, Debug Log, incl. drop & create                   =");
		System.out.println("= (2) Benchmark, Debug Log, excl. drop & create                   =");
		System.out.println("= (3) Benchmark, Debug Log, transactions, incl. drop & create     =");
		System.out.println("= (4) Benchmark, Debug Log, transactions, excl. drop & create     =");
		System.out.println("= (5) Benchmark, incl. drop & create                              =");
		System.out.println("= (6) Benchmark, excl. drop & create                              =");
		System.out.println("= (7) Benchmark, transactions, incl. drop & create                =");
		System.out.println("= (8) Benchmark, transactions, excl. drop & create                =");
		System.out.println("===================================================================");
		System.out.print("= Auswahl: ");
		
		return ConsoleReader.readInt();
	}
}