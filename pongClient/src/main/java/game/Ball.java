package game;

import java.awt.Graphics;

import main.ClientWindow;

public class Ball extends GraphicObject {
	final int DIAMETER = 30;
	
	public Ball() {
		xpos = ClientWindow.WINDOW_WIDTH/2;
		ypos = ClientWindow.WINDOW_HEIGHT/2;
	}
	
	@Override
	public void draw(Graphics g) {
		g.drawOval(Math.round(xpos) - DIAMETER/2, Math.round(ypos) - DIAMETER/2, DIAMETER, DIAMETER);
	}

}
