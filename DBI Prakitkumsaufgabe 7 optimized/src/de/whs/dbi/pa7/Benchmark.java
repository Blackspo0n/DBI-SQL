package de.whs.dbi.pa7;

import de.whs.dbi.pa7.database.tpsCreator;

public class Benchmark {
	private static long startTime;
	
	public static void BenchmarkTask(int size, tpsCreator tpsDBCreator, boolean debugLog, boolean useTransaction, boolean usePreamble) {
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
		
		
		tpsDBCreator.createBranchTupel(size);
		tpsDBCreator.createAccountTupel(size);
		tpsDBCreator.createTellerTupel(size);
		
		if(useTransaction) {
			tpsDBCreator.endAndCommitTransaction();
		}
		
		System.out.println("Benchmark abgeschlossen. Dauer " + getCurrentMilliseconds() + " Millisekunden");
	}
	
	public static void startTimer() {
		startTime  = System.currentTimeMillis();
	}
	
	public static long getCurrentMilliseconds() {
		return System.currentTimeMillis() - startTime;
	}
}
