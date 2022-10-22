package client;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

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
		Match contents = new Match();
		super.add(contents);
		//super.add(new Panel(contents));
		super.setTitle("PONGIO");
		contents.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
		
		super.setLocation(Util.UserProperties.getScrWid()/2 - WINDOW_WIDTH/2, Util.UserProperties.getScrHei()/2 - WINDOW_HEIGHT/2);
		super.setResizable(false);
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		super.addKeyListener(new ActionListener());
		super.pack();
		
		super.setVisible(true);
	}
	

	
	
	public static class KeyDown{
		public static boolean SPACE = false;
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
					KeyDown.A = true;
					break;
				case KeyEvent.VK_Z:
					KeyDown.Z = true;
					break;
				case KeyEvent.VK_L:
					KeyDown.L = true;
					break;
				case KeyEvent.VK_COMMA:
					KeyDown.comma = true;
					break;
				case KeyEvent.VK_SPACE:
					KeyDown.SPACE = true;
					break;
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			switch (e.getKeyCode()) {
				case KeyEvent.VK_A:
					KeyDown.A =false;
					break;
				case KeyEvent.VK_Z:
					KeyDown.Z =false;
					break;
				case KeyEvent.VK_L:
					KeyDown.L =false;
					break;
				case KeyEvent.VK_COMMA:
					KeyDown.comma =false;
					break;
				case KeyEvent.VK_SPACE:
					KeyDown.SPACE = false;
					break;
			}
		}

	}
}
