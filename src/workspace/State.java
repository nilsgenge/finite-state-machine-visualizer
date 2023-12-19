package workspace;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import utilz.colortable;

public class State {

	private String name;
	private Boolean isStartState = false;
	private Boolean isEndState = false;
	private Boolean isSelected = false;
	private int xPos;
	private int yPos;
	private int radius;
	private Boolean movePending = false;

	public State(String n, int x, int y, int r) {
		name = n;
		xPos = x;
		yPos = y;
		radius = r;
	}

	public void render(Graphics2D g2) {
		g2.setStroke(new BasicStroke(3));
		int r = getRadius();
		g2.setColor(colortable.BG_MAIN);
		g2.fillOval(getX() - r, getY() - r, r * 2, r * 2);
		if (isSelected() || isMoving()) {
			g2.setColor(colortable.HIGHLIGHT);
		} else {
			g2.setColor(colortable.STROKE);
		}
		g2.drawOval(getX() - r, getY() - r, r * 2, r * 2);

		if (isStartState) {
			g2.setFont(new Font("Monospaced", Font.PLAIN, 50));
			g2.setColor(colortable.TEXT);
			g2.drawString("S", xPos - 15, yPos + 15);
		} else if (isEndState) {
			if (isSelected() || isMoving()) {
				g2.setColor(colortable.HIGHLIGHT);
			} else {
				g2.setColor(colortable.STROKE);
			}
			g2.drawOval(getX() - (int)(r * 0.8), getY() - (int)(r * 0.8), (int) (r * 2 * 0.8), (int) (r * 2 * 0.8));
		}
	}
	
	public void update() {
		
	}

	public void checkSelected(int mx, int my) { // MouseX, MouseY as Input
		double distClickToPoint = Math.sqrt(Math.pow(mx - xPos, 2) + Math.pow(my - yPos, 2));
		double distCircleToPoint = Math.sqrt(2 * Math.pow(radius, 2));

		if (distClickToPoint < distCircleToPoint) {
			isSelected = true;
		} else {
			isSelected = false;
		}
	}

	public void setX(int x) {
		xPos = x;
	}

	public int getX() {
		return xPos;
	}

	public void addX(int x) {
		this.xPos += x;
	}

	public void setY(int y) {
		yPos = y;
	}

	public int getY() {
		return yPos;
	}

	public void addY(int y) {
		this.yPos += y;
	}

	public void setRadius(int r) {
		radius = r;
	}

	public int getRadius() {
		return radius;
	}

	public void setSelected(boolean s) {
		isSelected = s;
	}

	public Boolean isSelected() {
		return isSelected;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public Boolean isMoving() {
		return movePending;
	}

	public void setMoving(boolean m) {
		movePending = m;
	}
	
	public Boolean isStartState() {
		return isStartState;
	}

	public void setStartState(boolean isStartState) {
		this.isStartState = isStartState;
	}

	public Boolean isEndState() {
		return isEndState;
	}

	public void setEndState(Boolean isEndState) {
		this.isEndState = isEndState;
	}

}
