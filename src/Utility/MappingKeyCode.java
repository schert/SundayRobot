package Utility;

import java.awt.event.KeyEvent;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jnativehook.keyboard.NativeKeyEvent;

import core.Constants;

public class MappingKeyCode {
	private static final Logger log4j = LogManager.getLogger(MappingKeyCode.class);
	public static Map<Integer, Integer> key = new HashMap<>();
	
	public static void mapKey(NativeKeyEvent event) {
		
		if(key.containsKey(event.getKeyCode()))
			return;
			
 		String keyStCode = NativeKeyEvent.getKeyText(event.getKeyCode());
		keyStCode = keyStCode.replaceAll(" ", "_");
		keyStCode = keyStCode.replaceAll("Ctrl", "CONTROL");
		keyStCode = keyStCode.replaceAll("Backspace", "BACK_SPACE");
		
		try {
			Field field = KeyEvent.class.getDeclaredField("VK_"+(keyStCode.toUpperCase()));
			int value = field.getInt(null);
			addKey(event.getKeyCode(), value);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			addKey(event.getKeyCode(), event.getRawCode());
			log4j.error(Constants.ERROR.INVALID_KEY+ event.getKeyCode() + " - " + keyStCode);
		}
	}
	
	public static void addKey(Integer keyCode, Integer ev) {
		key.put(keyCode, ev);
	}
	
	public static Integer getEvent(Integer keyCode) {
		return key.get(keyCode);
	}
}
