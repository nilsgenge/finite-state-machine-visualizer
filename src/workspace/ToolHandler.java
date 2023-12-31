package workspace;

import inputs.MouseInputs;
import main.Main;
import utilz.tools;

public class ToolHandler {

	protected Main main;
	private ObjectHandler oh;
	private MouseInputs m;

	private String currentTool;

	private int leftMouseLastX;
	private int leftMouseLastY;
	private int rightMouseLastX;
	private int rightMouseLastY;

	public ToolHandler(Main main) {
		this.main = main;
	}

	public void initialize() {
		this.m = main.m;
		this.oh = main.oh;
		currentTool = tools.EMPTY;
	}

	public void updateTool() {
		checkDeselect();
		checkNewState();
		checkNewTransition();
		checkNewStartState();
		checkNewEndState();
	}

	public void addStringToTransition(String s) {
		if(oh.getSelectedTransition() != null) {
			switch(s) {
				case("DELETE"):
					String oldString = oh.getSelectedTransition().getText();
					if(oldString.length() > 0) {
						String newString = oldString.substring(0, oldString.length() - 1);
						oh.getSelectedTransition().setText(newString);
					}
					break;
				default:
					oh.getSelectedTransition().addString(s);
					break;
			} 			
		}
	}
	
	public void checkNewStartState() {
		if (currentTool.equals(tools.START)) {
			if (isNewInput("left", m.getM1X(), m.getM1Y()) && isInWorkspace(m.getM1X(), m.getM1Y())) {
				oh.setNewStartState();
			}
		}
	}
	
	public void checkNewEndState() {
		if (currentTool.equals(tools.END)) {
			if (isNewInput("left", m.getM1X(), m.getM1Y()) && isInWorkspace(m.getM1X(), m.getM1Y())) {
				oh.setNewEndState();
			}
		}
	}
	
	public void checkNewTransition() {
		if (currentTool.equals(tools.TRANSITION)) {
			if (isNewInput("left", m.getM1X(), m.getM1Y()) && isInWorkspace(m.getM1X(), m.getM1Y())) {
				oh.setNewTransition();
			}
		}
	}

	public void checkDeselect() {
		if (isNewInput("right", m.getM2X(), m.getM2Y())) {
			if (m.m2Pressed() && isInWorkspace(m.getM2X(), m.getM2Y())) {
				if (currentTool != tools.EMPTY) {
					this.setCurrentTool(tools.EMPTY);
				}
				oh.deselectAllStates();
				rightMouseLastX = m.getM2X();
				rightMouseLastY = m.getM2Y();
			}
		}
	}

	public void checkNewState() {
		if (currentTool.equals(tools.STATE)) {
			if (isNewInput("left", m.getM1X(), m.getM1Y()) && isInWorkspace(m.getM1X(), m.getM1Y())) {
				oh.setNewState(m.getM1X(), m.getM1Y());
			}
		}
	}

	public Boolean isNewInput(String k, int mX, int mY) {
		switch (k) {
		case ("left"): {
			if (mX != leftMouseLastX || mY != leftMouseLastY) {
				return true;
			} else {
				return false;
			}
		}
		case ("right"): {
			if (mX != rightMouseLastX || mY != rightMouseLastY) {
				return true;
			} else {
				return false;
			}
		}
		case ("middle"): {
			return false;
		}
		default:
			return false;
		}

	}
	
	public boolean isInWorkspace(int x, int y) {
		if (x >= 350 && y >= 100) {
			return true;
		} else {
			return false;
		}
	}

	public String getCurrentTool() {
		return currentTool;
	}

	public void setCurrentTool(String tool) {
		currentTool = tool;
	}

}
