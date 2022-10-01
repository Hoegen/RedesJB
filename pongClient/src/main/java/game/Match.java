package game;

import java.util.ArrayList;

public class Match {
	public final static int MARGIN = 20;
	
	private ArrayList<Player> playerlist = new ArrayList<Player>();
	
	private Ball ball = new Ball();
	
	public Match() {
		playerlist.add(new Player("Jogador 1", 0));
		playerlist.add(new Player("Jogador 2", 1));
		
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
	
	 
}
