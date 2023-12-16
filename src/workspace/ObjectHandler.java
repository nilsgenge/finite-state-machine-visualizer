package workspace;

import java.awt.Graphics2D;
import java.util.*;
import inputs.MouseInputs;
import utilz.tools;

public class ObjectHandler {

	MouseInputs m;
	Transition u;
	State z;
	ToolHandler th;

	LinkedList<Transition> transitions;
	LinkedList<State> states;
	
	private int middleMouseLastX;
	private int middleMouseLastY;
	private int leftMouseLastX;
	private int leftMouseLastY;

	private State transitionFirstState = null;
	private Boolean transitionInProgress = false;
	private Boolean somethingSelected = false;

	public ObjectHandler(MouseInputs m, ToolHandler th) {
		this.m = m;
		this.th = th;
		transitions = new LinkedList<Transition>();
		states = new LinkedList<State>();
	}

	public void renderObjects(Graphics2D g2) {
		renderTransitions(g2);
		renderStates(g2);
	}

	public void renderStates(Graphics2D g2) {
		if (states.size() > 0) {
			for (int i = 0; i < states.size(); i++) {
				states.get(i).render(g2);
			}
		}
	}

	public void renderTransitions(Graphics2D g2) {
		if (transitions.size() > 0) {
			for (int i = 0; i < transitions.size(); i++) {
				transitions.get(i).render(g2);
			}
		}
	}
	
	public void updateObjects() {
		checkIfSelected();
		screenShifter();
		stateShifter();
		updateTransitions();
		transitionShifter();
		
		resetMousePos();
	}

	public void updateTransitions() {
		if (transitions.size() > 0) {
			for (int i = 0; i < transitions.size(); i++) {
				if (!somethingSelected) {
					transitions.get(i).update();
					transitions.get(i).checkSelected(m.getM1X(), m.getM1Y());
					if (transitions.get(i).isSelected() || transitions.get(i).isMoving()) {
						somethingSelected = true;
						break;
					} 
				}
			}
		}
		somethingSelected = false;
	}
	
	public void checkIfSelected() {
		if (th.getCurrentTool() != null) {
			if (!th.getCurrentTool().equals(tools.STATE)) {
				if (states.size() > 0) {
					for (int i = 0; i < states.size(); i++) {
						if (!somethingSelected) {
							states.get(i).checkSelected(m.getM1X(), m.getM1Y());
							if (states.get(i).isSelected() || states.get(i).isMoving()) {
								somethingSelected = true;
								break;
							} 
						}
					}
				}
			}
		}
		somethingSelected = false;
	}

	public void objectDeleteTriggered() {	
		for (int a = 0; a < states.size(); a++) {
			if (states.get(a).isSelected()) {
				for (int b = 0; b < transitions.size(); b++) {
					if (transitions.get(b).getVorgaenger() == states.get(a)
							|| transitions.get(b).getNachfolger() == states.get(a)) {
						transitions.remove(b);
					}
				}
				states.remove(a);
			}
		}
		for (int c = 0; c < transitions.size(); c++) {
			if (transitions.get(c).isSelected()) {
				transitions.remove(c);
			}
		}
	}

	public void setNewStartState() {
		if (states.size() > 0 && anyStateSelected()) {
			for (int i = 0; i < states.size(); i++) {
				if(states.get(i).isStartState() && !states.get(i).isSelected()) {
					states.get(i).setStartState(false);
				} else if (states.get(i).isStartState() && states.get(i).isSelected()) {
					states.get(i).setStartState(false);
					deselectAllStates();
				} else if (!states.get(i).isStartState() && states.get(i).isSelected()) {
					states.get(i).setStartState(true);
					deselectAllStates();
				}
			}
		}
	}

