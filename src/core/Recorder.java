package core;

import java.awt.MouseInfo;
import java.awt.Point;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseListener;
import org.jnativehook.mouse.NativeMouseWheelEvent;
import org.jnativehook.mouse.NativeMouseWheelListener;

import Utility.MappingKeyCode;
import action.MoveMouse;
import action.PressKey;
import action.PressMouse;
import action.ReleaseKey;
import action.ReleaseMouse;
import action.WheelMouse;

public class Recorder implements Runnable, NativeMouseListener, NativeMouseWheelListener, NativeKeyListener {

    private static final Logger log4j = LogManager.getLogger(Constants.class);
    private SundayRobot robot;
    private boolean compress = true;

    public Recorder(SundayRobot robot) {
	this.robot = robot;

	try {
	    GlobalScreen.registerNativeHook();
	    java.util.logging.Logger logger = java.util.logging.Logger
		    .getLogger(GlobalScreen.class.getPackage().getName());
	    logger.setLevel(Level.WARNING);
	} catch (NativeHookException ex) {
	    ex.printStackTrace();
	    log4j.error(Constants.ERROR.ROBOT_NO_DEVICE);
	}

	GlobalScreen.addNativeMouseListener(this);
	GlobalScreen.addNativeKeyListener(this);
	GlobalScreen.addNativeMouseWheelListener(this);
    }

    @Override
    public void run() {

	double xOld = -1, yOld = -1;
	long delay = 0;

	while (robot.isRunRecord()) {
	    Point p = MouseInfo.getPointerInfo().getLocation();

	    if (xOld == p.getX() && yOld == p.getY() && compress) {
		delay++;
		sleep();
		continue;
	    }

	    xOld = p.getX();
	    yOld = p.getY();

	    Action action = new Action(new MoveMouse());
	    action.getParamsMap().put("xPosition", xOld);
	    action.getParamsMap().put("yPosition", yOld);

	    if (delay > 0) {
		Action actionPrev = robot.getLastActionType(MoveMouse.class, robot.sizeAction(), false);
		actionPrev.setTimeAfter(Constants.SETTINGS.INTERVAL * delay);
	    }

	    action.setTimeAfter(Constants.SETTINGS.INTERVAL);
	    delay = 0;
	    compress = true;

	    robot.addAction(action);

	    sleep();
	}

	GlobalScreen.removeNativeMouseListener(this);
	GlobalScreen.removeNativeKeyListener(this);
	GlobalScreen.removeNativeMouseWheelListener(this);

	try {
	    GlobalScreen.unregisterNativeHook();
	} catch (NativeHookException e) {
	    e.printStackTrace();
	    log4j.error(Constants.ERROR.ROBOT_NO_DEVICE);
	}
    }

    private void sleep() {
	try {
	    Thread.sleep(Constants.SETTINGS.INTERVAL);
	} catch (InterruptedException e) {
	    e.printStackTrace();
	    log4j.error(Constants.ERROR.INTERRUPT_RECORD);
	}
    }

    @Override
    public void nativeMouseClicked(NativeMouseEvent event) {
    }

    @Override
    public void nativeMousePressed(NativeMouseEvent event) {
	Action action = new Action(new PressMouse());
	action.getParamsMap().put(PressMouse.xPosition, event.getX());
	action.getParamsMap().put(PressMouse.yPosition, event.getY());

	robot.addAction(action);
    }

    @Override
    public void nativeMouseReleased(NativeMouseEvent event) {
	Action action = new Action(new ReleaseMouse());
	action.getParamsMap().put(ReleaseMouse.xPosition, event.getX());
	action.getParamsMap().put(ReleaseMouse.yPosition, event.getY());

	robot.addAction(action);
    }

    private Set<Integer> keyPressed = new HashSet<>();

    @Override
    public void nativeKeyPressed(NativeKeyEvent event) {
	Action action = new Action(new PressKey());
	action.getParamsMap().put(PressKey.key, event.getKeyCode());
	MappingKeyCode.mapKey(event);

	keyPressed.add(event.getKeyCode());

	compress = false;
	robot.addAction(action);
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent event) {
	Action action = new Action(new ReleaseKey());
	action.getParamsMap().put(ReleaseKey.key, event.getKeyCode());
	keyPressed.remove(event.getKeyCode());

	compress = false;
	robot.addAction(action);
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent event) {
    }

    @Override
    public void nativeMouseWheelMoved(NativeMouseWheelEvent nativeEvent) {
	Action action = new Action(new WheelMouse());
	action.getParamsMap().put(WheelMouse.wheel, nativeEvent.getWheelRotation());
	compress = false;
	robot.addAction(action);
    }
}
