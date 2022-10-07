package game;

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
		g.drawRect(1, 1, Contents.WIDTH, Contents.HEIGHT);
		
	}

	@Override
	public void checkCollision(GraphicObject other) {}
	
	 
}
