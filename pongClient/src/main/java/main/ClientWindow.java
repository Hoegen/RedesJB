package main;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;

import game.Match;

public class ClientWindow extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	*
	**/
	public final static int WINDOW_WIDTH  = 1000;
	public final static int WINDOW_HEIGHT = 500;
	
	public ClientWindow() {
		Contents contents = new Contents(new Match());
		super.add(contents);
		super.setTitle("PONGIO");
		contents.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
		
		super.setBackground(Color.BLACK);
		super.setLocation(UserProperties.getScrWid()/2 - WINDOW_WIDTH/2, UserProperties.getScrHei()/2 - WINDOW_HEIGHT/2);
		super.setResizable(false);
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		super.pack();
		
		super.setVisible(true);
	}
}
