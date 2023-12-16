package gui;

import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.Graphics2D;
import utilz.colortable;
import utilz.tools;
import workspace.ToolHandler;

public class ToolButton extends Button {

	private String type;
	private Boolean toolChanged = false;
	private Boolean checked = false;
	private int lastX;
	private int lastY;

	private ToolHandler th;

	public ToolButton(int x, int y, int width, int height, String name, String type, ToolHandler th) {
		super(x, y, width, height, name);
		this.type = type;
		this.th = th;

	}

	public void render(Graphics2D g2) {
		renderButtonOutline(g2);
		//cant be switch statement because case expression must be constant (error message)
		if (type == tools.EMPTY) {

		} else if (type == tools.STATE) {
			g2.drawOval(x + (int) (width * 0.1), y + (int) (height * 0.1), (int) (width * 0.8), (int) (height * 0.8));
			g2.drawLine(x + width / 2 + 1, y + (int) (height * 0.3) + 1, x + width / 2 + 1,y + (int) (height * 0.7) + 1);
			g2.drawLine(x + (int) (width * 0.3) + 1, y + height / 2 + 1, x + (int) (width * 0.7) + 1,y + height / 2 + 1);
		} else if (type == tools.TRANSITION) {
			g2.drawLine(x + width / 2 + 1, y + (int) (height * 0.3) + 1, x + width / 2 + 1,y + (int) (height * 0.7) + 1);
		} else if (type == tools.START) {
			g2.setFont(new Font("Arial", Font.PLAIN, 65));
			g2.setColor(colortable.TEXT);
			g2.drawString("S", x+20, y+63);
		} else if(type == tools.END) {
			g2.drawOval(x + (int) (width * 0.15), y + (int) (height * 0.15), (int) (width * 0.7), (int) (height * 0.7));
			g2.drawOval(x + (int) (width * 0.25), y + (int) (height * 0.25), (int) (width * 0.5), (int) (height * 0.5));
		}
	}

	public void ifPressed() {
		if (!toolChanged && th.getCurrentTool() != this.getType()) {
			th.setCurrentTool(this.getType());
			toolChanged = true;
		} else if (toolChanged) {
			th.setCurrentTool(tools.EMPTY);
			toolChanged = false;
		}
	}

	public void checkPressed(int mX, int mY) {
		if ((lastX != mX || lastY != mY) && inHitbox(mX, mY)) {
			this.checked = false;
			this.lastX = mX;
			this.lastY = mY;
		}
		if (inHitbox(mX, mY) && this.checked.equals(false)) {
			this.ifPressed();
			this.checked = true;
		}
		if (!inHitbox(mX, mY)) {
			this.checked = false;
			this.toolChanged = false;
		}
	}

	public boolean inHitbox(int mX, int mY) {
		if ((mX > x && mX < x + width)) {
			if ((mY > y && mY < y + height)) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public void renderButtonOutline(Graphics2D g2) {
		g2.setColor(colortable.STROKE);
		g2.setStroke(new BasicStroke(3));
		g2.drawRect(x, y, width, height);
	}

	public String getType() {
		return type;
	}

}
