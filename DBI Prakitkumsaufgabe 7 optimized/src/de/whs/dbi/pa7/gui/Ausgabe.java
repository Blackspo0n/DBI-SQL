package de.whs.dbi.pa7.gui;

import javax.swing.JFrame;
import javax.swing.JTextArea;

public class Ausgabe extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JFrame frame;
	public static JTextArea textArea;
	Ausgabe()
	{
		this.frame= new JFrame("Ausgabe");
		frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		frame.setSize(500, 500);
		textArea=new JTextArea();
		
		frame.add(textArea);
		
		frame.setVisible(true);;
	}
}
