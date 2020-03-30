package main;

import java.io.File;

import core.SundayRobot;
import core.ui.MainUi;

public class Main {

	public static void main(String[] args) {
		SundayRobot robot = new SundayRobot();
		robot.setUi(new MainUi(robot));
		
		if(args.length > 0) {
			String fileName = args[0];
			robot.restoreActions(new File(fileName));
		}
	}
	
}