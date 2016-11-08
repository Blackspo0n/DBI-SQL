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
	public JFrame frame;
	public JTextField eingabe;
	public JLabel schrift;
	public JButton bestaetigen;
	public JFrame ausgabeFenster;
	public JTextArea ausgabe;
	
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
		
		
		//ans Ende
		frame.setVisible(true);
	}
	
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
	private void erstellenAusgabe(List<Agent> list)
	{
		//erstelen eines neuen Frames
		this.ausgabeFenster=new JFrame();
		ausgabeFenster.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		ausgabeFenster.setLocationRelativeTo(null);
		ausgabe.setSize(200, 300);
		ausgabe= new JTextArea();
		
		
		//einfügen in die Liste
		ausgabe.setText("AgentID          Umsatz");
		for (Agent row: list)
		{
			ausgabe.setText(ausgabe.getText()+"\n"+ row.getAgent()+"          "+row.getUmsatz());
			
		}
		
		//hinzufügen einer Scrollbar
			JScrollPane scrollpane = new JScrollPane(ausgabe);     
		//ans Ende
		ausgabeFenster.add(scrollpane);
		ausgabeFenster.setVisible(true);
	}
	
	
	
	@Override
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
