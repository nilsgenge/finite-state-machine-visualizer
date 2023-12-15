package gui;

import java.awt.Font;
import java.awt.Graphics2D;
import inputs.MouseInputs;
import main.*;
import utilz.colortable;
import workspace.ToolHandler;

public class GuiHandler {

	private Main main;
	public Toolbar tb;
	public Sidebar sb;
	private MouseInputs m;
	private ToolHandler th;

	private int screenWidth;
	private int screenHeight;

	public GuiHandler(Main main, MouseInputs m, ToolHandler th) {
		this.main = main;
		this.m = m;
		this.th = th;

		tb = new Toolbar(this, m, th);
		sb = new Sidebar(this);

		screenWidth = main.s.getScreenWidth();
		screenHeight = main.s.getScreenHeight();
	}

	public void renderGui(Graphics2D g2) {
		sb.render(g2);
		tb.render(g2);
		g2.setFont(new Font("Monospaced", Font.PLAIN, 12));
		g2.setColor(colortable.TEXT);
		g2.drawString("Equipped Tool: " + th.getCurrentTool(), 20, 120);
	}

	public void updateGui() {
		tb.update();
	}

	public int getScreenHeight() {
		return screenHeight;
	}

	public int getScreenWidth() {
		return screenWidth;
	}

}