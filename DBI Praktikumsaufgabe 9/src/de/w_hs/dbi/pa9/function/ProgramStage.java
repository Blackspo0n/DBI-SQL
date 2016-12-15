package de.w_hs.dbi.pa9.function;

import java.sql.SQLException;

import de.w_hs.dbi.pa9.Main;
import de.w_hs.dbi.pa9.database.DatabaseConnection;

/**
 * 
 * Diese Klasse enth�lt alle die drei Schritte des Programmablaufs. Zudem werden hier die Abfrage
 * Werte per Zufall definiert und die Gewichtung erfolgt im geforderten Verh�ltnis.
 *
 * @author Mario Kellner
 * @author Markus Hausmann
 * @author Jonas Stadtler
 * 
 *
 */
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
	/**
	 * Entscheidet per gewichteten Zufall dar�ber, welche Abfrage an die Datenbank geschickt wird..
	 * 
	 * @return Gibt die Zahl 1, 2 oder 3 zur�ck. Dadurch wird im folgenden �ber die Abfrage entschieden.
	 */
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
	/**
	 * Entscheidet �ber die auszuf�hrende Abfrage.
	 * 
	 * @throws SQLException Fehler, welcher bei der Abfrage an die Datenbank auftreten kann.
	 */
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
	/**
	 * Entscheidet �ber die jeweiligen Zahlen, welche mit der Abfrage zur Datenbank geschickt werden.
	 * @param name Bezeichnung der anzusprechenden Tabelle.
	 * @param n Gr��e der Datenbank in TPS.
	 * @return Zuf�llige, aber passende �bergabewerte, welche zur Abfrage an die Datenbank 
	 * verwendet werden.
	 */
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
	/**
	 * Ausf�hren des ersten Programmteils.
	 * 
	 * @throws SQLException M�glicher Fehler bei der DB-Abfrage.
	 * @throws InterruptedException M�glicher Fehler bei der Wartezeit.
	 */
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
	/**
	 * Ausf�hren des zweiten Programmteils.
	 * 
	 * @throws SQLException M�glicher Fehler bei der DB-Abfrage.
	 * @throws InterruptedException M�glicher Fehler bei der Wartezeit.
	 */
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
	/**
	 * Ausf�hren des dritten Programmteils.
	 * 
	 * @throws SQLException  M�glicher Fehler bei der DB-Abfrage.
	 * @throws InterruptedException M�glicher Fehler bei der Wartezeit.
	 */
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