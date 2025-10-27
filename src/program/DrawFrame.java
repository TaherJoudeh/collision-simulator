package program;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.Random;

import Input.KeyInput;
import Input.MouseInput;

public class DrawFrame extends Canvas implements Runnable {

	private Thread thread;
	public static boolean isRunning;
	public static double amountOfTicks, ns;
	
	public static boolean isPaused;
	
	private static Random random;
	private KeyInput keyInput;
	private MouseInput mouseInput;
	
	public static boolean inSpace = false,
			energyLoss = true, particleSystemEnabled = true;
	
	public DrawFrame() {
		
		setFocusTraversalKeysEnabled(false);
		requestFocus();
		setFocusable(true);
				
		thread = new Thread(this);
		
		init();
		addKeyListener(keyInput);
		addMouseListener(mouseInput);
	}
	
	private void init() {
		random = new Random();
		keyInput = new KeyInput();
		mouseInput = new MouseInput();
		spawn();
	}
	
	public synchronized void start() {
		
		if (isRunning)
			return;
		isRunning = true;
		thread.start();
		
	}
	
	public synchronized void stop() {
		
		if (!isRunning)
			return;
		isRunning = false;
		System.exit(0);
		
	}
	
	@Override
	public void run() {
		long lastTime = System.nanoTime();
	    amountOfTicks = 120.0;
//	    double ns = 1000000000 / amountOfTicks;
	    double n = 1000000000;
	    ns = n/amountOfTicks;
	    double delta = 0;
	    long timer = System.currentTimeMillis();
	    int frames = 0 ;
	    while (isRunning) {
	        long now = System.nanoTime();
	        delta += (now - lastTime) / ns;
	        lastTime = now;
	        while (delta >= 1) {
	        	if (!isPaused)
	        		update();
	            delta--;
	        }
	        if(isRunning)
	            render();
	        frames++;

	        if(System.currentTimeMillis() - timer > 1000) {
	            timer += 1000;
	            //System.out.println("FPS: " + frames);
	            frames = 0;
	        }
	    }
	    stop();
		
	}

	
	private void update() {
			updateAll();
	}
	private void render() {
		
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		
		//Background
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Window.WIDTH, Window.HEIGHT);
		// Window Boundaries
		drawWindowBorder(g);
		
		renderAll(g);
		
		g.dispose();
		bs.show();
	}
	
	private void drawWindowBorder(Graphics g) {
		g.setColor(Color.WHITE);
		//TOP
		g.drawRect(0, 0, Window.WIDTH, 1);
		//LEFT
		g.drawRect(0, 0, 1, Window.HEIGHT);
		//RIGHT
		g.drawRect(Window.WIDTH-8, 0, 1, Window.HEIGHT);
		//DOWN
		g.drawRect(0, Window.HEIGHT-30, Window.WIDTH, 1);	
	}

	public static void spawnMovingParticle() {
		
		Random random = new Random();
		int randWidthHeight = random.nextInt(16) + 16,
				randX = random.nextInt(Window.WIDTH-randWidthHeight),
				randY = random.nextInt(Window.HEIGHT-randWidthHeight);
		Position position = new Position(randX, randY);
		
		float randVelX = (float)Math.random()*3f,
				randVelY = (float)Math.random()*3f;
		randVelX = random.nextBoolean() ? -randVelX : randVelX;
		randVelY = random.nextBoolean() ? -randVelY : randVelY;
		
		new Particle(position, randWidthHeight, randWidthHeight, randVelX, 0);
		
	}
	
	private void spawn() {
		
		for (int i = 0 ; i < Particle.PARTICLES_NUMBER; i++) {
			
			int randWidthHeight = random.nextInt(24) + 16,
//			int randWidthHeight = random.nextInt(128) + 64,
					randX = random.nextInt(Window.WIDTH-randWidthHeight),
					randY = random.nextInt(Window.HEIGHT-randWidthHeight);
			Position position = new Position(randX, randY);
			
//			float randVelX = random.nextInt(2) + 1,
//					randVelY = random.nextInt(2) + 1;
			float randVelX = (float)Math.random()*3f,
					randVelY = (float)Math.random()*3f;
			randVelX = random.nextBoolean() ? -randVelX : randVelX;
			randVelY = random.nextBoolean() ? -randVelY : randVelY;
			if (!inSpace)
				new Particle(position, randWidthHeight, randWidthHeight, randVelX, 0);
			else new Particle(position, randWidthHeight, randWidthHeight, randVelX/3f, randVelY/3f);
			
		}
		
		//spawnMovingParticle();
		
	}
	
	public static void makeParticles(int n, float x, float y) {
		
		for (int i = 0; i < n; i++) {
			int randWidthHeight = random.nextInt(10);
			float randVelX = random.nextFloat()*5,
					randVelY = random.nextFloat()*5;
			randVelX = random.nextBoolean() ? randVelX : -randVelX;
			randVelY = random.nextBoolean() ? randVelY : -randVelY;
			new ParticleEffect(new Position(x,y),randWidthHeight,randWidthHeight,randVelX,randVelY);
		}
		
	}
	
	private void updateAll() {
		
		for (int i = 0; i < Particle.allParticles.size(); i++)
			Particle.allParticles.get(i).update();
		
	}
	
	private void renderAll(Graphics g) {
		
		for (int i = 0; i < Particle.allParticles.size(); i++) {
//			System.out.println(Particle.allParticles.get(i).getVelY());
//			System.out.println(Particle.allParticles.get(i).getVelX());
			Particle.allParticles.get(i).render(g);
		}
		
	}
	
}
