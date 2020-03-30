package action;

import java.awt.event.InputEvent;
import java.io.Serializable;

import core.AbstractAction;
import core.Action;
import core.SundayRobot;

public class PressMouse extends AbstractAction implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final String xPosition = "xPosition", yPosition = "yPosition";

	@Override
	public void execute(SundayRobot robot, Action action) {
		int x = (int) action.getParamsMap().get(xPosition);
		int y = (int) action.getParamsMap().get(yPosition);
			
		robot.getRobot().mouseMove(x, y);
		robot.getRobot().mousePress(InputEvent.BUTTON1_MASK);
	}
	
}