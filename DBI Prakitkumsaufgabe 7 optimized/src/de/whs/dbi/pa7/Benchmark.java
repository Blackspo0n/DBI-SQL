package de.whs.dbi.pa7;

import java.io.PrintStream;

import de.whs.dbi.pa7.database.TpsCreatorInterface;

public class Benchmark {
	
	/**
	 * Globale Startzeit des Benchmarks
	 */
	private static long startTime;
	
	/**
	 * Führt den Benchmark mit den Übergebenen Parametern durch.
	 * 
	 * @param size Die geplante Größe der n-tps-Datenbank
	 * @param tpsDBCreator Das Objekt, welches die Datenbank erzeugen kann
	 * @param debugLog Aktiviert das Debug Logging (SQL-Queries)
	 * @param useTransaction Gibt an, ob SQL-Transaktionen für die Datenbank ausgeführt werden sollen
	 * @param usePreamble Gibt an, ob die drop- und createstatements mit ins Benchmark mit einfließen sollen
	 */
	public static void BenchmarkTask(int size, TpsCreatorInterface tpsDBCreator, boolean debugLog, boolean useTransaction, boolean usePreamble) {
		de.whs.dbi.pa7.funktionen_gui.Dokument_erstellen.erstellenTextdatei();
		System.out.println("Brenchmarking gestartet");
		System.out.println("Parameter: debugLog = " + debugLog + ", useTransaction = " + useTransaction + ", usePreamble = " + usePreamble);

		tpsDBCreator.setDebug(debugLog);
		
		if(!usePreamble) {
			startTimer();
			if(useTransaction) {
				tpsDBCreator.beginTransaktion();
			}
		}

		tpsDBCreator.dropSchema();
		tpsDBCreator.createSchema();
		
		if(usePreamble) {
			startTimer();
			if(useTransaction) {
				tpsDBCreator.beginTransaktion();
			}
		}
		
		long localTime  = System.currentTimeMillis();
		tpsDBCreator.createBranchTupel(size);
		System.out.println("Branches angelegt! Dauer " + (System.currentTimeMillis() - localTime) + " Millisekunden.");
		
		localTime  = System.currentTimeMillis();
		tpsDBCreator.createAccountTupel(size);
		System.out.println("Accounts angelegt! Dauer " + (System.currentTimeMillis() - localTime) + " Millisekunden.");
		
		localTime  = System.currentTimeMillis();
		tpsDBCreator.createTellerTupel(size);
		System.out.println("Teller angelegt! Dauer " + (System.currentTimeMillis() - localTime) + " Millisekunden.");
		
		if(useTransaction) {
			tpsDBCreator.endAndCommitTransaction();
		}
		
		System.out.println("Benchmark abgeschlossen. Gesamtdauer " + getCurrentMilliseconds() + " Millisekunden.");

		tpsDBCreator.finishUp();
	}
	
	/**
	 * Setzt die Startzeit auf die jetzige Zeit
	 */
	public static void startTimer() {
		startTime  = System.currentTimeMillis();
	}
	
	/**
	 * Gibt die Differenz aus der Startzeit und der jetzigen Zeit aus
	 * 
	 * @return Differenz der Subtraktion
	 */
	public static long getCurrentMilliseconds() {
		return System.currentTimeMillis() - startTime;
	}
}