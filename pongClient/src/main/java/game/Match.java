package game;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import main.ClientWindow;
import main.Contents;

public class Match extends GraphicObject {
	public final static int MARGIN = 20;
	public static int XSIZE;
	public static int YSIZE;
	
	
	private ArrayList<Player> playerlist = new ArrayList<Player>();
	
	private Ball ball = new Ball();
	
	public Match() {
		playerlist.add(new Player("Jogador 1", 0));
		playerlist.add(new Player("Jogador 2", 1));
		YSIZE = ClientWindow.WINDOW_HEIGHT;
		XSIZE = ClientWindow.WINDOW_WIDTH;
		
	}
	
	public ArrayList<Player> getPlayerlist() {
		return playerlist;
	}

	public void setPlayerlist(ArrayList<Player> playerlist) {
		this.playerlist = playerlist;
	}

	public Ball getBall() {
		return ball;
	}

	public void setBall(Ball ball) {
		this.ball = ball;
	}

	@Override
	public void move() {}

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
	
	 
}
