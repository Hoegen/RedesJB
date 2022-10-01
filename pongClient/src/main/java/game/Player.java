package game;

import main.ClientWindow;

public class Player {
	
	private int score;
	
	private int id;
	
	private String name;
	
	private Racket racket;
	
	public Player(String name, int id) {
		score = 0;
		this.id = id;
		this.name = name;
		racket = new Racket(this);
		
		switch(id) {
			case (0):
				//JOGADOR DO LADO ESQUERDO
				racket.xpos = racket.getWidth()/2 + Match.MARGIN;
				racket.ypos = ClientWindow.WINDOW_HEIGHT/2;
				break;
				
			case (1):
				//JOGADOR DO LADO DIREITO
				racket.xpos = ClientWindow.WINDOW_WIDTH - (racket.getWidth()/2 + Match.MARGIN);
				racket.ypos = ClientWindow.WINDOW_HEIGHT/2;
				break;
		}
		
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Racket getRacket() {
		return racket;
	}
	public void setRacket(Racket racket) {
		this.racket = racket;
	}
	public boolean isHuman() {
		return human;
	}
	public void setHuman(boolean human) {
		this.human = human;
	}
	private boolean human;
	
	
	
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
