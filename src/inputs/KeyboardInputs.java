package inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;
import main.Main;

public class KeyboardInputs implements KeyListener {

	private Main m;

	private Map<Integer, KeyListener> keyBindings;

	public KeyboardInputs(Main main) {
		this.m = main;

		keyBindings = new HashMap<>();
		initializeMap();
	}

	public void addKeyListener(int keyCode, KeyListener listener) {
		keyBindings.put(keyCode, listener);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if (keyBindings.containsKey(keyCode)) {
			keyBindings.get(keyCode).keyPressed(e);
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	private void initializeMap() {
		// DELETE_KEY
		addKeyListener(KeyEvent.VK_DELETE, new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				m.oh.objectDeleteTriggered();
			}

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}
		});
		// BACKSPACE_KEY
		addKeyListener(KeyEvent.VK_BACK_SPACE, new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				m.oh.objectDeleteTriggered();
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyTyped(KeyEvent e) {
			}
		});

	}

}
