import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Datenbankanbindung 
{
	//benennen einer Verbindungsvariablen
	public static Connection myConnection;
	
	//herstellen einer Verbindung zur Datenbank
	public static void verbinden()
	{
		try
		{
			Class.forName("org.postgresql.Driver");
			myConnection=DriverManager.getConnection("jdbc:postgresql://localhost/CAP-Vertriebsdatenbank", "dbi", "dbi_pass");
			if(myConnection.isValid(0))
			{
				System.out.println("Verbunden!");
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/*
	 * Abfrage an die Datenbank und verarbeiten der Informationen. 
	 * Danach ausgeben der Informationen.
	 */
	public static void abfrage(int produktID)
	{
		
	}
}
