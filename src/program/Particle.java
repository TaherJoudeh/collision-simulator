package program;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;
import java.util.Random;
import Input.KeyInput;

public class Particle {

	public static int PARTICLES_NUMBER = 1;
	public final static float GRAVITY_ACCELERATION = 0.1f;
	public final static LinkedList<Particle> allParticles = new LinkedList<> ();
	
	public final static float MUIX = 0.2f;
	public final static float MUIY = 0.2F;
	
	public final static float MUSX = MUIX*2f;
	public final static float MUSY = MUIY*2f;
	
	public final static float MUI_WALL = 2f;
	public final static float MUS_WALL = 0.05f;
	
	private Random random = new Random();
	
	private Position position;
	private float width, height;
	private float velX, velY;
	private float mass;
	private Color color;
	
	private Trail trail;
		
	private float radius;
	private float acceleration;
	
	private boolean isGrounded;
	
	private LinkedList<Particle> allCollided;
	
	Random R = new Random();
	
	public Particle(Position position, float width, float height, float velX , float velY) {
		
		this.position = position;
		this.width = width;
		this.height = height;
		this.velX = velX;
		this.velY = velY;
		
//		mass = (float) Math.sqrt(width*width + height*height)/256f;
		radius = width/2f;
		mass = (float) (2*Math.PI*radius)/64f;
		
		acceleration = GRAVITY_ACCELERATION;
		
		color = new Color( random.nextInt(255), random.nextInt(255), random.nextInt(255));
				
		allCollided = new LinkedList<>();
		
		allParticles.add(this);
		
		trail = new Trail(750,this);

	}
	
	public Position getPosition() { return position; }
	public void setPosition(Position position) { this.position = position; }
	
	public float getX() { return position.getX(); }
	public void setX(float x) { position.setX(x); }
	
	public float getY() { return position.getY(); }
	public void setY(float y) { position.setY(y); }
	
	public float getWidth() { return width; }
	public void setWidth(float width) { this.width = width; }
	
	public float getHeight() { return height; }
	public void setHeight(float height) { this.height = height; }
	
	public float getVelX() { return velX; }
	public void setVelX(float velX) { this.velX = velX; }
	
	public float getVelY() { return velY; }
	public void setVelY(float velY) { this.velY = velY; }
	
	public float getMass() { return mass; }

	public Color getColor() { return color; }
	
	public Rectangle getHitbox() {
		return new Rectangle((int)position.getX(), (int)position.getY(), (int)width, (int)height);
	}
	
	public Position getCenter() {
		return new Position(getX()+width*0.5f,getY()+height*0.5f);
	}
	
	private boolean isCenterClose(Particle p1) {
		
		if (getDistBetweenTwoPoints(getCenter(), p1.getCenter()) < radius+p1.radius)
			return true;
		return false;
		
	}
	
	private float getDistBetweenTwoPoints(Position pos1, Position pos2) {
		
		return (float) Math.sqrt((float)Math.pow(pos1.getY()-pos2.getY(), 2) + (float)Math.pow(pos1.getX()-pos2.getX(), 2));
		
	}
	
	public void giveBoost() {
		
		isGrounded = false;
		float randVelX = (float)Math.random()*10f,
				randVelY = (float)Math.random()*10f;
		randVelX = random.nextBoolean() ? -randVelX : randVelX;
//		randVelY = random.nextBoolean() ? -velY : velY;
		
		velX = randVelX;
		velY -= randVelY;
		
	}
	
	public void update() {
		
//		if (velY >= -0.05f && velY <= 0.05f)
//			velY = 0;
//		
//		if (velX >= -0.1f && velX <= 0.1f)
//			velX = 0;
		
//		if (isGrounded) {
//			acceleration = 0;
//			velY = 0;
//		}else acceleration = GRAVITY_ACCELERATION;
		
		if (!DrawFrame.inSpace)
			velY += acceleration;
		
//		float fricX = mass*GRAVITY_ACCELERATION*MUIX*velX,
//				fricY = mass*GRAVITY_ACCELERATION*MUIY*velY;
//		float fricSX = mass*GRAVITY_ACCELERATION*MUSX,
//				fricSY = mass*GRAVITY_ACCELERATION*MUSY;
		float fricWall = mass*GRAVITY_ACCELERATION*MUS_WALL;
		float fricEach = mass*GRAVITY_ACCELERATION*MUSY;
		
		float kEnergyX1 = 0.5f*mass*velX*velX;
//				kEnergyY1 = 0.5f*mass*velY*velY;
		
//		if (kEnergyX1 < fricSX)
//			velX = 0;
//		if (kEnergyX1 < fricX)
//			velX = 0;
		if (kEnergyX1 < fricWall && DrawFrame.energyLoss)
			velX = 0;
		
//		if (kEnergyY1 < fricSY) {
//			velX = 0;
//			velY = 0;
//		}
		
//		if (kEnergyX1 < fricX && kEnergyY1 < fricY) {
//			velX = 0;
//			velY = 0;
//		}
		
		position.setX(position.getX()+velX);
		position.setY(position.getY()+velY);
		
		//velocityLimit();
		
		windowCollision();
		
		int iterations = 3;
		
		for (int i = 0; i < iterations; i++)
			collisionSystem();
//		System.out.println(false);
		trail.update();
		
	}

