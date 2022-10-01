package game;

import java.awt.Graphics;

public abstract class GraphicObject {
	
	protected float xpos;
	protected float ypos;
	protected float tilt;
	
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
	
}
