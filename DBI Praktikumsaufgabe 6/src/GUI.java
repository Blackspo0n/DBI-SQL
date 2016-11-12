import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class GUI extends JFrame implements ActionListener
{
	
	private static final long serialVersionUID = 1L;
	public JFrame frame;
	public JTextField eingabe;
	public JLabel schrift;
	public JButton bestaetigen;
	public JFrame ausgabeFenster;
	public JTextArea ausgabe;
	
	/**
	 * Dies ist der Konstrukter der GUI.
	 * 
	 * @author Markus Hausmann
	 * @author Mario Kellner
	 * @author Jonas Stadtler
	 * 
	 */
	public GUI()
	{
		/*
		 *Erstellen und anpassen des Frames.  
		 */
		this.frame=new JFrame("Menue");
		frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		frame.setSize(500, 500);
		frame.setLocationRelativeTo(null);
		
		/*
		 * Erstellen der Eingabenansicht.
		 */
		
		erstellenEingabe();
		
		
		
		frame.setVisible(true);
	}
	
	/**
	 * Erstellen des Eingabebereichs, wird vom Konstruktor der GUI aufgerufen.
	 * 
	 * @author Markus Hausmann
	 * @author Mario Kellner
	 * @author Jonas Stadtler
	 * 
	 */
	private void erstellenEingabe()
	{
		eingabe=new JTextField();
		schrift=new JLabel("Geben sie eine ProduktID ein:");
		bestaetigen=new JButton();
		bestaetigen.setText("bestätigen");
		
		//Layout zuweisen
		GridLayout eingabeFenster=new GridLayout(3,0);
		frame.setLayout(eingabeFenster);
		
		frame.add(schrift);
		frame.add(eingabe);
		frame.add(bestaetigen);
		
		eingabe.addActionListener(this);
		bestaetigen.addActionListener(this);
		
		bestaetigen.setActionCommand("BESTAETIGEN");
		
		
	}
	/**
	 * Erstellt ein Ausgabefenste, in welchem alle Ergebnisse aufgelistet sind.
	 * 
	 * @author Markus Hausmann
	 * @author Mario Kellner
	 * @author Jonas Stadtler
	 * 
	 * @param list Es wird eine Liste aller Agents übergeben, die im ResultSet enthalten waren.
	 */
	private void erstellenAusgabe(List<Agent> list)
	{
		
		//erstelen eines neuen Frames
		this.ausgabeFenster=new JFrame();
		ausgabeFenster.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		ausgabeFenster.setSize(200,300);
		ausgabeFenster.setLocationRelativeTo(null);
		ausgabe= new JTextArea();
		ausgabe.setSize(200, 300);
		
		
		//einfügen in die Liste
		ausgabe.setText("AgentID          Umsatz");
		for (Agent row: list)
		{
			ausgabe.setText(ausgabe.getText()+"\n"+ row.getAgent()+"          "+row.getUmsatz());
			
		}
		
		//hinzufügen einer Scrollbar
			JScrollPane scrollpane = new JScrollPane(ausgabe);     
		
		ausgabeFenster.add(scrollpane);
		ausgabeFenster.setVisible(true);
	}
	
	
	
	
	/** 
	 * ActionListener, welcher von GUI implementiert wird. Dieser wurde dem Button "bestätigen" zugewiesen.
	 * 
	 * @author Markus Hausmann
	 * @author Mario Kellner
	 * @author Jonas Stadtler
	 * 
	 * @param a Übergaben des Actionevents, welches aufgerufen wird.
	 */
	public void actionPerformed(ActionEvent a) 
	{
		// TODO Auto-generated method stub
		if(a.getActionCommand().equals("BESTAETIGEN"))
		{
			
		
			if(Datenbankanbindung.verbinden().equals("Fehler bei der Verbindung zur Datenbank!"))
			{
				JOptionPane.showMessageDialog(frame, "Fehler bei der Verbindung zur Datenbank!","Fehler!", JOptionPane.ERROR_MESSAGE);
			}
			try
			{
				erstellenAusgabe(Datenbankanbindung.abfrage(eingabe.getText()));
			}
			catch(NullPointerException e)
			{
				JOptionPane.showMessageDialog(frame, "Keine Einträge zur ProduktID gefunden!","Fehler!", JOptionPane.ERROR_MESSAGE);
			}
			
		}
		
	}

}
