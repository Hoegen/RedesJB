package client;


import java.awt.Color;
import java.awt.Graphics;
//import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JPanel;

public class Match extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	public boolean paused = true;
	private boolean lastframespacekeydown = false;
	
	public final static int MARGIN = 20;
	public static int XSIZE = ClientWindow.WINDOW_WIDTH;
	public static int YSIZE = ClientWindow.WINDOW_HEIGHT;
	
	HashMap<Integer, Player> playerlist = new HashMap<Integer, Player>();
	private Ball ball;
	
	private ArrayList<GraphicObject> drawlist = new ArrayList<GraphicObject>();
	
	public Match() {
		lastframespacekeydown = ClientWindow.KeyDown.SPACE;
		playerlist.put(1, new Player("Jogador 1", 1));
		playerlist.put(2, new Player("Jogador 2", 2));
		ball = new Ball(this);
		
		this.setBackground(Color.BLACK);
		
		
		drawlist.add(ball);
		for(Player player : playerlist.values()) {
			drawlist.add(player);
		}
		
		new Thread(()-> run()).start();
		super.setDoubleBuffered(true);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		drawField(g);
		
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
				repaint();
				tickdelta--;
			}
		}
	}

	private void move() {
		
		
		if(ClientWindow.KeyDown.SPACE && !lastframespacekeydown) {
			paused = !paused;
		}
		lastframespacekeydown = ClientWindow.KeyDown.SPACE;
		
		for(GraphicObject go : drawlist) {
			go.move();
		}
	}
	
	public void point() {
		ball.reposition();
	}

	void drawField(Graphics g) {
		g.setColor(Color.DARK_GRAY);
		g.drawRect(0, 0, Match.XSIZE -1, Match.YSIZE -1);
		g.drawOval(-Match.YSIZE/2, 0, Match.YSIZE, Match.YSIZE);
		g.drawOval(Match.XSIZE - Match.YSIZE/2, 0, Match.YSIZE, Match.YSIZE);
		g.drawLine(Match.XSIZE/2, 0, Match.XSIZE/2, Match.YSIZE);
	}
	
}
