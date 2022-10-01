package main;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
//import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JPanel;

import game.GraphicObject;
import game.Match;

public class Contents extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ArrayList<GraphicObject> drawlist = new ArrayList<GraphicObject>();
	
	private Match currentmatch;
	
	public Contents(Match match) {
		setCurrentmatch(match);
		
		this.addKeyListener(new ActionListener());
		
		drawlist.add(match.getBall());
		
		for(int i = 0; i < match.getPlayerlist().size(); i++) {
			drawlist.add(match.getPlayerlist().get(i).getRacket());
		}
		
		new Thread(()-> run()).start();
		
		super.setDoubleBuffered(true);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		
		g.setColor(Color.white);
		for(GraphicObject go : drawlist) {
			go.draw(g);
		}
		
	}
	
	public void run() {
		//game loop
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double tickdelta = 0;
		long now = System.nanoTime();
		while(true) {
			now = System.nanoTime();
			tickdelta += (now -lastTime)/ns;
			lastTime = now;
			if(tickdelta > 1) {
				move();
				checkCollision();
				repaint();
				tickdelta--;
				System.out.println("tick amount = " + tickdelta );
			}
		}
	}

	private void move() {
		for(GraphicObject go : drawlist) {
			go.move();
		}
	}

	private void checkCollision() {
		// TODO Auto-generated method stub
		
	}

	public Match getCurrentmatch() {
		return currentmatch;
	}

	public void setCurrentmatch(Match currentmatch) {
		this.currentmatch = currentmatch;
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
