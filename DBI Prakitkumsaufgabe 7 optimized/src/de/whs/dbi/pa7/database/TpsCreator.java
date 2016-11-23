package de.whs.dbi.pa7.database;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.ThreadLocalRandom;

import org.postgresql.copy.CopyIn;
import org.postgresql.copy.CopyManager;
import org.postgresql.core.BaseConnection;

/**
 * Die tpsCreator Klasse enthält alle nötigen Befehle um eine initial n-tps-Datenbank
 * erstellen zu können. Hier kann auserdem ein instanziiertes Verbindungsobjekt übergeben werden,
 * um auf unterschiedliche Datenbanken gleichzeitig arbeiten zu können
 * 
 * @author Mario Kellner
 * @author Markus Hausmann
 * @author Jonas Stadtler
 */
public class TpsCreator implements TpsCreatorInterface {
	
	/**
	 * Debug Variable
	 */
	private boolean isDebug = false;
	
	/**
	 * Das Verbindungsobjekt
	 */
	private DatabaseConnection connection;
	
	/**
	 * Tabellen die in dem Shema enthalten sind, momentan nur nötig zum "droppen" der Datenbank
	 */
	public String[] tables = {"branches", "accounts", "tellers", "history"};
	
	/**
	 * Die Create-Statements für das Schema
	 */
	public String[] schema = {
			"CREATE TABLE branches (branchid INT NOT NULL, branchname CHAR(20) NOT NULL, balance INT NOT NULL, address CHAR(72) NOT NULL)",
			"CREATE TABLE accounts (accid INT NOT NULL, NAME CHAR(20) NOT NULL, balance INT NOT NULL, branchid INT NOT NULL, address CHAR(68) NOT NULL)",
			"CREATE TABLE tellers (tellerid INT NOT NULL, tellername CHAR(20) NOT NULL, balance INT NOT NULL, branchid INT NOT NULL, address CHAR(68) NOT NULL)",
			"CREATE TABLE history (accid INT NOT NULL, tellerid INT NOT NULL, delta INT NOT NULL, branchid INT NOT NULL, accbalance INT NOT NULL, cmmnt CHAR(30) NOT NULL)",
	};
	
	/**
	 * Alter Statements zum setzen der Keys
	 */
	public String[] AlterStatements = {
			"ALTER TABLE branches ADD PRIMARY KEY(branchid)",
			"ALTER TABLE accounts ADD PRIMARY KEY(accid)",
			"ALTER TABLE tellers ADD PRIMARY KEY(tellerid)",
			"ALTER TABLE accounts ADD FOREIGN KEY(branchid) REFERENCES branches(branchid)",
			"ALTER TABLE tellers ADD FOREIGN KEY(branchid) REFERENCES branches(branchid)",
			"ALTER TABLE history ADD FOREIGN KEY(branchid) REFERENCES branches(branchid)",
			"ALTER TABLE history ADD FOREIGN KEY(tellerid) REFERENCES tellers(tellerid)",
			"ALTER TABLE history ADD FOREIGN KEY(accid) REFERENCES accounts(accid)"
	};

	
	/**
	 * Unser Konstruktor. Er Überprüft, ob ein nicht leeres DatabaseConnection Objekt übergeben worden ist.
	 * 
	 * 
	 * @param connection Das Verbindungsobjekt
	 * @throws NullPointerException Das Verbindungsobjekt ist leer 
	 */
	public TpsCreator(DatabaseConnection connection) throws Exception {
		System.out.println("Info: Optimierte Version wird benutzt!");
		
		if(connection == null ) {
			throw new NullPointerException("connection object cannot be null");
		}
		
		try {
			connection.databaseLink.isValid(0);
		}
		catch(Exception err) {
			throw err;
		}
		
		this.connection = connection;
		
	}

