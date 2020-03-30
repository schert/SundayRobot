package action;

import java.io.Serializable;

import core.AbstractAction;
import core.Action;
import core.SundayRobot;

public class WheelMouse extends AbstractAction implements Serializable {
	public static String wheel = "wheel";
	private static final long serialVersionUID = 1L;
	
	@Override
	public void execute(SundayRobot robot, Action action) {
		int wheelInt = (int) action.getParamsMap().get(wheel);
		robot.getRobot().mouseWheel(wheelInt);
	}
	
}
