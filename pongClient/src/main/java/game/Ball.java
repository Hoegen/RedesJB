package game;

import java.awt.Graphics;
import main.ClientWindow;
import main.Geometry;

public class Ball extends GraphicObject {
	final int DIAMETER = 30;
	float dir;
	float speed;
	float xSpeed;
	float ySpeed;
	
	public Ball() {
		xpos = ClientWindow.WINDOW_WIDTH/2;
		ypos = ClientWindow.WINDOW_HEIGHT/2;
		setSpeed(5*(float)Math.random() + 5, 360*(float)Math.random());
	}
	
	void setSpeed(float newspeed, float newdir) {
		
		//corrige ângulos negativos
		if(newdir < 0) {
			newdir = 360 - newdir;
		}else if(newdir > 360) {
			newdir -= 360;
		}
		
		speed = newspeed;
		dir = newdir;
		xSpeed = Geometry.xSpeed(newdir, newspeed);
		ySpeed = Geometry.ySpeed(newdir, newspeed);
		
		
		//corrige mudanças de direção que joguem a bolinha para BUGS
		boolean xbug = false;
		boolean ybug = false;
		
		xbug |= xpos + xSpeed < 0 + DIAMETER/2 && xSpeed < 0;
		if(!xbug) xbug |= xpos + xSpeed > Match.XSIZE - DIAMETER/2 && xSpeed > 0;
		
		ybug |= ypos - ySpeed < 0 + DIAMETER/2 && ySpeed > 0;
		if(!ybug) ybug |= ypos - ySpeed > Match.YSIZE - DIAMETER/2 && ySpeed < 0;
		
		
		if(xbug) {
			System.out.println("bugo");
			dir = Geometry.getDir(-xSpeed, ySpeed, 0, 0);
			xSpeed = -xSpeed;
		}
		if(ybug) {
			System.out.println("bugo");
			dir = Geometry.getDir(xSpeed, ySpeed, 0, 0);
			ySpeed = -ySpeed;
		}
	}
	
	public void setSpeed(float newspeed) {
		setSpeed(newspeed, dir);
	}
	
	public float getSpeed() {
		return speed;
	}
	
	@Override
	public void draw(Graphics g) {
		g.drawOval(Math.round(xpos) - DIAMETER/2, Math.round(ypos) - DIAMETER/2, DIAMETER, DIAMETER);
	}

	@Override
	public void move() {
		xpos += xSpeed;
		ypos -= ySpeed;
	}

	@Override
	public void checkCollision(GraphicObject other) {
		
		if(other.getClass().equals(Racket.class)) {
			if(Geometry.distance(this, other) < DIAMETER/2) {
				System.out.println("racket collision");
				setSpeed(speed, Geometry.getDir(this, other));
			}
			
			
		}else if(other.getClass().equals(Match.class)) {
			//espelha a componente horizontal ou vertical da velocidade
			if(xpos < 0 + DIAMETER/2 || xpos > Match.XSIZE - DIAMETER/2) {
				
				if(xpos < 0) xpos = 0 + DIAMETER/2;
				else if(xpos > Match.XSIZE) xpos = Match.XSIZE - DIAMETER/2;
				
				System.out.println("xcollision: xpos = "  + xpos + ", ypos = " + ypos + ", DIAMETER = " + DIAMETER);
				setSpeed(speed, Geometry.getDir(-xSpeed, -ySpeed, 0, 0));
			}
			if(ypos < 0 + DIAMETER/2 || ypos > Match.YSIZE - DIAMETER/2) {
				
				if(ypos < 0) ypos = 0 + DIAMETER/2;
				else if(ypos > Match.YSIZE) ypos = Match.YSIZE - DIAMETER/2;
				
				System.out.println("Ycollision: xpos = "  + xpos + ", ypos = " + ypos + ", DIAMETER = " + DIAMETER);
				setSpeed(speed, Geometry.getDir(xSpeed, ySpeed, 0, 0));
			}
		}
	}

}
