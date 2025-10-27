package program;

import java.awt.Dimension;
import javax.swing.JFrame;

public class Window {

	public final static int WIDTH = 1280, HEIGHT = 720;
	public final static Dimension WINDOW_DIMENSION = new Dimension(WIDTH, HEIGHT);
	
	public Window(String name, DrawFrame drawFrame) {

		drawFrame.setMinimumSize(WINDOW_DIMENSION);
		drawFrame.setPreferredSize(WINDOW_DIMENSION);
		drawFrame.setMaximumSize(WINDOW_DIMENSION);
		
		JFrame jframe = new JFrame(name);
		
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		jframe.setMinimumSize(WINDOW_DIMENSION);
		jframe.setPreferredSize(WINDOW_DIMENSION);
		jframe.setMaximumSize(WINDOW_DIMENSION);
		
		jframe.setFocusTraversalKeysEnabled(false);
		jframe.requestFocus();
		        
		jframe.setSize(WINDOW_DIMENSION);
		jframe.add(drawFrame);
		jframe.setResizable(false);
		jframe.setLocationRelativeTo(null);
		jframe.pack();
		jframe.setVisible(true);
		drawFrame.start();
		
	}
	
}