	public void setNewTransition() {
		checkIfSelected();
		if (states.size() > 0) {
			transitionInProgress = (transitionFirstState != null);
			for (int i = 0; i < states.size(); i++) {
				if (states.get(i).isSelected()) {
					if (transitionInProgress && transitionFirstState != null) {
						// check if transition already exists
						boolean transitionExists = false;
						if (transitions.size() > 0) {
							for (int a = 0; a < transitions.size(); a++) {
								if ((transitions.get(a).getVorgaenger() == transitionFirstState
										&& transitions.get(a).getNachfolger() == states.get(i))) {
									transitionExists = true;
									break;
								}
							}
						}
						// if no transition found, add transition
						if (!transitionExists && transitionFirstState != states.get(i)) {
							transitions.add(new Transition(transitionFirstState, states.get(i), ""));

							// Reset states
							deselectAllStates();
							this.transitionFirstState = null;
							transitionInProgress = false;
							break;
						}
					} else if (!transitionInProgress && transitionFirstState == null) {
						transitionFirstState = states.get(i);
						transitionInProgress = true;
						break;
					}
				}
			}
		}
	}

	public void transitionShifter() {
		if (th.getCurrentTool() != null) {
			if (th.getCurrentTool() == tools.EMPTY) {
				if (transitions.size() > 0) {
					for (int i = 0; i < transitions.size(); i++) {
						if ((transitions.get(i).isSelected() || transitions.get(i).isMoving()) && !m.m3Pressed()) {
							if (m.m1Pressed()) {
								transitions.get(i).setMoving(true);
								transitions.get(i).setOffset(leftMouseLastX, leftMouseLastY, m.getX(), m.getY());
							} else if (!m.m1Pressed()) {
								transitions.get(i).setMoving(false);
								transitions.get(i).setSelected(true);
							}
						}
					}
				}
			}
		}
	}

	public void setNewState(int x, int y) {
		String name = "State" + states.size();
		int radius = 35;
		State newState;

		boolean stateExists = states.stream().anyMatch(state -> state.getX() == x && state.getY() == y);

		if (!stateExists) {
			newState = new State(name, x, y, radius);
			states.add(newState);
		}
	}

	public void screenShifter() {
		if (m.m3Pressed()) {
			shiftAllStates(m.getX() - middleMouseLastX, 0);
			shiftAllStates(0, m.getY() - middleMouseLastY);
			deselectAllStates();
		}
		middleMouseLastX = m.getX();
		middleMouseLastY = m.getY();
	}

	public void stateShifter() {
		if (th.getCurrentTool() != null && states.size() > 0) {
			if (th.getCurrentTool().equals(tools.EMPTY)) {
				for (int i = 0; i < states.size(); i++) {
					if ((states.get(i).isSelected() || states.get(i).isMoving()) && !m.m3Pressed()) {
						if (m.m1Pressed()) {
							states.get(i).setMoving(true);
							states.get(i).addX((int) (m.getX() - leftMouseLastX));
							states.get(i).addY((int) (m.getY() - leftMouseLastY));
						} else if (!m.m1Pressed()) {
							states.get(i).setMoving(false);
							states.get(i).setSelected(true);
						}
					}
				}
			}
		}
	}

	public void shiftAllStates(int x, int y) {
		float movingMultiplier = 1f;
		if (states.size() > 0) {
			for (int i = 0; i < states.size(); i++) {
				states.get(i).addX((int) (x * movingMultiplier));
				states.get(i).addY((int) (y * movingMultiplier));
			}
		}
	}
	
	public Boolean anyStateSelected() {
		if (states.size() > 0) {
			for (int i = 0; i < states.size(); i++) {
				if(states.get(i).isSelected()) {
					return true;
				}
			}
		}
		return false;
	}
	
	public void resetMousePos() {
		leftMouseLastX = m.getX();
		leftMouseLastY = m.getY();
	}
	
	public void deselectAllStates() {
		if (this.states != null) {
			for (int i = 0; i < states.size(); i++) {
				states.get(i).setSelected(false);
			}
		}
		m.resetLeftMousePosition();
		this.transitionFirstState = null;
	}
	
	public State getSelectedState() {
		for (int c = 0; c < states.size(); c++) {
			if (states.get(c).isSelected()) {
				return states.get(c);
			}
		}
		return null;
	}
	
	public Transition getSelectedTransition() {
		for (int c = 0; c < transitions.size(); c++) {
			if (transitions.get(c).isSelected()) {
				return transitions.get(c);
			}
		}
		return null;
	}

}
