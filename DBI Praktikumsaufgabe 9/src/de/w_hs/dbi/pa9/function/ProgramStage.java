package de.w_hs.dbi.pa9.function;

import java.sql.SQLException;

import de.w_hs.dbi.pa9.Main;
import de.w_hs.dbi.pa9.database.DatabaseConnection;

/**
 * 
 * Diese Klasse enthält alle die drei Schritte des Programmablaufs. Zudem werden hier die Abfrage
 * Werte per Zufall definiert und die Gewichtung erfolgt im geforderten Verhältnis.
 *
 * @author Mario Kellner
 * @author Markus Hausmann
 * @author Jonas Stadtler
 * 
 *
 */
public class ProgramStage 
{
	private int TransactionNumber;
	private int AccountsNumber;
	private int BranchesNumber;
	private int TellersNumber;
	private int deltaNumber;
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
	 * Entscheidet per gewichteten Zufall darüber, welche Abfrage an die Datenbank geschickt wird..
	 * 
	 * @return Gibt die Zahl 1, 2 oder 3 zurück. Dadurch wird im folgenden über die Abfrage entschieden.
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
	 * Entscheidet über die jeweiligen Zahlen, welche mit der Abfrage zur Datenbank geschickt werden.
	 * @param name Bezeichnung der anzusprechenden Tabelle.
	 * @param n Größe der Datenbank in TPS.
	 * @return Zufällige, aber passende Übergabewerte, welche zur Abfrage an die Datenbank 
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
	
	public void thinkTime() throws InterruptedException {
		long startTime = System.currentTimeMillis();
		
		this.generateNumbers();
		
		while(System.currentTimeMillis()-startTime < 50) {
			Thread.sleep(1);
		}
	}
	
	public void generateNumbers() {
		this.TransactionNumber = choose();
		this.AccountsNumber = chooseNumber("accounts", 100);
		this.TellersNumber = chooseNumber("tellers", 100);
		this.BranchesNumber = chooseNumber("branches", 100);
		this.deltaNumber = chooseNumber("delta", 100);
	}
	
	
	/**
	 * Entscheidet über die auszuführende Abfrage. 
	 * Die zufälligen Werte werden von der Funktion generateNumbers berechnet.
	 * 
	 * @throws SQLException Fehler, welcher bei der Abfrage an die Datenbank auftreten kann.
	 */
	private void doTX() throws SQLException
	{
		switch(this.TransactionNumber)
		{
			case 1:
				txh.kontostandTX(this.AccountsNumber);
				break;
			case 2:
				txh.einzahlungTX(
						this.AccountsNumber,
						this.TellersNumber,
						this.BranchesNumber,
						this.deltaNumber
				);
				break;
			case 3:
				txh.analyseTX(this.deltaNumber);
				break;
		}
	}
	
	/**
	 * Ausführen des ersten Programmteils.
	 * 
	 * @throws SQLException Möglicher Fehler bei der DB-Abfrage.
	 * @throws InterruptedException Möglicher Fehler bei der Wartezeit.
	 */
	public void attackStage() throws SQLException, InterruptedException
	{	
		long start = System.currentTimeMillis();
		
		// generiere Initialnummern
		this.generateNumbers();
		
		while(start + 240000 >= System.currentTimeMillis())
		{
			doTX();
			this.thinkTime();
		}
	}
	
	/**
	 * Ausführen des zweiten Programmteils.
	 * 
	 * @throws SQLException Möglicher Fehler bei der DB-Abfrage.
	 * @throws InterruptedException Möglicher Fehler bei der Wartezeit.
	 */
	public void benchStage() throws SQLException, InterruptedException
	{
		int txCounter = 0;
		long start = System.currentTimeMillis();
		
		// generiere Initialnummern
		this.generateNumbers();
		
		while(start + 300000 >= System.currentTimeMillis())
		{
			doTX();
			txCounter++;
			this.thinkTime();	
		}
		
		Main.setTxCountSum(Main.getTxCountSum() + txCounter);
		
		System.out.println("Anzahl der Transaktionen: " + txCounter);
		System.out.println("Anzahl der Transaktionen pro Sekunde: " + (double)((double)txCounter/(double)300));
		
	}
	
	/**
	 * Ausführen des dritten Programmteils.
	 * 
	 * @throws SQLException  Möglicher Fehler bei der DB-Abfrage.
	 * @throws InterruptedException Möglicher Fehler bei der Wartezeit.
	 */
	public void boomOutStage() throws SQLException, InterruptedException
	{
		long start = System.currentTimeMillis();
		
		// generiere Initialnummern
		this.generateNumbers();
		
		while(start + 60000 >= System.currentTimeMillis())
		{
			doTX();
			this.thinkTime();
		}
	}
}
