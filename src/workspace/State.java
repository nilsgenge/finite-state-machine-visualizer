package workspace;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import utilz.colortable;

public class State {

	private String name;
	private boolean isStart = false;
	private boolean isSelected = false;
	private int xPos;
	private int yPos;
	private int radius;
	private boolean movePending = false;

	public State(String n, int x, int y, int r) {
		name = n;
		xPos = x;
		yPos = y;
		radius = r;
	}

	public void render(Graphics2D g2) {
		g2.setStroke(new BasicStroke(3));
		int r = getRadius();
		int r2 = getRadius() - 3;
		if (isSelected() || isMoving()) {
			g2.setColor(colortable.HIGHLIGHT); // selected
		} else {
			g2.setColor(colortable.STROKE); // not selected
		}
		g2.fillOval(getX() - r, getY() - r, r * 2, r * 2);
		g2.setColor(colortable.BG_MAIN); // inner circle, bg
		g2.fillOval(getX() - r2, getY() - r2, r2 * 2, r2 * 2);
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

	public void setStart(Boolean b) {
		isStart = b;
	}

	public Boolean isStart() {
		return isStart;
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

	public boolean isSelected(int mx, int my) {
		checkSelected(mx, my);
		return isSelected;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public boolean isMoving() {
		return movePending;
	}

	public void setMoving(boolean m) {
		movePending = m;
	}

}
