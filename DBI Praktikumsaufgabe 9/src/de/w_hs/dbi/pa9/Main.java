package de.w_hs.dbi.pa9;

import java.sql.SQLException;

import de.w_hs.dbi.pa9.database.ConnectionInformation;
import de.w_hs.dbi.pa9.database.DatabaseConnection;
import de.w_hs.dbi.pa9.function.ProgramStage;
import de.w_hs.dbi.pa9.function.TXHandler;

public class Main 
{
	public static DatabaseConnection connection=null;
	public static int txCountSum;
	

	public static synchronized int getTxCountSum()
	{
		return txCountSum;
	}

	public static synchronized void setTxCountSum(int txCountSum)
	{
		Main.txCountSum = txCountSum;
	}
	
	public static long time = System.currentTimeMillis()+1000;
	
	/**
	 * Main Funktion.
	 * @param args
	 * @throws Exception
	 * @author Mario Kellner
	 * @author Markus Hausmann
	 * @author Jonas Stadtler
	 */
	public static void main(String args[])
	{
		System.out.println(System.currentTimeMillis()+60000);
		
		ConnectionInformation infos = new ConnectionInformation();
		//infos.setHost("127.0.0.1");
		infos.setHost("192.168.122.70");
		infos.setDatabase("benchmark");
		infos.setUser("postgres");
		infos.setPassword("DBIPr");
		
	
		try
		{
			// Erstelle Verbindung um die History !einmalig! zu löschen!			
			DatabaseConnection con = new DatabaseConnection(infos);
			con.connect();
			con.clearHistory();
			
		    // Create the threads
		    Thread[] threadList = new Thread[5];

		    // spawn threads
		    for (int i = 0; i < 5; i++)
		    {
		        threadList[i] = new ClientThread(infos);
		        threadList[i].start();
		    }
		    
		    // Start everyone at the same time
		    System.out.println("Wartezeit für die Threads beträgt " + (time - System.currentTimeMillis()) + " Millisekunden");
		    while(time > System.currentTimeMillis())
		    {
		    	Thread.sleep(1);
		    }
		    
		    System.out.println("Wartezeit erreicht");
		    
		    ClientThread.setStartTrans();
			
			for (int i = 0; i < 5; i++)
	        {
	           threadList[i].join();
	        }
		    
			System.out.println("Gesamte Anzahl der Transaktionen: " + getTxCountSum());
			System.out.println("TPS: " + (double)getTxCountSum()/(double)300);
			
			System.out.println("Finish!");
		    
		} 
		catch (SQLException sqle)
		{
			System.out.println("Datenbank Fehler: " + sqle.getMessage());
			sqle.printStackTrace();
		}
		catch (Exception e)
		{
			System.out.println("Ein Fehler ist aufgetreten: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static void abortProgramm() {
		System.exit(0);
	}
}
