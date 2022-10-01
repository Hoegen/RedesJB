package main;


import java.awt.Color;
import java.awt.Graphics;
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
	
	public Contents(Match match) {
		drawlist.add(match.getBall());
		
		for(int i = 0; i < match.getPlayerlist().size(); i++) {
			drawlist.add(match.getPlayerlist().get(i).getRacket());
		}
		
		super.setDoubleBuffered(true);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		
		g.setColor(new Color(0,0,0));
		for(GraphicObject go : drawlist) {
			System.out.println(go.getXpos() + " = x; y = " + go.getYpos() + " for " + go.getClass().toString() + go.getClass().getCanonicalName());
			go.draw(g);
		}
		
	}
	
}
