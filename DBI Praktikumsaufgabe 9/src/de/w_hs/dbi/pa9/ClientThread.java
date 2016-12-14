package de.w_hs.dbi.pa9;

import java.sql.SQLException;

import de.w_hs.dbi.pa9.database.ConnectionInformation;
import de.w_hs.dbi.pa9.database.DatabaseConnection;
import de.w_hs.dbi.pa9.function.ProgramStage;

public class ClientThread extends Thread {
	private DatabaseConnection threadCon;
	static boolean startTrans = false;
	
	public ClientThread(ConnectionInformation infos) throws Exception {
		threadCon = new DatabaseConnection(infos);
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
				System.out.println("Thread erfolgreich erfolgreich verbunden!");
			}
			else
			{
				return;
			}
			
			while(!getStartTrans()) {
				yield();
			}
			
			System.out.println("Thread funktioniert");

			ProgramStage program = new ProgramStage(threadCon);
			program.attackTime();
			program.benchStage();
			program.boomOutStage();
		} catch (Exception e) {
			
		}

	}
	
}