	public void render(Graphics g) {

		trail.render(g);
		
		g.setColor(color);
		g.fillOval((int)getX(), (int)getY(), (int)width, (int)height);
		g.setColor(Color.WHITE);
		g.drawOval((int)getX(), (int)getY(), (int)width, (int)height);
		
		// Hitboxes
		if (KeyInput.showHitboxes) {
			g.setColor(Color.RED);
			g.drawOval((int)getX(), (int)getY(), (int)width, (int)height);
		}
		
		if (KeyInput.showHitboxes2) {
			g.setColor(Color.GREEN);
			g.drawRect((int)getX(), (int)getY(), (int)width, (int)height);
		}
				
//		if (velX == 0 && velY >= -0.05f && velY <= 0.05f && velY != 0) {
//			g.setColor(Color.YELLOW);
//			g.drawOval((int)getX(), (int)getY(), width, height);
//		}
		
	}
	
	private void velocityLimit() {

		if (velX > 5f)
			velX = 5f;
		if (velX < -5f)
			velX = -5f;
		
	}

	private void windowCollision() {
		
//		if (velX == 0 && velY == 0)
//			return;
		
		//X
		if (getX() < 1) {
			position.setX(1);
			float nextVelX = calculateU1(mass, 10000000, velX, 0);
			if (DrawFrame.energyLoss)
				velX = nextVelX-(nextVelX*(mass*GRAVITY_ACCELERATION)*MUI_WALL);
			else velX = nextVelX;
		}
		
		if (getX()+width > Window.WIDTH-1) {
			position.setX(Window.WIDTH-width-1);
			float nextVelX = calculateU1(mass, 10000000, velX, 0);
			if (DrawFrame.energyLoss)
				velX = nextVelX-(nextVelX*(mass*GRAVITY_ACCELERATION)*MUI_WALL);
			else velX = nextVelX;
		}
		
		//Y
		if (getY() < 0) {
			position.setY(0);
			float nextVelY = calculateU1(mass, 10000000, velY, 0);
			if (DrawFrame.energyLoss)
				velY = nextVelY-(nextVelY*(mass*GRAVITY_ACCELERATION)*MUI_WALL);
			velY = nextVelY;
		}
		
		if (getY()+height+32 > Window.HEIGHT) {
			position.setY(Window.HEIGHT-height-32);
			float nextVelY = calculateU1(mass, 10000000, velY, 0);
			
			float energy = 0.5f*mass*velY*velY;
			float fric = mass*GRAVITY_ACCELERATION*MUI_WALL;
			
			if (energy < fric && DrawFrame.energyLoss)
				nextVelY = 0;
			
			if (DrawFrame.energyLoss) {
//				velY = nextVelY - nextVelY*GRAVITY_ACCELERATION*2;
//			velY = -(velY-velY*velY*MUI);
			velY = nextVelY-nextVelY*(mass*GRAVITY_ACCELERATION)*MUI_WALL;
			velX -= velX*(mass*GRAVITY_ACCELERATION)*MUS_WALL;
			} else velY = nextVelY;
//			isGrounded = true;
//			if (velY == -0.03f)
//				isGrounded = true;
//			else isGrounded = false;
			if (velY >= -0.005f && velY <= 0.005f)
				isGrounded = true;
			else isGrounded = false;
		}
		
	}
	
	private float getVelocity() {
		return (float)Math.sqrt((velX*velX) + (velY*velY));
	}
	private float getAngle() {
		return (float)Math.atan(velY/velX);
	}
	
