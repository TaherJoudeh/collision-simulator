package program;

public class Position {

	private float x, y;
	
	public Position() {
		
		x = Window.WIDTH/2;
		y = Window.HEIGHT/2;
		
	}
	
	public Position(float x, float y) {
		
		this.x = x;
		this.y = y;
		
	}
	
	public Position(Position other) {
		this(other.x,other.y);
	}
	
	public float getX() { return x; }
	public void setX(float x) { this.x = x; }
	
	public float getY() { return y; }
	public void setY(float y) { this.y = y; }
	
	public Position clone() {
		return new Position(x,y);
	}
	
}
