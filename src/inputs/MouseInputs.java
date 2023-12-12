package inputs;

import java.awt.MouseInfo;
import javax.swing.SwingUtilities;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseInputs implements MouseListener {

	private Boolean m1Pressed = false; // left mouse button
	private Boolean m2Pressed = false; // right mouse button
	private Boolean m3Pressed = false; // middle mouse button
	private int mX; // mousepos on entire screen
	private int mY;
	private int m1X; // mousepos on last left click
	private int m1Y;
	private int m2X; // mousepos on last right click
	private int m2Y;
	private int m3X; // mousepos on last middle click
	private int m3Y;

	public void updateMousePos() {
		mX = (int) MouseInfo.getPointerInfo().getLocation().getX();
		mY = (int) MouseInfo.getPointerInfo().getLocation().getY();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (SwingUtilities.isLeftMouseButton(e)) {
			m1Pressed = true;
			m1X = e.getX();
			m1Y = e.getY() - 30;
		}
		if (SwingUtilities.isRightMouseButton(e)) {
			m2Pressed = true;
			m2X = e.getX();
			m2Y = e.getY() - 30;
		}
		if (SwingUtilities.isMiddleMouseButton(e)) {
			m3Pressed = true;
			m3X = e.getX();
			m3Y = e.getY() - 30;
		}

	}

	@Override
	public void mouseReleased(MouseEvent e) {

		if (SwingUtilities.isLeftMouseButton(e)) {
			m1Pressed = false;
		}
		if (SwingUtilities.isRightMouseButton(e)) {
			m2Pressed = false;
		}
		if (SwingUtilities.isMiddleMouseButton(e)) {
			m3Pressed = false;
		}
	}

	public void resetLeftMousePosition() {
		this.m1X = 0;
		this.m1Y = 0;
	}

	public int getX() {
		return mX;
	}

	public int getY() {
		return mY;
	}

	public int getM1X() {
		return m1X;
	}

	public int getM1Y() {
		return m1Y;
	}

	public int getM2X() {
		return m2X;
	}

	public int getM2Y() {
		return m2Y;
	}

	public int getM3X() {
		return m3X;
	}

	public int getM3Y() {
		return m3Y;
	}

	public boolean m1Pressed() {
		return m1Pressed;
	}

	public boolean m2Pressed() {
		return m2Pressed;
	}

	public boolean m3Pressed() {
		return m3Pressed;
	}
}
