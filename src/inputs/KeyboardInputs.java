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
        addKeyListener(KeyEvent.VK_DELETE, () -> {
        	m.oh.objectDeleteTriggered();
        });
        
        // CONTROL_KEY
        addKeyListener(KeyEvent.VK_BACK_SPACE, () -> {
        	m.oh.objectDeleteTriggered();
        });
        
        // CONTROL_KEY
        addKeyListener(KeyEvent.VK_CONTROL, () -> {
        	//action here
        });

        // ESCAPE_KEY
        addKeyListener(KeyEvent.VK_ESCAPE, () -> {
        	//action here
        });

        // SHIFT_KEY
        addKeyListener(KeyEvent.VK_SHIFT, () -> {
        	//action here
        });

        // SPACE_KEY
        addKeyListener(KeyEvent.VK_SPACE, () -> {
        	//action here
        });
        
    	// ALT_KEY
        addKeyListener(KeyEvent.VK_ALT, () -> {
        	//action here
        });
        
        // ARROW_KEYS
        addKeyListener(KeyEvent.VK_LEFT, () -> {
        	//action here
        });
        addKeyListener(KeyEvent.VK_RIGHT, () -> {
        	//action here
        });
        addKeyListener(KeyEvent.VK_UP, () -> {
        	//action here
        });
        addKeyListener(KeyEvent.VK_DOWN, () -> {
        	//action here
        });
        
        //ALPHABET
        for (char a = 'a'; a <= 'z'; a++) {
        	final char currentChar = a;
        	int keyCode = KeyEvent.getExtendedKeyCodeForChar(a);
        	addKeyListener(keyCode, () -> {
        		//action here
        	});
        }

	}
	
	private void addKeyListener(int keyCode, Runnable action) {
        addKeyListener(keyCode, new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == keyCode) {
                    action.run();
                }
            }

            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {}
        });
    }

}
