package de.w_hs.dbi.pa9;

import de.w_hs.dbi.pa9.database.ConnectionInformation;
import de.w_hs.dbi.pa9.database.DatabaseConnection;
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
			TXHandler txh= new TXHandler(connection);
			System.out.println(txh.einzahlungTX(1, 1, 1, 200));
			System.out.println(txh.kontostandTX(1));
			System.out.println(txh.analyseTX(200));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
