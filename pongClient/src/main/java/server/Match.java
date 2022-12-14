package server;


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
	private boolean paused = true;
	private boolean lastframespacekeydown = false;
	
	public final static int MARGIN = 20;
	public static int XSIZE = 1000;
	public static int YSIZE = 500;
	
	public HashMap<Integer, Player> playerlist = new HashMap<Integer, Player>();
	public Ball ball;
	
	private ArrayList<GraphicObject> drawlist = new ArrayList<GraphicObject>();
	
	public Match() {
		lastframespacekeydown = KeyDown.SPACE;
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
		double framespersecond = 60.0;
		double delta = 0;
		double ticks = 0;
		long now = System.nanoTime();
		while(true) {
				now = System.nanoTime();
				delta = now - lastTime;
				ticks = (delta*framespersecond) / 1000000000.0;
				checkCollision();
				move(ticks);
				repaint();
				lastTime = now;
		}
	}


	private void move(double ticks) {
		if(KeyDown.SPACE && !lastframespacekeydown) {
			System.out.println("comando de pausa criado pelo servidor");
			changePause();
		}
		lastframespacekeydown = KeyDown.SPACE;
		
		for(GraphicObject go : drawlist) {
			go.move(ticks);
		}
	}
	
	public void point() {
		if(ball.getXpos() < XSIZE/2) {
			playerlist.get(2).addScore();
		}else {
			playerlist.get(1).addScore();
		}
		ball.reposition();
		this.paused = true;
		System.out.println("Server paused due to point: " + paused);
		if(!paused) {
			System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA FILHO DA PUTA!");
		}
	}
	
	public void changePause() {
		System.out.println("changePause()");
		paused = !paused;
	}
	
	public boolean isPaused() {
		return paused;
	}

	private void checkCollision() {
		for(int i = 0; i < drawlist.size(); i++) {
			drawlist.get(i).checkCollision(null);
			for(int j = i + 1; j < drawlist.size(); j++) {
				drawlist.get(i).checkCollision(drawlist.get(j));
				drawlist.get(j).checkCollision(drawlist.get(i));
			}
		}
	}

	void drawField(Graphics g) {
		g.setColor(Color.DARK_GRAY);
		g.drawRect(0, 0, Match.XSIZE -1, Match.YSIZE -1);
		g.drawOval(-Match.YSIZE/2, 0, Match.YSIZE, Match.YSIZE);
		g.drawOval(Match.XSIZE - Match.YSIZE/2, 0, Match.YSIZE, Match.YSIZE);
		g.drawLine(Match.XSIZE/2, 0, Match.XSIZE/2, Match.YSIZE);
	}
	
	public static class KeyDown{
		public static boolean SPACE = false;
		public static boolean A = false;
		public static boolean Z = false;
		public static boolean L = false;
		public static boolean comma = false;
		
	}
	
}
