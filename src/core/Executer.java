package core;

import core.ui.MainUi;

public class Executer implements Runnable{
	
	private SundayRobot robot;
	
	public Executer(SundayRobot robot) {
		this.robot = robot;
	}

	@Override
	public void run() {
		long current, start = System.currentTimeMillis();
		current = start;
		int min = (int) (((MainUi) robot.getUi()).getSpinnerMin().getValue())*60*1000;
		int sec = (int) (((MainUi) robot.getUi()).getSpinnerSec().getValue())*1000;
		
		while(robot.next() != null && robot.isRunAction() && ((min==0 && sec==0) || (current - start <= min + sec))) {
			current = System.currentTimeMillis();
		};
		
		robot.stopRun();
	}
}
