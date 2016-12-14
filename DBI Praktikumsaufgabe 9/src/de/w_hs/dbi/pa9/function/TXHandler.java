package de.w_hs.dbi.pa9.function;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import de.w_hs.dbi.pa9.database.DatabaseConnection;

public class TXHandler 
{
	private DatabaseConnection connection;
	private Statement statement;
	public TXHandler(DatabaseConnection con) throws SQLException
	{
		this.connection=con;
		statement = connection.databaseLink.createStatement();
	}
	public double kontostandTX(int accID) throws SQLException
	{
		Statement statement = connection.databaseLink.createStatement();
		ResultSet rs = statement.executeQuery("SELECT balance FROM accounts WHERE accid="+accID);
		if(rs.next() == false)
		{
			throw new SQLException("Kontonummer nicht vorhanden!");
		}
		return rs.getDouble(1);
	}
	public double einzahlungTX(int accID, int tellerID, int branchID, double delta) throws SQLException
	{
		connection.databaseLink.setAutoCommit(false);
		
		statement.executeUpdate("UPDATE branches SET balance=balance+"+delta+" WHERE branchid="+branchID);
		statement.executeUpdate("UPDATE tellers SET balance=balance+"+delta+" WHERE tellerid="+tellerID);
		statement.executeUpdate("UPDATE accounts SET balance=balance+"+delta+" WHERE accid="+accID);
		statement.executeUpdate("INSERT INTO history (accid, tellerid, delta, branchid, accbalance, cmmnt)"
				+ "VALUES("+accID+", "+tellerID+", "+delta+", "+branchID+", (SELECT balance FROM accounts WHERE accid="+accID+"), ' ')");
		ResultSet rs = statement.executeQuery("SELECT balance FROM accounts WHERE accid="+accID);
		
		
		connection.databaseLink.commit();
		connection.databaseLink.setAutoCommit(true);
		if(rs.next() == false)
		{
			throw new SQLException("Kontonummer nicht vorhanden");
		}
		
		return rs.getDouble(1);
	}
	public int analyseTX(double delta) throws SQLException
	{
		Statement statement = connection.databaseLink.createStatement();
		ResultSet rs = statement.executeQuery("SELECT COUNT(*) FROM history WHERE delta="+delta);
		if(rs.next() == false)
		{
			throw new SQLException("Kein Eintrag mit dem Wert "+delta+" vorhanden!");
		}
		return rs.getInt(1);
		
	}
}
