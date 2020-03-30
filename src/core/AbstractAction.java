package core;

import java.awt.Robot;

public abstract class AbstractAction implements ActionInterface {
	public void execute(Robot robot, Action action) {};
	
	public String getActionName() {
		return this.getClass().getSimpleName();
	}
}
