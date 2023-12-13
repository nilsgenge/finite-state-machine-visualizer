package main;

import java.awt.*;

import javax.swing.JPanel;

import gui.*;
import utilz.colortable;
import workspace.*;

public class Renderer extends JPanel {

	Main main;
	ObjectHandler oh;
	GuiHandler gui;

	private Boolean PRINT_PERFORMANCE_UPDATES = false;
	private final int FPS_SET = 60;
	private int UPS_SET = 60;

	public Renderer(Main main) {
		this.main = main;
	}

	public void render() {
		this.oh = main.oh;
		this.gui = main.g;
		double timePerFrame = 1000000000 / FPS_SET;
		double timePerUpdate = 1000000000 / UPS_SET;

		long previousTime = System.nanoTime();

		int updates = 0;
		int frames = 0;
		long lastCheck = System.currentTimeMillis();

		double deltaU = 0;
		double deltaF = 0;

		while (true) {
			long currentTime = System.nanoTime();

			deltaU += (currentTime - previousTime) / timePerUpdate;
			deltaF += (currentTime - previousTime) / timePerFrame;
			previousTime = currentTime;

			if (deltaU >= 1) {
				update();
				updates++;
				deltaU--;
			}

			if (deltaF >= 1) {
				repaint();
				frames++;
				deltaF--;
			}

			if (System.currentTimeMillis() - lastCheck >= 1000) {
				lastCheck = System.currentTimeMillis();
				if (PRINT_PERFORMANCE_UPDATES) {
					System.out.println("FPS: " + frames + "| UPS: " + updates);
				}
				frames = 0;
				updates = 0;
			}
		}
	}

	public void update() {
		oh.updateObjects();
		gui.updateGui();
		main.th.updateTool();
	}
	
	public void subRender(Graphics2D g2) {
		setBackground(colortable.BG_MAIN);
		oh.renderObjects(g2);
		gui.renderGui(g2);
	}

	@Override
	public void paintComponent(Graphics g) {
		if (this.oh != null) {
			Graphics2D g2 = (Graphics2D) g;
			super.paintComponent(g);

			g2.setRenderingHints(
					new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));
			g2.setRenderingHints(new RenderingHints(RenderingHints.KEY_ALPHA_INTERPOLATION,RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY));
			g2.setRenderingHints(new RenderingHints(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY));
			g2.setRenderingHints(new RenderingHints(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE));
			g2.setRenderingHints(new RenderingHints(RenderingHints.KEY_FRACTIONALMETRICS,RenderingHints.VALUE_FRACTIONALMETRICS_ON));
			g2.setRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
			g2.setRenderingHints(new RenderingHints(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE));
			g2.setRenderingHints(new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON));
			
			// RENDER
			subRender(g2);
		}
	}
}
