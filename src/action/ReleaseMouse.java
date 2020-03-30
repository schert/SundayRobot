package action;

import java.awt.event.InputEvent;
import java.io.Serializable;

import core.AbstractAction;
import core.Action;
import core.SundayRobot;

public class ReleaseMouse extends AbstractAction implements Serializable {

	public static final String xPosition = "xPosition", yPosition = "yPosition";
	private static final long serialVersionUID = 1L;

	@Override
	public void execute(SundayRobot robot, Action action) {
		int x = (int) action.getParamsMap().get(xPosition);
		int y = (int) action.getParamsMap().get(yPosition);
			
		robot.getRobot().mouseMove(x, y);
		robot.getRobot().mouseRelease(InputEvent.BUTTON1_MASK);
	}
	
}
