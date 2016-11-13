package de.whs.dbi.pa7.database;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.IllformedLocaleException;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Die tpsCreator Klasse enthält alle nötigen Befehle um eine initial n-tps-Datenbank
 * erstellen zu können. Hier kann auserdem ein instanziiertes Verbindungsobjekt übergeben werden,
 * um auf unterschiedliche Datenbanken gleichzeitig arbeiten zu können
 * 
 * @author Mario Kellner
 * @author Markus Hausmann
 * @author Jonas Stadtler
 */
public class tpsCreator {

    
	
	private boolean isDebug = false;
	
	/**
	 * Das Verbindungsobjekt
	 */
	private DatabaseConnection connection;
	
	/**
	 * Tabellen die in dem Shema enthalten sind, momentan nur nötig zum "droppen" der Datenbank
	 */
	public String[] tables = { "branches", "accounts", "tellers", "history"};
	
	/**
	 * Die Create-Statements für das Schema
	 */
	public String[] schema ={
			"CREATE TABLE branches (branchid INT NOT NULL, branchname CHAR(20) NOT NULL, balance INT NOT NULL, address CHAR(72) NOT NULL, PRIMARY KEY (branchid))",
			"CREATE TABLE accounts ( accid INT NOT NULL, NAME CHAR(20) NOT NULL, balance INT NOT NULL, branchid INT NOT NULL, address CHAR(68) NOT NULL, PRIMARY KEY (accid), FOREIGN KEY (branchid) REFERENCES branches )",
			"CREATE TABLE tellers ( tellerid INT NOT NULL, tellername CHAR(20) NOT NULL, balance INT NOT NULL, branchid INT NOT NULL, address CHAR(68) NOT NULL, PRIMARY KEY (tellerid), FOREIGN KEY (branchid) REFERENCES branches)",
			"CREATE TABLE history ( accid INT NOT NULL, tellerid INT NOT NULL, delta INT NOT NULL, branchid INT NOT NULL, accbalance INT NOT NULL, cmmnt CHAR(30) NOT NULL, FOREIGN KEY (accid) REFERENCES accounts, FOREIGN KEY (tellerid) REFERENCES tellers, FOREIGN KEY (branchid) REFERENCES branches )",
	};
	
	/**
	 * Unser Konstruktor. Er Überprüft, ob ein nicht leeres DatabaseConnection Objekt übergeben worden ist.
	 * 
	 * 
	 * @param connection Das Verbindungsobjekt
	 * @throws NullPointerException Das Verbindungsobjekt ist leer 
	 */
	public tpsCreator(DatabaseConnection connection) throws Exception {
		
		if(connection == null ) {
			throw new NullPointerException("connection object cannot be null");
		}
		
		try {
			connection.databaseLink.isValid(0);
			//connection.databaseLink.setAutoCommit(false);
		}
		catch(Exception err) {
			throw err;
		}
		
		this.connection = connection;
		
	}


	public void beginTransaktion() {
		try {
			connection.databaseLink.setAutoCommit(false);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	public void endAndCommitTransaction() {
		try {
			connection.databaseLink.commit();
			
			connection.databaseLink.setAutoCommit(true);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Löscht die Tabellen aus der Datenbank
	 * 
	 * @return Gibt an, ob ein Fehler vorhanden ist.
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
	 * Erstellt die Tupel in der Tabelle Branch
	 * 
	 * @param n Anzahl der Tupel, die erstellt werden
	 * @return Gibt an, ob ein Fehler vorhanden ist
	 */
	public boolean createBranchTupel(int n) {
		
		try {
			PreparedStatement insertBranches = connection.databaseLink.prepareStatement(
					"INSERT INTO branches (branchid, branchname, balance, address) VALUES (? , 'branch', 0, 'branch')"
			);
			
			for (int i = 1; i <= n; i++) {
				insertBranches.setInt(1, i);
				insertBranches.addBatch();
				
				if(isDebug) {
					System.out.println("INSERT INTO branches (branchid, branchname, balance, address) VALUES("
							+ i + ", 'branch', 0, 'branch')");
				}
			}

			insertBranches.executeBatch();
			
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
		int localConst = n*10000;
		int localRandom;
		
		try {
			PreparedStatement insertBranches = connection.databaseLink.prepareStatement(
					"INSERT INTO accounts (accid, NAME, balance, address, branchid) VALUES(?, 'account', 0,'test', ?)"
			);
			
			for (int i = 1; i <= localConst; i++) {

				localRandom = ThreadLocalRandom.current().nextInt(1, n + 1);

				insertBranches.setInt(1, i);
				insertBranches.setInt(2, localRandom);
				insertBranches.addBatch();
				
				if(isDebug) {
					System.out.println("INSERT INTO branches (branchid, branchname, balance, address) VALUES("
							+ i + ", 'branch', 0, 'branch')");
				}
			}

			insertBranches.executeBatch();
			
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
		int localRandom;
		
		try {
			PreparedStatement insertBranches = connection.databaseLink.prepareStatement(
					"INSERT INTO tellers (tellerid, tellername, balance, address, branchid) VALUES(?, 'teller', 0, 'adress', ?)"
			);
			
			for (int i = 1; i <= localConst; i++) {

				localRandom = ThreadLocalRandom.current().nextInt(1, n + 1);

				insertBranches.setInt(1, i);
				insertBranches.setInt(2, localRandom);
				insertBranches.addBatch();
				
				if(isDebug) {
					System.out.println("INSERT INTO branches (branchid, branchname, balance, address) VALUES("
							+ i + ", 'branch', 0, 'branch')");
				}
			}

			insertBranches.executeBatch();
			
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
		
		/**
		 * Jetzt kommen die Datensätze in die Datenbank yay \o/
		 */
		
		
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


	public boolean isDebug() {
		return isDebug;
	}


	public void setDebug(boolean isDebug) {
		this.isDebug = isDebug;
	}
	
}
