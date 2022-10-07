package main;


import java.awt.Color;
import java.awt.Graphics;
//import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JPanel;

import game.Ball;
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
		
		this.setBackground(Color.BLACK);
		
		drawlist.add(match);
		drawlist.add(match.getBall());
		
		for(int i = 0; i < match.getPlayerlist().size(); i++) {
			drawlist.add(match.getPlayerlist().get(i).getRacket());
		}
		
		new Thread(()-> run()).start();
		super.setDoubleBuffered(true);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
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
				checkCollision();
				move();
				repaint();
				tickdelta--;
			}
		}
	}

	private void move() {
		for(GraphicObject go : drawlist) {
			go.move();
		}
	}

	private void checkCollision() {
		for(int i = 0; i < drawlist.size(); i++) {
			for(int j = i + 1; j < drawlist.size(); j++) {
				drawlist.get(i).checkCollision(drawlist.get(j));
				drawlist.get(j).checkCollision(drawlist.get(i));
			}
		}
	}

	public Match getCurrentmatch() {
		return currentmatch;
	}

	public void setCurrentmatch(Match currentmatch) {
		this.currentmatch = currentmatch;
	}
}
