package client;

import java.awt.Graphics;

public class Player extends GraphicObject {
	private boolean human;
	private int width;
	private int height;
	private char upkey = 'a';
	private char downkey = 'z';
	private float speed = 0;
	private String shape = "RECT";
	int score = 0;
	
	private int id;
	
	private String name;
	
	public Player(String name, int id) {
		final int LEFT = 1;
		final int RIGHT = 2;
		
		
		width = 20;
		height = 100;
		tilt = 0;
		this.id = id;
		this.name = name;
		
		switch(id) {
			case (LEFT):
				//JOGADOR DO LADO ESQUERDO
				xpos = getWidth()/2 + Match.MARGIN;
				ypos = Match.YSIZE/2;
				break;
				
			case (RIGHT):
				//JOGADOR DO LADO DIREITO
				xpos = Match.XSIZE - (getWidth()/2 + Match.MARGIN);
				ypos = Match.YSIZE/2;
				break;
		}
		
	}
	
	@Override
	public void draw(Graphics g) {
		
		switch(shape) {
			case "RECT":
				g.drawRect(Math.round(xpos - width/2), Math.round(ypos - height/2), width, height);
				break;
		}
		
	}
	
	void serverUpdate() {
		for(int i = 0; i < Connection.Receiver.players.size(); i++) {
			if(id == Connection.Receiver.players.get(i).id) {
				setSpeed(Connection.Receiver.players.get(i).speed);
				ypos = Connection.Receiver.players.get(i).ypos;
				if(score != Connection.Receiver.players.get(i).score) {
					System.out.println("hurdur");
					System.out.println("Client: " + score);
					System.out.println("Server: " + Connection.Receiver.players.get(i).score);
				}
				System.out.println("Player number: " + this.id);
				System.out.println("Client before: " + score);
				score = Connection.Receiver.players.get(i).score;
				System.out.println("Client  after: " + score + "\n\n");
				
			}
		}
	}

	@Override
	public void move(double delta) {
		setSpeed(0);
		
		switch (id) {
			case 1:
				if(ClientWindow.KeyDown.A) {
					setSpeed(getSpeed() - 10);
				}
				if(ClientWindow.KeyDown.Z) {
					setSpeed(getSpeed() + 10);
				}
				break;

			case 2:
				if(ClientWindow.KeyDown.L) {
					setSpeed(getSpeed() - 10);
				}
				if(ClientWindow.KeyDown.comma) {
					setSpeed(getSpeed() + 10);
				}
				break;
		}
		ypos += getSpeed()*delta;
	}

	@Override
	public void checkCollision(GraphicObject other) {
		if(other == null) {
			if(ypos < 0 ) {
				ypos = 0;
			}else if (ypos > Match.YSIZE){
				ypos = Match.YSIZE;
			}
		}
		
	}
	
	
	
	
	//Getters e Setters - Ó, vós que entrais, abandonai toda a esperança
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public boolean isHuman() {
		return human;
	}
	public void setHuman(boolean human) {
		this.human = human;
	}
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
	public char getUpkey() {
		return upkey;
	}
	public void setUpkey(char upkey) {
		this.upkey = upkey;
	}
	public char getDownkey() {
		return downkey;
	}
	public void setDownkey(char downkey) {
		this.downkey = downkey;
	}
	public String getShape() {
		return shape;
	}
	public void setShape(String shape) {
		this.shape = shape;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}
}
