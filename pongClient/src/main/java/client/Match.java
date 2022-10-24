package client;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.font.FontRenderContext;
//import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JPanel;

import server.ServidorFake;

public class Match extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	
	
	public final static int MARGIN = 20;
	public static int XSIZE = ClientWindow.WINDOW_WIDTH;
	public static int YSIZE = ClientWindow.WINDOW_HEIGHT;
	public Connection connection = new Connection(new server.ServidorFake());
	
	
	//FUNCIONAMENTO DO JOGO____________________________
	HashMap<Integer, Player> playerlist = new HashMap<Integer, Player>();
	private Ball ball;
	public boolean paused = true;
	
	//GRÁFICOS _________________________________
	private ArrayList<GraphicObject> drawlist = new ArrayList<GraphicObject>();
	GUI gui;
	
	public Match() {
		
		playerlist.put(1, new Player("Jogador 1", 1));
		playerlist.put(2, new Player("Jogador 2", 2));
		gui = new GUI(this);
		ball = new Ball(this);
		
		this.setBackground(Color.BLACK);
		
		new Thread(()-> ServidorFake.main()).start();
		
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
		
		gui.draw(g);
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
				serverUpdate();
				connection.sender.send();
				move(ticks);
				repaint();
				lastTime = now;
		}
	}

	private void serverUpdate() {
		if(Connection.Receiver.unchanged) return;
		
		Connection.Receiver.unchanged = true;
		
		paused = Connection.Receiver.paused;
		for(Player localplayer : playerlist.values()) {
			localplayer.serverUpdate();
		}
		
		ball.serverUpdate();
	}

	private void move(double delta) {
		
		if(ClientWindow.KeyDown.SPACE && !ClientWindow.lastframespacekeydown) {
			paused = !paused;
			ClientWindow.KeyPressed.SPACE = true;
		}
		ClientWindow.lastframespacekeydown = ClientWindow.KeyDown.SPACE;
		
		for(GraphicObject go : drawlist) {
			go.move(delta);
		}
	}
	
	public void point() {
		if(ball.getXpos() < XSIZE/2) {
			playerlist.get(2).addScore();
		}else {
			playerlist.get(1).addScore();
		}
		ball.reposition();
		paused = true;
		System.out.println("Client paused due to point: " + paused);
	}

	void drawField(Graphics g) {
		g.setColor(Color.DARK_GRAY);
		g.drawRect(0, 0, Match.XSIZE -1, Match.YSIZE -1);
		g.drawOval(-Match.YSIZE/2, 0, Match.YSIZE, Match.YSIZE);
		g.drawOval(Match.XSIZE - Match.YSIZE/2, 0, Match.YSIZE, Match.YSIZE);
		g.drawLine(Match.XSIZE/2, 0, Match.XSIZE/2, Match.YSIZE);
	}
	
	private class GUI{
		private final int MARGIN = 30;
		private Font font = new Font("", Font.PLAIN , 30);
		FontRenderContext frc;
		Match m;
		Player player1;
		Player player2;
		
		GUI(Match m){
			this.m = m;
			player1 = m.playerlist.get(1);
			player2 = m.playerlist.get(2);
			if(m.getGraphics() != null) {
				frc = m.getGraphics().getFontMetrics().getFontRenderContext();
			}
			
		}
		
		
		private void draw(Graphics g) {
			frc = g.getFontMetrics().getFontRenderContext();
			
			g.setFont(font);
			
			//escreve os nomes dos jogadores
			 
			g.drawString(player1.getName(), MARGIN, MARGIN + g.getFont().getSize()/2);
			g.drawString(player2.getName(), rightToLeftBound(XSIZE -MARGIN, player2.getName()), MARGIN + g.getFont().getSize()/2);
			
			
			//escreve as pontuações dos jogadores
			g.drawString(score(2), XSIZE/2 + MARGIN, MARGIN + font.getSize()/2);
			g.drawString(score(1), rightToLeftBound(XSIZE/2 - MARGIN, score(1)), MARGIN + g.getFont().getSize()/2);
			
			
		}
		
		private int rightToLeftBound(int rightbound, String string) {
			return rightbound - (int)font.getStringBounds(string, frc).getWidth(); 
		}
		
		private String score(int player) {
			return Integer.toString(m.playerlist.get(player).getScore());
		}
	}
	
}
