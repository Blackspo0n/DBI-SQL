package de.w_hs.dbi.pa9.function;

import java.sql.SQLException;

import de.w_hs.dbi.pa9.Main;
import de.w_hs.dbi.pa9.database.DatabaseConnection;

public class ProgramStage 
{
	private DatabaseConnection connection;
	private TXHandler txh;
	public ProgramStage(DatabaseConnection con) throws SQLException
	{
		if(con == null)
		{
			throw new NullPointerException("Connection darf nicht NULL sein!");
		}
		this.connection = con;
		this.txh = new TXHandler(con);
	}
	public int choose()
	{
		int number=(int)(Math.random()*100);
		if(number <= 35)
		{
			return 1;
		}
		else if( number > 35 && number <= 85)
		{
			return 2;
		}
		return 3;
	}
	private void doTX() throws SQLException
	{
		switch(choose())
		{
			case 1:
				txh.kontostandTX(chooseNumber("accounts", 100));
				break;
			case 2:
				txh.einzahlungTX(
						chooseNumber("accounts", 100),
						chooseNumber("tellers", 100),
						chooseNumber("branches", 100),
						chooseNumber("delta", 100));
				break;
			case 3:
				txh.analyseTX(chooseNumber("delta", 100));
				break;
		}
	}
	private int chooseNumber(String name, int n)
	{
		double number=Math.random();
		switch(name)
		{
			case "accounts":
				number*=10000*n;
				break;
			case "branches":
				number*=n;
				break;
			case "tellers":
				number*=10*n;
				break;
			case "delta":
				number*=10000;
				break;
		}
		return (int)(number+1);
	}
	public void attackTime() throws SQLException, InterruptedException
	{
		System.out.println("Führe Aufwämnphase durch");
		
		long start=System.currentTimeMillis();
		long start2=System.currentTimeMillis();
		
		while(start+240000>=System.currentTimeMillis())
		{
			doTX();
			Thread.sleep(50);
			if(System.currentTimeMillis()-start2 >= 1000) {
				System.out.println("Verbleibene Sekunden: " + (240 - ((System.currentTimeMillis()-start)/1000)));
				
				start2 = System.currentTimeMillis();
				
			}
		}
	}
	public void benchStage() throws SQLException, InterruptedException
	{
		System.out.println("Starte die Benchphase!");
		int txCounter = 0;
		

		long start=System.currentTimeMillis();
		long start2=System.currentTimeMillis();
		
		while(start+300000>=System.currentTimeMillis())
		{

			doTX();
			txCounter++;
			
			if(System.currentTimeMillis()-start2 >= 1000) {
				System.out.println("Anzahl der Transaktionen pro Sekunde: " + (double)txCounter/(double)((System.currentTimeMillis()-start)/1000));

				System.out.println("Verbleibene Sekunden: " + (300 - ((System.currentTimeMillis()-start)/1000)));
				start2 = System.currentTimeMillis();
				
			}
			
			Thread.sleep(50);
		}
		
		Main.setTxCountSum(Main.getTxCountSum() + txCounter);
		System.out.println("Anzahl der Transaktionen: " +txCounter);
		System.out.println("Anzahl der Transaktionen pro Sekunde: " + (double)((double)txCounter/(double)300));
		
	}
	public void boomOutStage() throws SQLException, InterruptedException
	{
		System.out.println("Boom Out Stage!");
		long start=System.currentTimeMillis();
		while(start+60000>=System.currentTimeMillis())
		{
			doTX();
			Thread.sleep(50);
			
		}
	}
}
