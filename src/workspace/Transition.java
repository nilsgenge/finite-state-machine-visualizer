package workspace;

import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import utilz.Vector2D;
import utilz.colortable;

public class Transition {

	private State vorgaenger;
	private State nachfolger;
	private String text;

	private int hitboxSize = 40;
	private boolean isSelected = false;
	private boolean isMoving = false;
	private int transformPointX;
	private int transformPointY;
	private int transformOffset = 0;
	private double angle;
	double distanceBetweenStates;
	int textRenderRange = 200;
	int transformPointRenderRenderRange = 200;
	int arrowRenderRange = 100;

	public Transition(State vorgaenger, State nachfolger, String text) {

		this.vorgaenger = vorgaenger;
		this.nachfolger = nachfolger;
		this.text = text;
	}

	public void update() {
		int deltaX = nachfolger.getX() - transformPointX;
		int deltaY = nachfolger.getY() - transformPointY;
		angle = Math.atan2(deltaY, deltaX);
		
		distanceBetweenStates = Math.sqrt(Math.pow(vorgaenger.getX() - nachfolger.getX(), 2)+ Math.pow(vorgaenger.getY() - nachfolger.getY(), 2));
		
		calcTransformPoint();
		checkTransformOffsetSize(distanceBetweenStates);
	}

	public void render(Graphics2D g2) {
		//LINE
		g2.setStroke(new BasicStroke(2));
		g2.setColor(colortable.STROKE);
		g2.drawLine(getX1(), getY1(), transformPointX, transformPointY);
		g2.drawLine(transformPointX, transformPointY, getX2(), getY2());
		
		if(distanceBetweenStates > arrowRenderRange) {
		drawArrow(g2);		
		}
		if(distanceBetweenStates > textRenderRange) {
			renderText(g2);		
		}

		// HITBOX
		if(distanceBetweenStates > transformPointRenderRenderRange) {
		g2.setColor(colortable.BG_MAIN);
		g2.fillOval(transformPointX - hitboxSize / 4, transformPointY - hitboxSize / 4, hitboxSize/2, hitboxSize/2);
		if (isSelected) {
			g2.setColor(colortable.HIGHLIGHT);
		} else {
			g2.setColor(colortable.STROKE);
		}
//		g2.setStroke(new BasicStroke(1));
		g2.drawOval(transformPointX - hitboxSize / 4, transformPointY - hitboxSize / 4, hitboxSize/2, hitboxSize/2);
		}
	}

	public void calcTransformPoint() {
		Vector2D A = new Vector2D(vorgaenger.getX(), vorgaenger.getY());
		Vector2D B = new Vector2D(nachfolger.getX(), nachfolger.getY());

		Vector2D M = Vector2D.findMidpoint(A, B);

		Vector2D O = Vector2D.findOrthogonalPoint(M, A, B, transformOffset);

		transformPointX = (int) O.getX();
		transformPointY = (int) O.getY();
	}

	public void drawArrow(Graphics2D g2) {
		int xPoints[] = new int[3];
		int yPoints[] = new int[3];

		g2.setColor(colortable.STROKE);
		// Contactpoint on Circle
		int pointerX = nachfolger.getX() - (int) (nachfolger.getRadius() * Math.cos(angle));
		int pointerY = nachfolger.getY() - (int) (nachfolger.getRadius() * Math.sin(angle));
		xPoints[0] = pointerX;
		yPoints[0] = pointerY;

		int size = 25;
		int width = 30;
		int firstX = pointerX - (int) (size * Math.cos(angle + Math.toRadians(width)));
		int firstY = pointerY - (int) (size * Math.sin(angle + Math.toRadians(width)));
		xPoints[1] = firstX;
		yPoints[1] = firstY;
		int secondX = pointerX - (int) (size * Math.cos(angle - Math.toRadians(width)));
		int secondY = pointerY - (int) (size * Math.sin(angle - Math.toRadians(width)));
		xPoints[2] = secondX;
		yPoints[2] = secondY;
		g2.fillPolygon(xPoints, yPoints, 3);
	}
	
	public void checkSelected(int mX, int mY) {
		double distClickToPoint = Math.sqrt(Math.pow(mX - transformPointX, 2) + Math.pow(mY - transformPointY, 2));
		double distCircleToPoint = Math.sqrt(2 * Math.pow(hitboxSize / 2, 2));
		if (distClickToPoint < distCircleToPoint) {
			isSelected = true;
		} else {
			isSelected = false;
		}
	}
	
	public void checkTransformOffsetSize(double distanceBetweenStates) {
		if (distanceBetweenStates < transformPointRenderRenderRange) {
			transformOffset = 0;
			return;
		}
		if (transformOffset > distanceBetweenStates) {
			transformOffset = (int) distanceBetweenStates;
		} else if (transformOffset < -distanceBetweenStates) {
			transformOffset = -(int) distanceBetweenStates;
		}
	}

