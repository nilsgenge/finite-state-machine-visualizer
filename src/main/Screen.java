package main;

import java.awt.*;
import javax.swing.*;

public class Screen extends JFrame {

	public static int SCREEN_HEIGHT;
	public static int SCREEN_WIDTH;

	private Renderer r;
	private Main main;

	public Screen(Renderer r, Main main) {
		this.r = r;
		this.main = main;
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		r.setPreferredSize(screenSize);
		SCREEN_HEIGHT = (int) screenSize.getHeight();
		SCREEN_WIDTH = (int) screenSize.getWidth();

		Container cp = getContentPane();
		cp.add(r);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		setTitle("Finite-State-Machine-Visualizer - See Github for Code");
		setLocationRelativeTo(null);
		setVisible(true);
		addMouseListener(main.m);
		addKeyListener(main.k);
	}

	public int getScreenHeight() {
		return getSize().height;
	}

	public int getScreenWidth() {
		return getSize().width;
	}

}
