package de.whs.dbi.pa7.database;

public interface TpsCreatorInterface {

	void beginTransaktion();

	void endAndCommitTransaction();

	/**
	 * Löscht die Tabellen aus der Datenbank
	 * 
	 * @return Gibt an, ob ein Fehler vorhanden ist.
	 */
	boolean dropSchema();

	/**
	 * Erstellt die Tabellen
	 * 
	 * @return Gibt an, ob win Fehler vorhanden ist.
	 */
	boolean createSchema();

	/**
	 * Erstellt die Tupel in der Tabelle Branch
	 * j
	 * @param n Anzahl der Tupel, die erstellt werden
	 * @return Gibt an, ob ein Fehler vorhanden ist
	 */
	boolean createBranchTupel(int n);

	/**
	 * Erstellt die Tupel in der Tabelle Accounts
	 * 
	 * @param n Anzahl der Tupel, die erstellt werden mal 10000
	 * @return Gibt an, ob ein Fehler vorhanden ist
	 */
	boolean createAccountTupel(int n);

	/**
	 * Erstellt die Tupel in der Tabelle Teller
	 * 
	 * @param n Anzahl der Tupel, die erstellt werden mal 10
	 * @return Gibt an, ob ein Fehler vorhanden ist
	 */
	boolean createTellerTupel(int n);

	/**
	 * Bündelt die Funktionen des tpsCreators und führt diese nach ein ander aus.
	 * 
	 * @param n Anzahl der Tupel, die erstellt werden
	 */
	void autoSetup(int n);

	boolean isDebug();

	void setDebug(boolean isDebug);

}