	/**
	 * Aktiviert das Transaktion-System.
	 */
	public void beginTransaktion() {
		try {
			connection.databaseLink.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Comitted die Queries zur DAtenbank und beendet das Transaktion-System
	 * 
	 * @return Gibt an, ob win Fehler vorhanden ist.
	 */
	public void endAndCommitTransaction() {
		try {
			connection.databaseLink.commit();
			connection.databaseLink.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Löscht die Tabellen aus der Datenbank
	 * 
	 * @return Gibt an, ob win Fehler vorhanden ist.
	 */
	public boolean dropSchema() {
		try {
			Statement statement = connection.databaseLink.createStatement();

			for (String table : this.tables) {
				StringBuilder strBuilder = new StringBuilder("DROP TABLE IF EXISTS ").append(table).append(" CASCADE");
				statement.executeUpdate(strBuilder.toString());
				
				if(isDebug) {
					System.out.println(strBuilder.toString());
				}
			}

			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	/**
	 * Erstellt die Tabellen
	 * 
	 * @return Gibt an, ob win Fehler vorhanden ist.
	 */
	public boolean createSchema() {
	
		try {
			Statement statement = connection.databaseLink.createStatement();

			for (String table : this.schema) {
				statement.executeUpdate(table);
				if(isDebug) {
					System.out.println(table);
				}
			}

			return true;
		} catch (SQLException e) {
			return false;
		}
	}
	
	/**
	 * Erstellt die Tabellen
	 * 
	 * @return Gibt an, ob win Fehler vorhanden ist.
	 */
	public boolean createKeys() {
	
		try {
			Statement statement = connection.databaseLink.createStatement();

			for (String table : this.AlterStatements) {
				statement.executeUpdate(table);
				if(isDebug) {
					System.out.println(table);
				}
			}

			return true;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return false;
		}
	}
	
	/**
	 * Erstellt die Tupel in der Tabelle Branch.
	 * 
	 * @param n Anzahl der Tupel, die erstellt werden
	 * @return Gibt an, ob ein Fehler vorhanden ist
	 */
	public boolean createBranchTupel(int n) {
		try {
			
			CopyManager cpm = new CopyManager((BaseConnection) connection.databaseLink);
			CopyIn ci = cpm.copyIn("COPY branches (branchid, branchname, balance, address) FROM STDIN WITH DELIMITER '|'");

			for (int i = 1; i <= n; i++) {
				// Stringbuilder immer neu erstellen, denn je größer dieser wird, desto langsamer wird dieser auch
				
				StringBuilder sb = new StringBuilder("|aaaaaaaaaaaaaaaaaaaa|0|aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa\n");
				sb.insert(0, i);
				ci.writeToCopy(sb.toString().getBytes(), 0, sb.length());
				
				if(isDebug) {
					System.out.println(sb.toString());
				}
			}
	        ci.endCopy();			
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * Erstellt die Tupel in der Tabelle Accounts
	 * 
	 * @param n Anzahl der Tupel, die erstellt werden mal 10000
	 * @return Gibt an, ob ein Fehler vorhanden ist
	 */
	public boolean createAccountTupel(int n) {
		int localConst = n*100000;
		
		try {
			CopyManager cpm = new CopyManager((BaseConnection) connection.databaseLink);
			
			CopyIn ci = cpm.copyIn("COPY accounts(accid, NAME, balance, address, branchid) FROM STDIN WITH DELIMITER '|'");

			for (int i = 1; i <= localConst; i++) {
				/* Stringbuilder immer neu erstellen, denn je größer dieser wird, desto langsamer wird dieser auch.
				* Der Initialwert des Stringbuilders ist extrem wichtig, denn durch diesen Wert wird automatisch genug Speicher reserviert
				*/
				StringBuilder sb = new StringBuilder("|aaaaaaaaaaaaaaaaaaaa|0|aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa|").append(
						ThreadLocalRandom.current().nextInt(1, n + 1)
				).append("\n").insert(0, i);
				
				ci.writeToCopy(sb.toString().getBytes(), 0, sb.length());
				
				if(isDebug) {
					System.out.print(sb.toString());
				}
			}
	        ci.endCopy();			
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}	
	
	/**
	 * Erstellt die Tupel in der Tabelle Teller
	 * 
	 * @param n Anzahl der Tupel, die erstellt werden mal 10
	 * @return Gibt an, ob ein Fehler vorhanden ist
	 */
	public boolean createTellerTupel(int n) {
		int localConst = n*10;
		
		try {
			CopyManager cpm = new CopyManager((BaseConnection) connection.databaseLink);
			CopyIn ci = cpm.copyIn("COPY tellers (tellerid, tellername, balance, address, branchid) FROM STDIN WITH DELIMITER '|'");
			
			for (int i = 1; i <= localConst; i++) {
				
				// Stringbuilder immer neu erstellen, denn je größer dieser wird, desto langsamer wird dieser auch
				StringBuilder sb = new StringBuilder("|aaaaaaaaaaaaaaaaaaaa|0|aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa|").append(
						ThreadLocalRandom.current().nextInt(1, n + 1)
				).append("\n").insert(0, i);
				ci.writeToCopy(sb.toString().getBytes(), 0, sb.length());
				
				if(isDebug) {
					System.out.println(sb.toString());
				}
			}
	        ci.endCopy();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * Bündelt die Funktionen des tpsCreators und führt diese nach ein ander aus.
	 * 
	 * @param n Anzahl der Tupel, die erstellt werden
	 */
	public void autoSetup(int n) {
		System.out.println("Starte automatisches Setup...");
		System.out.println("Erzeuge eine " + n + "-tps-Datenbank");
		
		if(!dropSchema()) {
			System.out.println("Warnung: Datenbank konnte nicht geleert werden!");
			return;
		}
		if(!createSchema()) {
			System.out.println("Tabellen konnten nicht erzeugt werden. Stoppe Setup ...");
			return;
		}
		
		if(!createBranchTupel(n)) {
			System.out.println("Branches konnten nicht angelegt werden. Stoppe Setup ...");
			return;
		}
		if(!createAccountTupel(n)) {
			System.out.println("Accounts konnten nicht angelegt werden. Stoppe Setup ...");
			return;
		}
		if(!createTellerTupel(n)) {
			System.out.println("Teller konnten nicht angelegt werden. Stoppe Setup ...");
			return;
		}

		System.out.println("Erstellen der " + n + "-tps-Datenbak erfolgreich");
	}

	/**
	 * Gibt an, ob das DebugLogging aktiviert ist.
	 */
	public boolean isDebug() {
		return isDebug;
	}
	
	/**
	 * Aktivert bzw deaktiviert den Debug Modus
	 */
	public void setDebug(boolean isDebug) {
		this.isDebug = isDebug;
	}

	@Override
	public void finishUp() {
		this.createKeys();
	}
}