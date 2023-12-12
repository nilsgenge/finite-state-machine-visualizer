package gui;

import java.awt.*;

import utilz.colortable;

public class Sidebar {

	private GuiHandler gh;
	private int width = 350;

	public Sidebar(GuiHandler gh) {
		this.gh = gh;
	}

	public void render(Graphics2D g2) {
		g2.setStroke(new BasicStroke(3));
		g2.setColor(colortable.BG_MENU);
		g2.fillRect(0, 0, (int) (width), gh.getScreenHeight());
		g2.setColor(Color.decode("#1f1235"));
		g2.drawRect(0, 0, (int) (width), gh.getScreenHeight());
	}

}
