package program;

import java.awt.Graphics;
import java.util.LinkedList;
import java.util.Random;

public class Trail {

	public final static int MAX_TRAILS = 100;
	private Particle particle;
	private LinkedList<TrailParticle> parPos;
	private int trailsNumber, count;
	private Random random = new Random();
	private int widthHeight; 
	
	public Trail(int trailsNumber, Particle particle) {
		
		if (trailsNumber > MAX_TRAILS)
			this.trailsNumber = MAX_TRAILS;
		else this.trailsNumber = trailsNumber;
	
		this.particle = particle;
		
		widthHeight = random.nextInt(32) + 2;
		
		parPos = new LinkedList<> ();
		
	}
	
	private void posCorrections() {
		for (int i = 0; i < parPos.size(); i++) {
			Particle p = parPos.get(i).getParticle();
//			p.setX(p.getX());
			p.setY(particle.getCenter().getY()-p.getHeight()/2);
		}
	}
	
	public void update() {
		
		if (count%1 == 0 && count != 0 && parPos.size() < trailsNumber) {
			if ( (particle.getVelX() >= 2f || particle.getVelX() <= -2f) ||
					(particle.getVelY() >= 2f || particle.getVelY() <= -2f) )
					parPos.add(new TrailParticle(particle.clone(),widthHeight));
//			else posCorrections();
		}
		
		for (int i = 0; i < parPos.size(); i++) {
			TrailParticle pi = parPos.get(i);
			pi.update();
			if (pi.hasDied()) {
				parPos.remove(pi);
				count = 0;
		}
		}
				
		count++;
		
	}
	
	public void render(Graphics g) {
		
		for (int i = 0; i < parPos.size(); i++)
			parPos.get(i).render(g);
		
	}
	
}
