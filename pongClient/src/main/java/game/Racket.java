package game;

import java.awt.Graphics;

public class Racket extends GraphicObject {
	private int width;
	private int height;
	
	private String shape = "RECT";
	
	public Racket() {
		width = 20;
		height = 100;
		tilt = 0;
	}
	
	@Override
	public void draw(Graphics g) {
		
		switch(shape) {
			case "RECT":
				g.drawRect(Math.round(xpos - width/2), Math.round(ypos - height/2), width, height);
				break;
		}
		
		
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
