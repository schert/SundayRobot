package action;

import java.io.Serializable;

import core.AbstractAction;
import core.Action;
import core.SundayRobot;

public class MoveMouse extends AbstractAction implements Serializable {
	public static final String xPosition = "xPosition", yPosition = "yPosition";
	private static final long serialVersionUID = 1L;

	@Override
	public void execute(SundayRobot robot, Action action) {
		double x = (double) action.getParamsMap().get(xPosition);
		double y = (double) action.getParamsMap().get(yPosition);
			
		robot.getRobot().mouseMove((int) x, (int) y);
	}
}
