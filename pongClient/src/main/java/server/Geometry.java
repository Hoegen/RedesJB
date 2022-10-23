package server;

public class Geometry {
	
	public static float distance(float x1, float y1, float x2, float y2) {
		return (float)Math.sqrt((x1-x2)*(x1-x2) + (y1 - y2)*(y1-y2));
	}
	
	public static float distance(GraphicObject go1, GraphicObject go2) {
		
		//trata a distância entre bola e raquete
		if(go1.getClass().equals(Ball.class) && go2.getClass().equals(Player.class)) {
			
			float x2 = go2.getXpos();
			float y2 = go2.getYpos();
			
			if(go1.getXpos() > x2 + ((Player)go2).getWidth()/2) {
				x2 += ((Player)go2).getWidth()/2;
			} else if(go1.getXpos() < x2 - ((Player)go2).getWidth()/2) {
				x2 -= ((Player)go2).getWidth()/2;
			}
			
			if(go1.getYpos() > y2 + ((Player)go2).getHeight()/2) {
				y2 += ((Player)go2).getHeight()/2;
			} else if(go1.getYpos() < y2 - ((Player)go2).getHeight()/2) {
				y2 -= ((Player)go2).getHeight()/2;
			}
			
			return distance(go1.getXpos(), go1.getYpos(), x2, y2);
		
		}else if(go2.getClass().equals(Ball.class) && go1.getClass().equals(Player.class)) {
			return distance(go2, go1);
		}
		
		
		return distance(go1.getXpos(), go1.getYpos(), go2.getXpos(), go2.getYpos());
	}
	
	public static float xSpeed(float dir, float speed) {
		return speed * (float)Math.cos(Math.toRadians(dir));
	}
	
	public static float ySpeed(float dir, float speed) {
		return speed * (float)Math.sin(Math.toRadians(dir));
	}
	
	/**
	 * 
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @return the direction to point 1 from point 2
	 */
	public static float getDir(float x1, float y1, float x2, float y2) {
		float distance = distance(x1, y1, x2, y2);
		
		//tratando caso os objetos estejam perfeitamente sobrepostos
		if(distance == 0) {
			//se possível, direciona para o centro da tela
			if(distance(x1, x2, Match.WIDTH, Match.HEIGHT) > 0)
				return getDir(x1, x2, Match.WIDTH/2, Match.HEIGHT/2);
			
			//caso contrário, manda para a direita
			else
				return 0;
		}
		
		float dir = (float) Math.toDegrees(Math.acos((x1-x2)/distance));
		
		if(y1 > y2) {
			dir = 360 - dir;
		}
		
		return dir;
	}
	
	/**
	 * 
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @return the direction to point 1 from point 2
	 */
	public static float getDir(GraphicObject go1, GraphicObject go2) {
		return getDir(go1.getXpos(), go1.getYpos(), go2.getXpos(), go2.getYpos());
	}
}
