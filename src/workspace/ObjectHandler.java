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

	// for selection & shifting
	private int middleMouseLastX;
	private int middleMouseLastY;
	private int leftMouseLastX;
	private int leftMouseLastY;

	private State transitionFirstState = null;
	private boolean transitionInProgress = false;

	public ObjectHandler(MouseInputs m, ToolHandler th) {
		this.m = m;
		this.th = th;
		transitions = new LinkedList<Transition>();
		states = new LinkedList<State>();
	}

	public void updateObjects() {
		checkIfSelected();
		screenShifter();
		zustandShifter();
		m.updateMousePos();
		updateTransitions();
		 transitionShifter();
	}

	// TEMPORARY
	public void testobjects() {
		State z1 = new State("q1", 100, 100, 35);
		State z2 = new State("q2", 500, 500, 35);
//		State z3 = new State("q3", 200, 350, 35);
				transitions.add(new Transition(z2, z1, "", ""));
//				transitions.add(new Transition(z2, z3, "M", "T"));
		states.add(z1);
		states.add(z2);
//		states.add(z3);
		shiftZustaendePos(700, 150);
	}

	public void checkIfSelected() {
			if (th.getCurrentTool() != null) {
				if (!th.getCurrentTool().equals(tools.STATE)) {
					if (states.size() > 0) {
						for (int i = 0; i < states.size(); i++) {
							states.get(i).isSelected(m.getM1X(), m.getM1Y());
						}
					}
				}
			} 
	}

	public void renderObjects(Graphics2D g2) {
		renderUebergaenge(g2);
		renderZustaende(g2);
	}

	public void renderZustaende(Graphics2D g2) {
		if (states.size() > 0) {
			for (int i = 0; i < states.size(); i++) {
				states.get(i).render(g2);
			}
		}
	}

	public void renderUebergaenge(Graphics2D g2) {
		if (transitions.size() > 0) {
			for (int i = 0; i < transitions.size(); i++) {
				transitions.get(i).render(g2);
			}
		}
	}

	public void updateTransitions() {
		if (transitions.size() > 0) {
			for (int i = 0; i < transitions.size(); i++) {
				transitions.get(i).update();
				transitions.get(i).checkSelected(m.getM1X(), m.getM1Y());
			}
		}
	}
	
	public Boolean anyObjectSelected() {
		if(states.size() > 0) {
			for(int a = 0; a < states.size(); a++) {
				if(states.get(a).isSelected()) {
					return true;
				}
			}
		}
		if(transitions.size() > 0) {
			for(int b = 0; b < transitions.size(); b++) {
				if(transitions.get(b).isSelected()) {
					return true;
				}
			}
		}
		return false;
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
							transitions.add(new Transition(transitionFirstState, states.get(i), "", ""));

							// Reset states
							deselectAllZustaende();
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
								transitions.get(i).setOffset(leftMouseLastX,leftMouseLastY, m.getX(), m.getY());
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
			shiftZustaendePos(m.getX() - middleMouseLastX, 0);
			shiftZustaendePos(0, m.getY() - middleMouseLastY);
			deselectAllZustaende();
		}
		middleMouseLastX = m.getX();
		middleMouseLastY = m.getY();
	}

	public void zustandShifter() {
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
				leftMouseLastX = m.getX();
				leftMouseLastY = m.getY();
			}
		}
	}

	public void shiftZustaendePos(int x, int y) {
		float movingMultiplier = 1f;
		if (states.size() > 0) {
			for (int i = 0; i < states.size(); i++) {
				states.get(i).addX((int) (x * movingMultiplier));
				states.get(i).addY((int) (y * movingMultiplier));
			}
		}
	}

	public void deselectAllZustaende() {
		if (this.states != null) {
			for (int i = 0; i < states.size(); i++) {
				states.get(i).setSelected(false);
			}
		}
		m.resetLeftMousePosition();
		this.transitionFirstState = null;
	}

}
