package main;

import java.awt.*;
import java.awt.image.VolatileImage;
import javax.swing.JPanel;
import gui.*;
import utilz.Settings;
import utilz.colortable;
import workspace.*;

public class Renderer extends JPanel {

	Main main;
	ObjectHandler oh;
	GuiHandler gui;
	
	private final int FPS_SET = 60;
	private int UPS_SET = 60;
	
	VolatileImage offScreenBuffer;

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
				if (Settings.PRINT_PERFORMANCE_UPDATES.getValue()) {
					System.out.println("FPS: " + frames + "| UPS: " + updates);
				}
				frames = 0;
				updates = 0;
			}
		}
	}

	public void update() {
		main.m.updateMousePos();
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
			super.paintComponent(g);
			
			if(Settings.ENABLE_DOUBLE_BUFFERING.getValue()) {
				//CHECKING BUFFERIMAGE
				if (offScreenBuffer == null || offScreenBuffer.contentsLost()) {
		            offScreenBuffer = createVolatileImage(getWidth(), getHeight());
		        }
				
				//INITIALIZING BUFFER GRAPHICS
				Graphics2D offScreenGraphics = offScreenBuffer.createGraphics();
				
				//RENDERING HINTS
				RenderingHints renderingHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				renderingHints.put(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
				renderingHints.put(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
				renderingHints.put(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
				renderingHints.put(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
				renderingHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
				renderingHints.put(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);
				renderingHints.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
				offScreenGraphics.setRenderingHints(renderingHints);
				
				//CLEAR BUFFERIMAGE
				clearBuffer(offScreenBuffer);
				
				//MAIN RENDERER
				subRender(offScreenGraphics);
				
				//DRAWING BUFFERIMAGE TO SCREEN
				g.drawImage(offScreenBuffer, 0, 0, this);
				offScreenGraphics.dispose();
				
			} else {
				//INITIALIZING GRAPHICS
				Graphics2D g2 = (Graphics2D) g;
				
				//RENDERING HINTS
				RenderingHints renderingHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				renderingHints.put(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
				renderingHints.put(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
				renderingHints.put(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
				renderingHints.put(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
				renderingHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
				renderingHints.put(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);
				renderingHints.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
				g2.setRenderingHints(renderingHints);
				
				//MAIN RENDERER
				subRender(g2);
			}
		}
	}
	
	private void clearBuffer(VolatileImage buffer) {
	    Graphics2D g2d = buffer.createGraphics();
	    g2d.setColor(getBackground());
	    g2d.fillRect(0, 0, buffer.getWidth(), buffer.getHeight());
	    g2d.dispose();
	}
}
