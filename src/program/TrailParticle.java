package program;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class TrailParticle {
	
	private final static int LIFESPAN = 50;
	private int count;
	private Particle particle;
	private Color color;
	private float originalVelX, originalVelY;	
	private int widthHeight;
	
	private Random random = new Random();
	
	public TrailParticle(Particle particle, int widthHeight) {
		
//		this.widthHeight = random.nextInt(32) + 16;
		this.widthHeight = widthHeight;
		originalVelX = particle.getVelX();
		originalVelY = particle.getVelY();
		
		this.particle = particle;
		color = particle.getColor();
//		this.particle = new Particle(new Position(particle.getPosition()),particle.getWidth(),particle.getHeight(),0,0);
		this.particle.setVelX(0);
		this.particle.setVelY(0);
//		
		count = LIFESPAN;
	}
	
	public int getCount() { return count; }
	
	public boolean hasDied() { return count <= 0; }
	
	public void update() {
//		particle.setWidth(particle.getWidth()-(particle.getWidth()/(Math.abs(originalVelX)*200f)+1f));
//		particle.setHeight(particle.getHeight()-(particle.getHeight()/(Math.abs(originalVelY)*200f)+1f));
		particle.setWidth(particle.getWidth()-(particle.getWidth()/32f));
		particle.setHeight(particle.getHeight()-(particle.getHeight()/32f));
		count--;
	}
	
	public Particle getParticle() { return particle; }
	
	public void render(Graphics g) {
//		g.setColor(new Color(color.getRed(),color.getGreen(),color.getBlue(),count/LIFESPAN));
		g.setColor(new Color(color.getRed(),color.getGreen(),color.getBlue(),((count*255)/LIFESPAN)));
//		g.setColor(new Color(color.getRed(),color.getGreen(),color.getBlue(),255));
//		g.setColor(new Color(255,255,255,((count*255)/LIFESPAN)));
		g.fillOval((int)(particle.getCenter().getX()-particle.getWidth()/2f), (int)(particle.getCenter().getY()-particle.getHeight()/2f), (int)particle.getWidth(), (int)particle.getHeight());
				
//		int r = random.nextInt(64) + 2;
		
//		g.fillOval((int)(particle.getCenter().getX()-particle.getWidth()/2f), (int)(particle.getCenter().getY()-particle.getHeight()/2f), widthHeight, widthHeight);
//		g.fillOval((int)(particle.getCenter().getX()-particle.getWidth()/2f), (int)(particle.getCenter().getY()-particle.getHeight()/2f), r,r);
	}
	
}
