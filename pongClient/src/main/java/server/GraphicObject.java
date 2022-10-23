package server;

import java.awt.Graphics;

public abstract class GraphicObject {
	
	protected float xpos;
	protected float ypos;
	protected float tilt;
	protected float speed;
	
	public abstract void move(double ticks);
	
	public abstract void draw(Graphics g);
	
	public float getXpos() {
		return xpos;
	}
	public void setXpos(float xpos) {
		this.xpos = xpos;
	}
	public float getYpos() {
		return ypos;
	}
	public void setYpos(float ypos) {
		this.ypos = ypos;
	}
	public float getTilt() {
		return tilt;
	}
	public void setTilt(float tilt) {
		this.tilt = tilt;
	}

	public abstract void checkCollision(GraphicObject other);
	
}
