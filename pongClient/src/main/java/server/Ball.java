package server;

import java.awt.Graphics;

public class Ball extends GraphicObject {
	final int DIAMETER = 30;
	float dir;
	public float getDir() {
		return dir;
	}

	public void setDir(float dir) {
		this.dir = dir;
	}

	float speed;
	float xSpeed;
	float ySpeed;
	Match match;
	
	public Ball(Match match) {
		this.match = match;
		xpos = Match.XSIZE/2;
		ypos = Match.YSIZE/2;
		setSpeed();
	}

	void setSpeed(float newspeed, float newdir) {
		
		//corrige ângulos negativos ou maiores que 360
		if(newdir < 0) {
			newdir = 360 - newdir;
		}else if(newdir > 360) {
			newdir -= 360;
		}
		
		//evita que a bolinha quique demais por ter um ângulo muito vertical
		if(Geometry.angleIsVertical(newdir)) {
			newdir = Geometry.achataAngulo(newdir);
		}
		
		speed = newspeed;
		dir = newdir;
		xSpeed = Geometry.xSpeed(newdir, newspeed);
		ySpeed = Geometry.ySpeed(newdir, newspeed);
		
		
		//corrige mudanças de direção que joguem a bolinha para BUGS
		boolean xbug = false;
		boolean ybug = false;
		
		ybug = ypos - ySpeed < 0 + DIAMETER/2 && ySpeed > 0;
		if(!ybug) ybug = ypos - ySpeed > Match.YSIZE - DIAMETER/2 && ySpeed < 0;

		if(ybug) {
			dir = Geometry.getDir(xSpeed, ySpeed, 0, 0);
			ySpeed = -ySpeed;
		}
		
		
		if(xpos + xSpeed < 0 + DIAMETER/2 && xSpeed < 0) {
			xbug = true;
		}else if(xpos + xSpeed > Match.XSIZE - DIAMETER/2 && xSpeed > 0) {
			xbug = true;
		}
		
		if(xbug) {
			match.point();
		}
	}
	
	public void setSpeed(float newspeed) {
		setSpeed(newspeed, dir);
	}
	
	private void setSpeed() {
		setSpeed(10*(float)Math.random() + 10, 360*(float)Math.random());
	}
	
	public float getSpeed() {
		return speed;
	}
	
	@Override
	public void draw(Graphics g) {
		g.drawOval(Math.round(xpos) - DIAMETER/2, Math.round(ypos) - DIAMETER/2, DIAMETER, DIAMETER);
	}

	@Override
	public void move(double ticks) {
		if(match.isPaused()) {
			return;
		}
			
		xpos += xSpeed * ticks;
		ypos -= ySpeed * ticks;
	}

	@Override
	public void checkCollision(GraphicObject other) {
		
		if(other == null) {
			wallCollision();
		}else 
			
		if(other.getClass().equals(Player.class)) {
			if(Geometry.distance(this, other) < DIAMETER) {
				setSpeed(speed, Geometry.getDir(this, other));
			}	
		}
	}
	
	void reposition() {
		xpos = Match.XSIZE/2;
		ypos = Match.YSIZE/2;
		setSpeed();
	}
	
	private void wallCollision() {
		boolean xcollision = false;
		
		//colisão vertical	
		if(ypos < 0 + DIAMETER/2 || ypos > Match.YSIZE - DIAMETER/2) {
			
			if(ypos < 0) ypos = 0 + DIAMETER/2;
			else if(ypos > Match.YSIZE) ypos = Match.YSIZE - DIAMETER/2;
			
			//espelha a velocidade verticalmente
			setSpeed(speed, Geometry.getDir(xSpeed, ySpeed, 0, 0));
			return;
		} 
		
			
		//lado esquerdo
		else if(xpos < 0 + DIAMETER/2) {
			xcollision = true;
			System.out.println("Pontuação jogador 2: " + match.playerlist.get(2).getScore());
			
			if(xpos < 0) {
				xpos = 0 + DIAMETER/2;
			}
		}
		
		//lado direito
		else if(xpos > Match.XSIZE - DIAMETER/2) {
			xcollision = true;
			System.out.println("Pontuação jogador 1: " + match.playerlist.get(1).getScore());
			
			if(xpos > Match.XSIZE) {
				xpos = Match.XSIZE - DIAMETER/2;
			}
		}
		
		if(xcollision) {
			match.point();
		}
	}

}
