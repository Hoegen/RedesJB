package server;

import java.awt.Graphics;

public class Player extends GraphicObject {
	private boolean human;
	private int width;
	private int height;
	private char upkey = 'a';
	private char downkey = 'z';
	private float speed = 0;
	private String shape = "RECT";
	private int score = 0;
	private int id;
	private String name;
	
	
	
	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}
	
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

	@Override
	public void move(double ticks) {
		speed = 0;
		
		switch (id) {
			case 1:
				if(Match.KeyDown.A) {
					speed -= 10;
				}
				if(Match.KeyDown.Z) {
					speed += 10;
				}
				break;

			case 2:
				if(Match.KeyDown.L) {
					speed -= 10;
				}
				if(Match.KeyDown.comma) {
					speed += 10;
				}
				break;
		}
		ypos += speed * ticks;
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
	public void addScore() {
		score++;
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
}
