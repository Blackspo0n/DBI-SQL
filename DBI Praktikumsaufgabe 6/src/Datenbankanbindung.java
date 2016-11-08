import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Datenbankanbindung 
{
	//benennen einer Verbindungsvariablen
	public static Connection myConnection;
	
	//herstellen einer Verbindung zur Datenbank
	public static String verbinden()
	{
		try
		{
			Class.forName("org.postgresql.Driver");
			myConnection=DriverManager.getConnection("jdbc:postgresql://localhost/CAP-Vertriebsdatenbank", "dbi", "dbi_pass");
			if(myConnection.isValid(0))
			{
				System.out.println("Verbunden!");
				
			}
			return "Verbunden!";
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			return "Fehler bei der Verbindung zur Datenbank!";
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Fehler bei der Verbindung zur Datenbank!";
		}
	}
	/*
	 * Abfrage an die Datenbank und verarbeiten der Informationen. 
	 * Danach ausgeben der Informationen.
	 */
	public static List<Agent> abfrage(int produktID)
	{
		//erstellen des Abfragebefehls
		String abfrageBefehl="SELECT o.aid, sum (o.dollars) AS u FROM orders AS o WHERE o.pid='p"+produktID+"' GROUP BY o.aid ORDER BY u DESC";
		//erstellen der ArrayList
		List<Agent>agents=new ArrayList<Agent>();
		//Abfrage an Datenbank
		try 
		{
			Statement statement=myConnection.createStatement();
			ResultSet rsAbfrage=statement.executeQuery(abfrageBefehl);
			//eintagen der Ergebnispaare in Liste
			while(rsAbfrage.next())
			{
				agents.add(new Agent(rsAbfrage.getDouble("dollars"), rsAbfrage.getString("aid")));
			}
		}
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return agents;
	}
}
