package core;

import java.awt.AWTException;
import java.awt.Robot;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.NoSuchElementException;

import javax.swing.JFrame;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import core.ui.MainUi;

public class SundayRobot {
	
	private static final Logger log4j = LogManager.getLogger(SundayRobot.class);
	private Robot robot;
	private LinkedList<Action> actions = new LinkedList<>();
	private int current = 0;
	private Thread thRecord, thRunAction;
	private boolean runRecord = false;
	private boolean runAction = false;
	private JFrame ui;
	private boolean loop = false;
	
	public SundayRobot() {
		try {
			Constants.initConstants();
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
			log4j.error(Constants.ERROR.ROBOT_INSTANCE);
		}	
	}
	
	public void start() {
		if(!runAction) {
			runAction = true;
			thRunAction = new Thread(new Executer(this));
			thRunAction.start();
		} else
			log4j.error(Constants.WARNING.ACTION_ALREADY_START);
	}
	
	public void record() {
		if(!runRecord) {
			runRecord = true;
			thRecord = new Thread(new Recorder(this));
			thRecord.start();
		} else
			log4j.error(Constants.WARNING.RECORD_ALREADY_START);
	}
	
	public void stopRun() {
		runAction = false;
		current = 0;
		((MainUi) ui).getButtonPlay().setText(Constants.UI.PLAY);
	}
	
	public void stopRecord() {
		runRecord = false;
	}
	
	public Action getAction(int n) {
		return actions.get(n);
	}
	
	public void addAction(Action action) {
		actions.add(action);
	}
	
	public void removeAction(Object obj) {
		actions.remove(obj);
	}
	
	public Action getLastActionType(Class<?> actionClass, int position, boolean avanti) {
		ListIterator<Action> iterator = actions.listIterator(position);
		Action action = null;
		
		try {
			while((avanti && (action = iterator.next()) != null) || (!avanti && ((action = iterator.previous()) != null))) {
				if(action.getClassAction().getClass().equals(actionClass))
					break;
			}
		}
		catch (NoSuchElementException e) {
			return null;
		}

		return action;
	}
	
	public int sizeAction() {
		return actions.size();
	}
	
	public void setLoop(boolean loop) {
		this.loop = loop;
	}
	
	public void clear() {
		actions.clear();
	}
	
	protected Action next() {
		
		if(actions.size() <= 0)
			return null;
		
		if(current >= actions.size()) {
			if(!loop)
				return null;
			else
				current = 0;
		}
			
		
		Action action = actions.get(current);
		current++;
		
		try {
			Thread.sleep(action.getTimeBefore());
		} catch (InterruptedException e) {
			e.printStackTrace();
			log4j.error(Constants.ERROR.INTERRUPT_ACTION);
		}
		
		action.getClassAction().execute(this,action);
		
		try {
			Thread.sleep(action.getTimeAfter());
		} catch (InterruptedException e) {
			e.printStackTrace();
			log4j.error(Constants.ERROR.INTERRUPT_ACTION);
		}
			
		return action;
	}

	public boolean isRunRecord() {
		return runRecord;
	}

	public boolean isRunAction() {
		return runAction;
	}

	public Robot getRobot() {
		return robot;
	}

	public JFrame getUi() {
		return ui;
	}

	public void setUi(JFrame ui) {
		this.ui = ui;
	}
	
	public void storeActions(File file) {
		try {
			FileOutputStream out = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(out);
			oos.writeObject(actions);
			oos.flush();
			oos.close();
		}
		catch(IOException e) {
			e.printStackTrace();
			log4j.error(Constants.ERROR.FILE_WRITE_ERROR);
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public void restoreActions(File file) {
		try {
			FileInputStream in = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(in);
			actions = (LinkedList<Action>) (ois.readObject());
			ois.close();
		}
		catch(IOException | ClassNotFoundException e) {
			e.printStackTrace();
			log4j.error(Constants.ERROR.FILE_READ_ERROR);
		}
		
	}
}
