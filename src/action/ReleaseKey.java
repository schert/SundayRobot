package action;

import java.io.Serializable;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import Utility.MappingKeyCode;
import core.AbstractAction;
import core.Action;
import core.Constants;
import core.SundayRobot;

public class ReleaseKey extends AbstractAction implements Serializable {

	public static String key = "key";
	private static final long serialVersionUID = 1L;
	private static final Logger log4j = LogManager.getLogger(ReleaseKey.class);
	
	@Override
	public void execute(SundayRobot robot, Action action) {
		int keyRaw = (int) action.getParamsMap().get(key);
		
		try {
			robot.getRobot().keyRelease(MappingKeyCode.getEvent(keyRaw));
		} catch (Exception e) {
			log4j.error(Constants.ERROR.INVALID_KEY+ MappingKeyCode.getEvent(keyRaw));
		}
	}
}