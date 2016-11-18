package de.whs.dbi.pa7.gui;



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
		bestaetigen.setBounds(89, 228, 106, 23);
		bestaetigen.addActionListener(this);
		bestaetigen.setActionCommand("BESTAETIGEN");
		frame.getContentPane().add(bestaetigen);
		//ans Ende
		frame.setVisible(true);
	}
	/**
	 * Auslagerung der Erstellung alles Eingabefelder.
	 */
	private void eingabefelder_hinzufuegen()
	{
		
		//Bezeichnung
		bezeichnungLabel=new JLabel("Bezeichnung:");
		bezeichnungLabel.setBounds(10, 8, 110, 14);
		bezeichnungFeld=new JTextField();
		bezeichnungFeld.setLocation(130, 5);
		bezeichnungFeld.setSize(95, 20);
		bezeichnung=new JPanel();
		bezeichnung.setBounds(30, 22, 235, 30);
		bezeichnungFeld.addActionListener(this);
		frame.getContentPane().setLayout(null);
		bezeichnung.setLayout(null);
		bezeichnung.add(bezeichnungLabel);
		bezeichnung.add(bezeichnungFeld);
		
		frame.getContentPane().add(bezeichnung);
		
		//Host
		hostLabel=new JLabel("Host:");
		hostLabel.setBounds(10, 8, 79, 14);
		hostFeld=new JTextField();
		hostFeld.setBounds(130, 5, 95, 20);
		host=new JPanel();
		host.setBounds(30, 63, 235, 30);
		hostFeld.addActionListener(this);
		host.setLayout(null);
		host.add(hostLabel);
		host.add(hostFeld);
		
		frame.getContentPane().add(host);
		
		//Database
		databaseLabel=new JLabel("Database:");
		databaseLabel.setBounds(10, 8, 95, 14);
		databaseFeld=new JTextField();
		databaseFeld.setBounds(130, 5, 95, 20);
		database=new JPanel();
		database.setBounds(30, 104, 235, 30);
		databaseFeld.addActionListener(this);
		database.setLayout(null);
		database.add(databaseLabel);
		database.add(databaseFeld);
		
		frame.getContentPane().add(database);
		
		//User 
		userLabel=new JLabel("User:");
		userLabel.setBounds(10, 8, 84, 14);
		userFeld=new JTextField();
		userFeld.setBounds(130, 5, 95, 20);
		user=new JPanel();
		user.setBounds(30, 145, 235, 30);
		userFeld.addActionListener(this);
		user.setLayout(null);
		user.add(userLabel);
		user.add(userFeld);
		
		frame.getContentPane().add(user);
		
		//Password
		passwordLabel=new JLabel("Password:");
		passwordLabel.setBounds(10, 8, 89, 14);
		passwordFeld=new JTextField();
		passwordFeld.setBounds(130, 5, 95, 20);
		password=new JPanel();
		password.setBounds(30, 186, 235, 30);
		passwordFeld.addActionListener(this);
		password.setLayout(null);
		password.add(passwordLabel);
		password.add(passwordFeld);
		
		frame.getContentPane().add(password);
		
		
		
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
