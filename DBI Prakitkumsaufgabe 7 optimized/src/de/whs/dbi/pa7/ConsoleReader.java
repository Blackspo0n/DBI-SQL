package de.whs.dbi.pa7;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Einige Konsolen Helfer. Die Klasse ist con der BasicIO.java aus dem GDI1 Kurs adaptiert
 * 
 * @author Mario Kellner
 * @author Markus Hausmann
 * @author Jonas Stadtler
 */
public class ConsoleReader {
	
	/**
	 * Liest ein j/J oder ein n/N aus der Konsole
	 * 
	 * @return j = true und n = false
	 */
	public static boolean readJn() {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		try {
			String strTmp = br.readLine();
			if(strTmp.length() != 0 && (strTmp.charAt(0) == 'j' || strTmp.charAt(0) == 'J')) {
				return true;
			}
		}
		catch(Exception err) {}
		
		return false;
	}
	
	/**
	 * Liest einen String aus der Konsole aus
	 * 
	 * @return ausgelesener String
	 */
	public static String readString () {
		BufferedReader din = new BufferedReader(new InputStreamReader(System.in)); 
		
		try {	
			return din.readLine();
		}
		catch(IOException e) {				  
			return "Ein-/Ausgabe-Fehler";
		}
	}
	
	/**
	 * Liest einen Integer aus der Konsole aus
	 * 
	 * @return ausgelesener String
	 */
	public static int readInt()
	{
		try 
		{
			BufferedReader din = new BufferedReader(new InputStreamReader(System.in));
			return Integer.parseInt(din.readLine());
		}
		catch (NumberFormatException e)
		{
			System.out.println("Bitte gebe eine gültige Zahl ein.");
			return readInt();
		}
		catch (IOException e)
		{
			return -1;
		}
	}
}