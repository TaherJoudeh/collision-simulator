package Input;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import program.DrawFrame;
import program.Particle;

public class KeyInput extends KeyAdapter {

	public static boolean showHitboxes, showHitboxes2;
	
	@Override
	public void keyReleased(KeyEvent e) {
		
		if (e.getKeyCode() == KeyEvent.VK_E) {
			for (int i = 0; i < Particle.allParticles.size(); i++)
				Particle.allParticles.get(i).giveBoost();
		}
		
		if (e.getKeyCode() == KeyEvent.VK_P)
			DrawFrame.isPaused = !DrawFrame.isPaused;
		
		if (e.getKeyCode() == KeyEvent.VK_L) {
			DrawFrame.amountOfTicks += 10;
			DrawFrame.ns = 1000000000 / DrawFrame.amountOfTicks;
		}
		if (e.getKeyCode() == KeyEvent.VK_K) {
			DrawFrame.amountOfTicks -= 10;
			DrawFrame.ns = 1000000000 / DrawFrame.amountOfTicks;
		}
		
		if (e.getKeyCode() == KeyEvent.VK_Q) {
			DrawFrame.spawnMovingParticle();
			Particle.PARTICLES_NUMBER++;
		}
		if (e.getKeyCode() == KeyEvent.VK_R) {
			if (Particle.PARTICLES_NUMBER >= 1) {
				Particle.allParticles.remove(0);
				Particle.PARTICLES_NUMBER--;
			}
		}
		
		super.keyReleased(e);
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		
		if (e.getKeyCode() == KeyEvent.VK_H) {
			if (showHitboxes2) {
				showHitboxes = false;
				showHitboxes2 = false;
				return;
			}
			if (showHitboxes && !showHitboxes2)
				showHitboxes2 = true;
			if (!showHitboxes && !showHitboxes2)
				showHitboxes = true;
		}
		
		super.keyPressed(e);
	}
	
}
