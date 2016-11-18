package de.whs.dbi.pa7.gui;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import de.whs.dbi.pa7.funktionen_gui.*;




public class MainGUI extends JFrame implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public JFrame frame;
	public ButtonGroup buttons;
	public JPanel verbindungsPanel;
	JButton verbindungseinstellung;
	JButton bestaetigen;
	public MainGUI ()
	{
		this.frame= new JFrame("Benchmark");
		frame.setSize(500, 500);
		frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		//erstellen der Verbinungseinstellungen
		verbindungsPanel= new JPanel();
		verbindungsPanel.setBounds(30, 11, 452, 33);
		verbindung(Serialisierung.ausgeben());
		//Versionsauswahl
		versionsAuswahl();
		//Auswahl der Option
		optionen();
		frame.getContentPane().add(verbindungsPanel);
		frame.setVisible(true);
	}
	List <Verbindungseinstellungen>verbindungsmoeglichkeiten= new ArrayList<Verbindungseinstellungen>();
	private void verbindung(List <Verbindungseinstellungen> ve)
	{
		JLabel verbindungLabel=new JLabel("Auswahl einer Datenbank:");
		verbindungLabel.setBounds(114, 9, 126, 14);
		verbindungsmoeglichkeiten=ve;
		buttons= new ButtonGroup();
		for(Verbindungseinstellungen a: ve)
		{
			JRadioButton b =new JRadioButton();
			b.setText(a.getBezeichnung());
			b.addActionListener(this);
			b.setActionCommand(a.database);
			System.out.println(a.database);
			buttons.add(b);
			verbindungsPanel.add(b);
		}
		verbindungseinstellung= new JButton("hinzufügen");
		verbindungseinstellung.setBounds(293, 5, 85, 23);
		verbindungseinstellung.addActionListener(this);
		verbindungseinstellung.setActionCommand("VERBINDUNGSEINSTELLUNG");
		
		bestaetigen=new JButton("bestätigen");
		bestaetigen.setBounds(10, 5, 83, 23);
		bestaetigen.addActionListener(this);
		verbindungsPanel.setLayout(null);
		bestaetigen.setActionCommand("AUSWAHL_DATENBANK");
		verbindungsPanel.add(bestaetigen);
		
		verbindungsPanel.add(verbindungLabel);
		verbindungsPanel.add(verbindungseinstellung);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		//Auswahl der Verbindung.
		for(Verbindungseinstellungen a: verbindungsmoeglichkeiten)
		{
			if(e.getActionCommand().equals(a.getBezeichnung()))
			{
				de.whs.dbi.pa7.AnbindungGUI.verbinden(a.getHost(), a.getDatabase(), a.getUser(), a.getPassword());
			}
		}
		
		try
		{
			//Auswahl der Option.
			int i=Integer.parseInt(e.getActionCommand());
			de.whs.dbi.pa7.AnbindungGUI.benchmarkMenu(i);
			new Ausgabe();
		}
		catch(NumberFormatException d)
		{
			//Auswahl der Version.
			//Neueingabe einer Datenbank(Option).
			switch(e.getActionCommand())
			{
			case "VERBINDUNGSEINSTELLUNG":
				new EingabeVerbindungsdaten();
				break;
			case "ALT":
				de.whs.dbi.pa7.AnbindungGUI.version(false);
				break;
			case "NEU":
				de.whs.dbi.pa7.AnbindungGUI.version(true);
				break;
			
			}
		}
	}
	private void versionsAuswahl()
	{
		JLabel versionLabel= new JLabel("Auswahl der Version:");
		versionLabel.setBounds(5, 9, 101, 14);
		ButtonGroup versionButton=new ButtonGroup();
		JPanel versionPanel=new JPanel();
		versionPanel.setLocation(30, 72);
		versionPanel.setSize(452, 33);
		versionPanel.setLayout(null);
		
		versionPanel.add(versionLabel);
		
		JRadioButton neu=new JRadioButton("Neue Version");
		neu.setBounds(149, 5, 89, 23);
		neu.addActionListener(this);
		neu.setActionCommand("NEU");
		versionButton.add(neu);
		versionPanel.add(neu);
				
		JRadioButton alt=new JRadioButton("Alte Version");
		alt.setBounds(279, 5, 83, 23);
		alt.addActionListener(this);
		frame.getContentPane().setLayout(null);
		alt.setActionCommand("ALT");
		versionButton.add(alt);
		versionPanel.add(alt);
		frame.getContentPane().add(versionPanel);

	}
	private void optionen()
	{
		JPanel optionPanel=new JPanel();
		optionPanel.setBounds(30, 116, 421, 267);
		optionPanel.setLayout(null);
		JLabel optionLabel=new JLabel("Benchmark, transactions, excl. drop & create");
		optionLabel.setBounds(32, 3, 217, 14);
		optionPanel.add(optionLabel);
		
		ButtonGroup optionButton=new ButtonGroup();
		
		JRadioButton button1=new JRadioButton("Benchmark, Debug Log, incl. drop & create ");
		button1.setBounds(54, 26, 233, 23);
		button1.addActionListener(this);
		button1.setActionCommand("1");
		optionButton.add(button1);
		optionPanel.add(button1);
		
		JRadioButton button2=new JRadioButton("Benchmark, Debug Log, excl. drop & create");
		button2.setBounds(52, 52, 235, 23);
		button2.addActionListener(this);
		button2.setActionCommand("2");
		optionButton.add(button2);
		optionPanel.add(button2);
		
		JRadioButton button3=new JRadioButton("Benchmark, Debug Log, transactions, incl. drop & create");
		button3.setBounds(54, 78, 297, 23);
		button3.addActionListener(this);
		button3.setActionCommand("3");
		optionButton.add(button3);
		optionPanel.add(button3);
		
		JRadioButton button4=new JRadioButton("Benchmark, Debug Log, transactions, excl. drop & create");
		button4.setBounds(54, 110, 301, 23);
		button4.addActionListener(this);
		button4.setActionCommand("4");
		optionButton.add(button4);
		optionPanel.add(button4);
		
		JRadioButton button5=new JRadioButton("Benchmark, incl. drop & create ");
		button5.setBounds(32, 136, 175, 23);
		button5.addActionListener(this);
		button5.setActionCommand("5");
		optionButton.add(button5);
		optionPanel.add(button5);
		
		JRadioButton button6=new JRadioButton("Benchmark, excl. drop & create");
		button6.setBounds(126, 165, 177, 23);
		
		button6.addActionListener(this);
		button6.setActionCommand("6");
		optionButton.add(button6);
		optionPanel.add(button6);
		
		JRadioButton button7=new JRadioButton("Benchmark, transactions, incl. drop & create");
		button7.setBounds(95, 191, 239, 23);
		button7.addActionListener(this);
		button7.setActionCommand("7");
		optionButton.add(button7);
		optionPanel.add(button7);
		
		JRadioButton button8=new JRadioButton("Benchmark, transactions, excl. drop & create");
		button8.setBounds(91, 217, 243, 23);
		button8.addActionListener(this);
		button8.setActionCommand("8");
		optionButton.add(button8);
		optionPanel.add(button8);
		
		frame.getContentPane().add(optionPanel);
	}
}
