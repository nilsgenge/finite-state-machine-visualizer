package main;

import gui.*;
import inputs.*;
import workspace.*;

public class Main {
	public Screen s;
	public Renderer r;
	public ObjectHandler oh;
	public MouseInputs m;
	public KeyboardInputs k;
	public ToolHandler th;
	public GuiHandler g;

	public Main() {

		th = new ToolHandler(this);
		m = new MouseInputs();
		k = new KeyboardInputs(this);
		r = new Renderer(this);
		s = new Screen(r, this);
		oh = new ObjectHandler(m, th);
		g = new GuiHandler(this, m, th);

//		oh.testobjects();
		th.initialize();
		r.render();
	}

}
