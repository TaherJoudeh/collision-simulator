package program;

import java.awt.Color;
import java.awt.Graphics;

public class ParticleEffect extends Particle {
	
	private float x, y, width, height, velX, velY;
	
	private final float LIFE_SPAN = 500;
	
	private float lifespan;
	
	public ParticleEffect(Position position, int width, int height, float velX, float velY) {
		super(position, width, height, velX, velY);
		x = position.getX();
		y = position.getY();
		this.width = width;
		this.height = height;
		this.velX = velX;
		this.velY = velY;
		
		lifespan = LIFE_SPAN;
		allParticles.add(this);
	}

	@Override
	public void update() {
		
		lifespan -= 2f;
		if (lifespan <= 0)
			allParticles.remove(this);
		
		width -= 0.01f;
		height -= 0.01f;
			
		x += velX;
		y += velY;
		
		velY += Particle.GRAVITY_ACCELERATION/10f;
		
		if (x < 0 || x+width > Window.WIDTH-1)
			velX = -velX;
		if (y < 0 || y+height > Window.HEIGHT-1)
			velY = -velY;
		
	}
	
	@Override
	public void render(Graphics g) { 
		g.setColor(new Color(1f,1f,1f,lifespan/LIFE_SPAN));
		g.fillOval((int)x, (int)y, (int)width, (int)height);
	}
	
}
