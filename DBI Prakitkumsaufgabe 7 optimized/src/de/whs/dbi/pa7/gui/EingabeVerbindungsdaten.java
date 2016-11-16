package de.whs.dbi.pa7.gui;


import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import de.whs.dbi.pa7.funktionen_gui.Verbindungseinstellungen;

/**
 * Erstellung des Fensters zum eingebne von neuen Verbindungsdaten.
 * 
 * @author Markus Hausmann
 * @author Mario Kellner
 * @author Jonas Stadtler
 *
 */
public class EingabeVerbindungsdaten extends JFrame implements ActionListener
{
	
	private static final long serialVersionUID = 1L;
	public JFrame frame;
	public JTextField bezeichnungFeld;
	public JTextField hostFeld;
	public JTextField databaseFeld;
	public JTextField userFeld;
	public JTextField passwordFeld;
	public JLabel hostLabel;
	public JLabel bezeichnungLabel;
	public JLabel databaseLabel;
	public JLabel userLabel;
	public JLabel passwordLabel;
	public JPanel host;
	public JPanel bezeichnung;
	public JPanel database;
	public JPanel user;
	public JPanel password;
	public JButton bestaetigen;
	
	/**
	 * Konstruktor der GUI.
	 */
	public EingabeVerbindungsdaten() 
	{
		this.frame= new JFrame("Verbindungsdaten");
		frame.setSize(300, 600);
		frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		//Eingabefelder hinzufügen
		eingabefelder_hinzufuegen();
		//bestätigen der Eingabe
		bestaetigen=new JButton("bestätigen");
		bestaetigen.addActionListener(this);
		bestaetigen.setActionCommand("BESTAETIGEN");
		frame.add(bestaetigen);
		//ans Ende
		frame.setVisible(true);
	}
	/**
	 * Auslagerung der Erstellung alles Eingabefelder.
	 */
	private void eingabefelder_hinzufuegen()
	{
		frame.setLayout(new FlowLayout());
		
		//Bezeichnung
		bezeichnungLabel=new JLabel("Bezeichnung:");
		bezeichnungFeld=new JTextField();
		bezeichnungFeld.setSize(220, 222);
		bezeichnung=new JPanel();
		bezeichnungFeld.addActionListener(this);
		bezeichnung.add(bezeichnungLabel);
		bezeichnung.add(bezeichnungFeld);
		
		frame.add(bezeichnung);
		
		//Host
		hostLabel=new JLabel("Host:");
		hostFeld=new JTextField();
		host=new JPanel();
		hostFeld.addActionListener(this);
		host.add(hostLabel);
		host.add(hostFeld);
		
		frame.add(host);
		
		//Database
		databaseLabel=new JLabel("Database:");
		databaseFeld=new JTextField();
		database=new JPanel();
		databaseFeld.addActionListener(this);
		database.add(databaseLabel);
		database.add(databaseFeld);
		
		frame.add(database);
		
		//User 
		userLabel=new JLabel("User:");
		userFeld=new JTextField();
		user=new JPanel();
		userFeld.addActionListener(this);
		user.add(userLabel);
		user.add(userFeld);
		
		frame.add(user);
		
		//Password
		passwordLabel=new JLabel("Password:");
		passwordFeld=new JTextField();
		password=new JPanel();
		passwordFeld.addActionListener(this);
		password.add(passwordLabel);
		password.add(passwordFeld);
		
		frame.add(password);
		
		
		
	}
	/**
	 * Actionlistener, der nur auf den Button 'bestätigen' reagiert.
	 * 
	 */
	public void actionPerformed(ActionEvent e) 
	{
		// TODO Auto-generated method stub
		if(e.getActionCommand().equals("BESTAETIGEN"))
		{
			//überprüft ob Eingabe der wichtigen Felder vorliegt.
			if(!bezeichnungFeld.getText().equals(null)&& !hostFeld.getText().equals(null)&& !databaseFeld.getText().equals(null)&& !userFeld.getText().equals(null))
			{
				String text = passwordFeld.getText();
				if(text.equals(null))
				{
					text=" ";
				}
				
				//Serialisierung der eingegebenen Daten.
				de.whs.dbi.pa7.funktionen_gui.Serialisierung.eingeben(new Verbindungseinstellungen(bezeichnungFeld.getText(), hostFeld.getText(), databaseFeld.getText(), userFeld.getText(), text));
				frame.dispose();
			}
			else
			{
				//Gibt eine Fehlermeldung an den Benutzer, falls die getätigten Eingaben nicht ausreichend sind.
				JOptionPane.showMessageDialog(frame,"Sie müssen die Felder Bezeichnung, Host und User ausfüllen!", "Warnung!", JOptionPane.WARNING_MESSAGE);
			}
		}
	}
}
