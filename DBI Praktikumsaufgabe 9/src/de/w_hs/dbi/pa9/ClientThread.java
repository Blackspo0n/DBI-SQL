package de.w_hs.dbi.pa9;

import java.sql.SQLException;

import de.w_hs.dbi.pa9.database.ConnectionInformation;
import de.w_hs.dbi.pa9.database.DatabaseConnection;
import de.w_hs.dbi.pa9.function.ProgramStage;

/**
 *  Zuweisungen an Threads.
 *  
 *  @author Mario Kellner
	@author Markus Hausmann
 *  @author Jonas Stadtler
 *
 */
public class ClientThread extends Thread {
	private DatabaseConnection threadCon;
	static boolean startTrans = false;
	private int threadID;
	private static int nextThreadID = 1;
	
	private synchronized static int getNextId() {
		return nextThreadID++;
	}


	
	public ClientThread(ConnectionInformation infos) throws Exception {
		threadCon = new DatabaseConnection(infos);
		threadID = getNextId();
	}
	
	static synchronized void setStartTrans () {
		startTrans = true;
	}

	synchronized boolean getStartTrans () {
		return startTrans;
	}

	public void run() {
		try {
			threadCon.connect();
			
			if(threadCon.databaseLink.isValid(0))
			{
				System.out.println("[Thread " + threadID +"]Thread erfolgreich zur Datenbank verbunden!");
			}
			else
			{
				throw new SQLException("Keine Verbindung zur Datenbank möglich");
			}
			
			System.out.println("[Thread " + threadID +"]Auf Startsignal warten ...");
			
			while(!getStartTrans()) {
				yield();
			}
			
			System.out.println("[Thread " + threadID +"]Startsignal erhalten!");
			
			
			ProgramStage program = new ProgramStage(threadCon);
			

			System.out.println("[Thread " + threadID +"]Führe Funktion attackStage aus!");
			program.attackStage();
			
			System.out.println("[Thread " + threadID +"]Führe Funktion benchStage aus!");
			program.benchStage();
			
			System.out.println("[Thread " + threadID +"]Führe Funktion boomOutStage aus!");
			program.boomOutStage();
		}
		catch (SQLException sqle)
		{
			System.out.println("Datenbank Fehler: " + sqle.getMessage());
			sqle.printStackTrace();
			
			Main.abortProgramm();
		}
		catch (Exception e)
		{
			System.out.println("Ein Fehler ist aufgetreten: " + e.getMessage());
			e.printStackTrace();

			Main.abortProgramm();
		}
			
	}
}