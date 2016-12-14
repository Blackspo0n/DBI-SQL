package de.w_hs.dbi.pa9;

import de.w_hs.dbi.pa9.database.ConnectionInformation;
import de.w_hs.dbi.pa9.database.DatabaseConnection;
import de.w_hs.dbi.pa9.function.ProgramStage;
import de.w_hs.dbi.pa9.function.TXHandler;

public class Main 
{
	public static DatabaseConnection connection=null;
	
	
	public static void main(String args[])
	{
		ConnectionInformation infos= new ConnectionInformation();
		infos.setHost("127.0.0.1");
		infos.setDatabase("benchmark");
		infos.setUser("postgres");
		infos.setPassword("DBI");
		
		try {
			connection=new DatabaseConnection(infos);
			connection.connect();
			if(connection.databaseLink.isValid(0))
			{
				System.out.println("Verbunden!");
			}
			else
			{
				return;
			}
			ProgramStage program = new ProgramStage(connection);
			program.attackTime();
			program.benchStage();
			program.boomOutStage();
			System.out.println("Finish!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