	private float calculateU2(float m1, float m2, float v1, float v2) {
		
		return  ((2*m1*v1)/(m1+m2)) - (((m1-m2)*v2)/(m1+m2));
//		return ( (2*m1*v1) )/(m1+m2);
		
	}
	
	private float calculateU1(float m1, float m2, float v1, float v2) {
		
//		return v2 + calculateU2(m1,m2,v1,v2) - v1;
//		return (m1-m2)*v1/(m1+m2);
		return (((m1-m2)*v1)/(m1+m2)) + ((2*m2*v2)/(m1+m2));
		
	}
	
	private void collisionSystem() {
		
		for (int i = 0; i < allParticles.size(); i++) {
			
			if (allParticles.get(i) == this || allParticles.get(i) instanceof ParticleEffect)
				continue;
			
					Particle other = allParticles.get(i);
					
//					if (atI.getHitbox().intersects(atJ.getHitbox())) {
					if (isCenterClose(other)) {
						
						if (!allCollided.contains(other))
							allCollided.add(other);
						
						float ivx2 = calculateU1(mass,other.mass,velX,other.velX),
								ivy2 = calculateU1(mass,other.mass,velY,other.velY);
						float jvx2 = calculateU2(mass,other.mass,velX,other.velX),
								jvy2 = calculateU2(mass,other.mass,velY,other.velY);
						
//						if (atI.isGrounded && ivy2 == 0)
//							isGrounded = true;
						
						float dist = getDistBetweenTwoPoints(getCenter(), other.getCenter());
						float radii = radius + other.radius;
						
//						float mulX = 1f,
//								mulY = 1f;
						
//						float m = ( getCenter().getY()-other.getCenter().getY() ) /
//								( getCenter().getX()-other.getCenter().getX() );
						
						//Vector
						float vX = getCenter().getX()-other.getCenter().getX(),
								vY = getCenter().getY()-other.getCenter().getY();
						
						float vLength = getVectorLength(vX,vY);
												
						float dirX = vX/vLength,
								dirY = vY/vLength;
						
//						float dirX = (vX/vLength)*radii,
//								dirY = (vY/vLength)*radii;
						
						boolean canMove = true;
						
						float fricX = mass*GRAVITY_ACCELERATION*MUIX,
								fricY = mass*GRAVITY_ACCELERATION*MUIY;
						float fricX2 = other.mass*GRAVITY_ACCELERATION*MUIX,
								fricY2 = other.mass*GRAVITY_ACCELERATION*MUIY;
						
						float fricSX = mass*GRAVITY_ACCELERATION*MUSX;
						float fricSY = mass*GRAVITY_ACCELERATION*MUSY;
						float fricSX2 = other.mass*GRAVITY_ACCELERATION*MUSX;
						float fricSY2 = other.mass*GRAVITY_ACCELERATION*MUSY;
//						
						float kEnergyX1 = 0.5f*mass*velX*velX;
						float kEnergyY1 = 0.5f*mass*ivy2*ivy2;
						float kEnergyX2 = 0.5f*other.mass*other.velX*other.velX;
						float kEnergyY2 = 0.5f*other.mass*jvy2*jvy2;
//						float fricSX = mass*GRAVITY_ACCELERATION*MUSX,
//						float fricSY = mass*GRAVITY_ACCELERATION*MUSY;
//						float fricSX2 = other.mass*GRAVITY_ACCELERATION*MUSX,
//						float fricSY2 = other.mass*GRAVITY_ACCELERATION*MUSY; 
////						
//						float kEnergyX1 = 0.5f*mass*velX*velX,
//						float kEnergyY1 = 0.5f*mass*velY*velY;
//						float kEnergyX2 = 0.5f*other.mass*other.velX*other.velX,
//						float kEnergyY2 = 0.5f*other.mass*other.velY*other.velY;
						
					
						if (kEnergyY1 < fricSY && DrawFrame.energyLoss) {
							velY = 0;
							fricSY = 0;
						}
						if (kEnergyY2 < fricSY2 && DrawFrame.energyLoss) {
							other.velY = 0;
							fricSY2 = 0;
						}
						
						if (kEnergyX1 < fricSX && DrawFrame.energyLoss) {
							velX = 0;
							fricSX = 0;
						}
						if (kEnergyX2 < fricSX2 && DrawFrame.energyLoss) {
							other.velX = 0;
							fricSX2 = 0;
						}
						
//						velX = ivx2 - fricX;
//						velY = ivy2 - fricY;
//						other.velX = jvx2 - fricX2;
//						other.velY = jvy2 - fricY2;
						
						velX = ivx2;
						velY = ivy2;
						other.velX = jvx2;
						other.velY = jvy2;
						
//						velX -= fricX/100f;
//						velY -= fricY/100f;
//						other.velX -= fricX2/100f;
//						other.velY -= fricY2/100f;
						
						position.setX( position.getX() + dirX/2f);
						position.setY( position.getY() + dirY/2f);
						
						other.position.setX( other.position.getX() - dirX/2f);
						other.position.setY( other.position.getY() - dirY/2f);
						
						if (DrawFrame.particleSystemEnabled)
							if ( (velX >= 3 || velY >= 3) || (velX <= -3 || velY <= -3) )
								DrawFrame.makeParticles(5,position.getX()-dirX/2f,position.getY()-dirY/2f);
						
//						if (calculateAllCollidedEnergy() >= calculateEnergy() && allCollided.size() >= 2) {
//							position.setX( position.getX() + dirX/50f);
//							position.setY( position.getY() + dirY/50f);
//							
//							other.position.setX( other.position.getX() - dirX/59f);
//							other.position.setY( other.position.getY() - dirY/50f);
//						}else {
//							position.setX( position.getX() + dirX/2f);
//							position.setY( position.getY() + dirY/2f);
//							
//							other.position.setX( other.position.getX() - dirX/2f);
//							other.position.setY( other.position.getY() - dirY/2f);
//						}
						
//						if (calculateAllCollidedEnergy() >= calculateEnergy()) {
////							velX = 0;
////							velY = 0;
////							
////							allCollidedResetEnergy();
//							
////							System.out.println(true);
//						}else {
//							position.setX( position.getX() + dirX/2f);
//							position.setY( position.getY() + dirY/2f);
//							
//							other.position.setX( other.position.getX() - dirX/2f);
//							other.position.setY( other.position.getY() - dirY/2f);
//						}
						
//						velX = ivx2*dirX/(2f);
//						velY = ivy2*dirY/(2f);
//						other.velX = jvx2;
//						other.velY = jvy2;
						
//						velX = ivx2;
//						velY = ivy2;
//						other.velX = jvx2;
//						other.velY = jvy2;
						
//						 kEnergyX1 = 0.5f*mass*velX*velX;
//								kEnergyY1 = 0.5f*mass*velY*velY;
//						 kEnergyX2 = 0.5f*other.mass*other.velX*other.velX;
//								kEnergyY2 = 0.5f*other.mass*other.velY*other.velY;
						
//						if (kEnergyX1 < fricSX && kEnergyY1 < fricSY) {
//							velX = 0;
//							velY = 0;
//							canMove = false;
//						}
//						if (kEnergyX2 < fricSX2 && kEnergyY2 < fricSY2) {
//							other.velX = 0;
//							other.velY = 0;
//							canMove = false;
//						}
						
//						if (!canMove)
//							return;
						
//						if (kEnergyX1 < fricX && kEnergyY1 < fricY) {
//							velX = 0;
//							velY = 0;
//							canMove = false;
//						}
//						if (kEnergyX2 < fricX2 && kEnergyY2 < fricY2) {
//							other.velX = 0;
//							other.velY = 0;
//							canMove = false;
//						}
						
//						if (!canMove)
//							return;
						
					}else if (!allCollided.isEmpty()) allCollided.remove(other);
					
		}
		
	}

	private float calculateAllCollidedEnergy() {
		
		float sum = 0f;
		for (int i = 0; i < allCollided.size(); i++) {
			sum += allCollided.get(i).calculateEnergy();
		}
		
		return sum;
		
	}
	
	private float calculateEnergy() {
		return (float) ((0.5*mass*velY*velY) + (mass*GRAVITY_ACCELERATION));
	}
	
	private void allCollidedResetEnergy() {
		for (int i = 0; i < allCollided.size(); i++) {
			allCollided.get(i).velX = 0;
			allCollided.get(i).velY = 0;
		}
	}
	
	private float getVectorLength(float vX, float vY) {
		return (float)Math.sqrt(vX*vX + vY*vY);
	}
	
	public Particle clone() {
		Particle p =  new Particle(position.clone(),width,height,velX,velY);
		p.color = color;
		allParticles.remove(p);
		return p;
	}
	
}
