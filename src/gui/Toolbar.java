package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

import inputs.MouseInputs;
import main.*;
import utilz.colortable;
import utilz.tools;
import workspace.ToolHandler;

public class Toolbar {

	private GuiHandler gh;
	private MouseInputs m;
	private ToolHandler th;

	private int height = 100;

	private ToolButton toolButtons[];

	public Toolbar(GuiHandler gh, MouseInputs m, ToolHandler th) {
		this.gh = gh;
		this.m = m;
		this.th = th;

		initializeToolButtons();
	}

	public void render(Graphics2D g2) {
		g2.setStroke(new BasicStroke(3));
		g2.setColor(colortable.BG_MENU);
		g2.fillRect(0, 0, (int) (gh.getScreenWidth()), height);
		g2.setColor(Color.decode("#1f1235"));
		g2.drawRect(0, 0, (int) (gh.getScreenWidth()), height);

		renderButtons(g2);
	}

	public void update() {
		updateButtons();
	}

	public void renderButtons(Graphics2D g2) {
		if (this.toolButtons != null) {
			for (int i = 0; i < toolButtons.length; i++) {
				toolButtons[i].render(g2);
			}
		}
	}

	public void updateButtons() {
		if (this.toolButtons != null) {
			for (int i = 0; i < toolButtons.length; i++) {
				toolButtons[i].checkPressed(m.getM1X(), m.getM1Y());
			}
		}
	}

	public void initializeToolButtons() {
		int buttonWidth = 80;
		int buttonHeight = 80;
		int leftOffset = 10;
		int topOffset = 10;

		toolButtons = new ToolButton[6];
		toolButtons[0] = new ToolButton(0 * buttonWidth + 1 * leftOffset, topOffset, buttonWidth, buttonHeight,
				"StateTool", tools.STATE, th);
		toolButtons[1] = new ToolButton(1 * buttonWidth + 2 * leftOffset, topOffset, buttonWidth, buttonHeight,
				"TransitionTool", tools.TRANSITION, th);
		toolButtons[2] = new ToolButton(2 * buttonWidth + 3 * leftOffset, topOffset, buttonWidth, buttonHeight, "Empty",
				tools.EMPTY, th);
		toolButtons[3] = new ToolButton(3 * buttonWidth + 4 * leftOffset, topOffset, buttonWidth, buttonHeight, "Empty",
				tools.EMPTY, th);
		toolButtons[4] = new ToolButton(4 * buttonWidth + 5 * leftOffset, topOffset, buttonWidth, buttonHeight, "Empty",
				tools.EMPTY, th);
		toolButtons[5] = new ToolButton(5 * buttonWidth + 6 * leftOffset, topOffset, buttonWidth, buttonHeight, "Empty",
				tools.EMPTY, th);

	}
}
