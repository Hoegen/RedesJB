package main;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

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
		
		super.setLocation(UserProperties.getScrWid()/2 - WINDOW_WIDTH/2, UserProperties.getScrHei()/2 - WINDOW_HEIGHT/2);
		super.setResizable(false);
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		super.addKeyListener(new ActionListener());
		super.pack();
		
		super.setVisible(true);
	}
	

	
	
	public static class KeysPressed{
		public static boolean A = false;
		public static boolean Z = false;
		public static boolean L = false;
		public static boolean comma = false;
		
	}
	
	public class ActionListener implements KeyListener {

		@Override
		public void keyTyped(KeyEvent e) { //do nothing
		}

		@Override
		public void keyPressed(KeyEvent e) {
			switch (e.getKeyCode()) {
				case KeyEvent.VK_A:
					KeysPressed.A = true;
					break;
				case KeyEvent.VK_Z:
					KeysPressed.Z = true;
					break;
				case KeyEvent.VK_L:
					KeysPressed.L = true;
					break;
				case KeyEvent.VK_COMMA:
					KeysPressed.comma = true;
					break;
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			switch (e.getKeyCode()) {
				case KeyEvent.VK_A:
					KeysPressed.A =false;
					break;
				case KeyEvent.VK_Z:
					KeysPressed.Z =false;
					break;
				case KeyEvent.VK_L:
					KeysPressed.L =false;
					break;
				case KeyEvent.VK_COMMA:
					KeysPressed.comma =false;
					break;
			}
		}

	}
}
