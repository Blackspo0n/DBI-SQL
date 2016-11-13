package de.whs.dbi.pa7;

import de.whs.dbi.pa7.database.TpsCreatorInterface;

public class Benchmark {
	private static long startTime;
	
	public static void BenchmarkTask(int size, TpsCreatorInterface tpsDBCreator, boolean debugLog, boolean useTransaction, boolean usePreamble) {
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
	}
	
	public static void startTimer() {
		startTime  = System.currentTimeMillis();
	}
	
	public static long getCurrentMilliseconds() {
		return System.currentTimeMillis() - startTime;
	}

}