	public void renderText(Graphics2D g2) {
		g2.setFont(new Font("Monospaced", Font.PLAIN, 15));
		g2.setColor(colortable.TEXT);

		FontMetrics fontMetrics = g2.getFontMetrics();
		int stringWidth = fontMetrics.stringWidth(text);
		
		double lineAngle = Math.atan2(nachfolger.getY() - vorgaenger.getY(), nachfolger.getX() - vorgaenger.getX());
		double PI = Math.PI;

		int textX = 0;
		int textY = 0;
		int distToTransformPoint = 15;

		// Split in angles and adjust transformOffset accordingly
	// LEFT
		if (lineAngle > -PI / 8 && lineAngle < PI / 8) {
			textX = transformPointX - stringWidth / 2;
			textY = transformPointY - distToTransformPoint;
	// LEFT TOP
		} else if (lineAngle > PI / 8 && lineAngle < 3 * PI / 8) {
			textX = transformPointX + distToTransformPoint;
			textY = transformPointY - distToTransformPoint + 2;
	// TOP
		} else if (lineAngle > 3 * PI / 8 && lineAngle < 5 * PI / 8) {
			textX = transformPointX + distToTransformPoint;
			textY = transformPointY + 2;
	// RIGHT TOP
		} else if (lineAngle > 5 * PI / 8 && lineAngle < 7 * PI / 8) {
			textX = transformPointX + distToTransformPoint;
			textY = transformPointY + distToTransformPoint + 2;
	// RIGHT
		} else if (lineAngle > 7 * PI / 8 || lineAngle < -7 * PI / 8) {
			textX = transformPointX - stringWidth / 2;
			textY = transformPointY - distToTransformPoint;
	// RIGHT BOTTOM
		} else if (lineAngle > -7 * PI / 8 && lineAngle < -5 * PI / 8) {
			textX = transformPointX + distToTransformPoint;
			textY = transformPointY - distToTransformPoint + 2;
	// BOTTOM
		} else if (lineAngle > -5 * PI / 8 && lineAngle < -3 * PI / 8) {
			textX = transformPointX + distToTransformPoint;
			textY = transformPointY + 2;
	// LEFT BOTTOM
		} else if (lineAngle > -3 * PI / 8 && lineAngle < -PI / 8) {
			textX = transformPointX + distToTransformPoint;
			textY = transformPointY + distToTransformPoint + 2;
		}
		g2.drawString(text, textX, textY);
	}

	public void setOffset(int x1, int y1, int x2, int y2) {
		if (distanceBetweenStates < transformPointRenderRenderRange) {
			transformOffset = 0;
			return;
		}
		double deltaX = x2 - x1;
		double deltaY = y2 - y1;

		double lineAngle = Math.atan2(nachfolger.getY() - vorgaenger.getY(), nachfolger.getX() - vorgaenger.getX());
		double PI = Math.PI;
		int mouseDistance = (int) Math.sqrt(deltaX * deltaX + deltaY * deltaY);
		// Split in angles and adjust transformOffset accordingly
		// LEFT
		if (lineAngle > -PI / 8 && lineAngle < PI / 8) {
			if (deltaY > 0) {
				transformOffset -= mouseDistance;
			} else if (deltaY < 0) {
				transformOffset += mouseDistance;
			}
			// LEFT TOP
		} else if (lineAngle > PI / 8 && lineAngle < 3 * PI / 8) {
			if (deltaX > 0 && deltaY < 0) {
				transformOffset += mouseDistance;
			} else if (deltaX < 0 && deltaY > 0) {
				transformOffset -= mouseDistance;
			}
			// TOP
		} else if (lineAngle > 3 * PI / 8 && lineAngle < 5 * PI / 8) {
			if (deltaX > 0) {
				transformOffset += mouseDistance;
			} else if (deltaX < 0) {
				transformOffset -= mouseDistance;
			}
			// RIGHT TOP
		} else if (lineAngle > 5 * PI / 8 && lineAngle < 7 * PI / 8) {
			if (deltaX > 0 && deltaY > 0) {
				transformOffset += mouseDistance;
			} else if (deltaX < 0 && deltaY < 0) {
				transformOffset -= mouseDistance;
			}
			// RIGHT
		} else if (lineAngle > 7 * PI / 8 || lineAngle < -7 * PI / 8) {
			if (deltaY > 0) {
				transformOffset += mouseDistance;
			} else if (deltaY < 0) {
				transformOffset -= mouseDistance;
			}
			// RIGHT BOTTOM
		} else if (lineAngle > -7 * PI / 8 && lineAngle < -5 * PI / 8) {
			if (deltaX > 0 && deltaY < 0) {
				transformOffset -= mouseDistance;
			} else if (deltaX < 0 && deltaY > 0) {
				transformOffset += mouseDistance;
			}
			// BOTTOM
		} else if (lineAngle > -5 * PI / 8 && lineAngle < -3 * PI / 8) {
			if (deltaX > 0) {
				transformOffset -= mouseDistance;
			} else if (deltaX < 0) {
				transformOffset += mouseDistance;
			}
			// LEFT BOTTOM
		} else if (lineAngle > -3 * PI / 8 && lineAngle < -PI / 8) {
			if (deltaX < 0 && deltaY < 0) {
				transformOffset += mouseDistance;
			} else if (deltaX > 0 && deltaY > 0) {
				transformOffset -= mouseDistance;
			}
		}
		checkTransformOffsetSize(distanceBetweenStates);
	}

	public void setSelected(Boolean s) {
		this.isSelected = s;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public boolean isMoving() {
		return isMoving;
	}

	public void setMoving(boolean m) {
		isMoving = m;
	}

	public int getX1() {
		return vorgaenger.getX();
	}

	public int getY1() {
		return vorgaenger.getY();
	}

	public int getX2() {
		return nachfolger.getX();
	}

	public int getY2() {
		return nachfolger.getY();
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	public void addString(String newString) {
		this.text += newString;
	}

	public State getNachfolger() {
		return nachfolger;
	}

	public State getVorgaenger() {
		return vorgaenger;
	}
}
