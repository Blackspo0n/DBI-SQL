package de.whs.dbi.pa7.funktionen_gui;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Date;

public class Dokument_erstellen 
{
	public static void erstellenTextdatei()
	{
		try
		{
            System.setOut(new PrintStream(new FileOutputStream("ergebnis.txt")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
		System.out.println("Ergebnisse der Messung am "+new Date()+":");
	}
}
