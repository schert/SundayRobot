package action;

import java.util.Calendar;

import core.Action;
import core.ActionInterface;
import core.SundayRobot;

public class RandomMoveMouse implements ActionInterface {

	private static final int xSize = 50;
	private static final int ySize = 50;
	private int x = 800;
	private int y = 600;
	private boolean continua = true;
	
	@Override
	public void execute(SundayRobot robot, Action action) {
		
		int sleep = (int) action.getParamsMap().get("sleep");
		Calendar endTime = (Calendar) action.getParamsMap().get("endTime");
		
		while(endTime.after(Calendar.getInstance()) && continua) {
			x += Math.round((float) ((Math.random()-0.5f) * xSize));
			y += Math.round((float) ((Math.random()-0.5f) * ySize));
			
			try {
				Thread.sleep(sleep);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			robot.getRobot().mouseMove(x, y);
		}
	}
}
