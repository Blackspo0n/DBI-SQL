package de.whs.dbi.pa7.funktionen_gui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
/**
 * Funktionen die die Speicherung und das Auslesen von Verbindungseinstellungen ermöglicht.
 * 
 * @author Markus Hausmann
 * @author Mario Kellner
 * @author Jonas Stadtler
 *
 */


public class Serialisierung 
{
	/**
	 * Ermöglicht das Einlesen und persistente Speichern von Verbindungseinstellungen.
	 * 
	 * @param ve Übergabe der Verbindungseinstellung zur Speicherung.
	 */
	public static void eingeben(Verbindungseinstellungen ve)
	{
		
        try
        {
            ObjectOutputStream objectOutput = new ObjectOutputStream(new FileOutputStream("data.ser"));
            System.out.println(ve.bezeichnung);
            // Objekte serialisiert in Datei ausgeben
            objectOutput.writeObject(ve);
          
 
            // Ausgabekanal schließen
            objectOutput.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        } 
        
	}
	/**
	 * Ermöglicht das Auslesen von bereits gespeicherten Verbindungseinstellungen.
	 * 
	 * @return Liste der bereits gespeicherten Verbindungseinstellungen.
	 */
	public static List<Verbindungseinstellungen> ausgeben() 
	{
		List <Verbindungseinstellungen> ve= new ArrayList<Verbindungseinstellungen>();
		
		try 
		{
			ObjectInputStream objectInput = new ObjectInputStream(new FileInputStream("data.ser"));
			Verbindungseinstellungen o=null;
			
			Object test= objectInput.readObject();
			
			o= (Verbindungseinstellungen)test;
		/*
			 while((o=(Verbindungseinstellungen)objectInput.readObject())!=null)
			 {
				 ve.add((Verbindungseinstellungen)o);
			 }
			 */
			System.out.println(o.getBezeichnung());
			ve.add(o);
			
		
			
			 objectInput.close();
		} catch (FileNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ve;
		
	}
}
