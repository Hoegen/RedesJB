package game;

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashMap;

import main.ClientWindow;

public class Match extends GraphicObject {
	public final static int MARGIN = 20;
	public static int XSIZE = ClientWindow.WINDOW_WIDTH;
	public static int YSIZE = ClientWindow.WINDOW_HEIGHT;
	
	public boolean paused = true; 
	
	HashMap<Integer, Player> playerlist = new HashMap<Integer, Player>();
	
	private Ball ball;
	
	public Match() {
		playerlist.put(1, new Player("Jogador 1", 1));
		playerlist.put(2, new Player("Jogador 2", 2));
		ball = new Ball(this);
	}
	
	public HashMap<Integer, Player> getPlayerlist() {
		return playerlist;
	}

	public void setPlayerList(HashMap<Integer, Player> playerlist) {
		this.playerlist = playerlist;
	}

	public Ball getBall() {
		return ball;
	}

	public void setBall(Ball ball) {
		this.ball = ball;
	}

	@Override
	public void move() {
		if(ClientWindow.KeysPressed.SPACE) {
			paused = false;
		}
	}

	@Override
	public void draw(Graphics g) {
		Color color = g.getColor();
		g.setColor(Color.DARK_GRAY);
		g.drawRect(0, 0, XSIZE -1, YSIZE -1);
		g.drawOval(-YSIZE/2, 0, YSIZE, YSIZE);
		g.drawOval(XSIZE - YSIZE/2, 0, YSIZE, YSIZE);
		g.drawLine(XSIZE/2, 0, XSIZE/2, YSIZE);
		g.setColor(color);
	}

	@Override
	public void checkCollision(GraphicObject other) {}

	public void point() {
		ball.reposition();
	}
	
	 
}
