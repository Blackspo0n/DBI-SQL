package de.w_hs.dbi.pa9;

import de.w_hs.dbi.pa9.database.ConnectionInformation;
import de.w_hs.dbi.pa9.database.DatabaseConnection;
import de.w_hs.dbi.pa9.function.ProgramStage;
import de.w_hs.dbi.pa9.function.TXHandler;

public class Main 
{
	public static DatabaseConnection connection=null;
	public static int txCountSum;
	

	public static synchronized int getTxCountSum() {
		return txCountSum;
	}

	public static synchronized void setTxCountSum(int txCountSum) {
		Main.txCountSum = txCountSum;
	}

	/**
	 * Main Funktion.
	 * @param args
	 * @throws Exception
	 * @author Mario Kellner
	 * @author Markus Hausmann
 *	   @author Jonas Stadtler
	 */
	public static void main(String args[]) throws Exception
	{
		
		ConnectionInformation infos= new ConnectionInformation();
		infos.setHost("127.0.0.1");
		infos.setDatabase("benchmark");
		infos.setUser("postgres");
		infos.setPassword("DBIPr");
		

		try {
		    // Create the threads
		    Thread[] threadList = new Thread[5];

		    // spawn threads
		    for (int i = 0; i < 5; i++)
		    {
		        threadList[i] = new ClientThread(infos);
		        threadList[i].start();
		    }
		    
		    // Start everyone at the same time
		    Thread.sleep(1000);
		    
		    ClientThread.setStartTrans();

			for (int i = 0; i < 5; i++)
	        {
	           threadList[i].join();
	        }
			
			System.out.println("Gesamte Anzahl der Transaktionen: " + getTxCountSum());
			
			System.out.println("Finish!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
