package de.whs.dbi.pa7.gui;

import de.whs.dbi.pa7.funktionen_gui.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

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
	public MainGUI ()
	{
		this.frame= new JFrame("Benchmark");
		frame.setSize(500, 500);
		frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		//erstellen der Verbinungseinstellungen
		verbindungsPanel= new JPanel();
		verbindung(Serialisierung.ausgeben());
		//Button zum Hinzufügen einer Verbindungseinstellung
		verbindungseinstellung_hinzufuegen();
		frame.add(verbindungsPanel);
		//ans Ende
		frame.setVisible(true);
	}
	List <Verbindungseinstellungen>verbindungsmoeglichkeiten= new ArrayList<Verbindungseinstellungen>();
	private void verbindung(List <Verbindungseinstellungen> ve)
	{
		
		buttons= new ButtonGroup();
		for(Verbindungseinstellungen a: ve)
		{
			JRadioButton b =new JRadioButton();
			b.setText(a.getBezeichnung());
			b.addActionListener(this);
			b.setActionCommand(a.database);
			buttons.add(b);
			verbindungsPanel.add(b);
		}
		
	}
	private void verbindungseinstellung_hinzufuegen()
	{
		verbindungseinstellung= new JButton("hinzufügen");
		verbindungseinstellung.addActionListener(this);
		verbindungseinstellung.setActionCommand("VERBINDUNGSEINSTELLUNG");
		verbindungsPanel.add(verbindungseinstellung);
	}
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getActionCommand().equals("VERBINDUNGSEINSTELLUNG"))
		{
			new EingabeVerbindungsdaten();
		}
	//	for()
	//		if(e.getActionCommand().equals(arg0))
			{
				
			}
		
	}
}
