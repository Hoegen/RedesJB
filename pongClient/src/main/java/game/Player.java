package game;

public class Player {
	
	int score = 0;
	
	private int id;
	
	private String name;
	
	private Racket racket;
	
	public Player(String name, int id) {
		final int LEFT = 1;
		final int RIGHT = 2;
		
		this.id = id;
		this.name = name;
		racket = new Racket(this);
		
		switch(id) {
			case (LEFT):
				//JOGADOR DO LADO ESQUERDO
				racket.xpos = racket.getWidth()/2 + Match.MARGIN;
				racket.ypos = Match.YSIZE/2;
				break;
				
			case (RIGHT):
				//JOGADOR DO LADO DIREITO
				racket.xpos = Match.XSIZE - (racket.getWidth()/2 + Match.MARGIN);
				racket.ypos = Match.YSIZE/2;
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
